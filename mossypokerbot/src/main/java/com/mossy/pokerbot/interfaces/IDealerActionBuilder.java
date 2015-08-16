package com.mossy.pokerbot.interfaces;

import com.google.common.collect.ImmutableList;
import com.mossy.pokerbot.Action;
import com.mossy.pokerbot.interfaces.state.IGameState;

/**
 * Created by willrubens on 18/05/15.
 */
public interface IDealerActionBuilder {

    ImmutableList<Action> dealAllFlops(IGameState gameState);
    ImmutableList<Action> dealAllTurns(IGameState gameState);
    ImmutableList<Action> dealAllRivers(IGameState gameState);

}
