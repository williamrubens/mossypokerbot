/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.holdem.runner;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mossy.holdem.Card;
import com.mossy.holdem.modules.IteratedRolloutModule;
import com.mossy.holdem.PreFlopHandType;
import com.mossy.holdem.Rank;
import com.mossy.holdem.interfaces.IDeckFactory;

import java.io.PrintWriter;

import com.mossy.holdem.interfaces.IPreFlopIncomeRateSimulator;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;


/**
 *
 * @author d80050
 */
public class MossyPokerRunner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        try
        {



            // Set up a simple configuration that logs on the console.
            BasicConfigurator.configure();
           /*
            //PreFlopLookupCalculator lookupCalculator = new PreFlopLookupCalculator();
            StandardDeckFactory deckFactory = new StandardDeckFactory();
            HandScoreFactory handScoreFactory = new HandScoreFactory();
            IHandEvaluator fiveCardEvaluator = new FiveCardHandEvaluator(handScoreFactory);
            IHandEvaluator handEval = new HandEvaluator(fiveCardEvaluator);
            IHandFactory handFactory = new HandFactory();
            SummaryStatistics stats = new SummaryStatistics();
            PrintWriter writer = new PrintWriter("prefloplookup.csv");
            PreFlopHandTypeAdaptor adaptor = new PreFlopHandTypeAdaptor();   */

            PrintWriter writer = new PrintWriter("prefloplookup.csv");

            int numPlayers = 2;
            int boardCards = 5;

            Injector injector = Guice.createInjector(new IteratedRolloutModule(numPlayers, boardCards));
           // lookupCalculator.generatePreFlopLookup(stats, writer, 0.01, deckFactory.getMap(), adaptor, 2, handFactory, handEval);

            IPreFlopIncomeRateSimulator perFlopSimulator = injector.getInstance(IPreFlopIncomeRateSimulator.class);
            IDeckFactory deckFactory = injector.getInstance(IDeckFactory.class) ;

            //perFlopSimulator.simulateIncomeRate(deckFactory.build(), PreFlopHandType.fromHoleCards(Card.ACE_CLUBS, Card.ACE_DIAMONDS), 0.01);

            writer.close();

        }
        catch (Exception ex)
        {
            Logger log =  Logger.getLogger("main");
            log.error(String.format("MossyPokerRunner failed: %s ", ex.getMessage()));
        }


    }
}
