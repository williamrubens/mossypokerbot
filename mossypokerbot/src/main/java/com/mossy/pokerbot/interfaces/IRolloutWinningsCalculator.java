package com.mossy.pokerbot.interfaces;

import com.google.common.collect.ImmutableList;
import com.mossy.pokerbot.HoleCards;
import com.mossy.pokerbot.Card;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 15/05/13
 * Time: 18:22
 * To change this template use File | Settings | File Templates.
 */
public interface IRolloutWinningsCalculator
{
    double calculate(HoleCards myHoleCards, ImmutableList<HoleCards> oppHoleCards, ImmutableList<Card> boardCards) throws Exception;
}
