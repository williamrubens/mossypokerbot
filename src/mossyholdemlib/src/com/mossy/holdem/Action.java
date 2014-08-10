package com.mossy.holdem;

import com.google.common.collect.ImmutableList;

/**
 * User: William
 * Date: 14/09/2013
 * Time: 12:04
 */
public class Action
{
    public enum ActionType
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
        DEAL_FLOP,
        DEAL_TURN,
        DEAL_RIVER
    }
    static public class Factory
    {

        static public Action checkAction()
        {
            return new Action(ActionType.CHECK);
        }
        static public Action betAction(ChipStack amount)
        {
            return new Action(ActionType.BET, amount);
        }
        static public Action checkAction(ChipStack amount)
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
        static public Action dealFlopAction(Card c1, Card c2, Card c3)
        {
            return new Action(ActionType.DEAL_FLOP, ImmutableList.of(c1, c2, c3));
        }
        static public Action dealTurnAction(Card t)
        {
            return new Action(ActionType.DEAL_TURN, ImmutableList.of(t));
        }
        static public Action dealRiverAction(Card r)
        {
            return new Action(ActionType.DEAL_RIVER, ImmutableList.of(r));
        }
    }

    public ActionType type()
    {
        return actionType;
    }

    public ImmutableList<Card> cards()
    {
        return cards;
    }

    ActionType actionType;
    ChipStack amount = ChipStack.NO_CHIPS;
    ImmutableList<Card> cards = ImmutableList.of();

    private Action(ActionType a)
    {
        this.actionType = a;
    }

    private Action(ActionType a, ChipStack amount)
    {
        this.actionType = a;
        this.amount = amount;
    }

    private Action(ActionType a, ImmutableList<Card> cards)
    {
        this.actionType = a;
        this.cards = cards;
    }



}
