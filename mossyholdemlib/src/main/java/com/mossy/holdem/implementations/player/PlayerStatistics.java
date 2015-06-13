package com.mossy.holdem.implementations.player;

import com.mossy.holdem.interfaces.player.IPlayerStatistics;

/**
 * Created by willrubens on 13/06/15.
 */
class PlayerStatistics implements IPlayerStatistics{

    final float vpip;
    final float pfr;

    public PlayerStatistics(float vpip, float pfr) {
        this.vpip = vpip;
        this.pfr = pfr;
    }


    @Override
    public float vpip() {
        return vpip;
    }

    @Override
    public float pfr() {
        return pfr;
    }
}
