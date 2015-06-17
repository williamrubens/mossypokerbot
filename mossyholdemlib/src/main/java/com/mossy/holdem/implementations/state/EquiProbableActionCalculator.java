package com.mossy.holdem.implementations.state;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mossy.holdem.Action;
import com.mossy.holdem.interfaces.state.IActionProbabilityCalculator;
import com.mossy.holdem.interfaces.state.IGameState;

/**
 * Created by willrubens on 16/06/15.
 */
public class EquiProbableActionCalculator implements IActionProbabilityCalculator {
    @Override
    public ImmutableMap<Action.ActionType, Float> calculateProbability(IGameState state, ImmutableList<Action.ActionType> actions) {
        float numberOfActions = (float)actions.size();
        return FluentIterable.from(actions).toMap(action -> Float.valueOf(1.0f / numberOfActions));
    }
}
