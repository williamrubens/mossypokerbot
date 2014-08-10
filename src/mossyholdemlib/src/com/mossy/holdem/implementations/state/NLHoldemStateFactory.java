package com.mossy.holdem.implementations.state;

import com.mossy.holdem.Action;
import com.mossy.holdem.interfaces.IGameState;
import com.mossy.holdem.interfaces.IGameStateFactory;

public class  NLHoldemStateFactory implements IGameStateFactory
{

    @Override
    public IGameState NextState(IGameState currentState, Action action)
    {
        return null;
    }
}
