/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.pokerbot.implementations;

/**
 *
 * @author d80050
 */
public class PreFlopLookupCalculatorTest {
    
    public PreFlopLookupCalculatorTest() {
    }

//    @Test
//    public void testGeneratePreFlopLookup() {
//        try {
//            System.out.println("generatePreFlopLookup");
//
//              // Set up a simple configuration that logs on the console.
//            BasicConfigurator.configure();
//
//            SummaryStatistics stats1 = new SummaryStatistics();
//            SummaryStatistics stats2 = new SummaryStatistics();
//
//            Writer writer = null;
//
//            double tolerence = 0.0;
//            IDeck deck = new StandardDeckFactory().getMap();
//            int numPlayers = 10;
//            IHandFactory handFactory = new HandFactory();
//
//            HandScoreFactory handScoreFactory = new HandScoreFactory();
//            IHandEvaluator fiveCardHandEvaluator = new FiveCardHandEvaluator(handScoreFactory);
//            IHandEvaluator handEvaluator = new HandEvaluator(fiveCardHandEvaluator);          /*
//            PreFlopLookupCalculator instance = new PreFlopLookupCalculator();
//
//
//            instance.generateOnePreFlopLookup(Card.TWO_CLUBS, Card.TWO_DIAMONDS, deck, stats1, tolerence, numPlayers, handFactory, handEvaluator);
//            instance.generateOnePreFlopLookup(Card.SIX_CLUBS, Card.TWO_CLUBS, deck, stats2, tolerence, numPlayers, handFactory, handEvaluator);
//
//                                                                                         */
//            boolean doesNo1Win = stats1.getMean() > stats2.getMean();
//            assertEquals(true,doesNo1Win);
//
//        } catch (Exception ex) {
//            Logger.getLogger(PreFlopLookupCalculatorTest.class.getName()).log(Level.SEVERE, null, ex);
//            fail(ex.getMessage());
//        }
//
//    }
}