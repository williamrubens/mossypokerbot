/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.holdem.interfaces;

import com.google.common.collect.ImmutableSortedSet;
import com.mossy.holdem.Card;

/**
 *
 * @author d80050
 */
public interface IHand {
    
    IHand addCard(Card card);
    IHand removeCard(Card card);
    
   ImmutableSortedSet<Card> cards();
    
    int cardCount();
    
    Card highestCard();
    
}
