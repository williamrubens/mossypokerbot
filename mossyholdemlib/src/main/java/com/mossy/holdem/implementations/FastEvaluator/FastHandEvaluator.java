package com.mossy.holdem.implementations.fastevaluator;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.UnmodifiableIterator;
import com.google.inject.Inject;
import com.mossy.holdem.Rank;
import com.mossy.holdem.implementations.fastevaluator.tables.TopCardTable;
import com.mossy.holdem.interfaces.IHand;
import com.mossy.holdem.interfaces.IHandEvaluator;
import com.mossy.holdem.implementations.fastevaluator.tables.NBitsTable;
import com.mossy.holdem.implementations.fastevaluator.tables.StraightTable;
import com.mossy.holdem.interfaces.IHandScoreFactory;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 30/05/13
 * Time: 10:57
 * To change this template use File | Settings | File Templates.
 */


public class FastHandEvaluator implements IHandEvaluator
{

    final IHandScoreFactory scoreFactory;
    final HandBitsAdaptor adaptor;

    @Inject
    public FastHandEvaluator(IHandScoreFactory scoreFactory, HandBitsAdaptor adaptor)
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
                byte straightHighCard = StraightTable.ranksToIndex[cards.spades];
                if (straightHighCard > 0)
                    return scoreFactory.buildStraightFlushScore(Rank.fromIndex(straightHighCard));
                else
                    score = scoreFactory.buildFlushScore(adaptor.bitMasksToRanks(cards.spades));
            }
            else if (NBitsTable.table[cards.clubs] >= 5) {
                byte straightHighCard = StraightTable.ranksToIndex[cards.clubs];
                if (straightHighCard > 0)
                        return scoreFactory.buildStraightFlushScore(Rank.fromIndex(straightHighCard));
                else
                    score = scoreFactory.buildFlushScore(adaptor.bitMasksToRanks(cards.clubs));
            }
            else if (NBitsTable.table[cards.diamonds] >= 5) {
                byte straightHighCard = StraightTable.ranksToIndex[cards.diamonds];
                if (straightHighCard > 0)
                    return scoreFactory.buildStraightFlushScore(Rank.fromIndex(straightHighCard));
                else
                    score = scoreFactory.buildFlushScore(adaptor.bitMasksToRanks(cards.diamonds));
            }
            else if (NBitsTable.table[cards.hearts] >= 5) {
                byte straightHighCard = StraightTable.ranksToIndex[cards.hearts];
                if (straightHighCard > 0)
                    return scoreFactory.buildStraightFlushScore(Rank.fromIndex(straightHighCard));
                else
                    score = scoreFactory.buildFlushScore(adaptor.bitMasksToRanks(cards.hearts));
            }
            else {
                byte straightHighCard = StraightTable.ranksToIndex[ranks];
                if (straightHighCard > 0)
                    score = scoreFactory.buildStraightScore(Rank.fromIndex(straightHighCard));

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
                //int topFiveCardsBits = TopFiveCardsTable.ranksToIndex[ranks];
                //IHand topFiveCards = adaptor.adaptSuitBits(topFiveCardsBits);
                return scoreFactory.buildHighCardScore(hand.getHighestFiveCardHand());


            case 1:
            {
                //It's a one-pair hand

                // will have non-zero bit at location of pair
                int pairMask   = ranks ^ (cards.clubs ^ cards.spades ^ cards.diamonds ^ cards.hearts);

                int kickerMask = ranks ^ pairMask;      // Only one bit set in two_mask

                ImmutableSet<Rank> kickers = adaptor.bitMasksToRanks(kickerMask);

                UnmodifiableIterator<Rank> iKicker = kickers.asList().reverse().iterator();

                return scoreFactory.buildPairScore(adaptor.bitMaskToRank(pairMask), iKicker.next(), iKicker.next(), iKicker.next() );

            }
            case 2:
            {
                //Either two pair or trips

                int pairMask   = ranks ^ (cards.clubs ^ cards.spades ^ cards.diamonds ^ cards.hearts);
                if (pairMask != 0)
                {
                    //two pair

                    int kickerMask = ranks ^ pairMask;      // Only one bit set in two_mask

                    ImmutableSortedSet<Rank> pairs = adaptor.bitMasksToRanks(pairMask);
                    ImmutableSortedSet<Rank> kickers = adaptor.bitMasksToRanks(kickerMask);

                    Rank lowPair = pairs.first();
                    Rank highPair = pairs.last();

                    return scoreFactory.buildTwoPairScore(highPair, lowPair, kickers.first()) ;

                }
                else
                {
                    // trips

                    int t, second;

                    int threeMask = ((cards.clubs & cards.diamonds) | (cards.hearts & cards.spades)) &
                            ((cards.clubs & cards.hearts) | (cards.diamonds & cards.spades));


                    int nonTripsRanks = ranks ^ threeMask; // only one bit set in threeMask

                    int topKicker = TopCardTable.ranksToIndex[nonTripsRanks];

                    int remainingRanks = nonTripsRanks ^ (1 << topKicker);

                    int bottomKicker = TopCardTable.ranksToIndex[remainingRanks];

                    return scoreFactory.buildThreeOfAKindScore(adaptor.bitMaskToRank(threeMask), Rank.fromIndex(topKicker), Rank.fromIndex(bottomKicker));
                }
            }

            default:
            {
                // Possible quads, fullhouse, straight or flush, or two pair
                int fourMask = cards.clubs & cards.diamonds & cards.hearts & cards.spades;

                if(fourMask != 0)
                {
                    Rank quadRank = adaptor.bitMaskToRank(fourMask);
                    int remainingRanks = ranks ^ ( 1 << quadRank.index());

                    return scoreFactory.buildFourOfAKindScore(quadRank, Rank.fromIndex(TopCardTable.ranksToIndex[remainingRanks]));
                }



//              Technically, three_mask of defined below is really the set of
//              bits which are set in three or four of the suits, but since
//              we've already eliminated quads, this is OK
//              Similarly, two_mask is really two_or_four_mask, but since we've
//              already eliminated quads, we can use this shortcut

                int pairMask = ranks ^ ( cards.clubs ^ cards.hearts ^ cards.diamonds ^ cards.spades);

                if(NBitsTable.table[pairMask] != nDups)
                {
                    // Must be some trips then, which really means there is a
                    // full house since n_dups >= 3 3

                    int tripsMask = ((cards.clubs & cards.diamonds) | (cards.hearts & cards.spades)) &
                                    ((cards.clubs & cards.hearts) | (cards.diamonds & cards.spades));

                    // could be more thank one bit in trips mask
                    Rank trips = Rank.fromIndex(TopCardTable.ranksToIndex[tripsMask]);
                    int mask = (pairMask | tripsMask) ^ (1 << trips.index());
                    Rank pair = Rank.fromIndex(TopCardTable.ranksToIndex[mask]);
                    return scoreFactory.buildFullHouseScore(trips, pair);
                }

                if (score > 0) // flush and straight
                    return score;
                else {
                    // Must be two pair

                    Rank topPair = Rank.fromIndex(TopCardTable.ranksToIndex[pairMask]);
                    Rank bottomPair = Rank.fromIndex(TopCardTable.ranksToIndex[pairMask ^ (1 << topPair.index())]);
                    int kickers = ranks ^ (1 << topPair.index()) ^ (1 << bottomPair.index());
                    Rank kicker = Rank.fromIndex(TopCardTable.ranksToIndex[kickers]);

                    return scoreFactory.buildTwoPairScore(topPair, bottomPair, kicker);

                }
            }
        }

     }

}
