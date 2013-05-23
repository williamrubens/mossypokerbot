package com.mossy.holdem.interfaces;

import com.google.common.collect.ImmutableMap;
import com.mossy.holdem.IncomeRate;
import com.mossy.holdem.PreFlopHandType;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 23/05/13
 * Time: 21:22
 * To change this template use File | Settings | File Templates.
 */
public interface IPreFlopRolloutSimulator
{
    ImmutableMap<PreFlopHandType, IncomeRate> simulateRollout(IDeck deck, ImmutableMap<PreFlopHandType, IncomeRate> handTypeToIncomeRate, double tolerance) throws Exception;
}