package com.mossy.holdem.implementations.FastEvaluator;

import com.mossy.holdem.Card;
import com.mossy.holdem.Rank;
import com.mossy.holdem.Suit;
import com.mossy.holdem.implementations.Hand;
import com.mossy.holdem.implementations.HandFactory;
import com.mossy.holdem.interfaces.IHand;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 30/05/13
 * Time: 11:57
 * To change this template use File | Settings | File Templates.
 */
public class HandBitsAdaptor
{

    static final int CLUBS_BIT_SHIFT = 0;
    static final int HEARTS_BIT_SHIFT = 16;
    static final int DIAMONDS_BIT_SHIFT = 32;
    static final int SPADES_BIT_SHIFT = 48;

    final HandFactory handFactory;

    HandBitsAdaptor(HandFactory handFactory)
    {
        this.handFactory = handFactory;
    }

    CardBits adaptHand(IHand hand)
    {
        CardBits cardBits = new CardBits();

        for(Card card : hand.cards())
        {
            long cardbit = 1 << card.rank().index();
            switch(card.suit())
            {
                case CLUBS:
                    cardBits.clubs |= cardbit;
                    cardbit <<= CLUBS_BIT_SHIFT;
                    break;
                case HEARTS:
                    cardBits.hearts |= cardbit;
                    cardbit <<= HEARTS_BIT_SHIFT;
                    break;
                case DIAMONDS:
                    cardBits.diamonds |= cardbit;
                    cardbit <<= DIAMONDS_BIT_SHIFT;
                    break;
                case SPADES:
                    cardBits.spades |= cardbit;
                    cardbit <<= SPADES_BIT_SHIFT;
                    break;
            }
            cardBits.cards |= cardbit;
        }

        cardBits.nCards = hand.cardCount();

        return cardBits;
    }

    IHand adaptSuitBits(int suitBits, Suit suit)  throws Exception
    {
        ArrayList<Card> cards = new ArrayList<Card>();
        for(int i = 0; i < 13; ++i)
        {
            if((suitBits & (1 << i)) != 0)
            {
                cards.add(Card.from(Rank.fromIndex(i), suit));
            }
        }

        return handFactory.build(cards);
    }
}
