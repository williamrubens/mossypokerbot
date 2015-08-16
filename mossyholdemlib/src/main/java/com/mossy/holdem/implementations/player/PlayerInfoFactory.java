package com.mossy.holdem.implementations.player;

import com.mossy.holdem.Action;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.interfaces.state.IGameState;
import com.mossy.holdem.interfaces.player.IPlayerState;
import com.mossy.holdem.interfaces.player.IPlayerInfoFactory;

/**
 * Created by williamrubens on 21/08/2014.
 */
public class PlayerInfoFactory implements IPlayerInfoFactory
{
    @Override
    public IPlayerState newPlayer(int id, ChipStack bank) {
        return new PlayerState(id, bank, ChipStack.NO_CHIPS, false, false);
    }

    @Override
    public IPlayerState updatePlayer(IPlayerState currentPlayer, Action action, IGameState gameState)
    {
        if(action.type() == Action.ActionType.CHECK)
        {
            return new PlayerState(currentPlayer.id(), currentPlayer.bank(), currentPlayer.pot(), currentPlayer.isOut(), true);
        }
        if(action.type() == Action.ActionType.BET || action.type() == Action.ActionType.RAISE_TO ||
                action.type() == Action.ActionType.CALL ||
                action.type() == Action.ActionType.SMALL_BLIND || action.type() == Action.ActionType.BIG_BLIND)
        {
            ChipStack amount = action.amount();

            if (action.type() == Action.ActionType.CALL) {
                amount = gameState.getAmountToCall();
            }
            else if(action.type() == Action.ActionType.SMALL_BLIND) {
                amount = gameState.smallBlind();
            }
            else if(action.type() == Action.ActionType.BIG_BLIND) {
                amount = gameState.bigBlind();
            }
            else if(action.type() == Action.ActionType.RAISE_TO) {
                amount = action.amount().subtract(currentPlayer.pot());
            }


            if(currentPlayer.bank().compareTo(amount) < 0)
            {
                throw new RuntimeException(String.format("Cannot play amount %s of only have %s in bank", action.amount().toString(), currentPlayer.bank().toString()));
            }

            return new PlayerState(currentPlayer.id(), currentPlayer.bank().subtract(amount), currentPlayer.pot().add(amount), currentPlayer.isOut(), currentPlayer.hasChecked());
        }
        if(action.type() == Action.ActionType.FOLD)
        {
            return new PlayerState(currentPlayer.id(), currentPlayer.bank(), currentPlayer.pot(), true, currentPlayer.hasChecked());
        }
        if(action.type() == Action.ActionType.WIN)
        {
            ChipStack newBankroll = currentPlayer.bank();

            if(currentPlayer.id() == action.playerId()) {
                // we are the winner! let's take the money...\
                newBankroll = newBankroll.add(gameState.totalPot());
            }

            return new PlayerState(currentPlayer.id(), newBankroll, ChipStack.NO_CHIPS, false, false);

        }
        if(action.isDealerAction()) {
            if(currentPlayer.isOut()){
                return currentPlayer;
            }
            // dealer action, reset ourselves
            return new PlayerState(currentPlayer.id(), currentPlayer.bank(), ChipStack.NO_CHIPS, false, false);

        }

        throw new RuntimeException(String.format("Unrecognised update player action %s", action.toString()));

    }
}
