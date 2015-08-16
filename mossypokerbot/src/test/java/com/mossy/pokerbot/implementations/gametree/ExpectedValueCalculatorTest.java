package com.mossy.pokerbot.implementations.gametree;

import com.google.common.collect.ImmutableList;
import com.mossy.pokerbot.ChipStack;
import com.mossy.pokerbot.gametree.*;
import com.mossy.pokerbot.interfaces.player.IPlayerState;
import com.mossy.pokerbot.interfaces.state.IGameState;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

/**
 * Created by willrubens on 15/06/15.
 */
public class ExpectedValueCalculatorTest {

    public ITreeNode<IHoldemTreeData> buildTree() {

        IPlayerState player1 = mock(IPlayerState.class);
        IPlayerState player2 = mock(IPlayerState.class);
        IPlayerState player3 = mock(IPlayerState.class);
        IPlayerState player4 = mock(IPlayerState.class);

        stub(player1.id()).toReturn(0);
        stub(player2.id()).toReturn(0);
        stub(player3.id()).toReturn(0);
        stub(player4.id()).toReturn(0);

        stub(player1.bank()).toReturn(ChipStack.of(1));
        stub(player2.bank()).toReturn(ChipStack.of(2));
        stub(player3.bank()).toReturn(ChipStack.of(3));
        stub(player4.bank()).toReturn(ChipStack.of(4));

        IGameState grandChildState1 = mock(IGameState.class);
        IGameState grandChildState2 = mock(IGameState.class);
        IGameState grandChildState3 = mock(IGameState.class);
        IGameState grandChildState4 = mock(IGameState.class);

        stub(grandChildState1.playerStates()).toReturn(ImmutableList.of(player1));
        stub(grandChildState2.playerStates()).toReturn(ImmutableList.of(player2));
        stub(grandChildState3.playerStates()).toReturn(ImmutableList.of(player3));
        stub(grandChildState4.playerStates()).toReturn(ImmutableList.of(player4));

        MutableTreeNode<IHoldemTreeData> root = new MutableTreeNode<>(new HoldemTreeData(null, 1.0));
        MutableTreeNode<IHoldemTreeData> child1 = new MutableTreeNode<>(new HoldemTreeData(null, 0.5));
        MutableTreeNode<IHoldemTreeData> child2 = new MutableTreeNode<>(new HoldemTreeData(null, 0.5));
        MutableTreeNode<IHoldemTreeData> grandChild1 = new MutableTreeNode<>(new HoldemTreeData(grandChildState1, 0.1));
        MutableTreeNode<IHoldemTreeData> grandChild2 = new MutableTreeNode<>(new HoldemTreeData(grandChildState2, 0.9));
        MutableTreeNode<IHoldemTreeData> grandChild3 = new MutableTreeNode<>(new HoldemTreeData(grandChildState3, 0.2));
        MutableTreeNode<IHoldemTreeData> grandChild4 = new MutableTreeNode<>(new HoldemTreeData(grandChildState4, 0.8));

        child1.addChild(grandChild1);
        child1.addChild(grandChild2);
        child2.addChild(grandChild3);
        child2.addChild(grandChild4);

        root.addChild(child1);
        root.addChild(child2);

        return root;
    }

    @Test
    public void expectedValueCalculatorAndDummyTree_calculateExpectedValye_givesTheCorrectValue() {

        IPlayerState currentPlayer = mock(IPlayerState.class);
        stub(currentPlayer.bank()).toReturn(ChipStack.NO_CHIPS);
        stub(currentPlayer.id()).toReturn(0);

        ExpectedValueCalculator evCalc = new ExpectedValueCalculator();

        ChipStack ev = evCalc.calculateExpectedValue(currentPlayer, buildTree());

        ChipStack expected = (ChipStack.of(1).multiply(0.1).add( ChipStack.of(2).multiply(0.9) )).multiply(0.5)
                .add(ChipStack.of(3).multiply(0.2).add(ChipStack.of(4).multiply(0.8)).multiply(0.5));

        assertEquals(ev, expected);
    }
}
