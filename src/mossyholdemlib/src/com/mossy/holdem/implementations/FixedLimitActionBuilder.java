package com.mossy.holdem.implementations;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.GameStage;
import com.mossy.holdem.implementations.state.PlayerInfo;
import com.mossy.holdem.interfaces.IActionBuilder;
import com.mossy.holdem.interfaces.IDeck;
import com.mossy.holdem.interfaces.state.IFixedLimitState;
import com.mossy.holdem.interfaces.state.IGameState;
import com.mossy.holdem.interfaces.state.IPlayerInfo;

/**
 * Created by williamrubens on 08/09/2014.
 */
public class FixedLimitActionBuilder implements IActionBuilder
{
    public FixedLimitActionBuilder(IDeck deck)
    {
        this.deck = deck;
    }

    IDeck deck;


    @Override
    public ImmutableList<Action> buildAllChildActions(IGameState parentState) throws Exception
    {
        if(!(parentState instanceof IFixedLimitState))
        {
            throw new Exception("Cannot build actions for non-fixed limit state");
        }
        IFixedLimitState flParentState = (IFixedLimitState)parentState;

        // first check if anybody has opened the betting
        if(parentState.isPotOpen())
        {
            // special case for blinds
            if(parentState.stage() == GameStage.PRE_FLOP && parentState.nextPlayerSeat() == parentState.playerAfter(parentState.dealerPosition()))
            {
                return ImmutableList.of(Action.Factory.smallBlindAction());
            }

            return ImmutableList.of(Action.Factory.checkAction(), Action.Factory.foldAction(), Action.Factory.betAction(flParentState.getCurrentBetLimit()));
        }

        // ... could still be a big blind pre flop edge case
        if(parentState.stage() == GameStage.PRE_FLOP && parentState.nextPlayerSeat()  == parentState.playerAfter(parentState.playerAfter(parentState.dealerPosition())))
        {
            // either the big blind has no chips in, in which case it's a big blind action
            if(parentState.getNextPlayer().pot() == ChipStack.NO_CHIPS)
            {
                return ImmutableList.of(Action.Factory.bigBlindAction());
            }
            // or it's the big bling check or bet action after preflop
            return ImmutableList.of(Action.Factory.checkAction(), Action.Factory.foldAction(), Action.Factory.betAction(flParentState.getCurrentBetLimit()));
        }

        ImmutableList<IPlayerInfo> playerInfos = parentState.playerStates();

        return ImmutableList.of(Action.Factory.callAction(parentState.getAmountToCall()), Action.Factory.foldAction(), Action.Factory.betAction(flParentState.getCurrentBetLimit()));
    }
}
