package com.mossy.pokerbot.interfaces;

import com.google.common.collect.ImmutableMap;
import com.mossy.pokerbot.IncomeRate;
import com.mossy.pokerbot.PreFlopHandType;

import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 23/05/13
 * Time: 21:22
 * To change this template use File | Settings | File Templates.
 */
public interface IPreFlopRolloutSimulator
{
    ImmutableMap<PreFlopHandType, IncomeRate> simulateRollout(ImmutableMap<PreFlopHandType, IncomeRate> handTypeToIncomeRate, double tolerance, PrintWriter printWriter, int iteration) throws Exception;
}
