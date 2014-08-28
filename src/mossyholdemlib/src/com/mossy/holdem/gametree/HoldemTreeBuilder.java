package com.mossy.holdem.gametree;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.interfaces.state.IGameState;
import com.mossy.holdem.interfaces.state.IGameStateFactory;

/**
 * Created by williamrubens on 09/08/2014.
 */

public class HoldemTreeBuilder
{
    IGameStateFactory stateFactory;


    ITreeNode<IHoldemTreeData> buildTree(IGameState initialState)
    {
        return recursiveBuildTree(initialState);
    }


    ITreeNode<IHoldemTreeData> recursiveBuildTree(IGameState parentState)
    {

        ImmutableList<Action> possibleActions = parentState.possibleActions();

        ImmutableList.Builder<ITreeNode<IHoldemTreeData> > listBuilder = ImmutableList.builder();
        for(Action a : possibleActions)
        {

            IGameState childState = stateFactory.NextState(parentState, a);

            listBuilder.add(recursiveBuildTree(childState));
        }

      //  IHoldemTreeData data =

        //ITreeNode<IHoldemTreeData> rootNode = new TreeNode<IHoldemTreeData>(, listBuilder.build());

        return null;
    }


}

