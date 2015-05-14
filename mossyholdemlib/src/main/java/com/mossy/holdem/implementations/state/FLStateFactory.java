package com.mossy.holdem.implementations.state;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.Street;
import com.mossy.holdem.implementations.ImmutableListCollector;
import com.mossy.holdem.interfaces.state.IGameState;
import com.mossy.holdem.interfaces.state.IGameStateFactory;
import com.mossy.holdem.interfaces.state.IPlayerState;
import com.mossy.holdem.interfaces.state.IPlayerInfoFactory;

import java.util.stream.Stream;

/**
 * Created by williamrubens on 18/08/2014.
 */
public class FLStateFactory implements IGameStateFactory
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

    private ImmutableList<IPlayerState> updatePlayerList(ImmutableList<IPlayerState> playerStates, IPlayerState newPlayer)
    {
        // update player list
        ImmutableList.Builder<IPlayerState> playerStatesBuilder = ImmutableList.builder();

        for(IPlayerState player : playerStates)
        {
            if(player.id() == newPlayer.id())
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

    private ImmutableList<IPlayerState> updatePlayers(ImmutableList<IPlayerState> playerStates, IPlayerState newPlayer)
    {
        // update player list
        ImmutableList.Builder<IPlayerState> playerStatesBuilder = ImmutableList.builder();

        for(IPlayerState player : playerStates)
        {
            if(player.id() == newPlayer.id())
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

    public IGameState buildNewState(ImmutableList<IPlayerState> playerStates, int dealerPosition, int raiseCap)
    {
        return new FixedLimitState(lowerLimit, higherLimit, playerStates, Street.PRE_FLOP, dealerPosition, 0, raiseCap, Action.Factory.anteAction());
    }


    @Override
    public IGameState buildNextState(IGameState currentState, Action nextAction) throws Exception
    {
        if(!(currentState instanceof FixedLimitState))
        {
            throw new Exception("Cannot build next Fixed Limit state from a non Fixed Limit curentState");
        }

        FixedLimitState currentFLState = (FixedLimitState)currentState;

        IPlayerState nextPlayer = currentFLState.getNextPlayer();
        int dealerPosition = currentState.dealerPosition();
        int numberOfRaises = currentFLState.numberOfRaises();


        if(nextAction.type() == Action.ActionType.CALL)
        {
            if(!currentState.hasBets())
            {
                throw new Exception(String.format("Cannot call a pot with no raise"));
            }
         }
        else if(nextAction.type() == Action.ActionType.BET)
        {
            // todo check bet is appropriate size
        }
        else if(nextAction.type() == Action.ActionType.RAISE) {
            numberOfRaises++;
        }
        else if(nextAction.type() == Action.ActionType.FOLD)
        {
        }
         else if(nextAction.type() == Action.ActionType.CHECK)
        {
            if(currentFLState.hasBets() && currentFLState.street() != Street.PRE_FLOP)
            {
                throw new Exception("Cannot check pot that has a raise in it already");
            }
        }
        else if(nextAction.type() == Action.ActionType.SMALL_BLIND)
        {
            if(currentFLState.hasBets())
            {
                throw new Exception("Cannot post small blind when bets already open");
            }

        }
        else if(nextAction.type() == Action.ActionType.WIN)
        {
            ImmutableList<IPlayerState> newPlayerStates = currentState.playerStates().stream()
                    .map(player -> {
                        try {
                            return playerStateFactory.updatePlayer(player, nextAction, currentState);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(new ImmutableListCollector<>());

            return new FixedLimitState(lowerLimit, higherLimit, newPlayerStates, Street.FINISHED, currentState.playerAfter(dealerPosition), numberOfRaises, currentFLState.raiseCap(), nextAction );
        }

        IPlayerState nextPlayerUpdated = playerStateFactory.updatePlayer(nextPlayer, nextAction, currentState);

        ImmutableList<IPlayerState> newPlayerStates = updatePlayerList(currentState.playerStates(), nextPlayerUpdated);

        return new FixedLimitState(lowerLimit, higherLimit, newPlayerStates, currentFLState.street(), dealerPosition, numberOfRaises, currentFLState.raiseCap(), nextAction );

        //throw new Exception(String.format("Unexpected  action %s", nextAction.type()));
    }
}
