package com.mossy.holdem.implementations.state;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mossy.holdem.Action;
import com.mossy.holdem.Card;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.Street;
import com.mossy.holdem.interfaces.state.IFixedLimitState;
import com.mossy.holdem.interfaces.state.IPlayerState;
import com.mossy.holdem.implementations.ImmutableListCollector;

import java.util.function.BinaryOperator;
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
    final private ImmutableMap<Street, ChipStack> pots = ImmutableMap.of();
    final private int raiseCap;
    final private int numberOfRaises;
    final private Action lastAction;

    FixedLimitState(ChipStack lowerLimit, ChipStack higherLimit,
                 ImmutableList<IPlayerState> playerStates,
                 Street street,
                 int dealerPos, ImmutableList<Card> communityCards,
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
    }
    FixedLimitState(ChipStack lowerLimit, ChipStack higherLimit,
                    ImmutableList<IPlayerState> playerStates,
                    Street street,
                    int dealerPos,  int numberOfRaises, int raiseCap, Action lastAction)  {
        this(lowerLimit, higherLimit, playerStates, street, dealerPos, ImmutableList.<Card>of(), numberOfRaises, raiseCap, lastAction);
    }
    FixedLimitState(ChipStack lowerLimit, ChipStack higherLimit, ImmutableList<IPlayerState> playerStates,
                    Street street, int dealerPos, Action lastAction)  {
        this(lowerLimit, higherLimit, playerStates, street, dealerPos, ImmutableList.<Card>of(), 0, 3, lastAction);
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


    public int nextPlayerSeat() throws Exception
    {
        ChipStack lastPlayerBet = ChipStack.NO_CHIPS;
        // start with next player after dealer, work out who is next
        int nextPlayeSeat = playerAfter(dealerPos);
        boolean fullCircle = false;

        while(nextPlayeSeat != dealerPos || fullCircle == false)
        {
            IPlayerState nextPlayerInfo = playerStates.get(nextPlayeSeat);
            if(!hasBets() && !nextPlayerInfo.hasChecked())
            {
                return nextPlayeSeat;
            }
            else if(hasBets() && !nextPlayerInfo.isOut() && nextPlayerInfo.pot().compareTo(lastPlayerBet) < 0)
            {
                return nextPlayeSeat;
            }

            lastPlayerBet = nextPlayerInfo.pot();
            nextPlayeSeat = playerAfter(nextPlayeSeat);
            if(nextPlayeSeat == playerAfter(dealerPos))
            {
                fullCircle = true;
            }
        }


        // if we get here, it means that everyone has put in the same amount of money, which means it's a dealer action OR
        // we are pre flop and it's the big blind's turn to check or bet
        if(street == Street.PRE_FLOP && !playerStates.get(playerAfter(playerAfter(dealerPos))).hasChecked() )
        {
            return playerAfter(playerAfter(dealerPos));
        }
        // it's a dealer action
        // return -1;
        throw new Exception("Cannot determine next player");
    }

    @Override
    public int playerAfter(int currentPlayer)
    {
        return (currentPlayer + 1) % playerStates().size();
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
        return pots.values().stream().reduce(ChipStack.NO_CHIPS, ChipStack.adder);
    }


    @Override
    public IPlayerState getNextPlayer() throws Exception
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
    public ChipStack getAmountToCall() throws  Exception
    {
        return getHighestBet().subtract(getNextPlayer().pot());
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
            playerPots += player.pot().toString()  ;
        }

        return String.format("%s pot: %s stakes: %s lastAction %s", street, totalPot(), playerPots, lastAction());
    }


}
