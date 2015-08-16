package com.mossy.pokerbot.interfaces;

import com.mossy.pokerbot.Action;
import com.mossy.pokerbot.Card;
import com.mossy.pokerbot.interfaces.player.IPlayerState;
import com.mossy.pokerbot.interfaces.state.IGameState;

import java.util.Collection;

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
