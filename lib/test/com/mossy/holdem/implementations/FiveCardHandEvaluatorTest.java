/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.holdem.implementations;

import com.mossy.holdem.HandType;
import com.mossy.holdem.interfaces.IHand;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author d80050
 */
//@RunWith(Parameterized.class)
public class FiveCardHandEvaluatorTest {
    
//    IHand hand;
//    int expectedResult;
    
//    @Parameters
//    public static Collection<Object[]> data() 
//    {
//      Object[][] data = new Object[][] 
//      {{ new Hand(Card.CARD_Ah, Card.CARD_Kh, Card.CARD_Qh, Card.CARD_Jh, Card.CARD_Th ), HandScoreFactory.STRAIGHT_FLUSH + Card.CARD_Ah.getRank().getNumber()},
//       { new Hand(Card.CARD_Ah, Card.CARD_Ac, Card.CARD_Ad, Card.CARD_As, Card.CARD_Th ), HandScoreFactory.FOUR_OF_A_KIND + Card.CARD_Ah.getRank().getNumber()},
//       { new Hand(Card.CARD_Ah, Card.CARD_Ac, Card.CARD_Ad, Card.CARD_Td, Card.CARD_Th ), HandScoreFactory.FULL_HOUSE + Card.CARD_Ah.getRank().getNumber()},
//       { new Hand(Card.CARD_Ah, Card.CARD_Ac, Card.CARD_Ad, Card.CARD_As, Card.CARD_Th ), HandScoreFactory.FLUSH + Card.CARD_Ah.getRank().getNumber()},
//       { new Hand(Card.CARD_Ah, Card.CARD_Ac, Card.CARD_Ad, Card.CARD_As, Card.CARD_Th ), HandScoreFactory.STRAIGHT + Card.CARD_Ah.getRank().getNumber() },
//       { new Hand(Card.CARD_Ah, Card.CARD_Ac, Card.CARD_Ad, Card.CARD_As, Card.CARD_Th ), HandScoreFactory.THREE_OF_A_KIND + Card.CARD_Ah.getRank().getNumber() },
//       { new Hand(Card.CARD_Ah, Card.CARD_Ac, Card.CARD_Ad, Card.CARD_As, Card.CARD_Th ), HandScoreFactory.TWO_PAIR + Card.CARD_Ah.getRank().getNumber() },
//       { new Hand(Card.CARD_Ah, Card.CARD_Ac, Card.CARD_Ad, Card.CARD_As, Card.CARD_Th ), HandScoreFactory.PAIR + Card.CARD_Ah.getRank().getNumber() },
//       { new Hand(Card.CARD_Ah, Card.CARD_Ac, Card.CARD_Ad, Card.CARD_As, Card.CARD_Th ), HandScoreFactory.HIGH_CARD + Card.CARD_Ah.getRank().getNumber() }};
//    
//      return Arrays.asList(data);
//    }
//    
    
//    
//    public FiveCardHandEvaluatorTest(IHand h, int e)
//    {
//        hand = h;
//        expectedResult = e;
//    }
//  
    /**
     * Test of evaluateHand method, of class FiveCardHandEvaluator.
     */
    @Test
    public void testEvaluateHand() throws Exception 
    {
        String []  hands = new String [] {
            "AcKcQcJcTc", // straight flush 
            "KhQhJhTh9h", // straigh flush lower
            "KcKsKdKh9d", // four of a kind
            "QcQsQdQh8d", // four of a kind
            "KcKsKdQhQd", // full house
            "QcQhQdKsKd", // full house
            "Ac9c8c6c5c", // flush
            "Kc9c8c6c5c", // flush
            "3d4h5s6s7c", // straight
            "2c3d4h5s6s", // straight
            "2c2d2h5h3h", // trips
            "2c2h2d4h3h", // trips
            "AcAhKcKh8d", // two pair
            "KcKhQcQhJs", // two pair
            "8c6d5h4s3s", // high card
            "8c6d5h4s2s"}; // high card
        
        int lastScore = Integer.MAX_VALUE;
        
        HandFactory handFactory = new HandFactory();    
        HandScoreFactory scoreFactory = new HandScoreFactory();
        FiveCardHandEvaluator handEvaluator = new FiveCardHandEvaluator(scoreFactory);
        
        for(String handString : hands)
        {
            IHand hand = handFactory.build(handString);
            int handScore = handEvaluator.evaluateHand(hand);
            boolean isSmaller = handScore < lastScore;
            assertEquals(isSmaller, true);                    
        }
        
    }

    @Test
    public void testEvaluateSpecificHand() throws Exception
    {


        //String handString =    "KsQcTs9c9h";
        String handString =    "TcTh7c7h7s";

        HandFactory handFactory = new HandFactory();
        HandScoreFactory scoreFactory = new HandScoreFactory();
        FiveCardHandEvaluator handEvaluator = new FiveCardHandEvaluator(scoreFactory);
        StandardDeckFactory deckFactory = new StandardDeckFactory();


        int handScore = handEvaluator.evaluateHand(handFactory.build(handString));

        if((handScore & HandScoreFactory.HAND_TYPE_MASK) != ( (HandType.FULL_HOUSE.ordinal() + 1) << HandScoreFactory.HAND_TYPE_SHIFT))
        {
            fail(String.format("Failed to correctly evaluate Hand %s, found score 0x%x", handString, handScore));
        }
    }
    
    /**
     * Test of evaluateHand method, of class FiveCardHandEvaluator.
     */
    @Test
    public void testEvaluateRandomHand() throws Exception 
    {
        
        
        HandFactory handFactory = new HandFactory();    
        HandScoreFactory scoreFactory = new HandScoreFactory();
        FiveCardHandEvaluator handEvaluator = new FiveCardHandEvaluator(scoreFactory);
        StandardDeckFactory deckFactory = new StandardDeckFactory();
        
        for(HandType handType : HandType.values())
        {
            int iterations = 0;
            while(iterations++ < 10000)
            {
                IHand randomHand = handFactory.generateRandom(handType, deckFactory);
                int handScore = handEvaluator.evaluateHand(randomHand);
                
                if((handScore & HandScoreFactory.HAND_TYPE_MASK) != ( (handType.ordinal() + 1) << HandScoreFactory.HAND_TYPE_SHIFT) )
                {
                    fail(String.format("Failed to correctly evaluate Hand %s, found score 0x%x", randomHand, handScore));
                }
            }                  
        }
    }
}