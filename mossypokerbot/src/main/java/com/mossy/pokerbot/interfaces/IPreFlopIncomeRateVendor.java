package com.mossy.pokerbot.interfaces;

import com.mossy.pokerbot.IncomeRate;
import com.mossy.pokerbot.PreFlopHandType;

/**
 * Created with IntelliJ IDEA.
 * User: William
 * Date: 09/09/2013
 * Time: 23:24
 * To change this template use File | Settings | File Templates.
 */
public interface IPreFlopIncomeRateVendor
{

    IncomeRate getIncomeRate(int numPlayers, PreFlopHandType preFlopHand);
}
