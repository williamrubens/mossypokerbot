package com.mossy.holdem.interfaces.state;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mossy.holdem.Action;

/**
 * Created by willrubens on 13/06/15.
 */

public interface IActionProbabilityCalculator {
    ImmutableMap<Action.ActionType, Float> calculateProbability(IGameState state, ImmutableList<Action> actions);
}
