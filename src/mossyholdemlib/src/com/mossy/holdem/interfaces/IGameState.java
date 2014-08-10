package com.mossy.holdem.interfaces;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.Card;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.GameStage;

/**
 * Created by williamrubens on 19/07/2014.
 */
public interface IGameState
{

    GameStage stage();

    int       numPlayers();
    ChipStack potSize();
    ImmutableList<Action> actionHistory();
    ImmutableList<Card> boardCards();

    IGameState nextState(Action a) throws Exception;

}


