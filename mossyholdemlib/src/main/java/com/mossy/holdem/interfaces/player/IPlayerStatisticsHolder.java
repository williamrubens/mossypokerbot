package com.mossy.holdem.interfaces.player;

/**
 * Created by willrubens on 13/06/15.
 */
public interface IPlayerStatisticsHolder {
    IPlayerStatistics getStats(Integer nextPlayerId);
}
