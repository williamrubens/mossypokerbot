package com.mossy.holdem.interfaces;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.interfaces.state.IGameState;

/**
 * Created by williamrubens on 08/09/2014.
 */
public interface IActionBuilder
{
    ImmutableList<Action> buildAllChildActions(IGameState parentState) throws Exception;
}
