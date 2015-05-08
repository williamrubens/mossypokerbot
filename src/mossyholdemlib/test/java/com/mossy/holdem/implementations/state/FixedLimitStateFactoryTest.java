package com.mossy.holdem.implementations.state;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.GameStage;
import com.mossy.holdem.interfaces.state.IGameState;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;

//import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEqualsNoOrder;

//import static org.junit.Assert.assertEquals;


import com.mossy.holdem.interfaces.state.IPlayerInfo;
import com.mossy.holdem.interfaces.state.IPlayerInfoFactory;
import org.testng.annotations.*;

import java.util.ArrayList;


/**
 * Created by williamrubens on 09/08/2014.
 */
//@RunWith(Parameterized.class)
public class FixedLimitStateFactoryTest
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
//    public FixedLimitStateFactoryTest(int numPlayers, int dealerPosition, Action[] actions)
//    {
//        for(int p = 0; p < numPlayers; p++)
//        {
//            IPlayerInfo playerState = mock(IPlayerInfo.class);
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

    public FixedLimitStateFactoryTest()
    {

    }

    @DataProvider(name = "firstActionPreFlop")
    public Object[][] createData1() {
        return new Object[][] {
                {2, 0, 1, Action.Factory.callAction(ChipStack.ONE_CHIP)},
                {3, 0, 0, Action.Factory.callAction(ChipStack.TWO_CHIPS)},
                {3, 1, 1, Action.Factory.callAction(ChipStack.TWO_CHIPS)},
                {2, 1, 0, Action.Factory.callAction(ChipStack.ONE_CHIP)},
                {3, 2, 2, Action.Factory.callAction(ChipStack.TWO_CHIPS)},
                {2, 1, 0, Action.Factory.betAction(lowerLimit)},
                {2, 0, 1, Action.Factory.betAction(lowerLimit)},
                {3, 0, 0, Action.Factory.betAction(lowerLimit)},
                {3, 1, 1, Action.Factory.betAction(lowerLimit)},
                {3, 2, 2, Action.Factory.betAction(lowerLimit)},
                {2, 0, 1, Action.Factory.foldAction()},
                {3, 0, 0, Action.Factory.foldAction()},
                {3, 1, 1, Action.Factory.foldAction()},
                {2, 1, 0, Action.Factory.foldAction()},
                {3, 2, 2, Action.Factory.foldAction()},
        };
    }



    @Test(dataProvider = "firstActionPreFlop")
    public void NewPreFlopState_nextAction_AdjustsPlayerPot(int numPlayers, int dealerPosition, int nextToPlay, Action action) throws Exception
    {
        //given
        int smallBlindPos = (dealerPosition + 1) % numPlayers;
        int bigBlindPos = (dealerPosition + 2) % numPlayers;

        ArrayList<IPlayerInfo> players = new ArrayList<IPlayerInfo>();

        for(int p = 0; p < numPlayers; p++)
        {
            IPlayerInfo playerState = mock(IPlayerInfo.class);
            stub(playerState.pot()).toReturn(ChipStack.NO_CHIPS);
            players.add(playerState);
        }

        IPlayerInfoFactory playerFactory = mock(IPlayerInfoFactory.class);
        IGameState gameState = mock(FixedLimitState.class);
        IPlayerInfo nextPlayer = mock(IPlayerInfo.class);

        FLStateFactory stateFactory = new FLStateFactory(playerFactory, lowerLimit, higherLimit);

        stub(gameState.stage()).toReturn(GameStage.PRE_FLOP);
        stub(gameState.getNextPlayer()).toReturn(nextPlayer);
        stub(gameState.playerStates()).toReturn(ImmutableList.copyOf(players));


        // when
        stateFactory.buildNextState(gameState, action);

        // then
        verify(playerFactory).updatePlayer(nextPlayer, action);
    }

/*

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


        // post small blind
        IPlayerInfo nextPlayer = players.get(nextToPlay);

        stub(players.get(smallBlindPos).pot()).toReturn(smallBlind);
        stub(players.get(bigBlindPos).pot()).toReturn(bigBlind);

        stub(nextPlayer.play(amountToPlay)).toReturn(mock(IPlayerInfo.class));
        stub(nextPlayer.fold()).toReturn(mock(IPlayerInfo.class));

        IPlayerInfoFactory playerFactory = mock(IPlayerInfoFactory.class);

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

        ArrayList<IPlayerInfo> players = new ArrayList<IPlayerInfo>();

        for(int p = 0; p < numPlayers; p++)
        {
            IPlayerInfo playerState = mock(IPlayerInfo.class);
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
        ArrayList<IPlayerInfo> players = new ArrayList<IPlayerInfo>();

        for(int p = 0; p < 4; p++)
        {
            IPlayerInfo playerState = mock(IPlayerInfo.class);
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
        ArrayList<IPlayerInfo> players = new ArrayList<IPlayerInfo>();

        for(int p = 0; p < numPlayers; p++)
        {
            IPlayerInfo playerState = mock(IPlayerInfo.class);
            stub(playerState.pot()).toReturn(ChipStack.NO_CHIPS);
            players.add(playerState);
        }
        IPlayerInfo nextPlayer = players.get(nextToPlay);
        stub(nextPlayer.play(amountToPlay)).toReturn(mock(IPlayerInfo.class));
        stub(nextPlayer.fold()).toReturn(mock(IPlayerInfo.class));

        IPotManager currentPot = new FLPotManager(lowerLimit, higherLimit, ImmutableList.copyOf(players), GameStage.FLOP, dealerPosition, nextToPlay );

        // when
        IPotManager nextPot = currentPot.nextAction(action);

        if(action.type() == Action.ActionType.CHECK)
        {
            //asssertThat
        }
    }
*/
/*
    @Test
    public void NewPreFlopState_nextAction_AdjustsPlayerPot() throws Exception
    {
        //given

        // post small blind
        IPlayerInfo nextPlayer = players.get(nextToPlay);

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

        stub(nextPlayer.play(amountToPlay)).toReturn(mock(IPlayerInfo.class));

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
        IPlayerInfo nextPlayer = players.get(nextToPlay);
        IPlayerInfo nextPlayer2 = players.get((nextToPlay + 1) % numPlayers);

        stub(players.get(smallBlindPos).pot()).toReturn(smallBlind);
        stub(players.get(bigBlindPos).pot()).toReturn(bigBlind);
        stub(nextPlayer.play(bigBlind)).toReturn(mock(IPlayerInfo.class));
        stub(nextPlayer2.play(amountToPlayForSecondPlayer)).toReturn(mock(IPlayerInfo.class));

        IPotManager nextbet = new FLPotManager(smallBlind, bigBlind, ImmutableList.copyOf(players), dealerPosition, nextToPlay );
        nextbet.nextAction(Action.Factory.betAction(bigBlind));

        // when
        IPotManager nextGameState = nextbet.nextAction(action);

        // then
        verify(nextPlayer, times(1)).play(amountToPlayForSecondPlayer);
    }
*/



}
