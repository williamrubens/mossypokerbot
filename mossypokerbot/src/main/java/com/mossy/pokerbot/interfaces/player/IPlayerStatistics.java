package com.mossy.pokerbot.interfaces.player;

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
