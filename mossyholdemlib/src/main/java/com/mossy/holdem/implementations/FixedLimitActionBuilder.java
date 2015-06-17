package com.mossy.holdem.implementations;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.Street;
import com.mossy.holdem.interfaces.IActionBuilder;
import com.mossy.holdem.interfaces.IDealerActionBuilder;
import com.mossy.holdem.interfaces.state.IFixedLimitState;
import com.mossy.holdem.interfaces.state.IGameState;
import com.mossy.holdem.interfaces.player.IPlayerState;

/**
 * Created by williamrubens on 08/09/2014.
 */
public class FixedLimitActionBuilder implements IActionBuilder {
    IDealerActionBuilder dealer;

    public FixedLimitActionBuilder(IDealerActionBuilder dealer) {

        this.dealer = dealer;
    }

    public FixedLimitActionBuilder() {
        this(null);
    }


    private ImmutableList<Action> moveToNextStreetAction(IGameState parentState) {
        // check to see if players are all in, and no more bets can be made... therefore move to showdown
        int playersStillInWithBank = 0;
        for(IPlayerState player : parentState.playersStillIn()) {
            if(player.bank().compareTo(ChipStack.NO_CHIPS) > 0) {
                playersStillInWithBank++;
            }
        }

        if(playersStillInWithBank < 2) {
            return ImmutableList.of(Action.Factory.showdownAction());
        }
        if (dealer == null) {
            return ImmutableList.of();
        }

        switch (parentState.street()) {
            case PRE_FLOP:
                return dealer.dealAllFlops(parentState);
            case FLOP:
                return dealer.dealAllTurns(parentState);
            case TURN:
                return dealer.dealAllRivers(parentState);
            case RIVER:
                return ImmutableList.of(Action.Factory.showdownAction());

        }
        throw new RuntimeException("Failed to enumerate dealer action");
        //return ImmutableList.of();

    }


    @Override
    public ImmutableList<Action> buildAllChildActions(IGameState parentState) {
        if (!(parentState instanceof IFixedLimitState)) {
            throw new RuntimeException("Cannot build actions for non-fixed limit state");
        }
        IFixedLimitState flParentState = (IFixedLimitState) parentState;

        // check if everyone has folded, in which case there are no further actions
        if (parentState.playersStillIn().size() == 1 || parentState.street() == Street.SHOWDOWN) {
            ImmutableList.Builder listBuilder = ImmutableList.builder();
            for (IPlayerState player : parentState.playersStillIn()) {
                listBuilder.add(Action.Factory.winAction(player.id()));
            }
            return listBuilder.build();
        }

        // check if game is over and it's time to redeal (handled elsewhere)
        if (parentState.street() == Street.FINISHED) {
            return ImmutableList.of();
        }

        // first check if anybody has opened the betting
        if (!parentState.hasBets()) {
            // special case for blinds
            if (parentState.street() == Street.PRE_FLOP &&
                    parentState.nextPlayerSeat() == parentState.playerSeatAfter(parentState.dealerPosition())) {
                return ImmutableList.of(Action.Factory.smallBlindAction());
            }

            // if all players have checked
            boolean allPlayersChecked = parentState.playersStillIn().stream().allMatch(player -> player.hasChecked());
            if (allPlayersChecked) {
                return moveToNextStreetAction(parentState);
            }

            return ImmutableList.of(Action.Factory.checkAction(), Action.Factory.foldAction(), Action.Factory.betAction(nextBetAmount(flParentState)));
        }

        if (parentState.isBettingClosed()) {
            return moveToNextStreetAction(parentState);
        }

        // ... could still be a big blind pre flop edge case
        if (parentState.street() == Street.PRE_FLOP && parentState.nextPlayerSeat() == parentState.playerSeatAfter(parentState.playerSeatAfter(parentState.dealerPosition()))) {
            // either the big blind has no chips in, in which case it's a big blind action
            if (parentState.nextPlayer().pot() == ChipStack.NO_CHIPS) {
                return ImmutableList.of(Action.Factory.bigBlindAction());
            }
            // or it's the big blind check or bet action after everyone has called him preflip
            if (parentState.getAmountToCall().compareTo(ChipStack.NO_CHIPS) == 0) {
                return ImmutableList.of(Action.Factory.checkAction(), Action.Factory.foldAction(), Action.Factory.betAction(nextBetAmount(flParentState)));

            }
        }


        if (flParentState.numberOfRaises() >= flParentState.raiseCap()) {
            return ImmutableList.of(Action.Factory.callAction(), Action.Factory.foldAction());
        }

        ChipStack nextRaiseAmount = nextRaiseToAmount(flParentState);

        ChipStack amountToCall = flParentState.getAmountToCall();

        ChipStack toPutInPot = nextRaiseAmount.subtract(parentState.nextPlayer().pot());

        if (toPutInPot.compareTo(amountToCall) <= 0) {
            return ImmutableList.of(Action.Factory.callAction(), Action.Factory.foldAction());
        }
        return ImmutableList.of(Action.Factory.callAction(), Action.Factory.foldAction(), Action.Factory.raiseToAction(nextRaiseAmount));

    }

    private ChipStack nextBetAmount(IFixedLimitState flParentState) {
        ChipStack nextPlayerBank = flParentState.nextPlayer().bank();
        return nextPlayerBank.compareTo(flParentState.getCurrentBetLimit()) < 0 ? nextPlayerBank : flParentState.getCurrentBetLimit();
    }


    private ChipStack nextRaiseToAmount(IFixedLimitState flParentState) {

        // compute next raise amount paying attention to 2 edge cases:
        // 1) player might not have enough to make the riase, so he/she goes all in
        // 2) there might not be any players left to call the raise, in which case he/she puts the other player all in

        IPlayerState nextPlayer = flParentState.nextPlayer();
        ChipStack nextPlayerMaxRaise = nextPlayer.bank().add(nextPlayer.pot());
        ChipStack nextRaise = flParentState.getHighestBet().add(flParentState.getCurrentBetLimit());

        // need to check that there is at least one player left who has enough bank+pot to call this raise
        ChipStack maxBank = ChipStack.NO_CHIPS;
        for (IPlayerState player : flParentState.playersStillIn()) {
            if (player.id() != nextPlayer.id() && player.bank().add(player.pot()).compareTo(maxBank) > 0) {
                maxBank = player.bank().add(player.pot());
            }
        }

        if (maxBank.compareTo(nextRaise) < 0) {
            // can only raise to put everyone all in
            nextRaise = maxBank;
        }


        return nextPlayerMaxRaise.compareTo(nextRaise) < 0 ? nextPlayerMaxRaise : nextRaise;
    }
}
