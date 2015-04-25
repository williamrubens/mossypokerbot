package com.mossy.holdem.implementations.state;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.GameStage;
import com.mossy.holdem.interfaces.state.IGameState;
import com.mossy.holdem.interfaces.state.IPlayerInfo;
import com.mossy.holdem.interfaces.state.IPlayerInfoFactory;

/**
 * Created by williamrubens on 18/08/2014.
 */
public class FLStateFactory
{
    final IPlayerInfoFactory playerStateFactory;
    final ChipStack lowerLimit;
    final ChipStack higherLimit;

    public FLStateFactory(IPlayerInfoFactory playerStateFactory, ChipStack lowerLimit, ChipStack higherLimit)
    {
        this.playerStateFactory = playerStateFactory;
        this.lowerLimit = lowerLimit;
        this.higherLimit = higherLimit;
    }

    private ImmutableList<IPlayerInfo> updatePlayerList(ImmutableList<IPlayerInfo> playerStates, IPlayerInfo oldPlayer, IPlayerInfo newPlayer)
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

    IGameState buildNewState(ImmutableList<IPlayerInfo> playerStates, int dealerPosition)
    {
        return new FixedLimitState(lowerLimit, higherLimit, playerStates, GameStage.PRE_FLOP, dealerPosition);
    }


    IGameState buildNextState(IGameState currentState, Action nextAction) throws Exception
    {
        if(!(currentState instanceof FixedLimitState))
        {
            throw new Exception("Cannot build next Fixed Limit state from a non Fixed Limit curentState");
        }

        FixedLimitState currentFLState = (FixedLimitState)currentState;

        IPlayerInfo nextPlayer = currentFLState.getNextPlayer();
        int numPlayers = currentFLState.playerStates().size();
        int dealerPosition = currentState.dealerPosition();

        if(nextAction.type() == Action.ActionType.CALL)
        {
            if(currentState.isPotOpen())
            {
                throw new Exception(String.format("Cannot call a pot with no raise"));
            }

            IPlayerInfo nextPlayerUpdated = playerStateFactory.updatePlayer(nextPlayer, nextAction);

            ImmutableList<IPlayerInfo> newPlayerStates = updatePlayerList(currentState.playerStates(), nextPlayer, nextPlayerUpdated);

            return new FixedLimitState(lowerLimit, higherLimit, newPlayerStates, currentFLState.stage(), dealerPosition);
        }
        if(nextAction.type() == Action.ActionType.BET)
        {
            // todo check bet is appropriate size

            IPlayerInfo nextPlayerUpdated = playerStateFactory.updatePlayer(nextPlayer, nextAction);

            ImmutableList<IPlayerInfo> newPlayerStates = updatePlayerList(currentState.playerStates(), nextPlayer, nextPlayerUpdated);

            return new FixedLimitState(lowerLimit, higherLimit, newPlayerStates, currentFLState.stage(), dealerPosition);
        }
        if(nextAction.type() == Action.ActionType.FOLD)
        {

            IPlayerInfo nextPlayerUpdated = playerStateFactory.updatePlayer(nextPlayer, nextAction);

            ImmutableList<IPlayerInfo> newPlayerStates = updatePlayerList(currentState.playerStates(), nextPlayer, nextPlayerUpdated);

            return new FixedLimitState(lowerLimit, higherLimit,  newPlayerStates, currentFLState.stage(), dealerPosition);
        }
        if(nextAction.type() == Action.ActionType.CHECK)
        {
            if(!currentFLState.isPotOpen())
            {
                throw new Exception("Cannot check pot that has a raise in it already");
            }
            return new FixedLimitState(lowerLimit, higherLimit,  currentState.playerStates(), currentFLState.stage(), dealerPosition);
        }

        throw new Exception(String.format("Unexpected  action %s", nextAction.type()));
    }
}
