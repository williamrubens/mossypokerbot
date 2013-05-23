package com.mossy.holdem.implementations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.mossy.holdem.Card;
import com.mossy.holdem.HoleCards;
import com.mossy.holdem.IncomeRate;
import com.mossy.holdem.PreFlopHandType;
import com.mossy.holdem.interfaces.*;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 15/05/13
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class PreFlopIncomeRateSimulator implements IPreFlopIncomeRateSimulator
{
    static private Logger log = Logger.getLogger(PreFlopIncomeRateSimulator.class);
    final private IHoleCardDealer oppHoleCardsDealer;
    final private IBoardCardDealer boardCardDealer;
    final private IRolloutWinningsCalculator rolloutWinningsCalculator;
    final private INegativeIncomeRateFolder folder;
    final private SummaryStatisticsFactory statsFactory;

    @Inject
    private PreFlopIncomeRateSimulator(IHoleCardDealer _oppHoleCardsDealer, IBoardCardDealer _boardCardDealer,
                               IRolloutWinningsCalculator _rolloutWinningsCalculator, INegativeIncomeRateFolder _folder,
                               SummaryStatisticsFactory _statsFactory)
    {
        oppHoleCardsDealer = _oppHoleCardsDealer;
        boardCardDealer = _boardCardDealer;
        rolloutWinningsCalculator = _rolloutWinningsCalculator;
        folder = _folder;
        statsFactory = _statsFactory;
    }



    @Override
    public IncomeRate simulateIncomeRate(IDeck deck, ImmutableMap<PreFlopHandType, IncomeRate> handTypeToIncomeRate, PreFlopHandType handType, double tolerance) throws Exception
    {
        double error = 1;

        int numIterations = 0;
        int numWins = 0;
        int numDraws = 0;
        int numLose = 0;

        Timer evaluateTimer = new Timer("Evaluate");
        Timer dealingTimer = new Timer("Dealing");
        Timer handFactoryTimer = new Timer("Hand");
        Timer statsTimer = new Timer("Stats");
        long generationStart = System.currentTimeMillis();

        SummaryStatistics stats = statsFactory.build();

        while((Math.abs(error) > tolerance && numIterations < 100000) || numIterations < 100)
        {
            deck.shuffle();

            dealingTimer.startTimer();

            // deal my cards
            HoleCards myHoleCards = handType.toHoleCardsNonDeterministic();
            deck.dealCard(myHoleCards.card1());
            deck.dealCard(myHoleCards.card2());

            // deal opponent cards
            ImmutableList<HoleCards> oppHoleCards = oppHoleCardsDealer.deal(deck);

            // deal board cards
            ImmutableList<Card> boardCards = boardCardDealer.deal(deck);

            dealingTimer.stopTimer();

            // fold any hands required
            ImmutableList<HoleCards> nonFoldedHands = folder.foldHoleCards(oppHoleCards, handTypeToIncomeRate);

            evaluateTimer.startTimer();

            // calculate winnings
            double myWinnings = rolloutWinningsCalculator.calculate(myHoleCards, nonFoldedHands, boardCards);

            evaluateTimer.stopTimer();
            statsTimer.startTimer();

            stats.addValue(myWinnings);


            statsTimer.stopTimer();


            numIterations++;
            error = stats.getStandardDeviation() / stats.getMean();

            if(stats.getMean() == 0)
            {
                continue;
            }

            if(numIterations % 10000 == 0)
            {
                long totalTicks = System.currentTimeMillis() - generationStart;
                double percentHand = (double)handFactoryTimer.totalTime() / (double)totalTicks * 100.0d;
                double percentEval = (double)evaluateTimer.totalTime / (double)totalTicks* 100.0d;
                double percentDeal = (double)dealingTimer.totalTime / (double)totalTicks* 100.0d;
                double percentStats = (double)statsTimer.totalTime / (double)totalTicks* 100.0d;
                log.debug(String.format("sd %.2g Mean %.2g W: %.2g %% D: %.2g %% L: %.2g %% Iterations: %s of which Hand %.2g %% Evaluation %.2g %% Stats  %.2g %%, Dealing %.2g %%, Avg eval time: %s",stats.getStandardDeviation(), stats.getMean(), (double)numWins/(double)numIterations * 100.0d, (double)numDraws/(double)numIterations* 100.0d, (double)numLose/(double)numIterations* 100.0d,  numIterations, percentHand, percentEval,  percentStats ,percentDeal, evaluateTimer.avgTime() ));
            }
        }
        log.debug("num operations = " +  numIterations);

        return IncomeRate.fromStats(stats);

    }

}
