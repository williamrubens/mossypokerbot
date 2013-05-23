package com.mossy.holdem.implementations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.inject.Inject;
import com.mossy.holdem.IncomeRate;
import com.mossy.holdem.PreFlopHandType;
import com.mossy.holdem.interfaces.IDeck;
import com.mossy.holdem.interfaces.IPreFlopIncomeRateSimulator;
import com.mossy.holdem.interfaces.IPreFlopRolloutSimulator;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 23/05/13
 * Time: 21:24
 * To change this template use File | Settings | File Templates.
 */
public class PreFlopRolloutSimulator implements IPreFlopRolloutSimulator
{
    IPreFlopIncomeRateSimulator incomeRateSimulator;
    PreFlopHandTypeAdaptor adaptor;
    @Inject
    PreFlopRolloutSimulator(IPreFlopIncomeRateSimulator incomeRateSimulator,PreFlopHandTypeAdaptor adaptor)
    {
        this.incomeRateSimulator = incomeRateSimulator;
        this.adaptor = adaptor;

    }

    @Override
    public ImmutableMap<PreFlopHandType, IncomeRate> simulateRollout(IDeck deck, ImmutableMap<PreFlopHandType, IncomeRate> handTypeToIncomeRate, double tolerance) throws Exception
    {
        ImmutableSortedSet<PreFlopHandType> preflopHands = adaptor.adaptDeck(deck);

        ImmutableMap.Builder<PreFlopHandType, IncomeRate> newIncomeRateBuilder = ImmutableMap.builder();

        for(PreFlopHandType handType : preflopHands)
        {
            IncomeRate incomeRate = incomeRateSimulator.simulateIncomeRate(deck, handTypeToIncomeRate, handType, tolerance);
            newIncomeRateBuilder.put(handType, incomeRate);
        }

        return newIncomeRateBuilder.build();
    }
}

