package com.mossy.holdem.factories;

import com.google.common.collect.ImmutableMap;
import com.mossy.holdem.IncomeRate;
import com.mossy.holdem.PreFlopHandType;
import com.mossy.holdem.implementations.NegativeIncomeRateFolder;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 20/05/13
 * Time: 17:04
 * To change this template use File | Settings | File Templates.
 */
public interface INegativeIncomeRateFolderFactory
{
    NegativeIncomeRateFolder build(ImmutableMap<PreFlopHandType, IncomeRate> handTypeToIncomeRate);
}
