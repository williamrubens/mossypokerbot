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
public class PreFlopIncomeRateBuilder  implements IPreFlopIncomeRateBuilder
{
    final IStatisticsFactory statsFactory;
    final Map<PreFlopHandType, SummaryStatistics> preFlopHandTypeToStats = new HashMap<PreFlopHandType, SummaryStatistics>();

    @Inject
    private PreFlopIncomeRateBuilder(IStatisticsFactory _statsFactory)
    {
        statsFactory = _statsFactory;
    }


    @Override
    public IncomeRate getIncomeRate(PreFlopHandType handType)
    {
        if(preFlopHandTypeToStats.containsKey(handType))
        {
            return IncomeRate.fromStats(preFlopHandTypeToStats.get(handType));
        }
        return null;
    }

    @Override
    public void addWinnings(PreFlopHandType handType, double winnings)
    {
        if(preFlopHandTypeToStats.containsKey(handType))
        {
            preFlopHandTypeToStats.get(handType).addValue(winnings);
        }
        else
        {
            SummaryStatistics handStats = statsFactory.build();
            handStats.addValue(winnings);
            preFlopHandTypeToStats.put(handType, handStats);
        }
    }


    @Override
    public ImmutableMap<PreFlopHandType, IncomeRate> build()
    {
        ImmutableMap.Builder<PreFlopHandType, IncomeRate> preFlopHandStatsBuilder = ImmutableMap.builder();
        for(Map.Entry<PreFlopHandType, SummaryStatistics> handStats : preFlopHandTypeToStats.entrySet())
        {
            preFlopHandStatsBuilder.put(handStats.getKey(), IncomeRate.fromStats(handStats.getValue()));
        }
        return preFlopHandStatsBuilder.build();
    }

}
