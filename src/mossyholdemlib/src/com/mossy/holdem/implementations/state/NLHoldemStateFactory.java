package com.mossy.holdem.implementations.state;

import com.mossy.holdem.Action;
import com.mossy.holdem.interfaces.state.IGameState;
import com.mossy.holdem.interfaces.state.IGameStateFactory;

public class  NLHoldemStateFactory implements IGameStateFactory
{

    @Override
    public IGameState NextState(IGameState currentState, Action action)
    {
        return null;
    }
}
