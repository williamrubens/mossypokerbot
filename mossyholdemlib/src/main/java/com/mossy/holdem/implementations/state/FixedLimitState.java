package com.mossy.holdem.implementations.state;

import com.google.common.collect.*;
import com.mossy.holdem.Action;
import com.mossy.holdem.Card;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.Street;
import com.mossy.holdem.interfaces.state.IFixedLimitState;
import com.mossy.holdem.interfaces.player.IPlayerState;
import com.mossy.holdem.implementations.ImmutableListCollector;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by williamrubens on 18/08/2014.
 */
public class FixedLimitState implements IFixedLimitState
{
    final private int dealerPos;
    final private ChipStack lowerLimit;
    final private ChipStack higherLimit;
    final ImmutableList<IPlayerState> playerStates;
    final ImmutableList<Card> communityCards;
    final private Street street;
    final private ImmutableMap<Street, ChipStack> pots;
    final private int raiseCap;
    final private int numberOfRaises;
    final private Action lastAction;

    FixedLimitState(ChipStack lowerLimit, ChipStack higherLimit,
                 ImmutableList<IPlayerState> playerStates,
                 Street street,
                 int dealerPos, ImmutableList<Card> communityCards,
                    ImmutableMap<Street, ChipStack> pots,
                    int numberOfRaises, int raiseCap, Action lastAction)
    {
        this.lowerLimit = lowerLimit;
        this.higherLimit = higherLimit;
        this.playerStates = playerStates;
        this.dealerPos = dealerPos;
        this.street = street;
        this.communityCards = communityCards;
        this.raiseCap = raiseCap;
        this.numberOfRaises = numberOfRaises;
        this.lastAction = lastAction;
        this.pots = pots;
    }

    FixedLimitState(ChipStack lowerLimit, ChipStack higherLimit, ImmutableList<IPlayerState> playerStates,
                    Street street, int dealerPos, Action lastAction)  {
        this(lowerLimit, higherLimit, playerStates, street, dealerPos, ImmutableList.<Card>of(), ImmutableMap.<Street, ChipStack>of(), 0, 3, lastAction);
    }

    @Override
    public ChipStack lowerLimit()
    {
        return lowerLimit;
    }

    @Override
    public ChipStack higherLimit()
    {
        return higherLimit;
    }

    @Override
    public int raiseCap() {
        return raiseCap;
    }

    @Override
    public int numberOfRaises() {
        return numberOfRaises;
    }


    @Override
    public Street street()
    {
        return street;
    }

    @Override
    public int dealerPosition()
    {
        return dealerPos;
    }

    private int numPlayers()
    {
        return playerStates.size();
    }


    // TODO NEXT FIX NEXTPLAYERSEAT FOR MORE THAN ONE PLAYER



    public int nextPlayerSeat()
    {
        ChipStack highestPotSoFar = ChipStack.NO_CHIPS;

        // if there are no bets, go with first player who hasn't checked,
        // if there are bets, go with first player after the highest bidder who hasn't called

        ChipStack highestBet = getHighestBet();


        int playerSeat = playerSeatAfter(dealerPos);

        if(highestBet.compareTo(ChipStack.NO_CHIPS) == 0) {
            do {
                if (!playerStates.get(playerSeat).hasChecked() && !playerStates.get(playerSeat).isOut()) {
                    return playerSeat;
                }
                playerSeat = playerSeatAfter(playerSeat);
            } while (playerSeat != dealerPos);
            return dealerPos;
        }

        // find highest bidding seat
        while (playerSeat != dealerPos) {
            if (playerStates.get(playerSeat).pot().equals(highestBet)) {
                break;
            }
            playerSeat = playerSeatAfter(playerSeat);
        }
        final int highestBiddingSeat = playerSeat;
        // move to the first seat after highest bidder
        playerSeat = playerSeatAfter(playerSeat);

        // now find first player after highest bidding player that hasn't called
        for(; playerSeat != highestBiddingSeat; playerSeat = playerSeatAfter(playerSeat)) {
            IPlayerState player = playerStates.get(playerSeat);
            if(player.isOut()) {
                continue;
            }
            if(player.pot().compareTo(highestBet) < 0) {
                return playerSeat;
            }
        }


        // if we get here, it means that everyone has put in the same amount of money, which means it's a dealer action OR
        // we are pre flop and it's the big blind's turn to check or bet
        if(street == Street.PRE_FLOP && !playerStates.get(playerSeatAfter(playerSeatAfter(dealerPos))).hasChecked() )
        {
            return playerSeatAfter(playerSeatAfter(dealerPos));
        }
        // it's a dealer action
        // return -1;
        throw new RuntimeException("Cannot determine next player");
    }

    @Override
    public int playerSeatAfter(int currentPlayer)
    {
        return (currentPlayer + 1) % playerStates().size();
    }

    @Override
    public UnmodifiableIterator<IPlayerState> fromDealerIterator() {
        ImmutableList<IPlayerState> playersAfterDealer1 = playerStates.subList(playerSeatAfter(dealerPos), playerStates.size());
        ImmutableList<IPlayerState> playersAfterDealer2 = playerStates.subList(0, playerSeatAfter(dealerPos));
        return Iterators.unmodifiableIterator(Iterables.concat(playersAfterDealer1, playersAfterDealer2).iterator());
    }

    public int smallBlindPosition()
    {
        return (dealerPosition() + 1) % playerStates().size();
    }
    public int bigBlindPosition()
    {
        return (dealerPosition() + 2) % playerStates().size();
    }

    @Override
    public ChipStack smallBlind() {
        return lowerLimit().divide(2);
    }

    @Override
    public ChipStack bigBlind() {
        return lowerLimit();
    }

    @Override
    public ImmutableList<IPlayerState> playerStates()
    {
        return playerStates;
    }

    @Override
    public ImmutableList<IPlayerState> playersStillIn() {

        return playerStates().stream().filter(p -> !p.isOut())
                .collect(new ImmutableListCollector<>());
    }

    @Override
    public ImmutableList<Card> communityCards() {
        return communityCards;
    }

    @Override
    public ImmutableMap<Street, ChipStack> pots() {
        return pots;
    }

    @Override
    public ChipStack totalPot() {
        ChipStack totalPot = ChipStack.NO_CHIPS;
        for(ChipStack pot : this.pots().values()) {
            totalPot = totalPot.add(pot);
        }
        for(IPlayerState player : playerStates()){
            totalPot = totalPot.add(player.pot());
        };
        return totalPot;
    }


    @Override
    public IPlayerState nextPlayer()
    {
        return playerStates.get(nextPlayerSeat());
    }


    @Override
    public ChipStack getHighestBet()
    {
        ChipStack currentRaise = ChipStack.NO_CHIPS;
        for (IPlayerState p : playerStates)
        {
            if(p.pot().compareTo(currentRaise) > 0)
            {
                currentRaise = p.pot();
            }
        }
        return currentRaise;
    }

    @Override
    public ChipStack getAmountToCall()
    {
        return getHighestBet().subtract(nextPlayer().pot());
    }

    @Override
    public boolean hasBets()
    {
        return  getHighestBet().compareTo(ChipStack.NO_CHIPS) > 0;
    }

    @Override
    public boolean isBettingClosed()
    {
        if(!hasBets()) {
            return false;
        }
        ChipStack highestRaise = getHighestBet();
        Stream<IPlayerState> playersStillToPlay = playerStates.stream().
                filter(playerState -> !playerState.isOut() && playerState.pot().compareTo(highestRaise) < 0);
        boolean everyoneHasCalled = playersStillToPlay.count() == 0;
        // need to check for edge case in preflop when big blind could still raise if everyone has just called him
        if(everyoneHasCalled && street == Street.PRE_FLOP && highestRaise.compareTo(lowerLimit) == 0) {

            return playerStates.get(bigBlindPosition()).hasChecked();
        }
        return everyoneHasCalled;


    }

    @Override
    public double potOdds() {
        ChipStack amountToCall = getAmountToCall();

        return amountToCall.toDouble() / (amountToCall.toDouble() + totalPot().toDouble());
    }

    @Override
    public ChipStack getCurrentBetLimit()
    {
        if(street == Street.RIVER || street == Street.TURN)
        {
            return higherLimit;
        }
        return lowerLimit;
    }

    @Override
    public Action lastAction() {
        return lastAction;
    }

    @Override
    public String toString() {
        String playerPots = "";
        for(IPlayerState player: playerStates) {
            playerPots += player.pot().toString() + " " ;
        }

        return String.format("%s pot: %s stakes: %s lastAction %s", street, totalPot(), playerPots, lastAction());
    }


}
