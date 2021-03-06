/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.pokerbot.runner;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mossy.pokerbot.IncomeRate;
import com.mossy.pokerbot.interfaces.IPreFlopRolloutSimulator;
import com.mossy.pokerbot.modules.IteratedRolloutModule;
import com.mossy.pokerbot.PreFlopHandType;

import java.io.PrintWriter;

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




            int numPlayers = 10;
            int boardCards = 5;

            Injector injector = Guice.createInjector(new IteratedRolloutModule(numPlayers, boardCards));

            IPreFlopRolloutSimulator rolloutSimulator = injector.getInstance(IPreFlopRolloutSimulator.class);

            ImmutableMap<PreFlopHandType, IncomeRate> map = ImmutableMap.of();

            PrintWriter writer = new PrintWriter("prefloplookup_iterated.csv");


            for(int iterations = 0; iterations < 10; ++iterations)
            {
               map = rolloutSimulator.simulateRollout( map, 0.001, writer, iterations);
            }

            writer.close();


        }
        catch (Exception ex)
        {
            Logger log =  Logger.getLogger("main");
            log.error(String.format("MossyPokerRunner failed: %s ", ex.getMessage()));
        }


    }
}
