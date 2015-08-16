package com.mossy.pokerbot.interfaces;

import com.google.common.collect.ImmutableSortedSet;
import com.mossy.pokerbot.Rank;
import com.mossy.pokerbot.Suit;
import com.mossy.pokerbot.Card;


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
