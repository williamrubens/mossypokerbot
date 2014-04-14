package com.mossy.holdem.implementations;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.mossy.holdem.IncomeRate;
import com.mossy.holdem.PreFlopHandType;
import com.mossy.holdem.interfaces.*;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 16/05/13
 * Time: 12:43
 * To change this template use File | Settings | File Templates.
 */
public class PreFlopIncomeRateStore implements IPreFlopIncomeRateStore
{
    final Map<PreFlopHandType, IncomeRate> preFlopHandTypeToIncomeRate = new HashMap<PreFlopHandType, IncomeRate>();

    @Override
    public IncomeRate getIncomeRate(PreFlopHandType handType)
    {
        if(preFlopHandTypeToIncomeRate.containsKey(handType))
        {
            return preFlopHandTypeToIncomeRate.get(handType);
        }
        return null;
    }

    @Override
    public void addIncomeRate(PreFlopHandType handType, IncomeRate rate)
    {
        preFlopHandTypeToIncomeRate.put(handType, rate);
    }

    @Override
    public boolean compareTo(IPreFlopIncomeRateStore other, double tolerence) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }


}
