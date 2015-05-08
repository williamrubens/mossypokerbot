package com.mossy.holdem.interfaces;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Card;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 16/05/13
 * Time: 12:24
 * To change this template use File | Settings | File Templates.
 */
public interface IBoardCardDealer
{
    ImmutableList<Card> deal(IDeck deck);
}
