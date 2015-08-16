package com.mossy.pokerbot.interfaces.player;

import com.mossy.pokerbot.ProbabilityTriple;
import com.mossy.pokerbot.interfaces.state.IGameState;

/**
 * Created by willrubens on 13/06/15.
 */
public interface IPlayerModel {
    ProbabilityTriple calculateActionProbabilties(IGameState state);
}
