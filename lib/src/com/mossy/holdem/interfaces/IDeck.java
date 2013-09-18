package com.mossy.holdem.interfaces;

import com.google.common.collect.ImmutableSortedSet;
import com.mossy.holdem.Rank;
import com.mossy.holdem.Suit;
import com.mossy.holdem.Card;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author d80050
 */
public interface IDeck 
{
    
    Card pickRandom(Rank rank);
    Card pickRandom(Suit suit);
    
    ImmutableSortedSet<Card> allCards();
    ImmutableSortedSet<Card> undealtCards();
    
    void dealCard(Card card);
    
    Card dealTopCard();
    
    void shuffle();
    
    
}
