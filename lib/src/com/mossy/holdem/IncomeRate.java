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
    private final double incomeRate;
    private final double error;
    private final double standardDeviation;

    public IncomeRate(double incomeRate, double error, double sd)
    {
        this.incomeRate = incomeRate;
        this.error = error;
        this.standardDeviation = sd;
    }

    public double incomeRate() { return incomeRate; }
    public double standardDeviation() { return standardDeviation; }
    public double error() { return error; }

    static public IncomeRate fromStats(StatisticalSummary stats)
    {
        double error = stats.getMean() / Math.sqrt(stats.getN());
        return new IncomeRate(stats.getMean(), error, stats.getStandardDeviation());
    }

    String toCSVString()
    {
        return String.format("%.4g, %.4g, %.4g", incomeRate, error, standardDeviation);
    }

}
