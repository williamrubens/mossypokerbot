package com.mossy.pokerbot.implementations;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.mossy.pokerbot.HoleCards;
import com.mossy.pokerbot.annotations.Annotations.NumPlayers;
import com.mossy.pokerbot.interfaces.IDeck;
import com.mossy.pokerbot.interfaces.IHoleCardDealer;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 15/05/13
 * Time: 18:00
 * To change this template use File | Settings | File Templates.
 */
public class HoleCardDealer implements IHoleCardDealer
{
    final private int numPlayers;

    @Inject
    HoleCardDealer(@NumPlayers int _numPlayers)
    {
        numPlayers = _numPlayers;
    }

    @Override
    public ImmutableList<HoleCards> deal(IDeck deck)
    {
        ImmutableList.Builder<HoleCards> builder = ImmutableList.builder();
        for(int p = 0; p <numPlayers; ++p)
        {
            builder.add(HoleCards.from(deck.dealTopCard(), deck.dealTopCard()));
        }
        return builder.build();
    }
}
