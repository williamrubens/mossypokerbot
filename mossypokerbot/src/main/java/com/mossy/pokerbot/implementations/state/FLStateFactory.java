package com.mossy.pokerbot.implementations.state;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mossy.pokerbot.Action;
import com.mossy.pokerbot.Card;
import com.mossy.pokerbot.ChipStack;
import com.mossy.pokerbot.Street;
import com.mossy.pokerbot.implementations.ImmutableListCollector;
import com.mossy.pokerbot.interfaces.state.IGameState;
import com.mossy.pokerbot.interfaces.state.IGameStateFactory;
import com.mossy.pokerbot.interfaces.player.IPlayerState;
import com.mossy.pokerbot.interfaces.player.IPlayerInfoFactory;

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

    public IGameState buildNewState(ImmutableList<IPlayerState> playerStates, int dealerPosition)
    {
        return buildNewState(playerStates, dealerPosition, 3);
    }

    public IGameState buildNewState(ImmutableList<IPlayerState> playerStates, int dealerPosition, int raiseCap)
    {
        return new FixedLimitState(lowerLimit, higherLimit, playerStates,Street.PRE_FLOP, dealerPosition, ImmutableList.<Card>of(), ImmutableMap.<Street, ChipStack>of(),
                0, raiseCap, Action.Factory.dealHoleCards());
    }

    public IGameState buildState(Street street, ImmutableList<IPlayerState> playerStates, int dealerPosition, ImmutableList<Card> communityCards, Action lastAction, int raiseCap)
    {
        return new FixedLimitState(lowerLimit, higherLimit, playerStates, street, dealerPosition, communityCards, ImmutableMap.<Street, ChipStack>of(), 0, raiseCap,lastAction);
    }



    @Override
    public IGameState buildNextState(IGameState currentState, Action nextAction)
    {
        if(!(currentState instanceof FixedLimitState))
        {
            throw new RuntimeException("Cannot build next Fixed Limit state from a non Fixed Limit curentState");
        }

        FixedLimitState currentFLState = (FixedLimitState)currentState;


        int dealerPosition = currentState.dealerPosition();
        int numberOfRaises = currentFLState.numberOfRaises();
        ImmutableMap pots = currentState.pots();


        if(nextAction.type() == Action.ActionType.CALL)
        {
            if(!currentState.hasBets())
            {
                throw new RuntimeException(String.format("Cannot call a pot with no raise"));
            }
         }
        else if(nextAction.type() == Action.ActionType.BET)
        {
            // todo check bet is appropriate size
        }
        else if(nextAction.type() == Action.ActionType.RAISE_TO) {
            numberOfRaises++;
        }
        else if(nextAction.type() == Action.ActionType.FOLD)
        {
        }
         else if(nextAction.type() == Action.ActionType.CHECK) {
            if (currentFLState.hasBets() && currentFLState.street() != Street.PRE_FLOP) {
                throw new RuntimeException("Cannot check pot that has a raise in it already");
            }
        }
        else if(nextAction.type() == Action.ActionType.SMALL_BLIND) {
            if (currentFLState.hasBets()) {
                throw new RuntimeException("Cannot post small blind when bets already open");
            }
        }
        else if(nextAction.type() == Action.ActionType.SHOWDOWN) {


            return new FixedLimitState(lowerLimit, higherLimit, currentState.playerStates(), Street.SHOWDOWN, dealerPosition,currentFLState.communityCards(), currentState.pots(),  numberOfRaises, currentFLState.raiseCap(), nextAction);
        }
        else if(nextAction.type() == Action.ActionType.WIN) {
            ImmutableList<IPlayerState> newPlayerStates = currentState.playerStates().stream()
                    .map(player -> playerStateFactory.updatePlayer(player, nextAction, currentState))
                    .collect(new ImmutableListCollector<>());

            return new FixedLimitState(lowerLimit, higherLimit, newPlayerStates, Street.FINISHED, dealerPosition, currentState.communityCards(), ImmutableMap.of(), numberOfRaises, currentFLState.raiseCap(), nextAction);
        }
        else if (nextAction.isDealerAction()) {
            // clear away chips any bets
            ImmutableList.Builder<IPlayerState> playersBuilder = ImmutableList.builder();
            ChipStack pot = ChipStack.NO_CHIPS;

            for(IPlayerState player : currentState.playerStates()) {
                pot = pot.add(player.pot());
                playersBuilder.add(playerStateFactory.updatePlayer(player, nextAction, currentState));
            }
            // build new state

            ImmutableMap newPot = ImmutableMap.builder().putAll(currentState.pots()).put(currentState.street(), pot).build();

            ImmutableList nextCommunityCards = ImmutableList.builder().addAll(currentState.communityCards()).addAll(nextAction.cards()).build();

            return new FixedLimitState(lowerLimit, higherLimit, playersBuilder.build(), Street.nextStreet(currentState.street()), dealerPosition, nextCommunityCards, newPot, numberOfRaises,  currentFLState.raiseCap(), nextAction);

        }


        IPlayerState nextPlayer = currentFLState.nextPlayer();

        IPlayerState nextPlayerUpdated = playerStateFactory.updatePlayer(nextPlayer, nextAction, currentState);

        ImmutableList<IPlayerState> newPlayerStates = updatePlayerList(currentState.playerStates(), nextPlayerUpdated);

        return new FixedLimitState(lowerLimit, higherLimit, newPlayerStates, currentFLState.street(), dealerPosition, currentState.communityCards(),
                pots, numberOfRaises, currentFLState.raiseCap(), nextAction );

        //throw new Exception(String.format("Unexpected  action %s", nextAction.type()));
    }
}
