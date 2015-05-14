package com.mossy.holdem.interfaces.state;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mossy.holdem.Card;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.Street;

/**
 * Created by williamrubens on 19/07/2014.
 */
public interface IGameState
{

    Street street();

    int dealerPosition();

    ImmutableList<IPlayerState> playerStates();
    ImmutableList<IPlayerState> playersStillIn();

    ImmutableList<Card> communityCards();
    ImmutableMap<Street, ChipStack> pots();
    ChipStack totalPot();
    IPlayerState getNextPlayer() throws Exception;

    int nextPlayerSeat() throws Exception;
    int playerAfter(int seat);

    ChipStack smallBlind() ;
    ChipStack bigBlind();
    ChipStack getAmountToCall() throws  Exception;
    ChipStack getHighestBet();
    boolean hasBets();
    boolean isBettingClosed();

}


