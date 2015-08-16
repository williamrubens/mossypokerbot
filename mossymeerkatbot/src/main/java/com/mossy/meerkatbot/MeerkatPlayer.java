
package com.mossy.meerkatbot;

import com.biotools.meerkat.*;
import com.biotools.meerkat.Action;
import com.biotools.meerkat.Card;
import com.biotools.meerkat.util.Preferences;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mossy.pokerbot.*;
import com.mossy.pokerbot.interfaces.IHoldemPlayer;
import com.mossy.pokerbot.interfaces.player.IPlayerInfoFactory;
import com.mossy.pokerbot.interfaces.player.IPlayerState;
import com.mossy.pokerbot.interfaces.state.IFixedLimitState;
import com.mossy.pokerbot.modules.FixedLimitPlayModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.SortedMap;
import java.util.TreeMap;


/**
 * User: William
 * Date: 12/09/2013
 * Time: 19:47
 */
public class MeerkatPlayer implements Player
{
    final static private Logger log = LogManager.getLogger(MeerkatPlayer.class);

    Injector injector = null;
    IHoldemPlayer holdemPlayer;
    GameInfo gameInfo;
    MeerkatToMossyAdaptor adaptor;
    int ourSeat;

    private void initInjector(ChipStack lowerLimit, ChipStack higherLimit) {
        if (injector == null ) {
            injector = Guice.createInjector(new FixedLimitPlayModule(lowerLimit, higherLimit));
        }
        holdemPlayer = injector.getInstance(IHoldemPlayer.class);
        IPlayerInfoFactory playerFactory = injector.getInstance(IPlayerInfoFactory.class);
        SortedMap<Integer, IPlayerState> seatToPlayer = new TreeMap<>();

        for (int playerIdx = 0; playerIdx < gameInfo.getNumPlayers(); ++playerIdx) {
            PlayerInfo meerkatPlayer = gameInfo.getPlayer(playerIdx);
            if (meerkatPlayer.inGame()) {
                IPlayerState mossyPlayer = playerFactory.newPlayer(playerIdx, ChipStack.of(meerkatPlayer.getBankRoll()));
                seatToPlayer.put(Integer.valueOf(meerkatPlayer.getSeat()), mossyPlayer);
            }

        }
        holdemPlayer.startGame(seatToPlayer.values(), gameInfo.getButtonSeat());

    }


    @Override
    public void init(Preferences preferences)
    {
        adaptor = new MeerkatToMossyAdaptor();
    }

    @Override
    public void holeCards(Card card1, Card card2, int seat)
    {
        try
        {
            ourSeat = seat;
            com.mossy.pokerbot.Card mossyCard1 = adaptor.adaptCard(card1);
            com.mossy.pokerbot.Card mossyCard2 = adaptor.adaptCard(card2);
            holdemPlayer.setHoleCards(mossyCard1, mossyCard2, ourSeat);

            log.info(String.format("MossyBot: %s %s seat: %d", card1, card2, seat));
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage());
        }
     }

    @Override
    public Action getAction()
    {
        return adaptor.adaptAction(holdemPlayer.getNextAction(), (IFixedLimitState)holdemPlayer.currentState());

    }

    @Override
    public void actionEvent(int i, Action action)
    {
        if(action.getType() == 100) {
            // 100 = PublicGameInfo.SPECIAL_ACTION_RETURNUNCALLEDBET
            return;
        }
        if(action.getType() == Action.MUCK) {
            // don't handle mucks very wayy because opentestbed doesn't tell us that we are in
            // showdown yet...
            return;
        }

        com.mossy.pokerbot.Action mossyAction = adaptor.adaptAction(action, gameInfo);

        String playerName = gameInfo.getPlayer(i).getName();
        log.info(String.format("%s %s", mossyAction, playerName));

        holdemPlayer.setNextAction(mossyAction);
    }

    @Override
    public void stageEvent(int i)
    {
        if(i == 0) {
            return;
        }

        holdemPlayer.setNextAction(adaptor.adaptStageChangeEvent(i, gameInfo));
    }

    @Override
    public void showdownEvent(int i, Card card, Card card2)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void gameStartEvent(GameInfo gameInfo)
    {
        this.gameInfo = gameInfo;
        //holdemPlayer.startGame(gameInfo.getNumPlayers());
        ChipStack bigBlind = ChipStack.of(gameInfo.getBigBlindSize());
        initInjector(bigBlind, bigBlind.multiply(2.0));
    }

    @Override
    public void dealHoleCardsEvent()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void gameOverEvent()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void winEvent(int i, double v, String s)
    {
        //To change body of implemented methods use File | Settings | File Templates.
        String playerName = gameInfo.getPlayer(i).getName();
        log.info(String.format("%s wins %f %s", playerName, v, s));
        log.info("######################################");
    }

    @Override
    public void gameStateChanged()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
