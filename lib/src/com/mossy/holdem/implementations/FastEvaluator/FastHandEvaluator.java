package com.mossy.holdem.implementations.FastEvaluator;

import com.mossy.holdem.Card;
import com.mossy.holdem.Rank;
import com.mossy.holdem.Suit;
import com.mossy.holdem.implementations.HandScoreFactory;
import com.mossy.holdem.interfaces.IHand;
import com.mossy.holdem.interfaces.IHandEvaluator;
import com.mossy.holdem.implementations.FastEvaluator.tables.NBitsTable;
import com.mossy.holdem.implementations.FastEvaluator.tables.StraightTable;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 30/05/13
 * Time: 10:57
 * To change this template use File | Settings | File Templates.
 */


public class FastHandEvaluator implements IHandEvaluator
{

    final HandScoreFactory scoreFactory;
    final HandBitsAdaptor adaptor;

    FastHandEvaluator(HandScoreFactory scoreFactory, HandBitsAdaptor adaptor)
    {
        this.scoreFactory = scoreFactory;
        this.adaptor = adaptor;
    }

    @Override
    public int evaluateHand(IHand hand) throws Exception
    {

        // this is a port of the poker eval c evaluation function

        CardBits cards = adaptor.adaptHand(hand);

        int score = 0;

        int ranks = cards.clubs | cards.spades | cards.diamonds | cards.hearts;
        int nRanks = NBitsTable.table[ranks];
        int nDups = hand.cardCount() - nRanks;

        // Check for straight, flush, or straight flush, and return if we can
        // determine immediately that this is the best possible hand

        if (nRanks >= 5) {
            if (NBitsTable.table[cards.spades] >= 5) {
                byte straightHighCard = StraightTable.table[cards.spades];
                if (straightHighCard > 0)
                    return scoreFactory.buildStraightFlushScore(Rank.fromIndex(straightHighCard));
                else
                    score = scoreFactory.buildFlushScore(adaptor.adaptSuitBits(cards.spades, Suit.SPADES));
            }
            else if (NBitsTable.table[cards.clubs] >= 5) {
                byte straightHighCard = StraightTable.table[cards.clubs];
                if (straightHighCard > 0)
                    return scoreFactory.buildStraightFlushScore(Rank.fromIndex(straightHighCard));
                else
                    score = scoreFactory.buildFlushScore(adaptor.adaptSuitBits(cards.clubs, Suit.CLUBS));
            }
            else if (NBitsTable.table[cards.diamonds] >= 5) {
                byte straightHighCard = StraightTable.table[cards.diamonds];
                if (straightHighCard > 0)
                    return scoreFactory.buildStraightFlushScore(Rank.fromIndex(straightHighCard));
                else
                    score = scoreFactory.buildFlushScore(adaptor.adaptSuitBits(cards.diamonds, Suit.DIAMONDS));
            }
            else if (NBitsTable.table[cards.hearts] >= 5) {
                byte straightHighCard = StraightTable.table[cards.hearts];
                if (straightHighCard > 0)
                    return scoreFactory.buildStraightFlushScore(Rank.fromIndex(straightHighCard));
                else
                    score = scoreFactory.buildFlushScore(adaptor.adaptSuitBits(cards.hearts, Suit.HEARTS));
            }
            else {
                byte straightHighCard = StraightTable.table[ranks];
                if (straightHighCard > 0)
                    score = scoreFactory.buildStraightScore(Rank.fromIndex(straightHighCard);

            }
        }

        //Another win -- if there can't be a FH/Quads (nDups < 3),
        //which is true most of the time when there is a made hand, then if we've
        //found a five card hand, just return.  This skips the whole process of
        //computing two_mask/three_mask/etc.

        if (score > 0 && nDups < 3)
            return score;

        //By the time we're here, either:
        //  1) there's no five-card hand possible (flush or straight), or
        //  2) there's a flush or straight, but we know that there are enough
        //     duplicates to make a full house / quads possible.

        switch (nDups)
        {
            case 0:
                //  It's a no-pair hand
                int topFiveCardsBits = T
                IHand topFiveCards = adaptor.adaptSuitBits()
                return scoreFactory.buildHighCardScore(hand);


                        HandVal_HANDTYPE_VALUE(StdRules_HandType_NOPAIR)
                        + topFiveCardsTable[ranks];
            break;

            case 1: {
      /* It's a one-pair hand */
                uint32 t, kickers;

                two_mask   = ranks ^ (SC ^ SD ^ SH ^ SS);

                retval = HandVal_HANDTYPE_VALUE(StdRules_HandType_ONEPAIR)
                        + HandVal_TOP_CARD_VALUE(topCardTable[two_mask]);
                t = ranks ^ two_mask;      /* Only one bit set in two_mask */
      /* Get the top five cards in what is left, drop all but the top three
       * cards, and shift them by one to get the three desired kickers */
                kickers = (topFiveCardsTable[t] >> HandVal_CARD_WIDTH)
                        & ~HandVal_FIFTH_CARD_MASK;
                retval += kickers;

                return retval;
            }
            break;

            case 2:
      /* Either two pair or trips */

                two_mask   = ranks ^ (SC ^ SD ^ SH ^ SS);
                if (two_mask) {
                    uint32 t;

                    t = ranks ^ two_mask; /* Exactly two bits set in two_mask */
                    retval = HandVal_HANDTYPE_VALUE(StdRules_HandType_TWOPAIR)
                            + (topFiveCardsTable[two_mask]
                            & (HandVal_TOP_CARD_MASK | HandVal_SECOND_CARD_MASK))
                            + HandVal_THIRD_CARD_VALUE(topCardTable[t]);

                    return retval;
                }
                else {
                    int t, second;

                    three_mask = (( SC&SD )|( SH&SS )) & (( SC&SH )|( SD&SS ));

                    retval = HandVal_HANDTYPE_VALUE(StdRules_HandType_TRIPS)
                            + HandVal_TOP_CARD_VALUE(topCardTable[three_mask]);

                    t = ranks ^ three_mask; /* Only one bit set in three_mask */
                    second = topCardTable[t];
                    retval += HandVal_SECOND_CARD_VALUE(second);
                    t ^= (1 << second);
                    retval += HandVal_THIRD_CARD_VALUE(topCardTable[t]);
                    return retval;
                }
                break;

            default:
      /* Possible quads, fullhouse, straight or flush, or two pair */
                four_mask  = SH & SD & SC & SS;
                if (four_mask) {
                    int tc;

                    tc = topCardTable[four_mask];
                    retval = HandVal_HANDTYPE_VALUE(StdRules_HandType_QUADS)
                            + HandVal_TOP_CARD_VALUE(tc)
                            + HandVal_SECOND_CARD_VALUE(topCardTable[ranks ^ (1 << tc)]);
                    return retval;
                };

      /* Technically, three_mask as defined below is really the set of
         bits which are set in three or four of the suits, but since
         we've already eliminated quads, this is OK */
      /* Similarly, two_mask is really two_or_four_mask, but since we've
         already eliminated quads, we can use this shortcut */

                two_mask   = ranks ^ (SC ^ SD ^ SH ^ SS);
                if (nBitsTable[two_mask] != n_dups) {
        /* Must be some trips then, which really means there is a
           full house since n_dups >= 3 */
                    int tc, t;

                    three_mask = (( SC&SD )|( SH&SS )) & (( SC&SH )|( SD&SS ));
                    retval  = HandVal_HANDTYPE_VALUE(StdRules_HandType_FULLHOUSE);
                    tc = topCardTable[three_mask];
                    retval += HandVal_TOP_CARD_VALUE(tc);
                    t = (two_mask | three_mask) ^ (1 << tc);
                    retval += HandVal_SECOND_CARD_VALUE(topCardTable[t]);
                    return retval;
                };

                if (retval) /* flush and straight */
                    return retval;
                else {
        /* Must be two pair */
                    int top, second;

                    retval = HandVal_HANDTYPE_VALUE(StdRules_HandType_TWOPAIR);
                    top = topCardTable[two_mask];
                    retval += HandVal_TOP_CARD_VALUE(top);
                    second = topCardTable[two_mask ^ (1 << top)];
                    retval += HandVal_SECOND_CARD_VALUE(second);
                    retval += HandVal_THIRD_CARD_VALUE(topCardTable[ranks ^ (1 << top)
                            ^ (1 << second)]);
                    return retval;
                };

                break;
        };

  /* Should never happen */
        assert(!"Logic error in StdDeck_StdRules_EVAL_N");





        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    final long CLUBS_MASK    = 0x000000000000ffffl;
    final long HEARTS_MASK   = 0x00000000ffff0000l;
    final long DIAMONDS_MASK = 0x0000ffff00000000l;
    final long SPADES_MASK   = 0xffff000000000000l;

}
