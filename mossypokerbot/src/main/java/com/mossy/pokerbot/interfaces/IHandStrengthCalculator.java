package com.mossy.pokerbot.interfaces;

import com.google.common.collect.ImmutableList;
import com.mossy.pokerbot.Card;
import com.mossy.pokerbot.HoleCards;

/**
 * User: William
 * Date: 15/09/2013
 * Time: 23:04
 */
public interface IHandStrengthCalculator
{
    double calculateHandStrength(HoleCards holeCards, ImmutableList<Card> boardCards);
}
