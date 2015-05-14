package com.mossy.holdem.implementations;
import com.google.inject.Inject;
import com.mossy.holdem.*;
import com.mossy.holdem.interfaces.IKnowledgeBasedHoldemPlayer;
import com.mossy.holdem.interfaces.IPreFlopIncomeRateVendor;

import java.util.ArrayList;

/**
 * User: William
 * Date: 15/09/2013
 * Time: 11:29
 */
public class KnowledgeBasedHoldemPlayer implements IKnowledgeBasedHoldemPlayer
{
    IPreFlopIncomeRateVendor preFlopIncomeRateVendor;
    HoleCards holeCards;
    int numPlayers;
    Street street;
    ArrayList<Card> communityCards;

    @Inject
    KnowledgeBasedHoldemPlayer(IPreFlopIncomeRateVendor preFlopIncomeRateVendor)
    {
        this.preFlopIncomeRateVendor = preFlopIncomeRateVendor;

    }

    @Override
    public void startGame(int numPlayers)
    {
        this.numPlayers = numPlayers;
        street = Street.PRE_FLOP;
        communityCards = new ArrayList<Card>();
    }

    @Override
    public void setHoleCards(Card card1, Card card2, int seat)
    {
        holeCards = HoleCards.from(card1, card2);
    }

    @Override
    public void setFlop(Card card1, Card card2, Card card3)
    {
        communityCards.add(card1);
        communityCards.add(card2);
        communityCards.add(card3);

        street = Street.FLOP;
    }

    @Override
    public void setTurn(Card turn)
    {
        communityCards.add(turn);

        street = Street.TURN;
    }

    @Override
    public void setRiver(Card river)
    {
        communityCards.add(river);

        street = Street.RIVER;
    }

    @Override
    public Action getNextAction()
    {
        switch (street)
        {
            case PRE_FLOP:
                return getPreFlopAction();
            default:
                return getPostFlopAction();
        }
    }

    private Action getPreFlopAction()
    {
        IncomeRate ir = preFlopIncomeRateVendor.getIncomeRate(numPlayers, PreFlopHandType.fromHoleCards(holeCards));
        if(ir.incomeRate() > 0)
        {
            //return Action.Factory.callAction();
            return null;

        }
        else
        {
            return Action.Factory.foldAction();
        }

    }


    private Action getPostFlopAction()
    {
        return null;
    }

}
