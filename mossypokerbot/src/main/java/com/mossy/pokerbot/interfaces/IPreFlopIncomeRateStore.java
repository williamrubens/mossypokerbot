package com.mossy.pokerbot.interfaces;

import com.mossy.pokerbot.IncomeRate;
import com.mossy.pokerbot.PreFlopHandType;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 16/05/13
 * Time: 12:41
 * To change this template use File | Settings | File Templates.
 */
public interface IPreFlopIncomeRateStore
{

    IncomeRate getIncomeRate(PreFlopHandType handType);

    void addIncomeRate(PreFlopHandType handType, IncomeRate rate);

    boolean compareTo(IPreFlopIncomeRateStore other, double tolerence) ;
}
