package com.mossy.holdem.interfaces;

import com.mossy.holdem.Card;
import com.mossy.holdem.HoleCards;

import java.util.ArrayList;

/**
 * User: William
 * Date: 15/09/2013
 * Time: 23:04
 */
public interface IHandStrengthCalculator
{
    double calculateHandStrength(HoleCards holeCards, ArrayList<Card> boardCards) throws Exception;
}
