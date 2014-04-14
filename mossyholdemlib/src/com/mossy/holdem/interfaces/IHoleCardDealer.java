package com.mossy.holdem.interfaces;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.HoleCards;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 15/05/13
 * Time: 17:46
 * To change this template use File | Settings | File Templates.
 */
public interface IHoleCardDealer
{

    ImmutableList<HoleCards> deal(IDeck deck);
}
