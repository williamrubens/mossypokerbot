package com.mossy.holdem;

import org.apache.commons.math3.stat.descriptive.StatisticalSummary;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 16/05/13
 * Time: 15:57
 * To change this template use File | Settings | File Templates.
 */
final public class IncomeRate
{
    private double incomeRate;
    private double standardDeviation;

    public IncomeRate(double ir, double sd)
    {
        incomeRate = ir;
        standardDeviation = sd;
    }

    public double incomeRate() { return incomeRate; }
    public double standardDeviation() { return standardDeviation; }

    static public IncomeRate fromStats(StatisticalSummary stats)
    {
        return new IncomeRate(stats.getMean(), stats.getStandardDeviation());
    }

}
