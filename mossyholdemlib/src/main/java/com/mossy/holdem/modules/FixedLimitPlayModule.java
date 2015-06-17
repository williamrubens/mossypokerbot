package com.mossy.holdem.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mossy.holdem.gametree.ExpectedValueCalculator;
import com.mossy.holdem.gametree.HoldemTreeBuilder;
import com.mossy.holdem.gametree.IHoldemTreeBuilder;
import com.mossy.holdem.implementations.FixedLimitActionBuilder;
import com.mossy.holdem.implementations.HoldemPlayer;
import com.mossy.holdem.implementations.PreFlopIncomeRateLookupFactory;
import com.mossy.holdem.implementations.player.PlayerInfoFactory;
import com.mossy.holdem.implementations.state.FLStateFactory;
import com.mossy.holdem.implementations.state.FixedLimitState;
import com.mossy.holdem.interfaces.IActionBuilder;
import com.mossy.holdem.interfaces.IHoldemPlayer;
import com.mossy.holdem.interfaces.IPreFlopIncomeRateVendor;
import com.mossy.holdem.interfaces.player.IPlayerInfoFactory;
import com.mossy.holdem.interfaces.state.IGameState;
import com.mossy.holdem.interfaces.state.IGameStateFactory;

/**
 * Created by williamrubens on 11/05/2014.
 */
public class FixedLimitPlayModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(IHoldemPlayer.class).to(HoldemPlayer.class);
        bind(IHoldemTreeBuilder.class).to(HoldemTreeBuilder.class);
        bind(IGameState.class).to(FixedLimitState.class);
        bind(IGameStateFactory.class).to(FLStateFactory.class);
        bind(IActionBuilder.class).to(FixedLimitActionBuilder.class);
        bind(IPlayerInfoFactory.class).to(PlayerInfoFactory.class);
        bind(ExpectedValueCalculator.class).to(ExpectedValueCalculator.class);


    }

    @Provides
    IPreFlopIncomeRateVendor providePreFlopIncomeRateVendor() throws Exception
    {
        PreFlopIncomeRateLookupFactory factory = new PreFlopIncomeRateLookupFactory();

        return factory.Build("prefloplookup_iterated.csv");
    }
}
