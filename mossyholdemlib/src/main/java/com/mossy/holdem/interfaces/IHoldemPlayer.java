package com.mossy.holdem.interfaces;

import com.mossy.holdem.Action;
import com.mossy.holdem.Card;
import com.mossy.holdem.interfaces.player.IPlayerState;
import com.mossy.holdem.interfaces.state.IGameState;

import java.util.Collection;
import java.util.List;

/**
 * User: William
 * Date: 15/09/2013
 * Time: 11:27
 */
public interface IHoldemPlayer
{
    void startGame(Collection<IPlayerState> playerStates, int dealerPosition);
    void setHoleCards(Card card1, Card card2, int seat);
    void setFlop(Card card1, Card card2, Card card3);
    void setTurn(Card turn);
    void setRiver(Card river);

    Action getNextAction();
    void setNextAction(Action action);

    IGameState currentState();
}
