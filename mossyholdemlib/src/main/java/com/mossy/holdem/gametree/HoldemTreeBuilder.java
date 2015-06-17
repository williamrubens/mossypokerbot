package com.mossy.holdem.gametree;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mossy.holdem.Action;
import com.mossy.holdem.interfaces.IActionBuilder;
import com.mossy.holdem.interfaces.IBoardCardDealer;
import com.mossy.holdem.interfaces.state.IActionProbabilityCalculator;
import com.mossy.holdem.interfaces.state.IGameState;
import com.mossy.holdem.interfaces.state.IGameStateFactory;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by williamrubens on 09/08/2014.
 */

public class HoldemTreeBuilder implements IHoldemTreeBuilder {
    IGameStateFactory stateFactory;
    IActionBuilder actionBuilder;
    IActionProbabilityCalculator probCalc;

    public HoldemTreeBuilder(IGameStateFactory stateFactory, IActionBuilder actionBuilder, IActionProbabilityCalculator probCalc) {
        this.stateFactory = stateFactory;
        this.actionBuilder = actionBuilder;
        this.probCalc = probCalc;
    }

    @Override
    public ITreeNode<IHoldemTreeData> buildTree(IGameState initialState)
    {
        MutableTreeNode<IHoldemTreeData> root = new MutableTreeNode<>(new HoldemTreeData(initialState, 1.0));

        recursiveBuildNode(root, 0);

        return root;
    }

    void recursiveBuildNode(MutableTreeNode<IHoldemTreeData> parentNode, int level)
    {

        // first build the children, then the node
        if(level > 100 ) {
            int a = level;
        }
        level++;

        IGameState parentState = parentNode.data().state();
        // should work for both dealer and player actions
        ImmutableList<Action> actions = actionBuilder.buildAllChildActions(parentState);


        ImmutableList<Action.ActionType> actionTypes = FluentIterable.from(actions).transform(action -> action.type()).toList();

        ImmutableMap<Action.ActionType, Float> actionProbTuples = probCalc.calculateProbability(parentState, actionTypes);

        for(Action a : actions) {
            IGameState childState = stateFactory.buildNextState(parentState, a);
            float actionProb = actionProbTuples.get(a.type());
            MutableTreeNode<IHoldemTreeData> childNode = new MutableTreeNode<>(new HoldemTreeData(childState, actionProb));

            parentNode.addChild(childNode);

            recursiveBuildNode(childNode, level);

        }


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

