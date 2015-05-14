package com.mossy.holdem;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.gametree.HoldemTreeBuilder;
import com.mossy.holdem.gametree.IHoldemTreeData;
import com.mossy.holdem.gametree.ITreeNode;
import com.mossy.holdem.implementations.FixedLimitActionBuilder;
import com.mossy.holdem.implementations.StandardDeckFactory;
import com.mossy.holdem.implementations.state.*;
import com.mossy.holdem.interfaces.IDeck;
import com.mossy.holdem.interfaces.state.IGameState;
import com.mossy.holdem.interfaces.state.IPlayerState;
import com.mossy.holdem.interfaces.state.IPlayerInfoFactory;
import org.testng.annotations.Test;

/**
 * Created by willrubens on 12/05/15.
 */
public class FLTreeBuilderTest {
    @Test
    public void buildTree () throws Exception{

        IDeck deck = new StandardDeckFactory().build();
        FixedLimitActionBuilder actionBuilder = new FixedLimitActionBuilder(deck);
        IPlayerInfoFactory playerFactory = new PlayerInfoFactory();
        FLStateFactory stateFactory = new FLStateFactory(playerFactory, ChipStack.TWO_CHIPS, ChipStack.as(4)) ;


        HoldemTreeBuilder treeBuilder = new HoldemTreeBuilder(stateFactory, actionBuilder);

        ImmutableList<IPlayerState> players = ImmutableList.of(new PlayerState(0, ChipStack.as(10), ChipStack.NO_CHIPS),
                new PlayerState(1, ChipStack.as(10), ChipStack.NO_CHIPS));

        IGameState initialState = stateFactory.buildNewState(players, 0, 3);

        ITreeNode<IHoldemTreeData > papaNode = treeBuilder.buildTree(initialState);

        papaNode.children();



    }
}
