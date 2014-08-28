package com.mossy.holdem.interfaces.state;

import com.mossy.holdem.Action;

/**
 * Created by williamrubens on 21/08/2014.
 */
public interface IPlayerStateFactory
{
    IPlayerState updatePlayer(IPlayerState currentState, Action action) throws  Exception;
}
