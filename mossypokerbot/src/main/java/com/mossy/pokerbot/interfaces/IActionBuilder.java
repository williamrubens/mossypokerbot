package com.mossy.pokerbot.interfaces;

import com.google.common.collect.ImmutableList;
import com.mossy.pokerbot.Action;
import com.mossy.pokerbot.interfaces.state.IGameState;

/**
 * Created by williamrubens on 08/09/2014.
 */
public interface IActionBuilder
{
    ImmutableList<Action> buildAllChildActions(IGameState parentState);
}
