/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.holdem.implementations;

import com.google.common.collect.ImmutableSortedSet;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import com.mossy.holdem.Rank;
import com.mossy.holdem.Suit;
import com.mossy.holdem.Card;
import com.mossy.holdem.interfaces.IDeck;

/**
 *
 * @author d80050
 */
public class Deck implements IDeck
{
    private Random random = new Random();
 
    private Stack<Card> undealtCards = new Stack<Card>();
    private Stack<Card> dealtCards = new Stack<Card>();
    
    public Deck(List<Card> cards)
    {
        for(Card card : cards)
        {
            undealtCards.push(card);
        }
    }
   
    @Override
    public void shuffle()
    {
        dealtCards.addAll(undealtCards);
        undealtCards.clear();
        while(!dealtCards.isEmpty()){
            int cardIndex = random.nextInt(dealtCards.size());
            Card randomCard = dealtCards.remove(cardIndex);
            undealtCards.push(randomCard);
        }
    }

    @Override
    public void dealCard(Card card)
    {
        
        if(!undealtCards.remove(card))
        {
           throw new IllegalArgumentException(String.format("Card %s not in deck", card));
        }        
        dealtCards.push(card);              
    }

    @Override
    public Card dealTopCard()
    {        
        Card topCard = undealtCards.pop();
        dealtCards.push(topCard);
        return topCard;
    }

    @Override
    public Card pickRandom(Rank rank)
    {
        Card randomCard = null;
        for(Card currentCard : undealtCards)
        {
            if(currentCard.rank() == rank) 
            {
                randomCard = currentCard;
                break;
            }
        }
        if(randomCard != null)
        {
            dealCard(randomCard);
        }
        return randomCard;
    }

    @Override
    public Card pickRandom(Suit suit)
    {
        Card randomCard = null;
        for(Card currentCard : undealtCards)
        {
            if(currentCard.suit() == suit) 
            {
                randomCard = currentCard;
                break;
            }
        }
        if(randomCard != null)
        {
            dealCard(randomCard);
        }
        return randomCard;
    }

    @Override
    public ImmutableSortedSet<Card> allCards()
    {
        ImmutableSortedSet.Builder<Card> setBuilder = ImmutableSortedSet.orderedBy(new Card.SuitThenRankComparer());
        
        setBuilder.addAll(undealtCards).addAll(dealtCards);
        
        return setBuilder.build();
    }

}
