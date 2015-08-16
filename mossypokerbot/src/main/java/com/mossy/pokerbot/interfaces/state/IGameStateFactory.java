package com.mossy.pokerbot.interfaces.state;

import com.google.common.collect.ImmutableList;
import com.mossy.pokerbot.Action;
import com.mossy.pokerbot.interfaces.player.IPlayerState;

public interface IGameStateFactory
{
    IGameState buildNextState(IGameState currentState, Action action) ;
    IGameState buildNewState(ImmutableList<IPlayerState> playerStates, int dealerPosition);
}
