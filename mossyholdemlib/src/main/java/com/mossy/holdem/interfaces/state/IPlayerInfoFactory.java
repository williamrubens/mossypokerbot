package com.mossy.holdem.interfaces.state;

import com.mossy.holdem.Action;

/**
 * Created by williamrubens on 21/08/2014.
 */
public interface IPlayerInfoFactory
{
    IPlayerState updatePlayer(IPlayerState currentState, Action action, IGameState gameState) throws  Exception;
}
