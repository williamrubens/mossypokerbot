package com.mossy.holdem.implementations.state;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.GameStage;
import com.mossy.holdem.interfaces.state.IPotManager;
import com.mossy.holdem.interfaces.state.IPlayerState;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

//import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEqualsNoOrder;

//import static org.junit.Assert.assertEquals;


import org.testng.annotations.*;


/**
 * Created by williamrubens on 09/08/2014.
 */
//@RunWith(Parameterized.class)
public class FLPotManagerTest
{

/*
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] {
                {2, 0, new Action[] {Action.Factory.callAction()}},
                {3, 0, new Action[] {Action.Factory.callAction()}},
                {3, 1, new Action[] {Action.Factory.callAction()}},
                {2, 1, new Action[] {Action.Factory.callAction()}},
                {3, 2, new Action[] {Action.Factory.callAction()}},
                {2, 1, new Action[] {Action.Factory.betAction(ChipStack.TWO_CHIPS)}},
                {2, 0, new Action[] {Action.Factory.betAction(ChipStack.TWO_CHIPS)}},
                {3, 0, new Action[] {Action.Factory.betAction(ChipStack.TWO_CHIPS)}},
                {3, 1, new Action[] {Action.Factory.betAction(ChipStack.TWO_CHIPS)}},
                {3, 2, new Action[] {Action.Factory.betAction(ChipStack.TWO_CHIPS)}},

                };
        return Arrays.asList(data);
    }
*/


    final ChipStack lowerLimit = ChipStack.TWO_CHIPS;
    final ChipStack higherLimit = new ChipStack(4);
    final ChipStack smallBlind = lowerLimit.divide(2);
    final ChipStack bigBlind = lowerLimit;
  /*  Action[] actions;
    final int numPlayers;
    final int dealerPosition;
    final int nextToPlay;
    final int smallBlindPos;
    final int bigBlindPos;

    Action action;
*/
    //given
    //when
    //then.
//    public FLPotManagerTest(int numPlayers, int dealerPosition, Action[] actions)
//    {
//        for(int p = 0; p < numPlayers; p++)
//        {
//            IPlayerState playerState = mock(IPlayerState.class);
//            stub(playerState.pot()).toReturn(ChipStack.NO_CHIPS);
//            players.add(playerState);
//        }
//        this.actions = actions;
//        this.dealerPosition = dealerPosition;
//        this.numPlayers = numPlayers;
//        this.nextToPlay = (dealerPosition + 1 ) % numPlayers;
//
//        this.smallBlindPos = (dealerPosition + 1) % numPlayers;
//        this.bigBlindPos = (dealerPosition + 2) % numPlayers;
//
//        this.action = action;
//    }

    public FLPotManagerTest()
    {

    }

    @DataProvider(name = "firstActionPreFlop")
    public Object[][] createData1() {
        return new Object[][] {
                {2, 0, 1, Action.Factory.callAction(), ChipStack.ONE_CHIP},
                {3, 0, 0, Action.Factory.callAction(), ChipStack.TWO_CHIPS},
                {3, 1, 1, Action.Factory.callAction(), ChipStack.TWO_CHIPS},
                {2, 1, 0, Action.Factory.callAction(), ChipStack.ONE_CHIP},
                {3, 2, 2, Action.Factory.callAction(), ChipStack.TWO_CHIPS},
                {2, 1, 0, Action.Factory.betAction(), lowerLimit},
                {2, 0, 1, Action.Factory.betAction(), lowerLimit},
                {3, 0, 0, Action.Factory.betAction(), lowerLimit},
                {3, 1, 1, Action.Factory.betAction(), lowerLimit},
                {3, 2, 2, Action.Factory.betAction(), lowerLimit},
                {2, 0, 1, Action.Factory.foldAction(), ChipStack.NO_CHIPS},
                {3, 0, 0, Action.Factory.foldAction(), ChipStack.NO_CHIPS},
                {3, 1, 1, Action.Factory.foldAction(), ChipStack.NO_CHIPS},
                {2, 1, 0, Action.Factory.foldAction(), ChipStack.NO_CHIPS},
                {3, 2, 2, Action.Factory.foldAction(), ChipStack.NO_CHIPS},
        };
    }



    @Test(dataProvider = "firstActionPreFlop")
    public void NewPreFlopState_nextAction_AdjustsPlayerPot(int numPlayers, int dealerPosition, int nextToPlay, Action action, ChipStack amountToPlay) throws Exception
    {
        //given
        int smallBlindPos = (dealerPosition + 1) % numPlayers;
        int bigBlindPos = (dealerPosition + 2) % numPlayers;

        ArrayList<IPlayerState> players = new ArrayList<IPlayerState>();

        for(int p = 0; p < numPlayers; p++)
        {
            IPlayerState playerState = mock(IPlayerState.class);
            stub(playerState.pot()).toReturn(ChipStack.NO_CHIPS);
            players.add(playerState);
        }

        // post small blind
        IPlayerState nextPlayer = players.get(nextToPlay);

        stub(players.get(smallBlindPos).pot()).toReturn(smallBlind);
        stub(players.get(bigBlindPos).pot()).toReturn(bigBlind);

        stub(nextPlayer.play(amountToPlay)).toReturn(mock(IPlayerState.class));
        stub(nextPlayer.fold()).toReturn(mock(IPlayerState.class));

        IPotManager nextbet = new FLPotManager(lowerLimit, higherLimit, ImmutableList.copyOf(players), GameStage.PRE_FLOP, dealerPosition, nextToPlay );

        // when
        nextbet.nextAction(action);

        // then
        if(action.type() == Action.ActionType.FOLD)
        {
            verify(nextPlayer, times(1)).fold();
        }
        else
        {

            verify(nextPlayer, times(1)).play(amountToPlay);
        }
    }



    @DataProvider(name = "actionSequence")
    public Object[][] createActionSequenceData() {
        return new Object[][] {
                {2, 0, 1, Action.Factory.callAction(), ChipStack.ONE_CHIP},
                {3, 0, 0, Action.Factory.callAction(), ChipStack.TWO_CHIPS},
                {3, 1, 1, Action.Factory.callAction(), ChipStack.TWO_CHIPS},
                {2, 1, 0, Action.Factory.callAction(), ChipStack.ONE_CHIP},
                {3, 2, 2, Action.Factory.callAction(), ChipStack.TWO_CHIPS},
                {2, 1, 0, Action.Factory.betAction(), lowerLimit},
                {2, 0, 1, Action.Factory.betAction(), lowerLimit},
                {3, 0, 0, Action.Factory.betAction(), lowerLimit},
                {3, 1, 1, Action.Factory.betAction(), lowerLimit},
                {3, 2, 2, Action.Factory.betAction(), lowerLimit},
                {2, 0, 1, Action.Factory.foldAction(), ChipStack.NO_CHIPS},
                {3, 0, 0, Action.Factory.foldAction(), ChipStack.NO_CHIPS},
                {3, 1, 1, Action.Factory.foldAction(), ChipStack.NO_CHIPS},
                {2, 1, 0, Action.Factory.foldAction(), ChipStack.NO_CHIPS},
                {3, 2, 2, Action.Factory.foldAction(), ChipStack.NO_CHIPS},
        };
    }



    //@Test(dataProvider = "actionSequence")
    @Test
    public void actionSpecified_nextAvailableActions_currectActions() throws Exception
    {
        int numPlayers= 4;
        Action action = Action.Factory.checkAction();
        Action [] followingActions = new Action[]{Action.Factory.foldAction(), Action.Factory.checkAction(), Action.Factory.betAction()};

        int dealerPosition = 0;
        int nextToPlay = 1;
        //given
        int smallBlindPos = (dealerPosition + 1) % numPlayers;
        int bigBlindPos = (dealerPosition + 2) % numPlayers;

        ArrayList<IPlayerState> players = new ArrayList<IPlayerState>();

        for(int p = 0; p < numPlayers; p++)
        {
            IPlayerState playerState = mock(IPlayerState.class);
            stub(playerState.pot()).toReturn(ChipStack.NO_CHIPS);
            players.add(playerState);
        }

        IPotManager potManager = new FLPotManager(lowerLimit, higherLimit, ImmutableList.copyOf(players), GameStage.FLOP, dealerPosition, nextToPlay );

        // when
        ImmutableList<Action> possibleActions = potManager.possibleActions();

        // then
        assertEqualsNoOrder(possibleActions.toArray(), followingActions);

    }


    @Test(expectedExceptions = Exception.class)
    public void PostFlopStage_callAction_throwsException() throws Exception
    {
        //given
        ArrayList<IPlayerState> players = new ArrayList<IPlayerState>();

        for(int p = 0; p < 4; p++)
        {
            IPlayerState playerState = mock(IPlayerState.class);
            stub(playerState.pot()).toReturn(ChipStack.NO_CHIPS);
            players.add(playerState);
        }

        IPotManager nextbet = new FLPotManager(lowerLimit, higherLimit, ImmutableList.copyOf(players), GameStage.FLOP, 0, 1 );

        // when
        nextbet.nextAction(Action.Factory.callAction());

        // then
        //throws exception
    }

    @DataProvider(name = "firstActionPostFlop")
    public Object[][] createPostFlopData() {
        return new Object[][] {
                {2, 0, 1, Action.Factory.checkAction(), ChipStack.NO_CHIPS},
//                {3, 0, 0, Action.Factory.callAction(), ChipStack.TWO_CHIPS},
//                {3, 1, 1, Action.Factory.callAction(), ChipStack.TWO_CHIPS},
//                {2, 1, 0, Action.Factory.callAction(), ChipStack.ONE_CHIP},
//                {3, 2, 2, Action.Factory.callAction(), ChipStack.TWO_CHIPS},
//                {2, 1, 0, Action.Factory.betAction(), lowerLimit},
//                {2, 0, 1, Action.Factory.betAction(), lowerLimit},
//                {3, 0, 0, Action.Factory.betAction(), lowerLimit},
//                {3, 1, 1, Action.Factory.betAction(), lowerLimit},
//                {3, 2, 2, Action.Factory.betAction(), lowerLimit},
//                {2, 0, 1, Action.Factory.foldAction(), ChipStack.NO_CHIPS},
//                {3, 0, 0, Action.Factory.foldAction(), ChipStack.NO_CHIPS},
//                {3, 1, 1, Action.Factory.foldAction(), ChipStack.NO_CHIPS},
//                {2, 1, 0, Action.Factory.foldAction(), ChipStack.NO_CHIPS},
//                {3, 2, 2, Action.Factory.foldAction(), ChipStack.NO_CHIPS},
        };
    }

    @Test(dataProvider = "firstActionPostFlop")
    public void PostFlopStage_nextAction_AdjustsPlayerPot(int numPlayers, int dealerPosition, int nextToPlay, Action action, ChipStack amountToPlay) throws Exception
    {
        //given
        ArrayList<IPlayerState> players = new ArrayList<IPlayerState>();

        for(int p = 0; p < numPlayers; p++)
        {
            IPlayerState playerState = mock(IPlayerState.class);
            stub(playerState.pot()).toReturn(ChipStack.NO_CHIPS);
            players.add(playerState);
        }
        IPlayerState nextPlayer = players.get(nextToPlay);
        stub(nextPlayer.play(amountToPlay)).toReturn(mock(IPlayerState.class));
        stub(nextPlayer.fold()).toReturn(mock(IPlayerState.class));

        IPotManager currentPot = new FLPotManager(lowerLimit, higherLimit, ImmutableList.copyOf(players), GameStage.FLOP, dealerPosition, nextToPlay );

        // when
        IPotManager nextPot = currentPot.nextAction(action);

        if(action.type() == Action.ActionType.CHECK)
        {
            //asssertThat
        }
    }

/*
    @Test
    public void NewPreFlopState_nextAction_AdjustsPlayerPot() throws Exception
    {
        //given

        // post small blind
        IPlayerState nextPlayer = players.get(nextToPlay);

        stub(players.get(smallBlindPos).pot()).toReturn(smallBlind);
        stub(players.get(bigBlindPos).pot()).toReturn(bigBlind);

        ChipStack amountToPlay = ChipStack.NO_CHIPS;

        Action action = actions[0];

        if(action.type() == Action.ActionType.CALL)
        {
            if(nextPlayer == players.get(smallBlindPos))
            {
                amountToPlay = bigBlind.subtract(smallBlind);
            }
            else if(nextPlayer == players.get(bigBlindPos))
            {
                amountToPlay = ChipStack.NO_CHIPS;
            }
            else
            {
                amountToPlay = bigBlind;
            }
        }
        else if(action.type() == Action.ActionType.BET)
        {
            amountToPlay = action.amount();
        }
        else if(action.type() == Action.ActionType.FOLD)
        {
            amountToPlay = ChipStack.NO_CHIPS;
        }

        stub(nextPlayer.play(amountToPlay)).toReturn(mock(IPlayerState.class));

        IPotManager nextbet = new FLPotManager(smallBlind, bigBlind, ImmutableList.copyOf(players), dealerPosition, nextToPlay );

        // when
        IPotManager nextPot = nextbet.nextAction(action);

        // then
        assertThat(nextPot.)
        verify(nextPlayer, times(1)).play(amountToPlay);
    }

    /*

    @Test
    public void BetState_nextAction_AdjustsPlayerPot() throws Exception
    {
        //given
        IPlayerState nextPlayer = players.get(nextToPlay);
        IPlayerState nextPlayer2 = players.get((nextToPlay + 1) % numPlayers);

        stub(players.get(smallBlindPos).pot()).toReturn(smallBlind);
        stub(players.get(bigBlindPos).pot()).toReturn(bigBlind);
        stub(nextPlayer.play(bigBlind)).toReturn(mock(IPlayerState.class));
        stub(nextPlayer2.play(amountToPlayForSecondPlayer)).toReturn(mock(IPlayerState.class));

        IPotManager nextbet = new FLPotManager(smallBlind, bigBlind, ImmutableList.copyOf(players), dealerPosition, nextToPlay );
        nextbet.nextAction(Action.Factory.betAction(bigBlind));

        // when
        IPotManager nextGameState = nextbet.nextAction(action);

        // then
        verify(nextPlayer, times(1)).play(amountToPlayForSecondPlayer);
    }
*/



}
