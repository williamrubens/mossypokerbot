package com.mossy.holdem.interfaces.state;

import com.mossy.holdem.Action;
import com.mossy.holdem.interfaces.state.IGameState;

public interface IGameStateFactory
{
    IGameState buildNextState(IGameState currentState, Action action) throws Exception;
}
