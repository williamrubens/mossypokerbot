package com.mossy.holdem.interfaces.state;

import com.mossy.holdem.Action;

/**
 * Created by williamrubens on 21/08/2014.
 */
public interface IPlayerInfoFactory
{
    IPlayerInfo updatePlayer(IPlayerInfo currentState, Action action) throws  Exception;
}
