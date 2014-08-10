package com.mossy.holdem.interfaces;

import com.mossy.holdem.Action;

public interface IGameStateFactory
{
    IGameState NextState(IGameState currentState, Action action);
}
