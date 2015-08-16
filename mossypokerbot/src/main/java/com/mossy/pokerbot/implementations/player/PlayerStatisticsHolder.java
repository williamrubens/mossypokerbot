package com.mossy.pokerbot.implementations.player;

import com.google.common.collect.ImmutableMap;
import com.mossy.pokerbot.Action;
import com.mossy.pokerbot.interfaces.player.IPlayerStatistics;
import com.mossy.pokerbot.interfaces.player.IPlayerStatisticsHolder;
import com.mossy.pokerbot.interfaces.state.IGameState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by willrubens on 13/06/15.
 */
public class PlayerStatisticsHolder implements IPlayerStatisticsHolder {

    final static private Logger log = LogManager.getLogger(PlayerStatisticsHolder.class);

    Map<Integer, PlayerStatistics> playersToStats = new ConcurrentHashMap<>();


    @Override
    public IPlayerStatistics updatePlayer(IGameState gameState, Action action) {

        if(action.isDealerAction()) {
            return new PlayerStatistics(-1);
        }
        int playerId = gameState.nextPlayer().id();
        PlayerStatistics stats = playersToStats.get(playerId);
        if(stats == null) {
            log.info(String.format("Missing player Id %d, adding to map", playerId));
            stats = new PlayerStatistics(playerId);
            playersToStats.put(playerId, stats);
            return stats;
        }
        PlayerStatistics newState = stats.update(gameState, action);
        playersToStats.put(playerId, newState);
        return newState;
    }

    @Override
    public IPlayerStatistics get(int playerId) {
        PlayerStatistics stats = playersToStats.get(playerId);
        if(stats == null) {
           throw new RuntimeException(String.format("No stats for player id %d", playerId));
        }
        return stats;
    }

    @Override
    public ImmutableMap<Integer, IPlayerStatistics> getAll() {
        return ImmutableMap.copyOf(playersToStats);
    }
}
