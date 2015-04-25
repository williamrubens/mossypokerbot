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
    final ImmutableList<IPlayerInfo> playerStates;
    final private GameStage gameStage;

    FLPotManager(ChipStack lowerLimit, ChipStack higherLimit,
                 ImmutableList<IPlayerInfo> playerStates,
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


    private ImmutableList<IPlayerInfo> updatePlayerList(IPlayerInfo oldPlayer, IPlayerInfo newPlayer)
    {
        // update player list
        ImmutableList.Builder<IPlayerInfo> playerStatesBuilder = ImmutableList.builder();

        for(IPlayerInfo player : playerStates)
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

    private IPlayerInfo getLastPlayerState()
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
        for(IPlayerInfo p : playerStates)
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
        for (IPlayerInfo p : playerStates)
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
        return getHighestBet().equals(ChipStack.NO_CHIPS);
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