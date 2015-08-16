package com.mossy.holdem.interfaces.player;

import com.mossy.holdem.Action;
import com.mossy.holdem.interfaces.state.IGameState;

/**
 * Created by willrubens on 13/06/15.
 */
public interface IPlayerStatistics {

    int id();

    double vpip();

    double pfr();

    boolean hasRaisedPreFlopThisRound();
    int preFlopRaisesThisRound();

    int aggressionCountThisStreet();


}
