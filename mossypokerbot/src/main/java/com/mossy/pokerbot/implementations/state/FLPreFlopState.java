package com.mossy.pokerbot.implementations.state;

/*
public class FLPreFlopState implements IGameState
{


    final IPotManager potManager;

    FLPreFlopState(IPotManager potManager)
    {
        this.potManager = potManager;
    }

    @Override
    public Street street()
    {
        return Street.PRE_FLOP;
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