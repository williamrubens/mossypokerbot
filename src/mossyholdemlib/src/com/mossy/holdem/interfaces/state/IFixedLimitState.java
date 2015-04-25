package com.mossy.holdem.interfaces.state;

import com.mossy.holdem.ChipStack;

/**
 * Created by williamrubens on 08/09/2014.
 */
public interface IFixedLimitState extends IGameState
{
    ChipStack lowerLimit();

    ChipStack higherLimit();

    ChipStack getCurrentBetLimit();
}
