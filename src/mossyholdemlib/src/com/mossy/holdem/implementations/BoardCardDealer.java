package com.mossy.holdem.implementations;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.annotations.Annotations;
import com.mossy.holdem.interfaces.IBoardCardDealer;
import com.mossy.holdem.Card;
import com.mossy.holdem.interfaces.IDeck;

import java.util.ArrayList;
import com.google.inject.Inject;
import com.mossy.holdem.annotations.Annotations.NumBoardCards ;


/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 16/05/13
 * Time: 12:25
 * To change this template use File | Settings | File Templates.
 */
public class BoardCardDealer implements IBoardCardDealer
{
    final private int BOARD_CARDS;

    @Inject
    private BoardCardDealer(@NumBoardCards int boardCards)
    {
        BOARD_CARDS = boardCards;
    }


    @Override
    public ImmutableList<Card> deal(IDeck deck)
    {
        ImmutableList.Builder<Card> builder = ImmutableList.builder();
        ArrayList<Card> boardCards;
        boardCards = new ArrayList<Card>();
        for(int boardCardIdx = 0; boardCardIdx < BOARD_CARDS; ++boardCardIdx)
        {
            builder.add(deck.dealTopCard());
        }
        return builder.build();
    }
}
