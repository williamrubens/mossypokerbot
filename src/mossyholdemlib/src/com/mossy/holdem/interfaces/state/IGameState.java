package com.mossy.holdem.interfaces.state;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.GameStage;

/**
 * Created by williamrubens on 19/07/2014.
 */
public interface IGameState
{

    GameStage stage();

    int dealerPosition();

    ImmutableList<IPlayerInfo> playerStates();
    IPlayerInfo getNextPlayer() throws Exception;

    int nextPlayerSeat() throws Exception;
    int playerAfter(int seat);

    ChipStack getAmountToCall() throws  Exception;
    ChipStack getHighestBet();
    boolean isPotOpen();

}


