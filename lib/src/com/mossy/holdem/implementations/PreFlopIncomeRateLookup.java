package com.mossy.holdem.implementations;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.ImmutableMap;
import com.mossy.holdem.IncomeRate;
import com.mossy.holdem.PreFlopHandType;
import com.mossy.holdem.interfaces.IPreFlopIncomeRateVendor;

import java.io.FileReader;

/**
 * Created with IntelliJ IDEA.
 * User: William
 * Date: 09/09/2013
 * Time: 23:25
 * To change this template use File | Settings | File Templates.
 */
public class PreFlopIncomeRateLookup implements IPreFlopIncomeRateVendor
{
    private ImmutableMap<PreFlopHandType, IncomeRate> lookup;

    public PreFlopIncomeRateLookup(ImmutableMap<PreFlopHandType, IncomeRate> _lookup)
    {
        lookup = _lookup;
    }


    @Override
    public IncomeRate getIncomeRate(int numPlayers, PreFlopHandType preFlopHand)
    {
        if(numPlayers != 10)
        {
            return null;
        }
        return lookup.get(preFlopHand);
    }
}
