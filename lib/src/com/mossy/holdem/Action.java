package com.mossy.holdem;

/**
 * User: William
 * Date: 14/09/2013
 * Time: 12:04
 */
public class Action
{
    enum ActionType
    {
        CHECK,
        BET, // not sure if need bet? could be just raise?
        RAISE,
        FOLD,
        CALL,
        SMALL_BLIND,
        BIG_BLIND,
        ANTE,
        ALL_IN,
        SIT_OUT,
        POST_ANTE,
    }
    static public class Factory
    {

        static public Action checkAction()
        {
            return new Action(ActionType.CHECK);
        }
        static public Action betAction(double amount)
        {
            return new Action(ActionType.BET, amount);
        }
        static public Action checkAction(double amount)
        {
            return new Action(ActionType.RAISE, amount);
        }
        static public Action foldAction()
        {
            return new Action(ActionType.FOLD);
        }
        static public Action callAction()
        {
            return new Action(ActionType.CALL);
        }
        static public Action smallBlindAction()
        {
            return new Action(ActionType.SMALL_BLIND);
        }
        static public Action bigBlindAction()
        {
            return new Action(ActionType.BIG_BLIND);
        }
        static public Action anteAction()
        {
            return new Action(ActionType.ANTE);
        }
        static public Action allInAction()
        {
            return new Action(ActionType.ALL_IN);
        }
        static public Action sitOutAction()
        {
            return new Action(ActionType.SIT_OUT);
        }
        static public Action postAnteAction()
        {
            return new Action(ActionType.POST_ANTE);
        }
    }

    ActionType actionType;
    double amount;

    private Action(ActionType a)
    {
        this.actionType = a;
        this.amount = 0;
    }

    private Action(ActionType a, double amount)
    {
        this.actionType = a;
        this.amount = amount;
    }


}
