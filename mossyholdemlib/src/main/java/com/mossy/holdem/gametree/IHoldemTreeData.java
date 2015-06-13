package com.mossy.holdem.gametree;

import com.mossy.holdem.interfaces.state.IGameState;

/**
 * Created by williamrubens on 09/08/2014.
 */
public interface IHoldemTreeData
{
    double probability();
    IGameState state();

}
