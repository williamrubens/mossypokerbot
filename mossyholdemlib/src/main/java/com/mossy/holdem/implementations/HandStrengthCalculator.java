package com.mossy.holdem.implementations;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Card;
import com.mossy.holdem.HoleCards;
import com.mossy.holdem.interfaces.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by williamrubens on 11/05/2014.
 */
public class HandStrengthCalculator implements IHandStrengthCalculator
{
    private IHandEvaluator handEvaluator;
    private IHandFactory   handFactory;
    private IDeckFactory   deckFactory;

    @Inject
    public HandStrengthCalculator(IHandEvaluator handEvaluator, IHandFactory handFactory, IDeckFactory deckFactory) {
        this.handEvaluator = handEvaluator;
        this.handFactory = handFactory;
        this.deckFactory = deckFactory;
    }

    @Override
    public double calculateHandStrength(HoleCards holeCards, ImmutableList<Card> boardCards)
    {
        int winCount = 0, looseCount = 0, drawCount = 0;

        IHand myHand = handFactory.build(holeCards, boardCards);
        int myHandValue = handEvaluator.evaluateHand(myHand);

        IDeck deck = deckFactory.build();

        deck.dealCard(holeCards.card1());
        deck.dealCard(holeCards.card2());

        for(Card boardCard : boardCards)
        {
            deck.dealCard(boardCard);
        }

        List<Card> remainingCards = deck.undealtCards().asList();


        for(int oppHoleCardIdx1 = 0; oppHoleCardIdx1 < remainingCards.size(); ++oppHoleCardIdx1)
        {
            for(int oppHoleCardIdx2 = oppHoleCardIdx1 + 1 ; oppHoleCardIdx2 < remainingCards.size(); ++oppHoleCardIdx2)
            {
                HoleCards oppHoleCards = HoleCards.from(remainingCards.get(oppHoleCardIdx1), remainingCards.get(oppHoleCardIdx2));
                IHand oppHand = handFactory.build(oppHoleCards, boardCards);

                int oppHandValue = handEvaluator.evaluateHand(oppHand);

                if(myHandValue > oppHandValue)
                {
                    winCount++;
                }
                else if(myHandValue == oppHandValue)
                {
                    drawCount++;
                }
                else
                {
                    looseCount++;
                }
            }
        }

        double handStrength = ( winCount + drawCount/2.0 ) / (double)(winCount + looseCount + drawCount);

        return handStrength;
    }
}
