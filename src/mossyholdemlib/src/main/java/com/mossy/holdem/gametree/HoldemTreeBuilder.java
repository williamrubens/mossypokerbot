package com.mossy.holdem.gametree;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.interfaces.IActionBuilder;
import com.mossy.holdem.interfaces.state.IGameState;
import com.mossy.holdem.interfaces.state.IGameStateFactory;

/**
 * Created by williamrubens on 09/08/2014.
 */

public class HoldemTreeBuilder
{
    IGameStateFactory stateFactory;
    IActionBuilder actionBuilder;

    ITreeNode<IHoldemTreeData> buildTree(IGameState initialState) throws Exception
    {
        return recursiveBuildNode(initialState);
    }


    ITreeNode<IHoldemTreeData> recursiveBuildNode(IGameState parentState) throws Exception
    {

        // first build the children, then the node

        // should work for both dealer and player actions
        ImmutableList<Action> actions = actionBuilder.buildAllChildActions(parentState);

        ImmutableList.Builder<ITreeNode<IHoldemTreeData> > listBuilder = ImmutableList.builder();
        for(Action a : actions)
        {
            IGameState childState = stateFactory.NextState(parentState, a);

            ITreeNode<IHoldemTreeData> childNode = recursiveBuildNode(childState);

            listBuilder.add(childNode);

        }

        return new TreeNode<>(new HoldemTreeData(parentState), listBuilder.build());


    }


}

