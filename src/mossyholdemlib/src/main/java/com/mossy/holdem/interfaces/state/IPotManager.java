package com.mossy.holdem.interfaces.state;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;

/**
 * Created by williamrubens on 16/08/2014.
 */
public interface IPotManager
{

    int       numPlayers();

    IPotManager nextAction(Action a) throws Exception;
    ImmutableList<Action> possibleActions();
}
