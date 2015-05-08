package com.mossy.holdem.modules;

import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mossy.holdem.IncomeRate;
import com.mossy.holdem.PreFlopHandType;
import com.mossy.holdem.annotations.Annotations;
import com.mossy.holdem.implementations.*;
import com.mossy.holdem.implementations.FastEvaluator.FastHandEvaluator;
import com.mossy.holdem.interfaces.*;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 19/05/13
 * Time: 12:23
 * To change this template use File | Settings | File Templates.
 */
public class IteratedRolloutModule extends AbstractModule
{
    final int numOpponents;
    final int numBoardCards;

    public IteratedRolloutModule(int numPlayers, int numBoardCards) {
        this.numOpponents = numPlayers - 1;
        this.numBoardCards = numBoardCards;
    }

    @Override
    protected void configure()
    {
        bindConstant().annotatedWith(Annotations.NumBoardCards.class).to(numBoardCards);
        bindConstant().annotatedWith(Annotations.NumPlayers.class).to(numOpponents);

        bind(IHandFactory.class).to(HandFactory.class);
        bind(IHandScoreFactory.class).to(HandScoreFactory.class);
        bind(IStatisticsFactory.class).to(SummaryStatisticsFactory.class);

        bind(IDeckFactory.class).to(StandardDeckFactory.class)        ;
        bind(IRolloutWinningsCalculator.class).to(RolloutWinningsCalculator.class);
        bind(IPreFlopHandTypeAdaptor.class).to(PreFlopHandTypeAdaptor.class);
        bind(IPreFlopIncomeRateStore.class).to(PreFlopIncomeRateStore.class);
        bind(IPreFlopIncomeRateSimulator.class).to(PreFlopIncomeRateSimulator.class);
        bind(IPreFlopRolloutSimulator.class).to(PreFlopRolloutSimulator.class);

        bind(IHandEvaluator.class).to(FastHandEvaluator.class);
        //bind(IHandEvaluator.class).to(HandEvaluator.class);
        //bind(IHandEvaluator.class).annotatedWith(Annotations.FiveCardEvaluator.class).to(FiveCardHandEvaluator.class);

        bind(IBoardCardDealer.class).to(BoardCardDealer.class);
        bind(IHoleCardDealer.class).to(HoleCardDealer.class);

        bind(INegativeIncomeRateFolder.class).to(NegativeIncomeRateFolder.class);

    }

    @Provides
    ImmutableMap<PreFlopHandType, IncomeRate> preFlopHandTypeToIncomeRate()
    {
        return ImmutableMap.of();
    }


}
