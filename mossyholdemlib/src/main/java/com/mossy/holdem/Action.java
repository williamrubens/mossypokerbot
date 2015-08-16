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
        BET,
        RAISE_TO,
        FOLD,
        CALL,
        SMALL_BLIND,
        BIG_BLIND,
        ANTE,
        ALL_IN,
        SIT_OUT,
        SHOWDOWN,
        WIN,
//        DEALER_ACTION,
        DEAL_HOLE_CARDS,
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
        // raise to amount
        static public Action raiseToAction(ChipStack amount)
        {
            return new Action(ActionType.RAISE_TO, amount);
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
//        static public Action dealerAction()
//        {
//            return new Action(ActionType.DEALER_ACTION);
//        }
        static public Action dealHoleCards()
        {
            return new Action(ActionType.DEAL_HOLE_CARDS);
        }
//////////////// using these as hacks for now
        static public Action dealFlopAction()
        {
            return new Action(ActionType.DEAL_FLOP);
        }
        static public Action dealTurnAction() {
            return new Action(ActionType.DEAL_TURN);
        }
        static public Action dealRiverAction()
        {
            return new Action(ActionType.DEAL_RIVER);
        }
///////////////////////////////////////
        static public Action winAction(int playerId)
        {
            return new Action(ActionType.WIN, playerId);
        }
        static public Action showdownAction()
        {
            return new Action(ActionType.SHOWDOWN);
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

    public ChipStack amount()
    {
        return amount;
    }

    public int playerId() { if(actionType == ActionType.WIN){return playerId;} throw new RuntimeException("Action.playerId only supported for WIN acions"); }

    public boolean isPlayerAction()
    {
        return type() == ActionType.BET || type() == ActionType.CHECK || type() == ActionType.FOLD || type() == ActionType.CALL || type() == ActionType.RAISE_TO;

    }

    public boolean isDealerAction()
    {
        return type() == ActionType.DEAL_FLOP || type() == ActionType.DEAL_HOLE_CARDS || type() == ActionType.DEAL_RIVER || type() == ActionType.DEAL_TURN;
    }

    public boolean isAggressive() {
        return type() == ActionType.BET || type() == ActionType.RAISE_TO;
    }
    @Override
    public String toString() {
        if(actionType == ActionType.BET || actionType == ActionType.RAISE_TO) {
            return String.format("%s %s", actionType, amount);
        }
        return String.format("%s", actionType);

    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        Action action = (Action) o;

        if (actionType != action.actionType)
        {
            return false;
        }
        if (amount != null ? !amount.equals(action.amount) : action.amount != null)
        {
            return false;
        }
        if (cards != null ? !cards.equals(action.cards) : action.cards != null)
        {
            return false;
        }
        if (playerId != action.playerId)
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = actionType.hashCode();
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (cards != null ? cards.hashCode() : 0);
        result = 31 * result + (playerId);
        return result;
    }

    ActionType actionType;
    ChipStack amount = ChipStack.NO_CHIPS;
    ImmutableList<Card> cards = ImmutableList.of();
    int playerId = 0;

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

    private Action(ActionType a, int playerId)
    {
        this.actionType = a;
        this.playerId = playerId;
    }




}
