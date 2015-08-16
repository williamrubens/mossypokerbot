package com.mossy.pokerbot.interfaces;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 16/05/13
 * Time: 14:34
 * To change this template use File | Settings | File Templates.
 */
public interface IStatisticsFactory
{
    SummaryStatistics build();
}
