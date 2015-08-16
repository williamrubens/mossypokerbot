package com.mossy.holdem.interfaces.player;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mossy.holdem.Action;
import com.mossy.holdem.interfaces.state.IGameState;

/**
 * Created by willrubens on 13/06/15.
 */
public interface IPlayerStatisticsHolder {

    IPlayerStatistics updatePlayer(IGameState gameState,Action action);
    IPlayerStatistics get(int playerId);
    ImmutableMap<Integer, IPlayerStatistics> getAll();

}
