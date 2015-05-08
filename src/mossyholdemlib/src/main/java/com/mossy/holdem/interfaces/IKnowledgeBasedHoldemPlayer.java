package com.mossy.holdem.interfaces;

import com.mossy.holdem.Action;
import com.mossy.holdem.Card;

/**
 * User: William
 * Date: 15/09/2013
 * Time: 11:27
 */
public interface IKnowledgeBasedHoldemPlayer
{
    void startGame(int numPlayers);
    void setHoleCards(Card card1, Card card2, int seat);
    void setFlop(Card card1, Card card2, Card card3);
    void setTurn(Card turn);
    void setRiver(Card river);

    Action getNextAction();
}
