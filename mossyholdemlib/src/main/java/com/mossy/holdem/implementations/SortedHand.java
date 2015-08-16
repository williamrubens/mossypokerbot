/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.holdem.implementations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.UnmodifiableIterator;
import com.mossy.holdem.Card;
import com.mossy.holdem.Rank;
import com.mossy.holdem.Suit;
import com.mossy.holdem.interfaces.IHand;

/**
 *
 * @author d80050
 */
public final class SortedHand implements IHand
{

    ImmutableSortedSet<Card> cards;
    
    SortedHand()
    {
        cards = ImmutableSortedSet.orderedBy(new Card.RankThenSuitComparer()).build();
    }
    
     SortedHand(Card c1)
    {
        cards = ImmutableSortedSet.orderedBy(new Card.RankThenSuitComparer()).add(c1).build();
    }
    
    SortedHand(Card c1, Card c2)
    {
       cards = ImmutableSortedSet.orderedBy(new Card.RankThenSuitComparer()).add(c1, c2).build();
    }
    SortedHand(Card c1, Card c2, Card c3)
    {
        cards = ImmutableSortedSet.orderedBy(new Card.RankThenSuitComparer()).add(c1, c2, c3).build();
    }
    
    SortedHand(Card c1, Card c2, Card c3, Card c4)
    {
        cards = ImmutableSortedSet.orderedBy(new Card.RankThenSuitComparer()).add(c1, c2, c3, c4).build();
    }
     
    SortedHand(Card c1, Card c2, Card c3, Card c4, Card c5)
    {
        cards = ImmutableSortedSet.orderedBy(new Card.RankThenSuitComparer()).add(c1,c2, c3, c4, c5).build();
    }

    SortedHand(ImmutableSortedSet<Card> c)
    {
        cards = c;
    }

    private SortedHand(ImmutableList<Card> c)
    {
        cards = ImmutableSortedSet.copyOf(c);
    }
    
    @Override
    public ImmutableSortedSet<Card> cardsSorted()
    {
        return cards;
    }

    @Override
    public ImmutableSortedSet<Rank> getRanks(Suit suit)
    {
        ImmutableSortedSet.Builder<Rank> builder = ImmutableSortedSet.naturalOrder();

        for(Card card : cards)
        {
            if(card.suit() == suit)
            {
                builder.add(card.rank());
            }
        }

        return builder.build();
    }

    @Override
    public int cardCount() 
    {
        return cards.size();
    }

    @Override
    public Card highestCard()
    {
        // SortedSet stores cards from lowest to highest
        return cards.last();
    }

    @Override
    public IHand getHighestFiveCardHand()
    {
//        if(cards.size() < 5)
//        {
//            return this;
//        }
//        return new Hand(cards.asList().reverse().subList(0,5));

        UnmodifiableIterator iter = cards.iterator();
        int offset = cards.size() - 5;
        while(offset-- > 0)
        {
            iter.next();
        }
        return new SortedHand(cards.tailSet((Card)iter.next()) );
    }

    @Override
    public IHand addCard(Card card)
    {        
        ImmutableSortedSet<Card> moreCards = ImmutableSortedSet.orderedBy(new Card.RankThenSuitComparer()).addAll(cards).add(card).build();
        return new SortedHand(moreCards);
    }

    @Override
    public IHand removeCard(Card card)
    {
        ImmutableSortedSet.Builder<Card> builder = ImmutableSortedSet.orderedBy(new Card.RankThenSuitComparer());
        for(Card myCard : cards)
        {
            if(!myCard.equals(card))
            {
                builder.add(myCard);
            }
        }
        return new SortedHand(builder.build());
    }
    
    @Override
    public String toString()
    {
        StringBuilder strBuilder = new StringBuilder(cards.size() * 2);
        for(Card card : cards)
        {
            strBuilder.insert(0, card.toString());           
        }
        return strBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SortedHand hand = (SortedHand) o;

        return !(cards != null ? !cards.equals(hand.cards) : hand.cards != null);

    }

    @Override
    public int hashCode() {
        return cards != null ? cards.hashCode() : 0;
    }
}
