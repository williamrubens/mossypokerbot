package com.mossy.pokerbot.implementations;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.mossy.pokerbot.*;
import com.mossy.pokerbot.gametree.ExpectedValueCalculator;
import com.mossy.pokerbot.gametree.IHoldemTreeBuilder;
import com.mossy.pokerbot.gametree.IHoldemTreeData;
import com.mossy.pokerbot.gametree.ITreeNode;
import com.mossy.pokerbot.interfaces.IActionBuilder;
import com.mossy.pokerbot.interfaces.IHand;
import com.mossy.pokerbot.interfaces.IHoldemPlayer;
import com.mossy.pokerbot.interfaces.IPreFlopIncomeRateVendor;
import com.mossy.pokerbot.interfaces.player.*;
import com.mossy.pokerbot.interfaces.state.IGameState;
import com.mossy.pokerbot.interfaces.state.IGameStateFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.google.common.collect.FluentIterable.from;

/**
 * User: William
 * Date: 15/09/2013
 * Time: 11:29
 */
public class HoldemPlayer implements IHoldemPlayer
{
    final IPreFlopIncomeRateVendor preFlopIncomeRateVendor;
    final IHoldemTreeBuilder treeBuilder;
    final ExpectedValueCalculator evCalculator;
    IGameState currentState;
    final IGameStateFactory stateFactory;
    final IActionBuilder actionBuilder;
    final IPlayerInfoFactory playerFactory;
    final IMutablePlayerState myState;
    final IPlayerStatisticsHolder statsHolder;

    final static private Logger log = LogManager.getLogger(HoldemPlayer.class);


    @Inject
    HoldemPlayer(IPreFlopIncomeRateVendor preFlopIncomeRateVendor,
                 IHoldemTreeBuilder treeBuilder, ExpectedValueCalculator evCalculator,
                 IGameStateFactory stateFactory, IActionBuilder actionBuilder,
                 IPlayerInfoFactory playerFactory, IMutablePlayerState myState, IPlayerStatisticsHolder statsHolder)
    {
        this.preFlopIncomeRateVendor = preFlopIncomeRateVendor;
        this.treeBuilder = treeBuilder;
        this.evCalculator = evCalculator;
        this.stateFactory = stateFactory;
        this.actionBuilder = actionBuilder;
        this.playerFactory = playerFactory;
        this.myState = myState;
        this.statsHolder = statsHolder;

    }

    @Override
    public void startGame(Collection<IPlayerState> playerStates, int dealerPosition)
    {
        currentState = stateFactory.buildNewState(ImmutableList.copyOf(playerStates), dealerPosition);

    }

    @Override
    public void setHoleCards(Card card1, Card card2, int seat)
    {
        myState.setHoleCards(HoleCards.from(card1, card2));
        myState.copy(currentState.playerStates().get(seat));

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

    @Override
    public void setNextAction(Action action) {


        statsHolder.updatePlayer(currentState, action);

        currentState = stateFactory.buildNextState(currentState, action);



    }

    @Override
    public IGameState currentState() {
        return currentState;
    }

    private Action getPreFlopAction()
    {
        IncomeRate ir = preFlopIncomeRateVendor.getIncomeRate(currentState.playerStates().size(), PreFlopHandType.fromHoleCards(myState.holeCards()));
        if(ir.incomeRate() > 0)
        {
            ImmutableList<Action> actions = actionBuilder.buildAllChildActions(currentState);

            if(actions.size() == 1){
                return actions.get(0);
            }

            if(currentState.getAmountToCall().compareTo(currentState.bigBlind().multiply(2.0)) < 0) {
                if(from(actions).anyMatch(action -> action.type() == Action.ActionType.CHECK)) {
                    return Action.Factory.checkAction();
                }
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

        IHand communityCards = new HandFactory().build(currentState().communityCards());

        String output = String.format("MossyBot %s %s ev:", currentState.street(), communityCards);

        for(ITreeNode<IHoldemTreeData> childState : gameTree.children()) {
            ChipStack ev = evCalculator.calculateExpectedValue(myState, childState);
            evs.put(ev, childState.data().state().lastAction());

            output += String.format("%s %s, ", childState.data().state().lastAction(), ev);
        }

        log.info(output);

        ChipStack expectedValue = evs.lastKey();

        // we put in a hack here: Because we are currently assuming all actions occur with equal probablity
        // it over-represents the opponent fold scenarios when we have crappy cards and skews the ev's towards assuming the opp is more
        // likely to fold when ever we bet, even if we have shitty cards. Therefore, if the ev is negative anyway, and we can check, let's go
        // ahead and check
        if(expectedValue.compareTo(ChipStack.of(0)) < 0) {
            if(evs.values().contains(Action.Factory.checkAction()))
                return Action.Factory.checkAction();
            else
                return Action.Factory.foldAction();
        }
        return evs.get(expectedValue);

    }

}
