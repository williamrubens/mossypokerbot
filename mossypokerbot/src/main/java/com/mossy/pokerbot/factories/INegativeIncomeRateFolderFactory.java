package com.mossy.pokerbot.factories;

import com.google.common.collect.ImmutableMap;
import com.mossy.pokerbot.IncomeRate;
import com.mossy.pokerbot.PreFlopHandType;
import com.mossy.pokerbot.implementations.NegativeIncomeRateFolder;

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
