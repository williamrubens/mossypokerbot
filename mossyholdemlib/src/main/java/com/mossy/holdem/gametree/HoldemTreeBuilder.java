package com.mossy.holdem.gametree;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.mossy.holdem.Action;
import com.mossy.holdem.interfaces.IActionBuilder;
import com.mossy.holdem.interfaces.state.IActionProbabilityCalculator;
import com.mossy.holdem.interfaces.state.IGameState;
import com.mossy.holdem.interfaces.state.IGameStateFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

/**
 * Created by williamrubens on 09/08/2014.
 */

public class HoldemTreeBuilder implements IHoldemTreeBuilder {
    IGameStateFactory stateFactory;
    IActionBuilder actionBuilder;
    IActionProbabilityCalculator probCalc;
    PrintWriter output = null;
    boolean outputActionHistory;
    Deque<Action> actionHistory = new ArrayDeque<>();

    public HoldemTreeBuilder(IGameStateFactory stateFactory, IActionBuilder actionBuilder, IActionProbabilityCalculator probCalc, boolean outputActionHistory) {
        this.stateFactory = stateFactory;
        this.actionBuilder = actionBuilder;
        this.probCalc = probCalc;
        this.outputActionHistory = outputActionHistory;

        try {
            output = new PrintWriter(new BufferedWriter(new FileWriter("treeoutput.out")));
        }
        catch (IOException e ) {
            System.out.println("Cannot open file treeoutput.out");
            System.out.println(e);
            this.outputActionHistory = false;
        }
    }
    @Inject
    public HoldemTreeBuilder(IGameStateFactory stateFactory, IActionBuilder actionBuilder, IActionProbabilityCalculator probCalc) {
        this(stateFactory, actionBuilder, probCalc, false);
    }

    @Override
    public ITreeNode<IHoldemTreeData> buildTree(IGameState initialState)
    {
        MutableTreeNode<IHoldemTreeData> root = new MutableTreeNode<>(new HoldemTreeData(initialState, 1.0));

        recursiveBuildNode(root, 0);

        if(this.outputActionHistory && this.output != null) {
            output.flush();
            output.close();
        }

        return root;
    }

    void recursiveBuildNode(MutableTreeNode<IHoldemTreeData> parentNode, int level)
    {

        // first build the children, then the node
        if(level > 100 ) {
            int a = level;
        }
        level++;

        actionHistory.addLast(parentNode.data().state().lastAction());

        IGameState parentState = parentNode.data().state();
        // should work for both dealer and player actions
        ImmutableList<Action> actions = actionBuilder.buildAllChildActions(parentState);

        if(actions.size() == 0) {
            printActionHistory();
        }

        ImmutableMap<Action, Double> actionProbTuples = probCalc.calculateProbability(parentState, actions);

        for(Action a : actions) {
            IGameState childState = stateFactory.buildNextState(parentState, a);
            Double actionProb = actionProbTuples.get(a);
            MutableTreeNode<IHoldemTreeData> childNode = new MutableTreeNode<>(new HoldemTreeData(childState, actionProb));

            parentNode.addChild(childNode);

            recursiveBuildNode(childNode, level);

        }

        actionHistory.removeLast();



    }

    private void printActionHistory() {
        if(!this.outputActionHistory && this.output == null) {
            return;
        }

        String outputLine = new String();
        for(Action action : actionHistory) {
            outputLine += action.toString() + "/";
        }

        outputLine += "\n";

        output.write(outputLine);
    }


//    public ITreeNode<IHoldemTreeData> buildTree(IGameState initialState) throws Exception
//    {
//        return recursiveDepthFirstBuildNode(initialState, 0);
//    }
//
//    ITreeNode<IHoldemTreeData> recursiveDepthFirstBuildNode(IGameState parentState, int level) throws Exception
//    {
//
//        // first build the children, then the node
//        if(level > 100 ) {
//         int a = level;
//        }
//        // should work for both dealer and player actions
//        ImmutableList<Action> actions = actionBuilder.buildAllChildActions(parentState);
//
//        ImmutableList.Builder<ITreeNode<IHoldemTreeData> > listBuilder = ImmutableList.builder();
//
//        for(Action a : actions)
//        {
//            IGameState childState = stateFactory.buildNextState(parentState, a);
//
//
//            ITreeNode<IHoldemTreeData> childNode = recursiveDepthFirstBuildNode(childState, level +1);
//
//            listBuilder.add(childNode);
//
//        }
//
//        return new TreeNode<>(new HoldemTreeData(parentState), listBuilder.build());
//
//
//    }



}

