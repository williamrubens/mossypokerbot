package com.mossy.pokerbot.interfaces.state;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import com.mossy.pokerbot.Action;
import com.mossy.pokerbot.Card;
import com.mossy.pokerbot.ChipStack;
import com.mossy.pokerbot.Street;
import com.mossy.pokerbot.interfaces.player.IPlayerState;

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
    IPlayerState nextPlayer();

    int nextPlayerSeat();
    int playerSeatAfter(int seat);

    UnmodifiableIterator<IPlayerState> fromDealerIterator();

    ChipStack smallBlind() ;
    ChipStack bigBlind();
    ChipStack getAmountToCall();
    ChipStack getHighestBet();
    boolean hasBets();
    boolean isBettingClosed();

    double potOdds();

    Action lastAction();

}


