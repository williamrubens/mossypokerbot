package com.mossy.holdem.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.gametree.ExpectedValueCalculator;
import com.mossy.holdem.gametree.HoldemTreeBuilder;
import com.mossy.holdem.gametree.IExpectedValueCalculator;
import com.mossy.holdem.gametree.IHoldemTreeBuilder;
import com.mossy.holdem.implementations.*;
import com.mossy.holdem.implementations.fastevaluator.FastHandEvaluator;
import com.mossy.holdem.implementations.player.MutablePlayerState;
import com.mossy.holdem.implementations.player.NeuralNetworkPlayerModel;
import com.mossy.holdem.implementations.player.PlayerInfoFactory;
import com.mossy.holdem.implementations.player.PlayerStatisticsHolder;
import com.mossy.holdem.implementations.state.EquiProbableActionCalculator;
import com.mossy.holdem.implementations.state.FLStateFactory;
import com.mossy.holdem.implementations.state.FixedLimitState;
import com.mossy.holdem.implementations.state.MyWinActionProbabilityCalculator;
import com.mossy.holdem.interfaces.*;
import com.mossy.holdem.interfaces.player.IMutablePlayerState;
import com.mossy.holdem.interfaces.player.IPlayerInfoFactory;
import com.mossy.holdem.interfaces.player.IPlayerModel;
import com.mossy.holdem.interfaces.player.IPlayerStatisticsHolder;
import com.mossy.holdem.interfaces.state.IActionProbabilityCalculator;
import com.mossy.holdem.interfaces.state.IGameState;
import com.mossy.holdem.interfaces.state.IGameStateFactory;

/**
 * Created by williamrubens on 11/05/2014.
 */
public class FixedLimitPlayModule extends AbstractModule
{

    private final ChipStack lowerLimit;
    private final ChipStack higherLimit;

    public FixedLimitPlayModule(ChipStack lowerLimit, ChipStack higherLimit) {
        this.lowerLimit = lowerLimit;
        this.higherLimit = higherLimit;
    }

    @Override
    protected void configure()
    {
        bind(IHoldemPlayer.class).to(HoldemPlayer.class);
        bind(IHoldemTreeBuilder.class).to(HoldemTreeBuilder.class);
        //bind(IGameState.class).to(FixedLimitState.class);
        bind(IActionBuilder.class).to(FixedLimitActionBuilder.class);
        bind(IDealerActionBuilder.class).to(SingelDealerActionBuilder.class);
        bind(IPlayerInfoFactory.class).to(PlayerInfoFactory.class);
        bind(IExpectedValueCalculator.class).to(ExpectedValueCalculator.class);
        bind(IActionProbabilityCalculator.class).to(MyWinActionProbabilityCalculator.class).in(Scopes.SINGLETON);
        bind(IDeckFactory.class).to(StandardDeckFactory.class);
        bind(IHandStrengthCalculator.class).to(HandStrengthCalculator.class);
        bind(IHandFactory.class).to(HandFactory.class);
        bind(IHandEvaluator.class).to(FastHandEvaluator.class);
        bind(IHandScoreFactory.class).to(HandScoreFactory.class);

        bind(IMutablePlayerState.class).to(MutablePlayerState.class).in(Scopes.SINGLETON);
        bind(IPlayerStatisticsHolder.class).to(PlayerStatisticsHolder.class).in(Scopes.SINGLETON);
        bind(IPlayerModel.class).to(NeuralNetworkPlayerModel.class);


    }

    @Provides
    IPreFlopIncomeRateVendor providePreFlopIncomeRateVendor() throws Exception
    {
        PreFlopIncomeRateLookupFactory factory = new PreFlopIncomeRateLookupFactory();

        return factory.Build("prefloplookup_iterated.csv");
    }

    @Provides
    IGameStateFactory provideGameStateFactory(Provider<IPlayerInfoFactory> playerInfoFactory)
    {
        return new FLStateFactory(playerInfoFactory.get(), lowerLimit, higherLimit);
    }
}
