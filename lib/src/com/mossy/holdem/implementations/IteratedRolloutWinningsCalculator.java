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
public class IteratedRolloutWinningsCalculator implements IRolloutWinningsCalculator
{
    final IHandEvaluator handEvaluator;
    final IHandFactory  handFactory;
    final IHoleCardFolder folder;

    @Inject
    IteratedRolloutWinningsCalculator(IHandEvaluator _handEvaluator, IHandFactory _handFactory, IHoleCardFolder _folder)
    {
        handEvaluator = _handEvaluator;
        handFactory = _handFactory;
        folder = _folder;
    }

    @Override
    public double calculate(HoleCards myHoleCards, ImmutableList<HoleCards> oppHoleCards, ImmutableList<Card> boardCards)  throws Exception
    {
        boolean opponentWin = false;
        int numDrawingPlayers = 0;

        IHand myHand = handFactory.build(myHoleCards, boardCards);

        int myScore = handEvaluator.evaluateHand(myHand);

        ImmutableList<HoleCards> nonFoldedHands = folder.foldHoleCards(oppHoleCards);

        for(HoleCards playerCards : nonFoldedHands)
        {
            IHand opponentHand = handFactory.build(playerCards, boardCards);
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
            return (double) (nonFoldedHands.size()) / (double) numDrawingPlayers;
        }

        return  (double) nonFoldedHands.size();

    }
}
