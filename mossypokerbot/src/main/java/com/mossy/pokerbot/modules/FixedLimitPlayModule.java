package com.mossy.pokerbot.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.mossy.pokerbot.ChipStack;
import com.mossy.pokerbot.gametree.ExpectedValueCalculator;
import com.mossy.pokerbot.gametree.HoldemTreeBuilder;
import com.mossy.pokerbot.gametree.IExpectedValueCalculator;
import com.mossy.pokerbot.gametree.IHoldemTreeBuilder;
import com.mossy.pokerbot.implementations.*;
import com.mossy.pokerbot.implementations.fastevaluator.FastHandEvaluator;
import com.mossy.pokerbot.implementations.player.MutablePlayerState;
import com.mossy.pokerbot.implementations.player.NeuralNetworkPlayerModel;
import com.mossy.pokerbot.implementations.player.PlayerInfoFactory;
import com.mossy.pokerbot.implementations.player.PlayerStatisticsHolder;
import com.mossy.pokerbot.implementations.state.FLStateFactory;
import com.mossy.pokerbot.implementations.state.MyWinActionProbabilityCalculator;
import com.mossy.pokerbot.interfaces.*;
import com.mossy.pokerbot.interfaces.player.IMutablePlayerState;
import com.mossy.pokerbot.interfaces.player.IPlayerInfoFactory;
import com.mossy.pokerbot.interfaces.player.IPlayerModel;
import com.mossy.pokerbot.interfaces.player.IPlayerStatisticsHolder;
import com.mossy.pokerbot.interfaces.state.IActionProbabilityCalculator;
import com.mossy.pokerbot.interfaces.state.IGameStateFactory;

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
