package com.mossy.holdem.implementations.state;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.GameStage;
import com.mossy.holdem.implementations.FixedLimitActionBuilder;
import com.mossy.holdem.interfaces.state.IFixedLimitState;
import com.mossy.holdem.interfaces.state.IGameState;
import com.mossy.holdem.interfaces.state.IPlayerInfo;
import org.mockito.Mock;
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

    @DataProvider(name = "postBlindWagersAndExpectedActions")
    public Object[][] createData1() {
        return new Object[][] {
                {ChipStack.TWO_CHIPS, new ChipStack(4), 0, new ChipStack[]{ ChipStack.TWO_CHIPS, ChipStack.ONE_CHIP}, new Action[]{Action.Factory.callAction(ChipStack.ONE_CHIP), Action.Factory.betAction(ChipStack.TWO_CHIPS), Action.Factory.foldAction()}},
                {ChipStack.TWO_CHIPS, new ChipStack(4), 0, new ChipStack[]{ ChipStack.NO_CHIPS, ChipStack.NO_CHIPS}, new Action[]{Action.Factory.smallBlindAction()}} ,
                {ChipStack.TWO_CHIPS, new ChipStack(4), 0, new ChipStack[]{ ChipStack.NO_CHIPS, ChipStack.ONE_CHIP}, new Action[]{Action.Factory.bigBlindAction()}},
                {ChipStack.TWO_CHIPS, new ChipStack(4), 2, new ChipStack[]{ ChipStack.ONE_CHIP, ChipStack.TWO_CHIPS, ChipStack.NO_CHIPS}, new Action[]{Action.Factory.callAction(ChipStack.TWO_CHIPS), Action.Factory.betAction(ChipStack.TWO_CHIPS), Action.Factory.foldAction()}},
                {ChipStack.TWO_CHIPS, new ChipStack(4), 0, new ChipStack[]{ ChipStack.ONE_CHIP, ChipStack.TWO_CHIPS, ChipStack.TWO_CHIPS}, new Action[]{Action.Factory.callAction(ChipStack.ONE_CHIP), Action.Factory.betAction(ChipStack.TWO_CHIPS), Action.Factory.foldAction()}},
                {ChipStack.TWO_CHIPS, new ChipStack(4), 0, new ChipStack[]{ ChipStack.TWO_CHIPS, ChipStack.TWO_CHIPS, ChipStack.TWO_CHIPS}, new Action[]{Action.Factory.checkAction(), Action.Factory.betAction(ChipStack.TWO_CHIPS), Action.Factory.foldAction()}},
                {ChipStack.TWO_CHIPS, new ChipStack(4), 0, new ChipStack[]{ ChipStack.TWO_CHIPS, ChipStack.TWO_CHIPS, ChipStack.TWO_CHIPS, ChipStack.TWO_CHIPS}, new Action[]{Action.Factory.checkAction(), Action.Factory.betAction(ChipStack.TWO_CHIPS), Action.Factory.foldAction()}},
                {ChipStack.TWO_CHIPS, new ChipStack(4), 3, new ChipStack[]{ ChipStack.NO_CHIPS,ChipStack.NO_CHIPS,ChipStack.NO_CHIPS,ChipStack.NO_CHIPS}, new Action[]{Action.Factory.smallBlindAction()}},
                {ChipStack.TWO_CHIPS, new ChipStack(4), 3, new ChipStack[]{ ChipStack.NO_CHIPS,ChipStack.NO_CHIPS,ChipStack.NO_CHIPS,ChipStack.NO_CHIPS}, new Action[]{Action.Factory.smallBlindAction()}},
                {ChipStack.TWO_CHIPS, new ChipStack(4), 3, new ChipStack[]{ ChipStack.ONE_CHIP,ChipStack.TWO_CHIPS,ChipStack.NO_CHIPS,ChipStack.NO_CHIPS}, new Action[]{Action.Factory.callAction(ChipStack.TWO_CHIPS), Action.Factory.betAction(ChipStack.TWO_CHIPS), Action.Factory.foldAction()}},
//                //{ChipStack.TWO_CHIPS, new ChipStack(4), 0, new ChipStack[]{ ChipStack.TWO_CHIPS, ChipStack.TWO_CHIPS}, new Action[]{Action.Factory.dealFlopAction()}},

        };
    }

    @Test(dataProvider = "postBlindWagersAndExpectedActions")
    public void PreFlopStateWithWagers_buildAllChildActions_CorrectActions(ChipStack lowerLimit, ChipStack higherLimit, int dealerPos, ChipStack [] wagers,  Action[] expectedActions) throws Exception
    {

        ArrayList<IPlayerInfo> players = new ArrayList<>();
        ChipStack highestBet = ChipStack.NO_CHIPS;

        for(int p = 0; p < wagers.length; p++)
        {
            if(highestBet.compareTo(wagers[p]) < 0)
            {
                highestBet = wagers[p];
            }
            IPlayerInfo playerState = mock(IPlayerInfo.class);
            stub(playerState.pot()).toReturn(wagers[p]);
            stub(playerState.hasChecked()).toReturn(false);
            stub(playerState.isOut()).toReturn(false);
            players.add(playerState);
            System.out.print(String.format("Wager %s\n", wagers[p].toString()));
        }

//
//        IFixedLimitState flState = mock(IFixedLimitState.class);
//        stub(flState.lowerLimit()).toReturn(lowerLimit);
//        stub(flState.higherLimit()).toReturn(higherLimit);
//        stub(flState.dealerPosition()).toReturn(dealerPos);
//        stub(flState.getCurrentBetLimit()).toReturn(lowerLimit);
//        stub(flState.getAmountToCall()).toReturn(amountToCall);
//        stub(flState.getHighestBet()).toReturn(highestBet);
//        stub(flState.isPotOpen()).toReturn(highestBet.equals(ChipStack.NO_CHIPS));

        FixedLimitState flState = new FixedLimitState(lowerLimit, higherLimit, ImmutableList.copyOf(players), GameStage.PRE_FLOP, dealerPos);



        FixedLimitActionBuilder actionBuilder = new FixedLimitActionBuilder(null);
        // when

            ImmutableList<Action> nextActions = actionBuilder.buildAllChildActions(flState);

        // then
        assertEqualsNoOrder(nextActions.toArray(), expectedActions);


    }
}