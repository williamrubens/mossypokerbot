package com.mossy.pokerbot.implementations.state;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mossy.pokerbot.Action;
import com.mossy.pokerbot.interfaces.state.IActionProbabilityCalculator;
import com.mossy.pokerbot.interfaces.state.IGameState;

/**
 * Created by willrubens on 16/06/15.
 */
public class EquiProbableActionCalculator implements IActionProbabilityCalculator {
    @Override
    public ImmutableMap<Action, Double> calculateProbability(IGameState state, ImmutableList<Action> actions) {
        double numberOfActions = (double)actions.size();
        return FluentIterable.from(actions).toMap(action -> Double.valueOf(1.0f / numberOfActions));
    }
}
