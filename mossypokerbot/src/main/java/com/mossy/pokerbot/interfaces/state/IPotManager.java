package com.mossy.pokerbot.interfaces.state;

import com.google.common.collect.ImmutableList;
import com.mossy.pokerbot.Action;

/**
 * Created by williamrubens on 16/08/2014.
 */
public interface IPotManager
{

    int       numPlayers();

    IPotManager nextAction(Action a) throws Exception;
    ImmutableList<Action> possibleActions();
}
