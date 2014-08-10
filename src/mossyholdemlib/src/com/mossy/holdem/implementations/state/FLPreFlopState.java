package com.mossy.holdem.implementations.state;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.Card;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.GameStage;
import com.mossy.holdem.interfaces.IBoardCardDealer;
import com.mossy.holdem.interfaces.IGameState;
import com.mossy.holdem.interfaces.IPlayerState;

import java.math.BigDecimal;

public class FLPreFlopState implements IGameState
{
    final private int nextToPlay;
    final private int dealerPos;
    final private ChipStack lowerLimit;
    final private ChipStack higherLimit;
    final ImmutableList<IPlayerState> playerStates;

    FLPreFlopState(ChipStack lowerLimit, ChipStack higherLimit,
                   ImmutableList<IPlayerState> playerStates, int dealerPos, int nextToPlay)
    {
        this.lowerLimit = lowerLimit;
        this.higherLimit = higherLimit;
        this.playerStates = playerStates;
        this.nextToPlay = nextToPlay;
        this.dealerPos = dealerPos;
    }

    @Override
    public GameStage stage()
    {
        return GameStage.PRE_FLOP;
    }

    @Override
    public int numPlayers()
    {
        return playerStates.size();
    }

    @Override
    public ChipStack potSize()
    {
        ChipStack pot = new ChipStack(BigDecimal.ZERO);
        for(IPlayerState p : playerStates)
        {
            pot = pot.add(p.pot());
        }
        return pot;
    }

    @Override
    public ImmutableList<Action> actionHistory()
    {
        return null;
    }

    @Override
    public ImmutableList<Card> boardCards()
    {
        return null;
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

    @Override
    public IGameState nextState(Action a) throws Exception
    {
        if(a.type() == Action.ActionType.CALL)
        {
            IPlayerState nextPlayer = playerStates.get(nextToPlay);

            ChipStack amountToCall = getCurrentRaise().subtract(nextPlayer.pot());

            IPlayerState nextPlayerUpdated = nextPlayer.play(amountToCall);

            // update player list
            ImmutableList.Builder<IPlayerState> playerStatesBuilder = ImmutableList.builder();

            for(IPlayerState player : playerStates)
            {
                if(player == nextPlayer)
                {
                    playerStatesBuilder.add(nextPlayerUpdated);
                }
                else
                {
                    playerStatesBuilder.add(player);
                }

            }

            // else move to next player
            return new FLPreFlopState(lowerLimit, higherLimit, playerStatesBuilder.build(), dealerPos, nextToPlay + 1 % numPlayers());
        }
        if(a.type() == Action.ActionType.DEAL_FLOP)
        {
            // return new FlopState
        }
        throw new Exception(String.format("Unexpected preflop action %s", a.type()));

    }
}
