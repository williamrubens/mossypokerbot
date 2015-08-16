/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.pokerbot.interfaces;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.mossy.pokerbot.Card;
import com.mossy.pokerbot.Rank;
import com.mossy.pokerbot.Suit;

/**
 *
 * @author d80050
 */
public interface IHand  {
    
    IHand addCard(Card card);
    IHand removeCard(Card card);
    
    ImmutableSortedSet<Card> cardsSorted();
    ImmutableList<Card> cards();
    ImmutableSortedSet<Rank> getRanks(Suit suit);
    
    int cardCount();

    Card highestCard();

    IHand getHighestFiveCardHand();
    
}
