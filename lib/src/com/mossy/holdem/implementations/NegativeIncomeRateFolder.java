package com.mossy.holdem.implementations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.mossy.holdem.HoleCards;
import com.mossy.holdem.IncomeRate;
import com.mossy.holdem.PreFlopHandType;
import com.mossy.holdem.interfaces.IHoleCardFolder;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 17/05/13
 * Time: 11:54
 * To change this template use File | Settings | File Templates.
 */
public class NegativeIncomeRateFolder implements IHoleCardFolder
{
    static private Logger log = Logger.getLogger(NegativeIncomeRateFolder.class);
    private final ImmutableMap<PreFlopHandType, IncomeRate> handTypeToIncomeRate;

    @Inject
    NegativeIncomeRateFolder(  ImmutableMap<PreFlopHandType, IncomeRate> _handTypeToIncomeRate)
    {
        handTypeToIncomeRate = _handTypeToIncomeRate;
    }


    @Override
    public ImmutableList<HoleCards> foldHoleCards(ImmutableList<HoleCards> holeCardsList)
    {
        ImmutableList.Builder<HoleCards> builder =  ImmutableList.builder();

        for(HoleCards holeCards : holeCardsList)
        {
            PreFlopHandType handType = PreFlopHandType.fromHoleCards(holeCards);
            IncomeRate incomeRate = handTypeToIncomeRate.get(handType);
            if(incomeRate == null)
            {
                log.debug(String.format("Could not find income rate for Hand %s don't know how to fold", handType));
            }
            else if(incomeRate.incomeRate() < 0)
            {
                // fold the hand, ie don;t add it to the new list
                continue;
            }
            builder.add(holeCards);
        }

        return builder.build();

    }
}
