package com.mossy.holdem.implementations.state;

/**
 * Created by williamrubens on 18/08/2014.
 *//*
public class FlopState implements IGameState
{
    public FlopState(ImmutableList<Card> cards, IPotManager potManager)
    {
    }

    @Override
    public Street street()
    {
         return Street.FLOP
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