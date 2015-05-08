package com.mossy.holdem.implementations.state;

import com.mossy.holdem.Action;
import com.mossy.holdem.interfaces.state.IPlayerInfo;
import com.mossy.holdem.interfaces.state.IPlayerInfoFactory;

/**
 * Created by williamrubens on 21/08/2014.
 */
public class PlayerInfoFactory implements IPlayerInfoFactory
{
    @Override
    public IPlayerInfo updatePlayer(IPlayerInfo currentState, Action action) throws Exception
    {
        if(action.type() == Action.ActionType.CHECK)
        {
            return new PlayerInfo(currentState.bank().subtract(action.amount()), currentState.pot().add(action.amount()), currentState.isOut(), true);
        }
        if(action.type() == Action.ActionType.BET || action.type() == Action.ActionType.CALL )
        {
            if(currentState.bank().compareTo(action.amount()) < 0)
            {
                throw new Exception(String.format("Cannot play amount %s as only have %s in bank", action.amount().toString(), currentState.bank().toString()));
            }
            return new PlayerInfo(currentState.bank().subtract(action.amount()), currentState.pot().add(action.amount()), currentState.isOut(), currentState.hasChecked());
        }
        if(action.type() == Action.ActionType.FOLD)
        {
            return new PlayerInfo(currentState.bank(), currentState.pot(), true, currentState.hasChecked());
        }

        throw new Exception(String.format("Unrecognised update player action %s", action.toString()));

    }
}
