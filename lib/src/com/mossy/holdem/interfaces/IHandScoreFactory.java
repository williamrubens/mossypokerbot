package com.mossy.holdem.interfaces;

import com.mossy.holdem.Rank;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 19/05/13
 * Time: 12:28
 * To change this template use File | Settings | File Templates.
 */

public interface IHandScoreFactory {

    int buildStraightFlushScore(Rank highestRank);

    int buildFourOfAKindScore(Rank fourOfAKindRank);

    int buildFullHouseScore(Rank trips, Rank pairs);

    int buildFlushScore(IHand allCards);

    int buildStraightScore(Rank highestRank);

    int buildThreeOfAKindScore(Rank trips, Rank highKicker, Rank lowKicker);

    int buildTwoPairScore(Rank highPair, Rank lowPair, Rank kicker);

    int buildPairScore(Rank pair, Rank highKicker, Rank medKicker, Rank lowKicker);

    int buildHighCardScore(IHand kickers);
}
