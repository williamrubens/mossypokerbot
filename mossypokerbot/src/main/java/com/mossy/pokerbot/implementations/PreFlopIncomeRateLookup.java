package com.mossy.pokerbot.implementations;

import com.google.common.collect.ImmutableMap;
import com.mossy.pokerbot.IncomeRate;
import com.mossy.pokerbot.PreFlopHandType;
import com.mossy.pokerbot.interfaces.IPreFlopIncomeRateVendor;

/**
 * Created with IntelliJ IDEA.
 * User: William
 * Date: 09/09/2013
 * Time: 23:25
 * To change this template use File | Settings | File Templates.
 */
public class PreFlopIncomeRateLookup implements IPreFlopIncomeRateVendor
{
    // TODO make this dependent on the number of players
    private ImmutableMap<PreFlopHandType, IncomeRate> lookup;

    public PreFlopIncomeRateLookup(ImmutableMap<PreFlopHandType, IncomeRate> _lookup)
    {
        lookup = _lookup;
    }


    @Override
    public IncomeRate getIncomeRate(int numPlayers, PreFlopHandType preFlopHand)
    {

        return lookup.get(preFlopHand);
    }
}
