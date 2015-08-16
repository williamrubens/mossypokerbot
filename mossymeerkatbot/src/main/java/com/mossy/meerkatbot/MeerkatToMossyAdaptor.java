package com.mossy.meerkatbot;

import com.biotools.meerkat.Card;
import com.biotools.meerkat.Holdem;
import com.mossy.pokerbot.ChipStack;
import com.mossy.pokerbot.Rank;
import com.mossy.pokerbot.Suit;

import java.util.ArrayList;

/**
 * User: William
 * Date: 14/09/2013
 * Time: 11:19
 */
public class MeerkatToMossyAdaptor {
    com.mossy.pokerbot.Card adaptCard(com.biotools.meerkat.Card meerkatCard) {
        com.mossy.pokerbot.Suit mossySuit;
        com.mossy.pokerbot.Rank mossyRank;
        switch (meerkatCard.getRank()) {
            case com.biotools.meerkat.Card.ACE:
                mossyRank = Rank.ACE;
                break;
            case com.biotools.meerkat.Card.KING:
                mossyRank = Rank.KING;
                break;
            case com.biotools.meerkat.Card.QUEEN:
                mossyRank = Rank.QUEEN;
                break;
            case com.biotools.meerkat.Card.JACK:
                mossyRank = Rank.JACK;
                break;
            case com.biotools.meerkat.Card.TEN:
                mossyRank = Rank.TEN;
                break;
            case com.biotools.meerkat.Card.NINE:
                mossyRank = Rank.NINE;
                break;
            case com.biotools.meerkat.Card.EIGHT:
                mossyRank = Rank.EIGHT;
                break;
            case com.biotools.meerkat.Card.SEVEN:
                mossyRank = Rank.SEVEN;
                break;
            case com.biotools.meerkat.Card.SIX:
                mossyRank = Rank.SIX;
                break;
            case com.biotools.meerkat.Card.FIVE:
                mossyRank = Rank.FIVE;
                break;
            case com.biotools.meerkat.Card.FOUR:
                mossyRank = Rank.FOUR;
                break;
            case com.biotools.meerkat.Card.THREE:
                mossyRank = Rank.THREE;
                break;
            case com.biotools.meerkat.Card.TWO:
                mossyRank = Rank.TWO;
                break;
            default:
                throw new RuntimeException(String.format("Failed to find rank of meerkat card %s", meerkatCard));
        }
        switch (meerkatCard.getSuit()) {
            case com.biotools.meerkat.Card.SPADES:
                mossySuit = Suit.SPADES;
                break;
            case com.biotools.meerkat.Card.CLUBS:
                mossySuit = Suit.CLUBS;
                break;
            case com.biotools.meerkat.Card.DIAMONDS:
                mossySuit = Suit.DIAMONDS;
                break;
            case com.biotools.meerkat.Card.HEARTS:
                mossySuit = Suit.HEARTS;
                break;
            default:
                throw new RuntimeException(String.format("Failed to find suit of meerkat card %s", meerkatCard));
        }

        return com.mossy.pokerbot.Card.from(mossyRank, mossySuit);

    }

    com.biotools.meerkat.Action adaptAction(com.mossy.pokerbot.Action mossyAction, com.mossy.pokerbot.interfaces.state.IFixedLimitState gameState) {
        ChipStack amountToCall = gameState.getAmountToCall();

        switch (mossyAction.type()) {
            case CHECK:
                return com.biotools.meerkat.Action.checkAction();
            case BET:
                return com.biotools.meerkat.Action.betAction(gameState.getCurrentBetLimit().toDouble());
            case RAISE_TO:
                ChipStack amountToRaise = mossyAction.amount().subtract(amountToCall);

                if (amountToRaise.compareTo(ChipStack.of(0)) < 0) {
                    throw new RuntimeException("Cannot raise less than call amount");
                }

                return com.biotools.meerkat.Action.raiseAction(amountToCall.toDouble(), amountToRaise.toDouble());

            case FOLD:
                return com.biotools.meerkat.Action.foldAction(amountToCall.toDouble());
            case CALL:
                return com.biotools.meerkat.Action.callAction(amountToCall.toDouble());
            case SMALL_BLIND:
                return com.biotools.meerkat.Action.smallBlindAction(gameState.smallBlind().toDouble());
            case BIG_BLIND:
                return com.biotools.meerkat.Action.bigBlindAction(gameState.bigBlind().toDouble());
            // TODO fix ante for
            case ANTE:
                throw new RuntimeException("Ante not implemented!");
            case ALL_IN:
                return com.biotools.meerkat.Action.allInPassAction();
            case SIT_OUT:
                return com.biotools.meerkat.Action.sitout();
            default:
                throw new RuntimeException(String.format("Cannot convert %s to meerkat Action", mossyAction.type()));
        }

    }

    com.mossy.pokerbot.Action adaptAction(com.biotools.meerkat.Action meerkatAction, com.biotools.meerkat.GameInfo gi) {

        switch (meerkatAction.getType()) {
            case com.biotools.meerkat.Action.CHECK:
                return com.mossy.pokerbot.Action.Factory.checkAction();
            case com.biotools.meerkat.Action.BET:
                return com.mossy.pokerbot.Action.Factory.betAction(ChipStack.of(meerkatAction.getAmount()));
            case com.biotools.meerkat.Action.RAISE:

                return com.mossy.pokerbot.Action.Factory.raiseToAction(ChipStack.of(meerkatAction.getToCall() + meerkatAction.getAmount()));

            case com.biotools.meerkat.Action.FOLD:
                return com.mossy.pokerbot.Action.Factory.foldAction();
            // for now treat muck as fold...
            case com.biotools.meerkat.Action.MUCK:
                return com.mossy.pokerbot.Action.Factory.foldAction();
            case com.biotools.meerkat.Action.CALL:
                return com.mossy.pokerbot.Action.Factory.callAction();
            case com.biotools.meerkat.Action.SMALL_BLIND:
                return com.mossy.pokerbot.Action.Factory.smallBlindAction();
            case com.biotools.meerkat.Action.BIG_BLIND:
                return com.mossy.pokerbot.Action.Factory.bigBlindAction();
            // TODO fix ante for
            case com.biotools.meerkat.Action.POST_ANTE:
                throw new RuntimeException("Ante not implemented!");
            case com.biotools.meerkat.Action.ALLIN_PASS:
                return com.mossy.pokerbot.Action.Factory.allInAction();
            case com.biotools.meerkat.Action.SIT_OUT:
                return com.mossy.pokerbot.Action.Factory.sitOutAction();
            default:
                throw new RuntimeException(String.format("Cannot convert %s to mossy Action", meerkatAction.toString()));
        }

    }

    com.mossy.pokerbot.Action adaptStageChangeEvent(int newStage, com.biotools.meerkat.GameInfo gi) {

        int [] boardCards = gi.getBoard().getCardArray();

        // boardCards[0] is the number of cards available
        int numBoardCards = boardCards[0];

        ArrayList<com.mossy.pokerbot.Card> myBoardCards = new ArrayList<>();

        for(int boardCardIdx = 1; boardCardIdx <= numBoardCards; ++boardCardIdx){
            com.mossy.pokerbot.Card mossyCard = adaptCard(new Card(boardCards[boardCardIdx]));
            myBoardCards.add(mossyCard);

        }

        switch (newStage) {
            case Holdem.FLOP:
                return com.mossy.pokerbot.Action.Factory.dealFlopAction(myBoardCards.get(0), myBoardCards.get(1), myBoardCards.get(2));
            case Holdem.TURN:
                return com.mossy.pokerbot.Action.Factory.dealTurnAction(myBoardCards.get(3));
            case Holdem.RIVER:
                return com.mossy.pokerbot.Action.Factory.dealRiverAction(myBoardCards.get(4));

            default:
                throw new RuntimeException(String.format("Cannot convert dealer action to mossy Action"));
        }

    }



}
