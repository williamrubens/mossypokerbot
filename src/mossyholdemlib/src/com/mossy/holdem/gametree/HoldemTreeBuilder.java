package com.mossy.holdem.gametree;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.interfaces.IGameState;
import com.mossy.holdem.interfaces.IGameStateFactory;

import javax.swing.plaf.basic.BasicSliderUI;
import java.util.ArrayList;

/**
 * Created by williamrubens on 09/08/2014.
 */

/*
public class HoldemTreeBuilder
{

    IHoldemTreeDataFactory treeDataFactory;
    INextActionFactory actionFactory;
    IGameStateFactory stateFactory;

    HoldemTreeBuilder(IHoldemTreeDataFactory treeDataFactory)
    {
        this.treeDataFactory = treeDataFactory;
    }

    ITreeNode<IHoldemTreeData> buildTree(IGameState initialState)
    {
        return recursiveBuildTree(initialState);
    }


    ITreeNode<IHoldemTreeData> recursiveBuildTree(IGameState parentState)
    {

        ArrayList<Action> possibleActions = actionFactory.buildNextActions(parentState);

        ImmutableList.Builder<IHoldemTreeData> listBuilder = ImmutableList.builder();
        for(Action a : possibleActions)
        {

            IGameState childState = stateFactory.NextState(parentState, a);

            listBuilder.add(recursiveBuildTree(childState));
        }

        IHoldemTreeData data =

        TreeNode<IHoldemTreeData> rootNode = new TreeNode<IHoldemTreeData>(, listBuilder.build());




    }


}

*/
