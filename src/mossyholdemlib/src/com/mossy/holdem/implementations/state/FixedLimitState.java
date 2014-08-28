package com.mossy.holdem.implementations.state;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.GameStage;
import com.mossy.holdem.interfaces.state.IGameState;
import com.mossy.holdem.interfaces.state.IPlayerState;

/**
 * Created by williamrubens on 18/08/2014.
 */
public class FixedLimitState implements IGameState
{
    final private int nextToPlay;
    final private int dealerPos;
    final private ChipStack lowerLimit;
    final private ChipStack higherLimit;
    final ImmutableList<IPlayerState> playerStates;
    final private GameStage gameStage;

    FixedLimitState(ChipStack lowerLimit, ChipStack higherLimit,
                 ImmutableList<IPlayerState> playerStates,
                 GameStage gameStage,
                 int dealerPos, int nextToPlay)
    {
        this.lowerLimit = lowerLimit;
        this.higherLimit = higherLimit;
        this.playerStates = playerStates;
        this.nextToPlay = nextToPlay;
        this.dealerPos = dealerPos;
        this.gameStage = gameStage;
    }

    public ChipStack lowerLimit()
    {
        return lowerLimit;
    }

    public ChipStack higherLimit()
    {
        return higherLimit;
    }


    @Override
    public GameStage stage()
    {
        return gameStage;
    }

    @Override
    public int dealerPosition()
    {
        return dealerPos;
    }

    @Override
    public int nextToPlay()
    {
        return nextToPlay;
    }

    @Override
    public ImmutableList<IPlayerState> playerStates()
    {
        return playerStates;
    }

    @Override
    public ImmutableList<Action> possibleActions()
    {
        if(isPotOpen())
        {
            return ImmutableList.of(Action.Factory.checkAction(), Action.Factory.foldAction(), Action.Factory.betAction(getCurrentBetLimit()));
        }
        return ImmutableList.of(Action.Factory.callAction(getCurrentCall()), Action.Factory.foldAction(), Action.Factory.betAction(getCurrentBetLimit()));

    }

    @Override
    public IPlayerState getNextPlayer()
    {
        return playerStates.get(nextToPlay);
    }


    @Override
    public ChipStack getCurrentRaise()
    {
        ChipStack currentRaise = ChipStack.NO_CHIPS;
        for (IPlayerState p : playerStates)
        {
            if(p.pot().compareTo(currentRaise) > 0)
            {
                currentRaise = p.pot();
            }
        }
        return currentRaise;
    }

    public ChipStack getCurrentCall()
    {
        return getCurrentRaise().subtract(getNextPlayer().pot());
    }

    @Override
    public boolean isPotOpen()
    {
        return getCurrentRaise().equals(ChipStack.NO_CHIPS);
    }


    public ChipStack getCurrentBetLimit()
    {
        if(gameStage == GameStage.RIVER || gameStage == GameStage.TURN)
        {
            return higherLimit;
        }
        return lowerLimit;
    }


}
