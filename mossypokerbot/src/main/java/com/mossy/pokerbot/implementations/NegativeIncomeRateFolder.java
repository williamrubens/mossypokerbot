package com.mossy.pokerbot.implementations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mossy.pokerbot.HoleCards;
import com.mossy.pokerbot.IncomeRate;
import com.mossy.pokerbot.PreFlopHandType;
import com.mossy.pokerbot.interfaces.INegativeIncomeRateFolder;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 17/05/13
 * Time: 11:54
 * To change this template use File | Settings | File Templates.
 */
public class NegativeIncomeRateFolder implements INegativeIncomeRateFolder
{


    @Override
    public ImmutableList<HoleCards> foldHoleCards(ImmutableList<HoleCards> holeCardsList, ImmutableMap<PreFlopHandType, IncomeRate> handTypeToIncomeRate)
    {
        ImmutableList.Builder<HoleCards> builder =  ImmutableList.builder();

        for(HoleCards holeCards : holeCardsList)
        {
            PreFlopHandType handType = PreFlopHandType.fromHoleCards(holeCards);
            if(handTypeToIncomeRate != null)
            {
                IncomeRate incomeRate = handTypeToIncomeRate.get(handType);
                if(incomeRate != null && incomeRate.incomeRate() < 0)
                {
                    // fold the hand, ie don;t add it to the new list
                    continue;
                }
            }
            builder.add(holeCards);
        }

        return builder.build();

    }
}
