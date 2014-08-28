package com.mossy.holdem.implementations.state;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.Card;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.GameStage;
import com.mossy.holdem.interfaces.state.IGameState;
import com.mossy.holdem.interfaces.state.IPlayerState;
import com.mossy.holdem.interfaces.state.IPotManager;

import java.math.BigDecimal;
/*
public class FLPreFlopState implements IGameState
{


    final IPotManager potManager;

    FLPreFlopState(IPotManager potManager)
    {
        this.potManager = potManager;
    }

    @Override
    public GameStage stage()
    {
        return GameStage.PRE_FLOP;
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