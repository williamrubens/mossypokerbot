package com.mossy.holdem.implementations.state;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.interfaces.state.IGameState;
import com.mossy.holdem.interfaces.state.IPlayerState;
import com.mossy.holdem.interfaces.state.IPlayerInfoFactory;

/**
 * Created by williamrubens on 21/08/2014.
 */
public class PlayerInfoFactory implements IPlayerInfoFactory
{
    @Override
    public IPlayerState updatePlayer(IPlayerState currentPlayer, Action action, IGameState gameState) throws Exception
    {
        if(action.type() == Action.ActionType.CHECK)
        {
            return new PlayerState(currentPlayer.id(), currentPlayer.bank().subtract(action.amount()), currentPlayer.pot().add(action.amount()), currentPlayer.isOut(), true);
        }
        if(action.type() == Action.ActionType.BET || action.type() == Action.ActionType.RAISE ||
                action.type() == Action.ActionType.CALL ||
                action.type() == Action.ActionType.SMALL_BLIND || action.type() == Action.ActionType.BIG_BLIND)
        {
            ChipStack amount = action.amount();

            if (action.type() == Action.ActionType.CALL) {
                amount = gameState.getAmountToCall();
            }
            else if(action.type() == Action.ActionType.SMALL_BLIND){
                amount = gameState.smallBlind();
            }
            else if(action.type() == Action.ActionType.BIG_BLIND) {
                amount = gameState.bigBlind();
            }


            if(currentPlayer.bank().compareTo(amount) < 0)
            {
                throw new Exception(String.format("Cannot play amount %s as only have %s in bank", action.amount().toString(), currentPlayer.bank().toString()));
            }

            return new PlayerState(currentPlayer.id(), currentPlayer.bank().subtract(amount), currentPlayer.pot().add(amount), currentPlayer.isOut(), currentPlayer.hasChecked());
        }
        if(action.type() == Action.ActionType.FOLD)
        {
            return new PlayerState(currentPlayer.id(), currentPlayer.bank(), currentPlayer.pot(), true, currentPlayer.hasChecked());
        }
        if(action.type() == Action.ActionType.WIN)
        {
            // see if there's only one player left
            ImmutableList<IPlayerState> winningPlayers = gameState.playersStillIn();
            if(winningPlayers.size() == 1) {
                IPlayerState winningPlayer = winningPlayers.get(0);

                ChipStack newBankroll = currentPlayer.bank().add(gameState.totalPot());
                if(currentPlayer.id() == winningPlayer.id()) {
                    // we are the winner! let's take the money...\
                    newBankroll = newBankroll.add(gameState.totalPot());
                }

                return new PlayerState(currentPlayer.id(), newBankroll, ChipStack.NO_CHIPS, false, false);
            }
            throw new Exception(String.format("don't konw how to deal with more than one player winnig", action.toString()));
        }

        throw new Exception(String.format("Unrecognised update player action %s", action.toString()));

    }
}
