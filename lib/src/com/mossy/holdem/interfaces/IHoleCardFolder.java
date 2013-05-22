package com.mossy.holdem.interfaces;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.HoleCards;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 17/05/13
 * Time: 11:52
 * To change this template use File | Settings | File Templates.
 */
public interface IHoleCardFolder
{
    ImmutableList<HoleCards> foldHoleCards(ImmutableList<HoleCards> holeCards);
}
