package com.mossy.holdem.implementations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.inject.Inject;
import com.mossy.holdem.IncomeRate;
import com.mossy.holdem.PreFlopHandType;
import com.mossy.holdem.interfaces.IDeck;
import com.mossy.holdem.interfaces.IDeckFactory;
import com.mossy.holdem.interfaces.IPreFlopIncomeRateSimulator;
import com.mossy.holdem.interfaces.IPreFlopRolloutSimulator;
import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 23/05/13
 * Time: 21:24
 * To change this template use File | Settings | File Templates.
 */
public class PreFlopRolloutSimulator implements IPreFlopRolloutSimulator
{
    final private IPreFlopIncomeRateSimulator incomeRateSimulator;
    final private PreFlopHandTypeAdaptor adaptor;
    final static private Logger log = Logger.getLogger(PreFlopRolloutSimulator.class);
    final private IDeckFactory deckFactory;

    private

    @Inject
    PreFlopRolloutSimulator(IPreFlopIncomeRateSimulator incomeRateSimulator,PreFlopHandTypeAdaptor adaptor, IDeckFactory deckFactory)
    {
        this.incomeRateSimulator = incomeRateSimulator;
        this.adaptor = adaptor;
        this.deckFactory = deckFactory;

    }

    @Override
    public ImmutableMap<PreFlopHandType, IncomeRate> simulateRollout( ImmutableMap<PreFlopHandType, IncomeRate> handTypeToIncomeRate, double tolerance, PrintWriter printWriter) throws Exception
    {
        if(printWriter != null)
        {
            printWriter.println("HandType, Income Rate, Error, SD");
        }

        ImmutableSortedSet<PreFlopHandType> preflopHands = adaptor.adaptDeck(deckFactory.build());

        ImmutableMap.Builder<PreFlopHandType, IncomeRate> newIncomeRateBuilder = ImmutableMap.builder();

        int numCores = Runtime.getRuntime().availableProcessors();

        ListeningExecutorService threadPool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(numCores));

        for(PreFlopHandType handType : preflopHands)
        {
            IDeck deck = deckFactory.build();

            IncomeRateSimulationExecutor executor = new IncomeRateSimulationExecutor(handType, deck, handTypeToIncomeRate, tolerance, printWriter);

            threadPool.submit(executor );

        }

        return newIncomeRateBuilder.build();
    }

    class IncomeRateSimulationExecutor implements  Callable<IncomeRate>
    {
        PreFlopHandType handType;
        IDeck deck;
        ImmutableMap<PreFlopHandType, IncomeRate> handTypeToIncomeRate;
        double tolerance;
        PrintWriter printWriter;

        IncomeRateSimulationExecutor(PreFlopHandType handType, IDeck deck, ImmutableMap<PreFlopHandType, IncomeRate> handTypeToIncomeRate, double tolerance, PrintWriter printWriter)
        {
            this.handType = handType;
            this.deck = deck;
            this.handTypeToIncomeRate = handTypeToIncomeRate;
            this.tolerance = tolerance;
            this.printWriter = printWriter;

        }

        @Override
        public IncomeRate call()  throws Exception
        {
            log.info(String.format("Beginning income rate simulation for %s", handType));
            IncomeRate incomeRate = incomeRateSimulator.simulateIncomeRate(deck, handTypeToIncomeRate, handType, tolerance);

            if(printWriter != null)
            {
                printWriter.println(String.format("%s, %.4g, %.4g, %.4g", handType, incomeRate.incomeRate(), incomeRate.error(), incomeRate.standardDeviation()));
            }
            log.info(String.format("Finished income rate simulation for %s, found %.4g, %.4g, %.4g ", handType, incomeRate.incomeRate(), incomeRate.error(), incomeRate.standardDeviation()));

            return incomeRate;

        }

    }




}

