package com.mossy.holdem.interfaces;

import com.google.common.collect.ImmutableMap;
import com.mossy.holdem.IncomeRate;
import com.mossy.holdem.PreFlopHandType;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 16/05/13
 * Time: 12:41
 * To change this template use File | Settings | File Templates.
 */
public interface IPreFlopIncomeRateBuilder
{

    IncomeRate getIncomeRate(PreFlopHandType handType);

    void addWinnings(PreFlopHandType handType, double winnings);

    ImmutableMap<PreFlopHandType, IncomeRate> build();
}
