package com.mossy.holdem.interfaces.state;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.interfaces.player.IPlayerState;
import com.mossy.holdem.interfaces.state.IGameState;

public interface IGameStateFactory
{
    IGameState buildNextState(IGameState currentState, Action action) ;
    IGameState buildNewState(ImmutableList<IPlayerState> playerStates, int dealerPosition);
}
