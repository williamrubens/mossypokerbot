package com.mossy.pokerbot.implementations.state;

import com.google.common.collect.ImmutableList;
import com.mossy.pokerbot.Action;
import com.mossy.pokerbot.interfaces.player.IPlayerState;
import com.mossy.pokerbot.interfaces.state.IGameState;
import com.mossy.pokerbot.interfaces.state.IGameStateFactory;

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
