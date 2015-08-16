package com.mossy.pokerbot.interfaces.state;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mossy.pokerbot.Action;

/**
 * Created by willrubens on 13/06/15.
 */

public interface IActionProbabilityCalculator {
    ImmutableMap<Action, Double> calculateProbability(IGameState state, ImmutableList<Action> actions);
}
