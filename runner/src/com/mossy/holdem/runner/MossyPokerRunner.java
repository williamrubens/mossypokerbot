/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.holdem.runner;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mossy.holdem.Card;
import com.mossy.holdem.IncomeRate;
import com.mossy.holdem.interfaces.IPreFlopRolloutSimulator;
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


            PrintWriter writer = new PrintWriter("prefloplookup.csv");

            int numPlayers = 10;
            int boardCards = 5;

            Injector injector = Guice.createInjector(new IteratedRolloutModule(numPlayers, boardCards));

            IPreFlopRolloutSimulator rolloutSimulator = injector.getInstance(IPreFlopRolloutSimulator.class);
            IDeckFactory deckFactory = injector.getInstance(IDeckFactory.class) ;

            ImmutableMap<PreFlopHandType, IncomeRate> map = ImmutableMap.of();

            rolloutSimulator.simulateRollout( map, 0.001, writer);

            writer.close();

        }
        catch (Exception ex)
        {
            Logger log =  Logger.getLogger("main");
            log.error(String.format("MossyPokerRunner failed: %s ", ex.getMessage()));
        }


    }
}
