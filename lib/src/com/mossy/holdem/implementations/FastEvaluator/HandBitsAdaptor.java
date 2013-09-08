package com.mossy.holdem.implementations.FastEvaluator;

import com.google.common.collect.ImmutableSortedSet;
import com.google.inject.Inject;
import com.mossy.holdem.Card;
import com.mossy.holdem.Rank;
import com.mossy.holdem.implementations.FastEvaluator.tables.TopCardTable;
import com.mossy.holdem.implementations.HandFactory;
import com.mossy.holdem.interfaces.IHand;

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

    @Inject
    public HandBitsAdaptor(HandFactory handFactory)
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

    public Rank bitMaskToRank(int rank) throws Exception
    {
        return Rank.fromIndex(TopCardTable.ranksToIndex[rank]);
        /*
        for(int i = 0; i < 13; ++i)
        {
            if((rank & (1 << i)) != 0)
            {
                return Rank.fromIndex(i);
            }
        }    */
        //throw new Exception("Failed to convert rank");
    }

    // immutablesortedset sorts from LOWEST to HIGHEST
    public ImmutableSortedSet<Rank> bitMasksToRanks(int rank) throws Exception
    {
        int cardsTriedMask = 0;
        ImmutableSortedSet.Builder<Rank> rankBuilder = ImmutableSortedSet.naturalOrder();
        int rankBitsSet = rank;
        while(rankBitsSet != 0)
        {
            int topCardIndex;
            topCardIndex = TopCardTable.ranksToIndex[rankBitsSet];
            rankBuilder.add(Rank.fromIndex(topCardIndex));
            rankBitsSet ^= (1 << topCardIndex);

        }
        /*
        for(int i = 0; i < 13; ++i)
        {
            if((rank & (1 << i)) != 0)
            {
                rankBuilder.add(Rank.fromIndex(i));
            }
            cardsTriedMask |= (1 << i);
            if((rank & (~cardsTriedMask)) == 0)
            {
                return rankBuilder.build();
            }
        }                               */
        return rankBuilder.build();
    }
    /*
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
    */
}
