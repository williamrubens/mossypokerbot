package com.mossy.holdem.implementations;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.mossy.holdem.IncomeRate;
import com.mossy.holdem.PreFlopHandType;
import com.mossy.holdem.interfaces.*;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 23/05/13
 * Time: 20:01
 * To change this template use File | Settings | File Templates.
 */
public class IteratedRolloutSimulator implements IIteratedRolloutSimulator
{
    IPreFlopRolloutSimulator rolloutSimulator;

    @Inject
    IteratedRolloutSimulator(IPreFlopRolloutSimulator rolloutSimulator)
    {
        this.rolloutSimulator = rolloutSimulator;
    }

    @Override
    public void iterateSimulation( double tolerance) throws Exception
    {
        ImmutableMap<PreFlopHandType, IncomeRate> oldIncomeRate = ImmutableMap.of();

        int iteration = 0;

        while(true)
        {
            ImmutableMap<PreFlopHandType, IncomeRate>  newIncomeRate = rolloutSimulator.simulateRollout( oldIncomeRate, tolerance, null, iteration);

            if(compareIncomeRates(oldIncomeRate, newIncomeRate))
            {
                break;
            }

            oldIncomeRate = newIncomeRate;
            iteration++;

        }
    }

    boolean compareIncomeRates(ImmutableMap<PreFlopHandType, IncomeRate> lhs, ImmutableMap<PreFlopHandType, IncomeRate> rhs)
    {
        return true;
    }
}
