package com.mossy.pokerbot.gametree;

import com.mossy.pokerbot.interfaces.state.IGameState;

/**
 * Created by williamrubens on 09/08/2014.
 */
public interface IHoldemTreeData
{
    double probability();
    IGameState state();

}
