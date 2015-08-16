package com.mossy.pokerbot.interfaces.player;

import com.mossy.pokerbot.Action;
import com.mossy.pokerbot.ChipStack;
import com.mossy.pokerbot.interfaces.state.IGameState;

/**
 * Created by williamrubens on 21/08/2014.
 */
public interface IPlayerInfoFactory
{
    IPlayerState newPlayer(int id, ChipStack bank);
    IPlayerState updatePlayer(IPlayerState currentState, Action action, IGameState gameState);
}
