package com.mossy.holdem.implementations.state;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedSet;
import com.mossy.holdem.*;
import com.mossy.holdem.gametree.IHoldemTreeData;
import com.mossy.holdem.gametree.ITreeNode;
import com.mossy.holdem.implementations.HandFactory;
import com.mossy.holdem.interfaces.IDeck;
import com.mossy.holdem.interfaces.IDeckFactory;
import com.mossy.holdem.interfaces.IHandFactory;
import com.mossy.holdem.interfaces.IHandStrengthCalculator;
import com.mossy.holdem.interfaces.player.IMutablePlayerState;
import com.mossy.holdem.interfaces.player.IPlayerModel;
import com.mossy.holdem.interfaces.player.IPlayerStatisticsHolder;
import com.mossy.holdem.interfaces.state.IGameState;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

/**
 * Created by willrubens on 30/06/15.
 */
public class MyWinActionProbabilityCalculatorTest {

    public MyWinActionProbabilityCalculator buildProbCalc(ImmutableList<Action> actions ) {

        HoleCards myHoleCards = HoleCards.from(Card.ACE_CLUBS, Card.ACE_DIAMONDS);
        IHandStrengthCalculator mockHSCalc = mock(IHandStrengthCalculator.class);
        IMutablePlayerState mockMe = mock(IMutablePlayerState.class);
        IPlayerStatisticsHolder mockStatsHolder = mock(IPlayerStatisticsHolder.class);
        IPlayerModel mockPlayerModel = mock(IPlayerModel.class);
        //IHandFactory mockHandFactory = mock(IHandFactory.class);

        IHandFactory mockHandFactory = new HandFactory();

        IDeckFactory mockDeckFactory = mock(IDeckFactory.class);
        IDeck mockDeck = mock(IDeck.class);
        ImmutableSortedSet<Card> undealtCards = ImmutableSortedSet
                .orderedBy(new Card.SuitThenRankComparer()).add(Card.ACE_CLUBS).add(Card.ACE_DIAMONDS)
                .add(Card.ACE_HEARTS).add(Card.ACE_SPADES).build();

        when(mockMe.holeCards()).thenReturn(myHoleCards);
        when(mockMe.id()).thenReturn(0);
        when(mockDeckFactory.build()).thenReturn(mockDeck);
        when(mockDeck.undealtCards()).thenReturn(undealtCards);
        when(mockHSCalc.calculateHandStrength(isA(HoleCards.class), isA(ImmutableList.class))).thenReturn(Double.valueOf(1081d));


        return new MyWinActionProbabilityCalculator(mockMe, mockHSCalc, mockDeckFactory, mockHandFactory, mockPlayerModel);

    }

    // TODO: add unit tests to fix MyWinProbailityCalculator for Turn and river

    @DataProvider(name = "createMockState")
    public Object[][] createMockState() {
        return new Object[][] {
                {Street.SHOWDOWN, 4.0 * 3.0 / 2.0 , ImmutableList.of(Card.ACE_SPADES, Card.KING_CLUBS, Card.ACE_HEARTS), Action.Factory.dealFlopAction()},
                {Street.SHOWDOWN, 4.0 * 46.0 / 2.0, ImmutableList.of(Card.ACE_SPADES, Card.KING_CLUBS, Card.ACE_HEARTS, Card.KING_DIAMONDS), Action.Factory.dealTurnAction(Card.KING_DIAMONDS)},
                {Street.SHOWDOWN, 1.0 * 47.0 * 46.0 / 2.0, ImmutableList.of(Card.ACE_SPADES, Card.KING_CLUBS, Card.ACE_HEARTS, Card.KING_DIAMONDS, Card.KING_HEARTS), Action.Factory.dealRiverAction(Card.KING_HEARTS)}
        };
    }



    public IGameState buildState(Street street, ImmutableList<Card> communityCards, Action lastAction) {
        IGameState mockState = mock(IGameState.class);

        when(mockState.street()).thenReturn(street);
        when(mockState.communityCards()).thenReturn(communityCards);

        return mockState;
    }



    @Test(dataProvider = "createMockState")
    public void WithTwoActions_MyWindActionProbabilityCalculator_ShouldReturnTheRightProbabilities(Street street, double expectedProb,  ImmutableList<Card> communityCards, Action lastAction)  {
        // given
        ImmutableList<Action> actions = ImmutableList.of(Action.Factory.winAction(0), Action.Factory.winAction(1));
        MyWinActionProbabilityCalculator probCalc = buildProbCalc(actions);
        IGameState mockState = buildState(street, communityCards, lastAction);

        //when
        ImmutableMap<Action, Double> probs = probCalc.calculateProbability(mockState, actions);

        //then
        Double prob0 = probs.get(actions.get(0));
        Double prob1 = probs.get(actions.get(1));

        assertEquals(prob0.doubleValue(), expectedProb, 0.1);
        //assertEquals(prob1.doubleValue(), -5.0d, 0.1);

    }

    @Test(dataProvider = "mockStateData")
    public void WithFiveActions_MyWindActionProbabilityCalculator_ShouldReturnTheRightProbabilities(Street street, ImmutableList<Card> communityCards, Action lastAction)  {

        // given
        ImmutableList<Action> actions = ImmutableList.of(Action.Factory.winAction(0), Action.Factory.winAction(1), Action.Factory.winAction(2), Action.Factory.winAction(3), Action.Factory.winAction(4));
        MyWinActionProbabilityCalculator probCalc = buildProbCalc(actions);
        IGameState mockState = buildState(street, communityCards, lastAction);

        // when
        ImmutableMap<Action, Double> probs = probCalc.calculateProbability(mockState, actions);

        // then
        Double prob0 = probs.get(actions.get(0));
        Double prob1 = probs.get(actions.get(1));
        Double prob2 = probs.get(actions.get(2));
        Double prob3 = probs.get(actions.get(3));
        Double prob4 = probs.get(actions.get(4));

        assertEquals(prob0.doubleValue(), 10.0d, 0.1);
        assertEquals(prob1.doubleValue(), -9.0d/4.0d, 0.1);
        assertEquals(prob2.doubleValue(), -9.0d/4.0d, 0.1);
        assertEquals(prob3.doubleValue(), -9.0d/4.0d, 0.1);
        assertEquals(prob4.doubleValue(), -9.0d/4.0d, 0.1);
    }


}
