package com.mossy.pokerbot.implementations;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.inject.Inject;
import com.mossy.pokerbot.IncomeRate;
import com.mossy.pokerbot.PreFlopHandType;
import com.mossy.pokerbot.interfaces.IDeck;
import com.mossy.pokerbot.interfaces.IDeckFactory;
import com.mossy.pokerbot.interfaces.IPreFlopIncomeRateSimulator;
import com.mossy.pokerbot.interfaces.IPreFlopRolloutSimulator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    final static private Logger log = LogManager.getLogger(PreFlopRolloutSimulator.class);
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
    public ImmutableMap<PreFlopHandType, IncomeRate> simulateRollout( ImmutableMap<PreFlopHandType, IncomeRate> handTypeToIncomeRate, double tolerance, PrintWriter printWriter, int iteration) throws Exception
    {
        if(printWriter != null)
        {
            synchronized (printWriter)
            {
                printWriter.println("HandType, Income Rate, Error, SD, Iteration");
                printWriter.flush();
            }
        }

        ImmutableSortedSet<PreFlopHandType> preflopHands = adaptor.adaptDeck(deckFactory.build());

        ImmutableMap.Builder<PreFlopHandType, IncomeRate> newIncomeRateBuilder = ImmutableMap.builder();

        int numCores = Runtime.getRuntime().availableProcessors();

        ListeningExecutorService threadPool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(numCores));

        for(PreFlopHandType handType : preflopHands)
        {
            IDeck deck = deckFactory.build();

            IncomeRateSimulationExecutor executor = new IncomeRateSimulationExecutor(handType, deck, handTypeToIncomeRate, tolerance, printWriter, iteration);

            ListenableFuture<IncomeRate> future = threadPool.submit(( Callable<IncomeRate>) executor );


        }

        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.DAYS);

        return newIncomeRateBuilder.build();
    }

    class IncomeRateSimulationExecutor implements  Callable<IncomeRate>
    {
        final PreFlopHandType handType;
        final IDeck deck;
        final ImmutableMap<PreFlopHandType, IncomeRate> handTypeToIncomeRate;
        final double tolerance;
        final PrintWriter printWriter;
        final int iteration;

        IncomeRate incomeRate;

        IncomeRateSimulationExecutor(PreFlopHandType handType, IDeck deck, ImmutableMap<PreFlopHandType, IncomeRate> handTypeToIncomeRate, double tolerance, PrintWriter printWriter, int iteration)
        {
            this.handType = handType;
            this.deck = deck;
            this.handTypeToIncomeRate = handTypeToIncomeRate;
            this.tolerance = tolerance;
            this.printWriter = printWriter;
            this.iteration = iteration;

        }

        @Override
        public IncomeRate call()  throws Exception
        {
            log.info(String.format("Beginning income rate simulation for %s", handType));
            incomeRate = incomeRateSimulator.simulateIncomeRate(deck, handTypeToIncomeRate, handType, tolerance);

            log.info(String.format("Finished income rate simulation for %s, found %.4g, %.4g, %.4g ", handType, incomeRate.incomeRate(), incomeRate.error(), incomeRate.standardDeviation()));
            if(printWriter != null)
            {
                String temp = String.format("%s, %.4g, %.4g, %.4g, %s", handType, incomeRate.incomeRate(), incomeRate.error(), incomeRate.standardDeviation(), iteration) ;
                synchronized (printWriter)
                {
                    printWriter.println(temp);
                    printWriter.flush();

                }
            }

            return incomeRate;

        }


    }




}

