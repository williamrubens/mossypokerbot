package com.mossy.pokerbot.implementations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.UnmodifiableIterator;
import com.mossy.pokerbot.Card;
import com.mossy.pokerbot.HoleCards;
import com.mossy.pokerbot.Rank;
import com.mossy.pokerbot.Suit;
import com.mossy.pokerbot.interfaces.IHand;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * Created by willrubens on 05/07/15.
 */
public class UnsortedHand implements IHand {


    ImmutableList<Card> cards;

    private UnsortedHand(ImmutableList<Card> cards) {
        this.cards = ImmutableList.copyOf(cards);
    }

    static public  IHand from(ImmutableList<Card> cards) {
        return new UnsortedHand(cards);
    }

    static public  IHand from(HoleCards holeCards, List<Card> cards) {
        return new UnsortedHand(ImmutableList.<Card>builder().addAll(cards).add(holeCards.card1()).add(holeCards.card2()).build());
    }

    @Override
    public ImmutableSortedSet<Card> cardsSorted() {
        ImmutableSortedSet.Builder<Card> builder = ImmutableSortedSet.orderedBy(new Card.RankThenSuitComparer());

        for (Card card : cards) {
            builder.add(card);
        }

        return builder.build();
    }

    @Override
    public ImmutableList<Card> cards() {
        return cards;
    }

    @Override
    public ImmutableSortedSet<Rank> getRanks(Suit suit) {
        ImmutableSortedSet.Builder<Rank> builder = ImmutableSortedSet.naturalOrder();

        for (Card card : cards) {
            if (card.suit() == suit) {
                builder.add(card.rank());
            }
        }

        return builder.build();
    }

    @Override
    public int cardCount() {
        return cards.size();
    }

    @Override
    public Card highestCard() {
        // SortedSet stores cards from lowest to highest
        return cardsSorted().last();
    }

    @Override
    public IHand getHighestFiveCardHand() {
        if(cards.size() < 5)
        {
            return this;
        }

        ImmutableSortedSet<Card> sortedCards = cardsSorted();
        UnmodifiableIterator iter = sortedCards.iterator();
        int offset = sortedCards.size() - 5;
        while(offset-- > 0)
        {
            iter.next();
        }
        return new SortedHand(sortedCards.tailSet((Card)iter.next()) );
    }

    @Override
    public IHand addCard(Card card) {
        throw new NotImplementedException();
    }

    @Override
    public IHand removeCard(Card card) {
        throw new NotImplementedException();
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder(cards.size() * 2);
        for (Card card : cards) {
            strBuilder.insert(0, card.toString());
        }
        return strBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnsortedHand hand = (UnsortedHand) o;

        return !(cards != null ? !cardsSorted().equals(hand.cardsSorted()) : hand.cards != null);

    }

    @Override
    public int hashCode() {
        return cards != null ? cardsSorted().hashCode() : 0;
    }
}


