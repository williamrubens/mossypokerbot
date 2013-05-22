package com.mossy.holdem.interfaces;

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
    void simulateIncomeRate(IDeck deck, PreFlopHandType handType, double tolerance) throws Exception ;
}
