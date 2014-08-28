package com.mossy.holdem.implementations.state;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.Card;
import com.mossy.holdem.GameStage;
import com.mossy.holdem.interfaces.state.IGameState;
import com.mossy.holdem.interfaces.state.IPotManager;

/**
 * Created by williamrubens on 18/08/2014.
 *//*
public class FlopState implements IGameState
{
    public FlopState(ImmutableList<Card> cards, IPotManager potManager)
    {
    }

    @Override
    public GameStage stage()
    {
         return GameStage.FLOP
    }

    @Override
    public IGameState nextState(Action a) throws Exception
    {
        if(a.isPlayerAction())
        {
            return new FLPreFlopState(potManager.nextAction(a));
        }
        if(a.type() != Action.ActionType.DEAL_FLOP)
        {
            throw new Exception(String.format("Unexpected preflop action %s", a.type()))
        }
        return new FlopState(a.cards(), potManager);
    }
}
*/