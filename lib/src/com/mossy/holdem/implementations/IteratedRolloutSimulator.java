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
    IDeck deck;

    @Inject
    IteratedRolloutSimulator(IPreFlopRolloutSimulator rolloutSimulator, IDeck deck)
    {
        this.rolloutSimulator = rolloutSimulator;
        this.deck = deck;

    }

    @Override
    public void iterateSimulation(double tolerance) throws Exception
    {
        ImmutableMap<PreFlopHandType, IncomeRate> oldIncomeRate = ImmutableMap.of();

        while(true)
        {
            ImmutableMap<PreFlopHandType, IncomeRate>  newIncomeRate = rolloutSimulator.simulateRollout(deck, oldIncomeRate, tolerance);

            if(compareIncomeRates(oldIncomeRate, newIncomeRate))
            {
                break;
            }

            oldIncomeRate = newIncomeRate;

        }
    }

    boolean compareIncomeRates(ImmutableMap<PreFlopHandType, IncomeRate> lhs, ImmutableMap<PreFlopHandType, IncomeRate> rhs)
    {
        return true;
    }
}
