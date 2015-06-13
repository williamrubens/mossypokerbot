package com.mossy.holdem.interfaces;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.Card;
import com.mossy.holdem.interfaces.state.IGameState;

/**
 * Created by willrubens on 18/05/15.
 */
public interface IDealerActionBuilder {

    ImmutableList<Action> dealAllFlops(IGameState gameState);
    ImmutableList<Action> dealAllTurns(IGameState gameState);
    ImmutableList<Action> dealAllRivers(IGameState gameState);

}
