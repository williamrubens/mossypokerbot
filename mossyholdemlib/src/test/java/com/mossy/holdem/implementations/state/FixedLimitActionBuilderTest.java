package com.mossy.holdem.implementations.state;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.Street;
import com.mossy.holdem.implementations.FixedLimitActionBuilder;
import com.mossy.holdem.interfaces.player.IPlayerState;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.testng.Assert.assertEqualsNoOrder;

/**
 * Created by williamrubens on 09/09/2014.
 */
public class FixedLimitActionBuilderTest
{
    private ArrayList<IPlayerState> buildPlayersFromWagers(ChipStack[] wagers, ChipStack[] banks) {
        ArrayList<IPlayerState> players = new ArrayList<>();
        ChipStack highestBet = ChipStack.NO_CHIPS;

        for(int p = 0; p < wagers.length; p++)
        {
            if(highestBet.compareTo(wagers[p]) < 0)
            {
                highestBet = wagers[p];
            }
            IPlayerState playerState = mock(IPlayerState.class);
            stub(playerState.pot()).toReturn(wagers[p]);
            stub(playerState.id()).toReturn(p);
            stub(playerState.bank()).toReturn(banks[p]);
            stub(playerState.hasChecked()).toReturn(false);
            stub(playerState.isOut()).toReturn(false);
            players.add(playerState);
            System.out.print(String.format("Wager %s\n", wagers[p].toString()));
        }
        return players;
    }

    private ArrayList<IPlayerState> buildPlayersFromWagers(ChipStack[] wagers) {
        ChipStack [] banks = new ChipStack[wagers.length];
        for(int i =0; i < wagers.length; i++) {
            banks[i] = ChipStack.of(100);
        }
        return buildPlayersFromWagers(wagers, banks);
    }


    @DataProvider(name = "postBlindWagersAndExpectedActions")
    public Object[][] createData1() {
        return new Object[][] {
                {ChipStack.TWO_CHIPS, new ChipStack(4), 0, new ChipStack[]{ ChipStack.TWO_CHIPS, ChipStack.ONE_CHIP}, new Action[]{Action.Factory.callAction(), Action.Factory.raiseToAction(ChipStack.of(4)), Action.Factory.foldAction()}},
                {ChipStack.TWO_CHIPS, new ChipStack(4), 0, new ChipStack[]{ ChipStack.NO_CHIPS, ChipStack.NO_CHIPS}, new Action[]{Action.Factory.smallBlindAction()}} ,
                {ChipStack.TWO_CHIPS, new ChipStack(4), 0, new ChipStack[]{ ChipStack.NO_CHIPS, ChipStack.ONE_CHIP}, new Action[]{Action.Factory.bigBlindAction()}},
                {ChipStack.TWO_CHIPS, new ChipStack(4), 2, new ChipStack[]{ ChipStack.ONE_CHIP, ChipStack.TWO_CHIPS, ChipStack.NO_CHIPS}, new Action[]{Action.Factory.callAction(), Action.Factory.raiseToAction(ChipStack.of(4)), Action.Factory.foldAction()}},
                {ChipStack.TWO_CHIPS, new ChipStack(4), 0, new ChipStack[]{ ChipStack.ONE_CHIP, ChipStack.TWO_CHIPS, ChipStack.TWO_CHIPS}, new Action[]{Action.Factory.callAction(), Action.Factory.raiseToAction(ChipStack.of(4)), Action.Factory.foldAction()}},
                {ChipStack.TWO_CHIPS, new ChipStack(4), 0, new ChipStack[]{ ChipStack.TWO_CHIPS, ChipStack.TWO_CHIPS, ChipStack.TWO_CHIPS}, new Action[]{Action.Factory.checkAction(), Action.Factory.betAction(ChipStack.TWO_CHIPS)}},
                {ChipStack.TWO_CHIPS, new ChipStack(4), 0, new ChipStack[]{ ChipStack.TWO_CHIPS, ChipStack.TWO_CHIPS, ChipStack.TWO_CHIPS, ChipStack.TWO_CHIPS}, new Action[]{Action.Factory.checkAction(), Action.Factory.betAction(ChipStack.TWO_CHIPS)}},
                {ChipStack.TWO_CHIPS, new ChipStack(4), 3, new ChipStack[]{ ChipStack.NO_CHIPS,ChipStack.NO_CHIPS,ChipStack.NO_CHIPS,ChipStack.NO_CHIPS}, new Action[]{Action.Factory.smallBlindAction()}},
                {ChipStack.TWO_CHIPS, new ChipStack(4), 3, new ChipStack[]{ ChipStack.NO_CHIPS,ChipStack.NO_CHIPS,ChipStack.NO_CHIPS,ChipStack.NO_CHIPS}, new Action[]{Action.Factory.smallBlindAction()}},
                {ChipStack.TWO_CHIPS, new ChipStack(4), 3, new ChipStack[]{ ChipStack.ONE_CHIP,ChipStack.TWO_CHIPS,ChipStack.NO_CHIPS,ChipStack.NO_CHIPS}, new Action[]{Action.Factory.callAction(), Action.Factory.raiseToAction(ChipStack.of(4)), Action.Factory.foldAction()}},
                {ChipStack.TWO_CHIPS, new ChipStack(4), 0, new ChipStack[]{ new ChipStack(4), new ChipStack(4)}, new Action[]{}},

        };
    }

    @Test(dataProvider = "postBlindWagersAndExpectedActions")
    public void PreFlopStateWithWagers_buildAllChildActions_CorrectActions(ChipStack lowerLimit, ChipStack higherLimit, int dealerPos, ChipStack [] wagers,  Action[] expectedActions) throws Exception
    {

        ArrayList<IPlayerState> players = buildPlayersFromWagers(wagers);

        FixedLimitState flState = new FixedLimitState(lowerLimit, higherLimit, ImmutableList.copyOf(players), Street.PRE_FLOP, dealerPos, null);

        FixedLimitActionBuilder actionBuilder = new FixedLimitActionBuilder(null);

        // when
        ImmutableList<Action> nextActions = actionBuilder.buildAllChildActions(flState);

        // then
        assertEqualsNoOrder(nextActions.toArray(), expectedActions);


    }

    @DataProvider(name = "postFlopWagersAndExpectedActions")
    public Object[][] createData2() {
        return new Object[][] {
                {ChipStack.TWO_CHIPS, new ChipStack(4), 0, new ChipStack[]{ ChipStack.TWO_CHIPS, ChipStack.NO_CHIPS}, new Action[]{Action.Factory.callAction(), Action.Factory.raiseToAction(ChipStack.of(4)), Action.Factory.foldAction()}},
                {ChipStack.TWO_CHIPS, new ChipStack(4), 0, new ChipStack[]{ ChipStack.NO_CHIPS, ChipStack.NO_CHIPS}, new Action[]{Action.Factory.checkAction(), Action.Factory.betAction(ChipStack.TWO_CHIPS)}} ,
                {ChipStack.TWO_CHIPS, new ChipStack(4), 0, new ChipStack[]{ ChipStack.NO_CHIPS, ChipStack.TWO_CHIPS}, new Action[]{Action.Factory.callAction(), Action.Factory.raiseToAction(ChipStack.of(4)), Action.Factory.foldAction()}},
                {ChipStack.TWO_CHIPS, new ChipStack(4), 2, new ChipStack[]{ ChipStack.ONE_CHIP, ChipStack.TWO_CHIPS, ChipStack.NO_CHIPS}, new Action[]{Action.Factory.callAction(), Action.Factory.raiseToAction(ChipStack.of(4)), Action.Factory.foldAction()}},
                {ChipStack.TWO_CHIPS, new ChipStack(4), 0, new ChipStack[]{ ChipStack.TWO_CHIPS,  new ChipStack(4),  new ChipStack(4)}, new Action[]{Action.Factory.callAction(), Action.Factory.raiseToAction(ChipStack.of(6)), Action.Factory.foldAction()}},
                {ChipStack.TWO_CHIPS, new ChipStack(4), 0, new ChipStack[]{ ChipStack.TWO_CHIPS, ChipStack.TWO_CHIPS, ChipStack.TWO_CHIPS}, new Action[]{}},
                {ChipStack.TWO_CHIPS, new ChipStack(4), 0, new ChipStack[]{ ChipStack.TWO_CHIPS, ChipStack.TWO_CHIPS, ChipStack.TWO_CHIPS, ChipStack.TWO_CHIPS}, new Action[]{}},
                {ChipStack.TWO_CHIPS, new ChipStack(4), 3, new ChipStack[]{ ChipStack.NO_CHIPS,ChipStack.NO_CHIPS,ChipStack.NO_CHIPS,ChipStack.NO_CHIPS},new Action[]{Action.Factory.checkAction(), Action.Factory.betAction(ChipStack.TWO_CHIPS)}},
                {ChipStack.TWO_CHIPS, new ChipStack(4), 3, new ChipStack[]{ ChipStack.TWO_CHIPS, new ChipStack(4),ChipStack.NO_CHIPS,ChipStack.NO_CHIPS}, new Action[]{Action.Factory.callAction(), Action.Factory.raiseToAction(ChipStack.of(6)), Action.Factory.foldAction()}},
                {ChipStack.TWO_CHIPS, new ChipStack(4), 0, new ChipStack[]{ new ChipStack(4), new ChipStack(4)}, new Action[]{}},

        };
    }

    @Test(dataProvider = "postFlopWagersAndExpectedActions")
    public void PostFlopStateWithWagers_buildAllChildActions_CorrectActions(ChipStack lowerLimit, ChipStack higherLimit, int dealerPos, ChipStack [] wagers,  Action[] expectedActions) throws Exception
    {

        ArrayList<IPlayerState> players = buildPlayersFromWagers(wagers);

        FixedLimitState flState = new FixedLimitState(lowerLimit, higherLimit, ImmutableList.copyOf(players), Street.FLOP, dealerPos, null);

        FixedLimitActionBuilder actionBuilder = new FixedLimitActionBuilder(null);

        // when
        ImmutableList<Action> nextActions = actionBuilder.buildAllChildActions(flState);

        // then
        assertEqualsNoOrder(nextActions.toArray(), expectedActions);


    }

    @DataProvider(name = "postFlopWagersAndBanksAndExpectedActions")
    public Object[][] createData3() {
        return new Object[][] {
                {0, new ChipStack[]{ ChipStack.of(4), ChipStack.of(5)}, new ChipStack[]{ ChipStack.ONE_CHIP, ChipStack.NO_CHIPS},  new Action[]{Action.Factory.callAction(), Action.Factory.foldAction()}},
                {0, new ChipStack[]{ ChipStack.NO_CHIPS, ChipStack.TWO_CHIPS}, new ChipStack[]{ ChipStack.TWO_CHIPS, ChipStack.NO_CHIPS},  new Action[]{Action.Factory.callAction(), Action.Factory.foldAction()}},
                {2, new ChipStack[]{ ChipStack.ONE_CHIP, ChipStack.of(4), ChipStack.NO_CHIPS},  new ChipStack[]{ChipStack.of(10), ChipStack.of(10), ChipStack.of(10)},new Action[]{Action.Factory.callAction(), Action.Factory.raiseToAction(ChipStack.of(6)), Action.Factory.foldAction()}},
                {2, new ChipStack[]{ ChipStack.ONE_CHIP, ChipStack.of(4), ChipStack.of(6)},  new ChipStack[]{ChipStack.of(10), ChipStack.of(10), ChipStack.of(4)},new Action[]{Action.Factory.callAction(), Action.Factory.raiseToAction(ChipStack.of(8)), Action.Factory.foldAction()}},
                {2, new ChipStack[]{ ChipStack.of(8), ChipStack.of(4), ChipStack.of(6)},  new ChipStack[]{ChipStack.of(2), ChipStack.of(10), ChipStack.of(4)},new Action[]{Action.Factory.callAction(), Action.Factory.raiseToAction(ChipStack.of(10)), Action.Factory.foldAction()}},
                {2, new ChipStack[]{ ChipStack.of(8), ChipStack.of(10), ChipStack.of(6)},  new ChipStack[]{ChipStack.of(2), ChipStack.of(4), ChipStack.of(4)},new Action[]{Action.Factory.callAction(), Action.Factory.foldAction()}},
                {2, new ChipStack[]{ ChipStack.of(8), ChipStack.of(10), ChipStack.of(10)},  new ChipStack[]{ChipStack.of(2), ChipStack.of(10), ChipStack.of(0)},new Action[]{Action.Factory.callAction(), Action.Factory.foldAction()}},
                {2, new ChipStack[]{ ChipStack.of(10), ChipStack.of(10), ChipStack.of(10)},  new ChipStack[]{ChipStack.of(0), ChipStack.of(10), ChipStack.of(0)},new Action[]{Action.Factory.showdownAction()}},
                {2, new ChipStack[]{ ChipStack.of(10), ChipStack.of(10), ChipStack.of(10)},  new ChipStack[]{ChipStack.of(10), ChipStack.of(10), ChipStack.of(20)},new Action[]{}},
                {2, new ChipStack[]{ ChipStack.of(0), ChipStack.of(2), ChipStack.of(2)},  new ChipStack[]{ChipStack.of(4), ChipStack.of(10), ChipStack.of(10)},new Action[]{Action.Factory.callAction(), Action.Factory.raiseToAction(ChipStack.of(4)), Action.Factory.foldAction()}},
                {2, new ChipStack[]{ ChipStack.of(4), ChipStack.of(2), ChipStack.of(2)},  new ChipStack[]{ChipStack.of(0), ChipStack.of(10), ChipStack.of(10)},new Action[]{Action.Factory.callAction(), Action.Factory.raiseToAction(ChipStack.of(6)), Action.Factory.foldAction()}},

        };
    }

    @Test(dataProvider = "postFlopWagersAndBanksAndExpectedActions")
    public void PostFlopStateWithBanksLessThankRaises_buildAllChildActions_RemovesRaises(int dealerPos, ChipStack [] wagers, ChipStack [] banks,  Action[] expectedActions) throws Exception
    {
        ChipStack lowerLimit = ChipStack.of(2);
        ChipStack higherLimit = ChipStack.of(4);

            // the point here is to make sure that the game tree terminates at the appropriate place

        ArrayList<IPlayerState> players = buildPlayersFromWagers(wagers, banks);

        FixedLimitState flState = new FixedLimitState(lowerLimit, higherLimit, ImmutableList.copyOf(players), Street.FLOP, dealerPos, null);

        FixedLimitActionBuilder actionBuilder = new FixedLimitActionBuilder(null);

        // when
        ImmutableList<Action> nextActions = actionBuilder.buildAllChildActions(flState);

        // then
        assertEqualsNoOrder(nextActions.toArray(), expectedActions);
    }


}
