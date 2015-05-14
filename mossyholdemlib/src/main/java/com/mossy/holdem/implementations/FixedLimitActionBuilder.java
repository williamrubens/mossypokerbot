package com.mossy.holdem.implementations;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.Street;
import com.mossy.holdem.interfaces.IActionBuilder;
import com.mossy.holdem.interfaces.IDeck;
import com.mossy.holdem.interfaces.state.IFixedLimitState;
import com.mossy.holdem.interfaces.state.IGameState;

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

//    private ImmutableList<Action> buildDealerActions(IGameState parentState) {
//        if(parentState.street() == Street.PRE_FLOP){
//            for(card: deck.undealtCards())
//        }
//
//    }


    @Override
    public ImmutableList<Action> buildAllChildActions(IGameState parentState) throws Exception
    {
        if(!(parentState instanceof IFixedLimitState))
        {
            throw new Exception("Cannot build actions for non-fixed limit state");
        }
        IFixedLimitState flParentState = (IFixedLimitState)parentState;

        // check if everyone has folded, in which case there are no further actions
        if(parentState.playersStillIn().size() == 1) {
            return ImmutableList.of(Action.Factory.winAction() );
        }

        // check if game is over and it's time to redeal (handled elsewhere)
        if(parentState.street() == Street.FINISHED) {
            return ImmutableList.of();
        }

        // first check if anybody has opened the betting
        if(!parentState.hasBets())
        {
            // special case for blinds
            if(parentState.street() == Street.PRE_FLOP &&
               parentState.nextPlayerSeat() == parentState.playerAfter(parentState.dealerPosition()))
            {
                return ImmutableList.of(Action.Factory.smallBlindAction());
            }

            if(parentState.nextPlayerSeat() == parentState.dealerPosition()) {
                return ImmutableList.of(Action.Factory.dealerAction());
            }

            return ImmutableList.of(Action.Factory.checkAction(), Action.Factory.foldAction(), Action.Factory.betAction(flParentState.getCurrentBetLimit()));
        }

        if(parentState.isBettingClosed()) {
            return ImmutableList.of(Action.Factory.dealerAction());
        }

        // ... could still be a big blind pre flop edge case
        if(parentState.street() == Street.PRE_FLOP && parentState.nextPlayerSeat()  == parentState.playerAfter(parentState.playerAfter(parentState.dealerPosition()))) {
            // either the big blind has no chips in, in which case it's a big blind action
            if (parentState.getNextPlayer().pot() == ChipStack.NO_CHIPS) {
                return ImmutableList.of(Action.Factory.bigBlindAction());
            }
            // or it's the big blind check or bet action after everyone has called him preflip
            if (parentState.getAmountToCall().compareTo(ChipStack.NO_CHIPS) == 0) {
                return ImmutableList.of(Action.Factory.checkAction(), Action.Factory.foldAction(), Action.Factory.betAction(flParentState.getCurrentBetLimit()));

            }
        }


        if(flParentState.numberOfRaises() >= flParentState.raiseCap()) {
            return ImmutableList.of(Action.Factory.callAction(), Action.Factory.foldAction());
        }

        return ImmutableList.of(Action.Factory.callAction(), Action.Factory.foldAction(), Action.Factory.raiseAction(flParentState.getCurrentBetLimit()));
    }
}
