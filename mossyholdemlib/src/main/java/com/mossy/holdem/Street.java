package com.mossy.holdem;

/**
 * User: William
 * Date: 15/09/2013
 * Time: 11:50
 */
public enum Street
{
    PRE_FLOP,
    FLOP,
    TURN,
    RIVER,
    SHOWDOWN,
    FINISHED;

    static public Street nextStreet(Street street) {
        switch(street) {
            case PRE_FLOP: return FLOP;
            case FLOP: return TURN;
            case TURN: return RIVER;
            case RIVER: return SHOWDOWN;
            case SHOWDOWN: return SHOWDOWN;
            case FINISHED: return PRE_FLOP;
        }
        throw new RuntimeException("Invalid street: " + street.toString()  );
    }

}
