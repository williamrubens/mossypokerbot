package com.mossy.holdem.implementations.player;

import com.mossy.holdem.interfaces.player.IPlayerStatistics;
import com.mossy.holdem.interfaces.player.IPlayerStatisticsHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by willrubens on 13/06/15.
 */
public class PlayerStatisticsHolder implements IPlayerStatisticsHolder {

    Map<Integer, PlayerStatistics> playersToStats = new HashMap<Integer, PlayerStatistics>();

    @Override
    public IPlayerStatistics getStats(Integer nextPlayerId) {
        PlayerStatistics stats = playersToStats.get(nextPlayerId);
        if(stats == null) {
            stats = new PlayerStatistics(0.1f, 0.1f);
            playersToStats.put(nextPlayerId, stats);
        }
        return stats;
    }
}
