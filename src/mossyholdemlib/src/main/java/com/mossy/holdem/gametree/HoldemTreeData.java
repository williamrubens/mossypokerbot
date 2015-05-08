package com.mossy.holdem.gametree;

import com.mossy.holdem.interfaces.state.IGameState;

/**
 * Created by williamrubens on 08/09/2014.
 */
public class HoldemTreeData implements  IHoldemTreeData
{
    public HoldemTreeData(IGameState state)
    {
        this.state = state;
    }

    IGameState state;

    @Override
    public IGameState state()
    {
        return state;
    }
}
