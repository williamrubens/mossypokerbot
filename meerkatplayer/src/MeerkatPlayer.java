

import com.biotools.meerkat.*;
import com.biotools.meerkat.Action;
import com.biotools.meerkat.util.Preferences;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.mossy.holdem.HoleCards;
import com.mossy.holdem.IncomeRate;
import com.mossy.holdem.PreFlopHandType;
import com.mossy.holdem.interfaces.IPreFlopIncomeRateVendor;
import com.mossy.holdem.modules.GamePlayModule;
import org.apache.log4j.Logger;


/**
 * User: William
 * Date: 12/09/2013
 * Time: 19:47
 */
public class MeerkatPlayer implements Player
{
    final static private Logger log = Logger.getLogger(MeerkatPlayer.class);

    Injector injector;
    IPreFlopIncomeRateVendor preFlopIncomeRateVendor;
    GameInfo gameInfo;
    MeerkatToMossyAdaptor adaptor;
    HoleCards holeCards;
    int ourSeat;


    @Override
    public void init(Preferences preferences)
    {
        injector = Guice.createInjector(new GamePlayModule());
        preFlopIncomeRateVendor = injector.getInstance(IPreFlopIncomeRateVendor.class);

        adaptor = new MeerkatToMossyAdaptor();
    }

    @Override
    public void holeCards(Card card1, Card card2, int seat)
    {
        try
        {
            ourSeat = seat;
            holeCards = HoleCards.from(adaptor.adaptCard(card1), adaptor.adaptCard(card2));
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage());
        }
     }

    @Override
    public Action getAction()
    {
        double callAmount = gameInfo.getAmountToCall(ourSeat);
        if(gameInfo.isPreFlop())
        {
            IncomeRate ir = preFlopIncomeRateVendor.getIncomeRate(gameInfo.getNumPlayers(), PreFlopHandType.fromHoleCards(holeCards));
            if(ir.incomeRate() > 0)
            {
                Action.callAction(callAmount);
            }
            else
            {
                Action.muckAction();
            }
        }
        else
        {

        }
        return null;
    }

    @Override
    public void actionEvent(int i, Action action)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void stageEvent(int i)
    {
        //To change body of implemented methods use File | Settings | File Templates.
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
    }

    @Override
    public void gameStateChanged()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
