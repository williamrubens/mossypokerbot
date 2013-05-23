package com.mossy.holdem.interfaces;

import com.google.common.collect.ImmutableMap;
import com.mossy.holdem.IncomeRate;
import com.mossy.holdem.PreFlopHandType;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 15/05/13
 * Time: 17:12
 * To change this template use File | Settings | File Templates.
 */
public interface IPreFlopIncomeRateSimulator
{
    IncomeRate simulateIncomeRate(IDeck deck, ImmutableMap<PreFlopHandType, IncomeRate> handTypeToIncomeRate, PreFlopHandType handType, double tolerance) throws Exception ;

}
