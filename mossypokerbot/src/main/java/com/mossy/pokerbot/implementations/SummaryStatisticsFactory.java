package com.mossy.pokerbot.implementations;

import com.mossy.pokerbot.interfaces.IStatisticsFactory;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 16/05/13
 * Time: 14:36
 * To change this template use File | Settings | File Templates.
 */
public class SummaryStatisticsFactory implements IStatisticsFactory
{

    @Override
    public SummaryStatistics build()
    {
        return new SummaryStatistics();
    }
}
