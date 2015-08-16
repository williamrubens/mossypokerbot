package com.mossy.pokerbot.interfaces;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mossy.pokerbot.HoleCards;
import com.mossy.pokerbot.IncomeRate;
import com.mossy.pokerbot.PreFlopHandType;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 17/05/13
 * Time: 11:52
 * To change this template use File | Settings | File Templates.
 */
public interface INegativeIncomeRateFolder
{
    ImmutableList<HoleCards> foldHoleCards(ImmutableList<HoleCards> holeCardsList, ImmutableMap<PreFlopHandType, IncomeRate> handTypeToIncomeRate);
}
