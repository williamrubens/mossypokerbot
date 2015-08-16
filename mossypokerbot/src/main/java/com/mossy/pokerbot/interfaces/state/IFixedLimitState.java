package com.mossy.pokerbot.interfaces.state;

import com.mossy.pokerbot.ChipStack;

/**
 * Created by williamrubens on 08/09/2014.
 */
public interface IFixedLimitState extends IGameState
{
    ChipStack lowerLimit();

    ChipStack higherLimit();

    int raiseCap();
    int numberOfRaises();

    ChipStack getCurrentBetLimit();

}
