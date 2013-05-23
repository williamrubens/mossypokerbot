package com.mossy.holdem.implementations;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.mossy.holdem.Card;
import com.mossy.holdem.HoleCards;
import com.mossy.holdem.interfaces.*;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 15/05/13
 * Time: 18:40
 * To change this template use File | Settings | File Templates.
 */
public class RolloutWinningsCalculator implements IRolloutWinningsCalculator
{
    final IHandEvaluator handEvaluator;
    final IHandFactory  handFactory;
    @Inject
    RolloutWinningsCalculator(IHandEvaluator _handEvaluator, IHandFactory _handFactory)
    {
        handEvaluator = _handEvaluator;
        handFactory = _handFactory;
    }

    @Override
    public double calculate(HoleCards myHoleCards, ImmutableList<HoleCards> oppHoleCards, ImmutableList<Card> boardCards)  throws Exception
    {
        int numDrawingPlayers = 0;

        IHand myHand = handFactory.build(myHoleCards, boardCards);

        int myScore = handEvaluator.evaluateHand(myHand);

        for(HoleCards oppCards : oppHoleCards)
        {
            IHand opponentHand = handFactory.build(oppCards, boardCards);
            int opponentScore = handEvaluator.evaluateHand(opponentHand);

            if(opponentScore > myScore)
            {
                return -1;
            }
            else if(opponentScore == myScore)
            {
                numDrawingPlayers++;
            }

        }

        if(numDrawingPlayers > 0)
        {
            // I am also a drawing player, so add me in
            numDrawingPlayers++;
            // my winnings = total players / num players who draw - my blind
            // total players = opponents + me
            return (double) (oppHoleCards.size() + 1) / (double) numDrawingPlayers - 1.0d;
        }

        return  (double) oppHoleCards.size();

    }
}
