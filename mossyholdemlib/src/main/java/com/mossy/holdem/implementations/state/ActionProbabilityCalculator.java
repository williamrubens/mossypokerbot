package com.mossy.holdem.implementations.state;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mossy.holdem.Action;
import com.mossy.holdem.ProbabilityTriple;
import com.mossy.holdem.interfaces.player.IPlayerModel;
import com.mossy.holdem.interfaces.player.IPlayerStatistics;
import com.mossy.holdem.interfaces.player.IPlayerStatisticsHolder;
import com.mossy.holdem.interfaces.state.IGameState;
import com.mossy.holdem.interfaces.state.IActionProbabilityCalculator;

/**
 * Created by willrubens on 13/06/15.
 */
public class ActionProbabilityCalculator implements IActionProbabilityCalculator {

    final IPlayerStatisticsHolder statsHolder;
    final IPlayerModel playerModel;

    ActionProbabilityCalculator(IPlayerStatisticsHolder statsHolder, IPlayerModel playerModel) {
        this.statsHolder = statsHolder;
        this.playerModel = playerModel;
    }

    @Override
    public ImmutableMap<Action.ActionType, Float> calculateProbability(IGameState state, ImmutableList<Action.ActionType> actionTypes) {

        if(actionTypes.size() == 1 ){
            return ImmutableMap.of(actionTypes.get(0), Float.valueOf(1.0f));
        }

        if(actionTypes.get(0) == Action.ActionType.WIN) {
            ImmutableMap.Builder mapBuilder = ImmutableMap.builder();
            for(Action.ActionType action : actionTypes) {
                mapBuilder.put(action, Float.valueOf(1.0f / (float)actionTypes.size()) );
            }
            return mapBuilder.build();
        }

        Integer nextPlayerId = Integer.valueOf(state.nextPlayer().id());

        IPlayerStatistics playerStats = statsHolder.getStats(nextPlayerId);

        ProbabilityTriple probTriple = playerModel.calculateActionProbabilties(playerStats, state);

        if(actionTypes.size() == 2) {
            return ImmutableMap.of(Action.ActionType.FOLD, Float.valueOf(probTriple.fold()),
                    Action.ActionType.CALL, Float.valueOf(probTriple.call() + probTriple.raise()));
        }
        if(actionTypes.contains(Action.ActionType.CALL)) {
            return ImmutableMap.of(Action.ActionType.FOLD, Float.valueOf(probTriple.fold()),
                    Action.ActionType.CALL, Float.valueOf(probTriple.call()),
                    Action.ActionType.RAISE_TO, Float.valueOf(probTriple.raise()));
        }

        return ImmutableMap.of(Action.ActionType.FOLD, Float.valueOf(probTriple.fold()),
                Action.ActionType.CHECK, Float.valueOf(probTriple.call()),
                Action.ActionType.BET, Float.valueOf(probTriple.raise()));
    }
}
