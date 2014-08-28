package com.mossy.holdem.implementations.state;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.interfaces.state.IGameState;
import com.mossy.holdem.interfaces.state.IPlayerState;
import com.mossy.holdem.interfaces.state.IPlayerStateFactory;

/**
 * Created by williamrubens on 18/08/2014.
 */
public class FLStateFactory
{
    IPlayerStateFactory playerStateFactory;

    public FLStateFactory(IPlayerStateFactory playerStateFactory)
    {
        this.playerStateFactory = playerStateFactory;
    }

    private ImmutableList<IPlayerState> updatePlayerList(ImmutableList<IPlayerState> playerStates, IPlayerState oldPlayer, IPlayerState newPlayer)
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

    IGameState buildNextState(IGameState currentState, Action nextAction) throws Exception
    {
        if(!(currentState instanceof FixedLimitState))
        {
            throw new Exception("Cannot build next Fixed Limit state from a non Fixed Limit curentState");
        }

        FixedLimitState currentFLState = (FixedLimitState)currentState;

        IPlayerState nextPlayer = currentFLState.getNextPlayer();
        int numPlayers = currentFLState.playerStates().size();

        if(nextAction.type() == Action.ActionType.CALL)
        {
            if(currentState.isPotOpen())
            {
                throw new Exception(String.format("Cannot call a pot with no raise"));
            }

            IPlayerState nextPlayerUpdated = playerStateFactory.updatePlayer(nextPlayer, nextAction);

            ImmutableList<IPlayerState> newPlayerStates = updatePlayerList(currentState.playerStates(), nextPlayer, nextPlayerUpdated);

            return new FixedLimitState(currentFLState.lowerLimit(), currentFLState.higherLimit(), newPlayerStates, currentFLState.stage(), currentFLState.dealerPosition(), currentFLState.nextToPlay() + 1 % numPlayers);
        }
        if(nextAction.type() == Action.ActionType.BET)
        {
            // todo check bet is appropriate size

            IPlayerState nextPlayerUpdated = playerStateFactory.updatePlayer(nextPlayer, nextAction);

            ImmutableList<IPlayerState> newPlayerStates = updatePlayerList(currentState.playerStates(), nextPlayer, nextPlayerUpdated);

            return new FixedLimitState(currentFLState.lowerLimit(), currentFLState.higherLimit(), newPlayerStates, currentFLState.stage(), currentFLState.dealerPosition(), currentFLState.nextToPlay() + 1 % numPlayers);
        }
        if(nextAction.type() == Action.ActionType.FOLD)
        {

            IPlayerState nextPlayerUpdated = playerStateFactory.updatePlayer(nextPlayer, nextAction);

            ImmutableList<IPlayerState> newPlayerStates = updatePlayerList(currentState.playerStates(), nextPlayer, nextPlayerUpdated);

            return new FixedLimitState(currentFLState.lowerLimit(), currentFLState.higherLimit(), newPlayerStates, currentFLState.stage(), currentFLState.dealerPosition(), currentFLState.nextToPlay() + 1 % numPlayers);
        }
        if(nextAction.type() == Action.ActionType.CHECK)
        {
            if(!currentFLState.isPotOpen())
            {
                throw new Exception("Cannot check pot that has a raise in it already");
            }
            return new FixedLimitState(currentFLState.lowerLimit(), currentFLState.higherLimit(), currentState.playerStates(), currentFLState.stage(), currentFLState.dealerPosition(), currentFLState.nextToPlay() + 1 % numPlayers);
        }

        throw new Exception(String.format("Unexpected  action %s", nextAction.type()));
    }
}
