package com.mossy.holdem.interfaces.player;

import com.mossy.holdem.Action;
import com.mossy.holdem.interfaces.state.IGameState;

/**
 * Created by williamrubens on 21/08/2014.
 */
public interface IPlayerInfoFactory
{
    IPlayerState updatePlayer(IPlayerState currentState, Action action, IGameState gameState);
}
