package com.mossy.holdem.interfaces.player;

import com.mossy.holdem.ProbabilityTriple;
import com.mossy.holdem.interfaces.state.IGameState;

/**
 * Created by willrubens on 13/06/15.
 */
public interface IPlayerModel {
    ProbabilityTriple calculateActionProbabilties(IGameState state);
}
