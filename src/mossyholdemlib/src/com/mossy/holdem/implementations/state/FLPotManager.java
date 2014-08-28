package com.mossy.holdem.implementations.state;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.GameStage;
import com.mossy.holdem.interfaces.state.IPotManager;
import com.mossy.holdem.interfaces.state.IPlayerState;

/**
 * Created by williamrubens on 16/08/2014.
 *//*
public class FLPotManager implements IPotManager
{
    final private int nextToPlay;
    final private int dealerPos;
    final private ChipStack lowerLimit;
    final private ChipStack higherLimit;
    final ImmutableList<IPlayerState> playerStates;
    final private GameStage gameStage;

    FLPotManager(ChipStack lowerLimit, ChipStack higherLimit,
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


    @Override
    public int numPlayers()
    {
        return playerStates.size();
    }


    private ImmutableList<IPlayerState> updatePlayerList(IPlayerState oldPlayer, IPlayerState newPlayer)
    {
        // update player list
        ImmutableList.Builder<IPlayerState> playerStatesBuilder = ImmutableList.builder();

        for(IPlayerState player : playerStates)
        {
            if(player == oldPlayer)
            {
                playerStatesBuilder.add(newPlayer);
            }
            else
            {
                playerStatesBuilder.add(player);
            }

        }
        return playerStatesBuilder.build();
    }

    private IPlayerState getLastPlayerState()
    {
        int lastPlayer = nextToPlay - 1;
        if(lastPlayer < 0)
        {
            lastPlayer = numPlayers() - 1;
        }
        return playerStates.get(lastPlayer);
    }

    private boolean areAllPlayersEven()
    {
        ChipStack playerPot = ChipStack.NO_CHIPS;
        for(IPlayerState p : playerStates)
        {
            if(p.isOut())
            {
                continue;
            }
            if(playerPot == ChipStack.NO_CHIPS)
            {
                playerPot = p.pot();
                continue;
            }
            if(!p.pot().equals(playerPot))
            {
                return false;
            }
        }
        return true;
    }

    private ChipStack getCurrentRaise()
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

    private boolean isPotOpen()
    {
        return getCurrentRaise().equals(ChipStack.NO_CHIPS);
    }

    @Override
    public IPotManager nextAction(Action a) throws Exception
    {
    }



    private ChipStack getCurentBetLimit()
    {
        if(gameStage == GameStage.RIVER || gameStage == GameStage.TURN)
        {
            return higherLimit;
        }
        return lowerLimit;
    }
}
*/