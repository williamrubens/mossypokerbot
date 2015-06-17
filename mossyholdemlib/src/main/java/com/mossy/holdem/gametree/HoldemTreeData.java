package com.mossy.holdem.gametree;

import com.mossy.holdem.interfaces.state.IGameState;

/**
 * Created by williamrubens on 08/09/2014.
 */
public class HoldemTreeData implements  IHoldemTreeData
{
    final double probability;

    public HoldemTreeData(IGameState state, double probabliity)
    {
        this.state = state;
        this.probability = probabliity;
    }

    IGameState state;

    @Override
    public double probability() {return probability;}

    @Override
    public IGameState state()
    {
        return state;
    }
    @Override
    public String toString() {
        return state.toString() + " " + String.valueOf(probability);
    }
}
