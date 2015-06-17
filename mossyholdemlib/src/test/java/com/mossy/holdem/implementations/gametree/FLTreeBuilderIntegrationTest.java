package com.mossy.holdem.implementations.gametree;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.Card;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.Street;
import com.mossy.holdem.gametree.ExpectedValueCalculator;
import com.mossy.holdem.gametree.HoldemTreeBuilder;
import com.mossy.holdem.gametree.IHoldemTreeData;
import com.mossy.holdem.gametree.ITreeNode;
import com.mossy.holdem.implementations.DealerActionBuilder;
import com.mossy.holdem.implementations.FixedLimitActionBuilder;
import com.mossy.holdem.implementations.SmallDeckFactory;
import com.mossy.holdem.implementations.player.PlayerInfoFactory;
import com.mossy.holdem.implementations.player.PlayerState;
import com.mossy.holdem.implementations.state.*;
import com.mossy.holdem.interfaces.state.IActionProbabilityCalculator;
import com.mossy.holdem.interfaces.state.IGameState;
import com.mossy.holdem.interfaces.player.IPlayerState;
import com.mossy.holdem.interfaces.player.IPlayerInfoFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

import java.util.function.Predicate;

/**
 * Created by willrubens on 12/05/15.
 */
public class FLTreeBuilderIntegrationTest {

    public  ITreeNode<IHoldemTreeData> buildTree (Street street, int dealerPos, ImmutableList<Card> communityCards, Action lastAction) throws Exception{

        FixedLimitActionBuilder actionBuilder = new FixedLimitActionBuilder();
        IPlayerInfoFactory playerFactory = new PlayerInfoFactory();
        FLStateFactory stateFactory = new FLStateFactory(playerFactory, ChipStack.TWO_CHIPS, ChipStack.of(4)) ;

        IActionProbabilityCalculator probCalc = mock(IActionProbabilityCalculator.class);


        HoldemTreeBuilder treeBuilder = new HoldemTreeBuilder(stateFactory, actionBuilder, probCalc);

        ImmutableList<IPlayerState> players = ImmutableList.of(new PlayerState(0, ChipStack.of(10), ChipStack.NO_CHIPS),
                new PlayerState(1, ChipStack.of(10), ChipStack.NO_CHIPS));

        IGameState initialState = stateFactory.buildState(street, players, dealerPos, communityCards, lastAction, 3);

        return treeBuilder.buildTree(initialState);
    }

    public  ITreeNode<IHoldemTreeData> build2PlayerTreeWithDealer(Street street, int dealerPos, ImmutableList<Card> communityCards, Action lastAction, ChipStack playerBank) throws Exception{

        DealerActionBuilder dealer = new DealerActionBuilder(new SmallDeckFactory());
        FixedLimitActionBuilder actionBuilder = new FixedLimitActionBuilder(dealer);
        IPlayerInfoFactory playerFactory = new PlayerInfoFactory();
        FLStateFactory stateFactory = new FLStateFactory(playerFactory, ChipStack.TWO_CHIPS, ChipStack.of(4)) ;

        IActionProbabilityCalculator probCalc = new EquiProbableActionCalculator();

        HoldemTreeBuilder treeBuilder = new HoldemTreeBuilder(stateFactory, actionBuilder, probCalc);

        ImmutableList<IPlayerState> players = ImmutableList.of(new PlayerState(0, playerBank, ChipStack.NO_CHIPS),
                new PlayerState(1, playerBank, ChipStack.NO_CHIPS));

        IGameState initialState = stateFactory.buildState(street, players, dealerPos, communityCards, lastAction, 3);

        return treeBuilder.buildTree(initialState);
    }

    public  ITreeNode<IHoldemTreeData> build3PlayerTreeWithDealer(Street street, int dealerPos, ImmutableList<Card> communityCards, Action lastAction, ChipStack playerBank) throws Exception{

        DealerActionBuilder dealer = new DealerActionBuilder(new SmallDeckFactory());
        FixedLimitActionBuilder actionBuilder = new FixedLimitActionBuilder(dealer);
        IPlayerInfoFactory playerFactory = new PlayerInfoFactory();
        FLStateFactory stateFactory = new FLStateFactory(playerFactory, ChipStack.TWO_CHIPS, ChipStack.of(4)) ;

        IActionProbabilityCalculator probCalc = mock(IActionProbabilityCalculator.class);


        HoldemTreeBuilder treeBuilder = new HoldemTreeBuilder(stateFactory, actionBuilder, probCalc);

        ImmutableList<IPlayerState> players = ImmutableList.of(new PlayerState(0, playerBank, ChipStack.NO_CHIPS),
                new PlayerState(1, playerBank, ChipStack.NO_CHIPS), new PlayerState(2, playerBank, ChipStack.NO_CHIPS));

        IGameState initialState = stateFactory.buildState(street, players, dealerPos, communityCards, lastAction, 3);

        return treeBuilder.buildTree(initialState);
    }
    @DataProvider(name = "fullTreeBuilderTests")
    public Object[][] createFullTreeData() {
        return new Object[][] {
//                {Street.PRE_FLOP, 0, ImmutableList.of(), Action.Factory.dealHoleCards()},
//                {Street.FLOP, 0, ImmutableList.of(Card.ACE_CLUBS, Card.ACE_DIAMONDS, Card.ACE_HEARTS), Action.Factory.dealHoleCards()},
                {Street.TURN, 0, ImmutableList.of(), Action.Factory.dealHoleCards()},
                {Street.RIVER, 0, ImmutableList.of(), Action.Factory.dealHoleCards()},
//                {Street.SHOWDOWN, 0, ImmutableList.of(), Action.Factory.dealHoleCards()},

        };
    }

    @Test(dataProvider = "fullTreeBuilderTests")
    public void buildFullTree(Street street, int dealerPos, ImmutableList<Card> communityCards, Action lastAction) throws Exception
    {
        ITreeNode<IHoldemTreeData> fullTree = build2PlayerTreeWithDealer(street, dealerPos, communityCards, lastAction, ChipStack.of(10));

        int nodes = recursiveCountNodes(fullTree);
        fullTree.children();

    }

    public int recursiveCountNodes(ITreeNode<IHoldemTreeData> daddyNode)
    {
        int nodesSoFar = daddyNode.children().size();
        for(ITreeNode<IHoldemTreeData> child : daddyNode.children()) {
            nodesSoFar += recursiveCountNodes(child);
        }

        return nodesSoFar;
    }

    public ITreeNode<IHoldemTreeData> recursiveDepthFirstSearch(ITreeNode<IHoldemTreeData> daddyNode, Predicate<IHoldemTreeData> predicate)
    {
        for(ITreeNode<IHoldemTreeData> child : daddyNode.children()) {
            if(predicate.test(child.data())) {
                return child;
            }
            ITreeNode<IHoldemTreeData> foundChild = recursiveDepthFirstSearch(child, predicate);
            if(foundChild != null) {
                return foundChild;
            }
        }
        return null;

    }

        @DataProvider(name = "treeBuilderTests")
        public Object[][] createTreeData() {
            return new Object[][] {
//                    {Street.PRE_FLOP, 0, ImmutableList.of(), Action.Factory.dealHoleCards()},
//                    {Street.FLOP, 0, ImmutableList.of(Card.ACE_CLUBS, Card.ACE_DIAMONDS, Card.ACE_HEARTS), Action.Factory.dealHoleCards()},
                    {Street.TURN, 0, ImmutableList.of(), Action.Factory.dealHoleCards()},
                    {Street.RIVER, 0, ImmutableList.of(), Action.Factory.dealRiverAction(Card.ACE_DIAMONDS)},
    //                {Street.SHOWDOWN, 0, ImmutableList.of(), Action.Factory.dealHoleCards()},

            };
        }

    @Test(dataProvider = "treeBuilderTests")
    public void TwoPlayerGameTreeWithDealer_calculatesEv_(Street street, int dealerPos, ImmutableList<Card> communityCards, Action lastAction) throws Exception {

        ITreeNode<IHoldemTreeData> gameTree = build2PlayerTreeWithDealer(street, dealerPos, communityCards, lastAction, ChipStack.of(100));

        ExpectedValueCalculator evCalculator = new ExpectedValueCalculator();

        ChipStack ev = evCalculator.calculateExpectedValue(gameTree.data().state().playerStates().get(0), gameTree);

        ev.compareTo(ev);


    }

    @Test(dataProvider = "treeBuilderTests")
    public void TwoPlayerGameTreeWithDealer_raisesThreeTimes_capWorks(Street street, int dealerPos, ImmutableList<Card> communityCards, Action lastAction) throws Exception {

        ITreeNode<IHoldemTreeData> gameTree = build2PlayerTreeWithDealer(street, dealerPos, communityCards, lastAction, ChipStack.of(100));

        Predicate<IHoldemTreeData> hasRaisesPredicate = iHoldemTreeData -> iHoldemTreeData.state().lastAction().type() == Action.ActionType.RAISE_TO;
        // find node with first raise
        ITreeNode<IHoldemTreeData> firstRaiseNode = recursiveDepthFirstSearch(gameTree, hasRaisesPredicate);

        // check it has two children with raises
        ITreeNode<IHoldemTreeData> secondRaiseNode = recursiveDepthFirstSearch(firstRaiseNode, hasRaisesPredicate);
        ITreeNode<IHoldemTreeData> thirdRaiseNode = recursiveDepthFirstSearch(secondRaiseNode, hasRaisesPredicate);

        assertEquals(thirdRaiseNode.children().size(), 2);

    }

    @Test(dataProvider = "treeBuilderTests")
    public void gameTreeWithDealerAndSmallBank_raisesAllIn_cutsTreeShort(Street street, int dealerPos, ImmutableList<Card> communityCards, Action lastAction) throws Exception {

        // give players a bank of 6, therefore with a fixed limit of 2 and 4, the first raise will be to 6 and will therefore be an all in
        // therefore action should terminate there

        ITreeNode<IHoldemTreeData> gameTree = build2PlayerTreeWithDealer(street, dealerPos, communityCards, lastAction, ChipStack.of(6));

        Predicate<IHoldemTreeData> hasRaisesPredicate = iHoldemTreeData -> iHoldemTreeData.state().lastAction().type() == Action.ActionType.RAISE_TO;
        // find node with first raise
        ITreeNode<IHoldemTreeData> firstRaiseNode = recursiveDepthFirstSearch(gameTree, hasRaisesPredicate);

        ITreeNode<IHoldemTreeData> secondRaiseNode = recursiveDepthFirstSearch(firstRaiseNode, hasRaisesPredicate);

        assertEquals(secondRaiseNode, null);

    }

    @Test(dataProvider = "treeBuilderTests")
    public void ThreePlayerGameTreeWithDealer_raisesThreeTimes_capWorks(Street street, int dealerPos, ImmutableList<Card> communityCards, Action lastAction) throws Exception {

        ITreeNode<IHoldemTreeData> gameTree = build3PlayerTreeWithDealer(street, dealerPos, communityCards, lastAction, ChipStack.of(100));

        Predicate<IHoldemTreeData> hasRaisesPredicate = iHoldemTreeData -> iHoldemTreeData.state().lastAction().type() == Action.ActionType.RAISE_TO;
        // find node with first raise
        ITreeNode<IHoldemTreeData> firstRaiseNode = recursiveDepthFirstSearch(gameTree, hasRaisesPredicate);

        // check it has two children with raises
        ITreeNode<IHoldemTreeData> secondRaiseNode = recursiveDepthFirstSearch(firstRaiseNode, hasRaisesPredicate);
        ITreeNode<IHoldemTreeData> thirdRaiseNode = recursiveDepthFirstSearch(secondRaiseNode, hasRaisesPredicate);

        assertEquals(thirdRaiseNode.children().size(), 2);

    }


}
