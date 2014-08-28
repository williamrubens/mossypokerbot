package com.mossy.holdem.implementations.state;

import com.mossy.holdem.Action;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.interfaces.state.IPlayerState;
import com.mossy.holdem.interfaces.state.IPlayerStateFactory;

/**
 * Created by williamrubens on 21/08/2014.
 */
public class PlayerStateFactory implements IPlayerStateFactory
{
    @Override
    public IPlayerState updatePlayer(IPlayerState currentState, Action action) throws Exception
    {
        if(action.type() == Action.ActionType.BET || action.type() == Action.ActionType.CALL )
        {
            if(currentState.bank().compareTo(action.amount()) < 0)
            {
                throw new Exception(String.format("Cannot play amount %s as only have %s in bank", action.amount().toString(), currentState.bank().toString()));
            }
            return new PlayerState(currentState.bank().subtract(action.amount()), currentState.pot().add(action.amount()), currentState.isOut());
        }
        if(action.type() == Action.ActionType.FOLD)
        {
            return new PlayerState(currentState.bank(), currentState.pot(), true);
        }

        throw new Exception(String.format("Unrecognised update player action %s", action.toString()));

    }
}
