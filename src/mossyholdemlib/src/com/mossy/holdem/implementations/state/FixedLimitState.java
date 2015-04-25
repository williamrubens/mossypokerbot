package com.mossy.holdem.implementations.state;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.GameStage;
import com.mossy.holdem.interfaces.state.IFixedLimitState;
import com.mossy.holdem.interfaces.state.IPlayerInfo;

/**
 * Created by williamrubens on 18/08/2014.
 */
public class FixedLimitState implements IFixedLimitState
{
    final private int dealerPos;
    final private ChipStack lowerLimit;
    final private ChipStack higherLimit;
    final ImmutableList<IPlayerInfo> playerStates;
    final private GameStage gameStage;

    FixedLimitState(ChipStack lowerLimit, ChipStack higherLimit,
                 ImmutableList<IPlayerInfo> playerStates,
                 GameStage gameStage,
                 int dealerPos)
    {
        this.lowerLimit = lowerLimit;
        this.higherLimit = higherLimit;
        this.playerStates = playerStates;
        this.dealerPos = dealerPos;
        this.gameStage = gameStage;
    }

    @Override
    public ChipStack lowerLimit()
    {
        return lowerLimit;
    }

    @Override
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

    private int numPlayers()
    {
        return playerStates.size();
    }


    public int nextPlayerSeat() throws Exception
    {
        ChipStack lastPlayerBet = ChipStack.NO_CHIPS;
        // start with next player after dealer, work out who is next
        int nextPlayeSeat = playerAfter(dealerPos);
        boolean fullCircle = false;

        while(nextPlayeSeat != dealerPos || fullCircle == false)
        {
            IPlayerInfo nextPlayerInfo = playerStates.get(nextPlayeSeat);
            if(isPotOpen() && !nextPlayerInfo.hasChecked())
            {
                return nextPlayeSeat;
            }
            else if(!isPotOpen() && !nextPlayerInfo.isOut() && nextPlayerInfo.pot().compareTo(lastPlayerBet) < 0)
            {
                return nextPlayeSeat;
            }

            lastPlayerBet = nextPlayerInfo.pot();
            nextPlayeSeat = playerAfter(nextPlayeSeat);
            if(nextPlayeSeat == playerAfter(dealerPos))
            {
                fullCircle = true;
            }
        }


        // if we get here, it means that everyone has put in the same amount of money, which means it's a dealer action OR
        // we are pre flop and it's the big blind's turn to check or bet
        if(gameStage == GameStage.PRE_FLOP && !playerStates.get(playerAfter(playerAfter(dealerPos))).hasChecked() )
        {
            return playerAfter(playerAfter(dealerPos));
        }
        // it's a dealer action
        // return -1;
        throw new Exception("Cannot determine next player");
    }

    @Override
    public int playerAfter(int currentPlayer)
    {
        return (currentPlayer + 1) % playerStates().size();
    }

    @Override
    public ImmutableList<IPlayerInfo> playerStates()
    {
        return playerStates;
    }



    @Override
    public IPlayerInfo getNextPlayer() throws Exception
    {
        return playerStates.get(nextPlayerSeat());
    }


    @Override
    public ChipStack getHighestBet()
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

    @Override
    public ChipStack getAmountToCall() throws  Exception
    {
        return getHighestBet().subtract(getNextPlayer().pot());
    }

    @Override
    public boolean isPotOpen()
    {
        return getHighestBet().equals(ChipStack.NO_CHIPS);
    }

    @Override
    public ChipStack getCurrentBetLimit()
    {
        if(gameStage == GameStage.RIVER || gameStage == GameStage.TURN)
        {
            return higherLimit;
        }
        return lowerLimit;
    }


}
