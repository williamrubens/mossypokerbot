package com.mossy.holdem.implementations;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.mossy.holdem.*;
import com.mossy.holdem.gametree.ExpectedValueCalculator;
import com.mossy.holdem.gametree.IHoldemTreeBuilder;
import com.mossy.holdem.gametree.IHoldemTreeData;
import com.mossy.holdem.gametree.ITreeNode;
import com.mossy.holdem.interfaces.IActionBuilder;
import com.mossy.holdem.interfaces.IHoldemPlayer;
import com.mossy.holdem.interfaces.IPreFlopIncomeRateVendor;
import com.mossy.holdem.interfaces.player.IPlayerInfoFactory;
import com.mossy.holdem.interfaces.player.IPlayerState;
import com.mossy.holdem.interfaces.state.IGameState;
import com.mossy.holdem.interfaces.state.IGameStateFactory;

import java.util.*;

/**
 * User: William
 * Date: 15/09/2013
 * Time: 11:29
 */
public class HoldemPlayer implements IHoldemPlayer
{
    IPreFlopIncomeRateVendor preFlopIncomeRateVendor;
    IHoldemTreeBuilder treeBuilder;
    ExpectedValueCalculator evCalculator;
    IGameState currentState;
    IGameStateFactory stateFactory;
    IActionBuilder actionBuilder;
    IPlayerInfoFactory playerFactory;

    IPlayerState me;
    HoleCards holeCards;

    @Inject
    HoldemPlayer(IPreFlopIncomeRateVendor preFlopIncomeRateVendor,
                 IHoldemTreeBuilder treeBuilder, ExpectedValueCalculator evCalculator,
                 IGameStateFactory stateFactory, IActionBuilder actionBuilder,
                 IPlayerInfoFactory playerFactory)
    {
        this.preFlopIncomeRateVendor = preFlopIncomeRateVendor;
        this.treeBuilder = treeBuilder;
        this.evCalculator = evCalculator;
        this.stateFactory = stateFactory;
        this.actionBuilder = actionBuilder;
        this.playerFactory = playerFactory;

    }

    @Override
    public void startGame(List<IPlayerState> playerStates, int dealerPosition)
    {
        currentState = stateFactory.buildNewState(ImmutableList.copyOf(playerStates), dealerPosition);
    }

    @Override
    public void setHoleCards(Card card1, Card card2, int seat)
    {
        holeCards = HoleCards.from(card1, card2);
    }

    @Override
    public void setFlop(Card card1, Card card2, Card card3)
    {
        currentState = stateFactory.buildNextState(currentState, Action.Factory.dealFlopAction(card1, card2, card3));
    }

    @Override
    public void setTurn(Card turn)
    {
        currentState = stateFactory.buildNextState(currentState, Action.Factory.dealTurnAction(turn));
    }

    @Override
    public void setRiver(Card river)
    {
        currentState = stateFactory.buildNextState(currentState, Action.Factory.dealRiverAction(river));
    }

    @Override
    public Action getNextAction()
    {
        switch (currentState.street())
        {
            case PRE_FLOP:
                return getPreFlopAction();
            default:
                return getPostFlopAction();
        }
    }

    private Action getPreFlopAction()
    {
        IncomeRate ir = preFlopIncomeRateVendor.getIncomeRate(currentState.playerStates().size(), PreFlopHandType.fromHoleCards(holeCards));
        if(ir.incomeRate() > 0)
        {
            ImmutableList<Action> actions = actionBuilder.buildAllChildActions(currentState);

            if(actions.size() == 1){
                return actions.get(0);
            }

            if(currentState.getAmountToCall().compareTo(currentState.bigBlind().multiply(2.0)) < 0) {
                return Action.Factory.callAction();
            }

            return Action.Factory.foldAction();

        }
        else
        {
            return Action.Factory.foldAction();
        }

    }


    private Action getPostFlopAction()
    {

        ITreeNode<IHoldemTreeData> gameTree = treeBuilder.buildTree(currentState);


        SortedMap<ChipStack, Action> evs = new TreeMap();

        for(ITreeNode<IHoldemTreeData> childState : gameTree.children()) {
            ChipStack ev = evCalculator.calculateExpectedValue(me, childState);
            evs.put(ev, childState.data().state().lastAction());
        }

        return evs.get(evs.lastKey());

        // build tree
        // take ev
        // pick action



    }

}
