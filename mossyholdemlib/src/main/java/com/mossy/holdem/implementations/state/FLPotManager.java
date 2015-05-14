package com.mossy.holdem.implementations.state;

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
    final private Street gameStage;

    FLPotManager(ChipStack lowerLimit, ChipStack higherLimit,
                 ImmutableList<IPlayerState> playerStates,
                 Street gameStage,
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

    private ChipStack getHighestBet()
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

    private boolean hasBets()
    {
        return getHighestBet().equals(ChipStack.NO_CHIPS);
    }

    @Override
    public IPotManager nextAction(Action a) throws Exception
    {
    }



    private ChipStack getCurentBetLimit()
    {
        if(gameStage == Street.RIVER || gameStage == Street.TURN)
        {
            return higherLimit;
        }
        return lowerLimit;
    }
}
*/