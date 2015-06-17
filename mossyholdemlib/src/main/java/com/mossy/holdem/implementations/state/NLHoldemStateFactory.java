package com.mossy.holdem.implementations.state;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.interfaces.player.IPlayerState;
import com.mossy.holdem.interfaces.state.IGameState;
import com.mossy.holdem.interfaces.state.IGameStateFactory;

public class  NLHoldemStateFactory implements IGameStateFactory
{

    @Override
    public IGameState buildNextState(IGameState currentState, Action action) {
        return null;
    }

    @Override
    public IGameState buildNewState(ImmutableList<IPlayerState> playerStates, int dealerPosition) {
        return null;
    }
}
