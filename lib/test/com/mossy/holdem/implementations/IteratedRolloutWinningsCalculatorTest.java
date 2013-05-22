package com.mossy.holdem.implementations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.mossy.holdem.Card;
import com.mossy.holdem.HoleCards;
import com.mossy.holdem.interfaces.IHand;
import com.mossy.holdem.interfaces.IHandEvaluator;
import com.mossy.holdem.interfaces.IHandFactory;
import com.mossy.holdem.interfaces.IHoleCardFolder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 18/05/13
 * Time: 12:47
 * To change this template use File | Settings | File Templates.
 */
public class IteratedRolloutWinningsCalculatorTest {

    IteratedRolloutWinningsCalculator winCalc;
    IHandEvaluator mockEvaluator;
    ImmutableList mockFoldedCardsList;
    UnmodifiableIterator mockIter;

    @Before
    public void setUp() throws Exception {

        mockEvaluator = mock(IHandEvaluator.class) ;
        IHandFactory mockHandFactory = mock(HandFactory.class) ;
        IHoleCardFolder mockFolder = mock(IHoleCardFolder.class) ;

        winCalc = new IteratedRolloutWinningsCalculator(mockEvaluator, mockHandFactory, mockFolder);



        mockFoldedCardsList = mock(ImmutableList.class);
        mockIter = mock(UnmodifiableIterator.class);
        when(mockFoldedCardsList.iterator()).thenReturn(mockIter);
        when(mockIter.hasNext()).thenReturn(true,false);

        when(mockFolder.foldHoleCards((ImmutableList<HoleCards>)any())).thenReturn(mockFoldedCardsList);


    }

    void setTestFor(List<Integer> evaluationResults)
    {
        // create an iterator to simulate the number of non-folded cards
        ImmutableList.Builder<HoleCards> holeCardBuilder = ImmutableList.builder();

        for(Integer evaluationResult : evaluationResults)
        {
            // the actual hole cards don't matter as we are mocking the evaluation results
             holeCardBuilder.add(HoleCards.from(Card.ACE_DIAMONDS, Card.ACE_CLUBS));

        }



        for(Integer evalResult : evaluationResults)
        {

        }

    }



    @Test
    public void testCalculate() throws Exception
    {

        when(mockIter.hasNext()).thenReturn(true,false);
        when(mockEvaluator.evaluateHand((IHand)any())).thenReturn(0, 1);

        double myWinnings = winCalc.calculate(HoleCards.from(Card.ACE_CLUBS, Card.ACE_DIAMONDS), mockFoldedCardsList, null);

        assertEquals(myWinnings, -1.0d);

        when(mockIter.hasNext()).thenReturn(true,false);
        when(mockEvaluator.evaluateHand((IHand)any())).thenReturn(1, 1);

        myWinnings = winCalc.calculate(HoleCards.from(Card.ACE_CLUBS, Card.ACE_DIAMONDS), mockFoldedCardsList, null);

        assertEquals(myWinnings, 0.0d);

        when(mockIter.hasNext()).thenReturn(true,false);
        when(mockEvaluator.evaluateHand((IHand)any())).thenReturn(1, 0);

        myWinnings = winCalc.calculate(HoleCards.from(Card.ACE_CLUBS, Card.ACE_DIAMONDS), mockFoldedCardsList, null);

        assertEquals(myWinnings, 1.0d);


        when(mockIter.hasNext()).thenReturn(true,true,false);
        when(mockEvaluator.evaluateHand((IHand)any())).thenReturn(1, 0, 1);

        myWinnings = winCalc.calculate(HoleCards.from(Card.ACE_CLUBS, Card.ACE_DIAMONDS), mockFoldedCardsList, null);

        assertEquals(myWinnings, 1.0d);


    }


}
