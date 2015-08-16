package com.mossy.holdem.implementations.gametree;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.*;
import com.mossy.holdem.gametree.ExpectedValueCalculator;
import com.mossy.holdem.gametree.HoldemTreeBuilder;
import com.mossy.holdem.gametree.IHoldemTreeData;
import com.mossy.holdem.gametree.ITreeNode;
import com.mossy.holdem.implementations.*;
import com.mossy.holdem.implementations.fastevaluator.FastHandEvaluator;
import com.mossy.holdem.implementations.fastevaluator.HandBitsAdaptor;
import com.mossy.holdem.implementations.player.*;
import com.mossy.holdem.implementations.state.*;
import com.mossy.holdem.interfaces.IDealerActionBuilder;
import com.mossy.holdem.interfaces.player.IMutablePlayerState;
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

    public  ITreeNode<IHoldemTreeData> buildTree (Street street, int dealerPos, int numPlayers, ChipStack playerBank, ImmutableList<Card> communityCards, Action lastAction) throws Exception{


        IDealerActionBuilder dealer = new SingelDealerActionBuilder();
        FixedLimitActionBuilder actionBuilder = new FixedLimitActionBuilder(dealer);
        IPlayerInfoFactory playerFactory = new PlayerInfoFactory();
        FLStateFactory stateFactory = new FLStateFactory(playerFactory, ChipStack.TWO_CHIPS, ChipStack.of(4)) ;

        ImmutableList<IPlayerState> players = buildPlayers(numPlayers, playerBank);

        IMutablePlayerState myState = new MutablePlayerState();
        myState.copy(players.get(0));
        myState.setHoleCards(HoleCards.from(Card.ACE_CLUBS, Card.ACE_DIAMONDS));

        IActionProbabilityCalculator probCalc = new MyWinActionProbabilityCalculator( myState,
                new HandStrengthCalculator(new FastHandEvaluator(new HandScoreFactory(), new HandBitsAdaptor(new HandFactory())), new HandFactory(), new StandardDeckFactory()),
                new StandardDeckFactory(),
                new HandFactory(), new NeuralNetworkPlayerModel(new PlayerStatisticsHolder()));

        HoldemTreeBuilder treeBuilder = new HoldemTreeBuilder(stateFactory, actionBuilder, probCalc);



        IGameState initialState = stateFactory.buildState(street, players, dealerPos, communityCards, lastAction, 3);

        return treeBuilder.buildTree(initialState);
    }

    public ImmutableList<IPlayerState> buildPlayers(int numPlayers, ChipStack playerBank) {

        ImmutableList.Builder<IPlayerState> builder = ImmutableList.builder();
        for(int p = 0; p < numPlayers; ++p) {
            builder.add(new PlayerState(p, playerBank, ChipStack.NO_CHIPS));

        }
        return builder.build();
    }


    @DataProvider(name = "fullTreeBuilderTests")
    public Object[][] createFullTreeData() {
        return new Object[][] {
//                {Street.PRE_FLOP, 0, ImmutableList.of(), Action.Factory.dealHoleCards()},
                {Street.FLOP, 0, ImmutableList.of(Card.ACE_SPADES, Card.KING_CLUBS, Card.ACE_HEARTS), Action.Factory.dealFlopAction()},
                {Street.TURN, 0, ImmutableList.of(Card.ACE_SPADES, Card.KING_CLUBS, Card.ACE_HEARTS, Card.KING_DIAMONDS), Action.Factory.dealTurnAction(Card.KING_DIAMONDS)},
                {Street.RIVER, 0, ImmutableList.of(Card.ACE_SPADES, Card.KING_CLUBS, Card.ACE_HEARTS, Card.KING_DIAMONDS, Card.KING_HEARTS), Action.Factory.dealRiverAction(Card.KING_HEARTS)},
//                {Street.SHOWDOWN, 0, ImmutableList.of(), Action.Factory.dealHoleCards()},

        };
    }

    @Test(dataProvider = "fullTreeBuilderTests")
    public void buildFullTree(Street street, int dealerPos, ImmutableList<Card> communityCards, Action lastAction) throws Exception
    {
        ITreeNode<IHoldemTreeData> fullTree = buildTree(street, dealerPos, 2, ChipStack.of(100), communityCards, lastAction);

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


    @Test(dataProvider = "fullTreeBuilderTests")
    public void TwoPlayerGameTreeWithDealer_calculatesEv_(Street street, int dealerPos, ImmutableList<Card> communityCards, Action lastAction) throws Exception {

        ITreeNode<IHoldemTreeData> gameTree = buildTree(street, dealerPos, 2, ChipStack.of(100), communityCards, lastAction);


        ExpectedValueCalculator evCalculator = new ExpectedValueCalculator();

        ChipStack ev = evCalculator.calculateExpectedValue(gameTree.data().state().playerStates().get(0), gameTree);

        ev.compareTo(ev);


    }

    @Test(dataProvider = "fullTreeBuilderTests")
    public void TwoPlayerGameTreeWithDealer_raisesThreeTimes_capWorks(Street street, int dealerPos, ImmutableList<Card> communityCards, Action lastAction) throws Exception {

        ITreeNode<IHoldemTreeData> gameTree = buildTree(street, dealerPos, 2, ChipStack.of(100), communityCards, lastAction);

        Predicate<IHoldemTreeData> hasRaisesPredicate = iHoldemTreeData -> iHoldemTreeData.state().lastAction().type() == Action.ActionType.RAISE_TO;
        // find node with first raise
        ITreeNode<IHoldemTreeData> firstRaiseNode = recursiveDepthFirstSearch(gameTree, hasRaisesPredicate);

        // check it has two children with raises
        ITreeNode<IHoldemTreeData> secondRaiseNode = recursiveDepthFirstSearch(firstRaiseNode, hasRaisesPredicate);
        ITreeNode<IHoldemTreeData> thirdRaiseNode = recursiveDepthFirstSearch(secondRaiseNode, hasRaisesPredicate);

        assertEquals(thirdRaiseNode.children().size(), 2);

    }

    @Test(dataProvider = "fullTreeBuilderTests")
    public void gameTreeWithDealerAndSmallBank_raisesAllIn_cutsTreeShort(Street street, int dealerPos, ImmutableList<Card> communityCards, Action lastAction) throws Exception {

        // give players a bank of 6, therefore with a fixed limit of 2 and 4, the first raise will be to 6 and will therefore be an all in
        // therefore action should terminate there

        ITreeNode<IHoldemTreeData> gameTree = buildTree(street, dealerPos, 2, ChipStack.of(6), communityCards, lastAction);

        Predicate<IHoldemTreeData> hasRaisesPredicate = iHoldemTreeData -> iHoldemTreeData.state().lastAction().type() == Action.ActionType.RAISE_TO;
        // find node with first raise
        ITreeNode<IHoldemTreeData> firstRaiseNode = recursiveDepthFirstSearch(gameTree, hasRaisesPredicate);

        ITreeNode<IHoldemTreeData> secondRaiseNode = recursiveDepthFirstSearch(firstRaiseNode, hasRaisesPredicate);

        assertEquals(secondRaiseNode, null);

    }

    @Test(dataProvider = "fullTreeBuilderTests")
    public void ThreePlayerGameTreeWithDealer_raisesThreeTimes_capWorks(Street street, int dealerPos, ImmutableList<Card> communityCards, Action lastAction) throws Exception {

        ITreeNode<IHoldemTreeData> gameTree = buildTree(street, dealerPos, 3, ChipStack.of(100), communityCards, lastAction);

        Predicate<IHoldemTreeData> hasRaisesPredicate = iHoldemTreeData -> iHoldemTreeData.state().lastAction().type() == Action.ActionType.RAISE_TO;
        // find node with first raise
        ITreeNode<IHoldemTreeData> firstRaiseNode = recursiveDepthFirstSearch(gameTree, hasRaisesPredicate);

        // check it has two children with raises
        ITreeNode<IHoldemTreeData> secondRaiseNode = recursiveDepthFirstSearch(firstRaiseNode, hasRaisesPredicate);
        ITreeNode<IHoldemTreeData> thirdRaiseNode = recursiveDepthFirstSearch(secondRaiseNode, hasRaisesPredicate);

        assertEquals(thirdRaiseNode.children().size(), 2);

    }


}
