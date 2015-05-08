package com.mossy.holdem.interfaces.state;

import com.mossy.holdem.Action;
import com.mossy.holdem.interfaces.state.IGameState;

public interface IGameStateFactory
{
    IGameState NextState(IGameState currentState, Action action);
}
