package com.mossy.pokerbot.implementations;

import com.google.common.collect.ImmutableList;
import com.mossy.pokerbot.Card;
import com.mossy.pokerbot.HoleCards;
import com.mossy.pokerbot.interfaces.IHand;
import com.mossy.pokerbot.interfaces.IHandEvaluator;
import com.mossy.pokerbot.interfaces.IHandFactory;
import com.mossy.pokerbot.interfaces.INegativeIncomeRateFolder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 18/05/13
 * Time: 12:47
 * To change this template use File | Settings | File Templates.
 */
public class RolloutWinningsCalculatorTest {

    RolloutWinningsCalculator winCalc;
    IHandEvaluator mockEvaluator;
    ImmutableList holeCardList;
    INegativeIncomeRateFolder mockFolder;

    @Before
    public void setUp() throws Exception {

        mockEvaluator = Mockito.mock(IHandEvaluator.class) ;
        IHandFactory mockHandFactory = Mockito.mock(HandFactory.class) ;

        winCalc = new RolloutWinningsCalculator(mockEvaluator, mockHandFactory);
    }

    void setTestFor(List<Integer> evaluationResults) throws Exception
    {
        // create an iterator to simulate the number of non-folded cards
        ImmutableList.Builder<HoleCards> holeCardBuilder = ImmutableList.builder();

        int numOpponents = evaluationResults.size() - 1;
        while (numOpponents-- != 0)
        {
            // the actual hole cards don't matter of we are mocking the evaluation results
             holeCardBuilder.add(HoleCards.from(Card.ACE_DIAMONDS, Card.ACE_CLUBS));
        }

        holeCardList = holeCardBuilder.build();

        Mockito.when(mockEvaluator.evaluateHand((IHand) Matchers.any())).thenReturn(evaluationResults.get(0), evaluationResults.subList(1, evaluationResults.size()).toArray(new Integer []{})) ;
        //when(mockEvaluator.evaluateHand((IHand)any())).thenReturn(evaluationResults[0], Arrays.copyOfRange(evaluationResults, 1, evaluationResults.length)) ;

    }



    @Test
    public void testCalculate() throws Exception
    {

        setTestFor(Arrays.asList( new Integer[] {0,1})) ;

        double myWinnings = winCalc.calculate(HoleCards.from(Card.ACE_CLUBS, Card.ACE_DIAMONDS), holeCardList, null);

        assertEquals(myWinnings, -1.0d);

        setTestFor(Arrays.asList( new Integer[] {1,1})) ;

        myWinnings = winCalc.calculate(HoleCards.from(Card.ACE_CLUBS, Card.ACE_DIAMONDS), holeCardList, null);

        assertEquals(myWinnings, 0.0d);

        setTestFor(Arrays.asList( new Integer[] {1,0})) ;

        myWinnings = winCalc.calculate(HoleCards.from(Card.ACE_CLUBS, Card.ACE_DIAMONDS), holeCardList, null);

        assertEquals(myWinnings, 1.0d);

        setTestFor(Arrays.asList( new Integer[] {1,1,1,0})) ;

        myWinnings = winCalc.calculate(HoleCards.from(Card.ACE_CLUBS, Card.ACE_DIAMONDS), holeCardList, null);

        double expectedWinnings = (double)4.0d/3.0d - 1.0;

        assertEquals(myWinnings,expectedWinnings );

        setTestFor(Arrays.asList( new Integer[] {1,0,0,0,0})) ;

        myWinnings = winCalc.calculate(HoleCards.from(Card.ACE_CLUBS, Card.ACE_DIAMONDS), holeCardList, null);

        expectedWinnings = (double)4.0d;

        assertEquals(myWinnings,expectedWinnings );

        setTestFor(Arrays.asList( new Integer[] {0,0,0,1,0})) ;

        myWinnings = winCalc.calculate(HoleCards.from(Card.ACE_CLUBS, Card.ACE_DIAMONDS), holeCardList, null);

        expectedWinnings = (double)-1.0d;

        assertEquals(myWinnings,expectedWinnings );



    }


}
