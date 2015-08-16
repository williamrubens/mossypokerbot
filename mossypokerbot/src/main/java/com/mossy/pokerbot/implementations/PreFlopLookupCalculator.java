/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.pokerbot.implementations;

/**
 *
 * @author d80050
 */                                                /*
public class PreFlopLookupCalculator implements IPreFlopLookupCalculator
{ 
    final int BOARD_CARDS = 5;
    
    static Logger log = Logger.getLogger(PreFlopLookupCalculator.class);

    @Override
    public void generatePreFlopLookup(SummaryStatistics stats, Writer writer, double tolerence, IDeck deck, IPreFlopHandTypeAdaptor adaptor, int numPlayers, IHandFactory handFactory, IHandEvaluator handEvaluator)
    {
        ImmutableSortedSet<PreFlopHandType> preflopHands = adaptor.adaptDeck(deck);

        try {
            writer.write("Hand, EV, sd\n");
        } catch (IOException ex) {
            log.error("Can't write to writer");
        }

        for(PreFlopHandType hand : preflopHands)
        {
            HoleCards holeCards = adaptor.adaptPreFlopHandType(hand);
            Card holeCard1 = holeCards.card1();
            Card holeCard2 = holeCards.card2();
            log.debug(String.format("generating pre flop information for %s %s", holeCard1, holeCard2));
            try
            {
                generateOnePreFlopLookup(holeCard1, holeCard2, deck, stats, tolerence, numPlayers, handFactory, handEvaluator);
                String csvString = String.format("%s %s, %g, %g\n", holeCard1, holeCard2, stats.getMean(), stats.getStandardDeviation());
                log.info(String.format("winState for %s %s = %s, csv string: %s", holeCard1, holeCard2, stats.getMean(), csvString));
                writer.write(csvString);
                writer.flush();
                stats.clear();
            }
            catch(Exception ex)
            {
                log.info(String.format("preflopLookup for %s %s failed: %s", holeCard1, holeCard2, ex.getMessage()));
            }
        }
        
   
    }
    
    public void generateOnePreFlopLookup(Card holeCard1, Card holeCard2, IDeck deck, SummaryStatistics stats, double tolerence, int numPlayers, IHandFactory handFactory, IHandEvaluator handEvaluator) throws Exception
    { 
        
       
        double error = 1;
       
        int numIterations = 0;
        int numWins = 0;
        int numDraws = 0;
        int numLose = 0;
        
        Timer evaluateTimer = new Timer("Evaluate");
        Timer dealingTimer = new Timer("Dealing");
        Timer handFactoryTimer = new Timer("Hand");
        Timer statsTimer = new Timer("Stats");
        long generationStart = System.currentTimeMillis();
        
        
        while((Math.abs(error) > tolerence && numIterations < 100000) || stats.getN() < 100)
        {                       
            deck.shuffle();
           
            ArrayList<ArrayList<Card>> playerHoleCards = new ArrayList<ArrayList<Card>>();
            
            dealingTimer.startTimer();
            // player 0 will be the one we check
            deck.dealCard(holeCard1);
            deck.dealCard(holeCard2);
            
        
            for(int p = 0; p < numPlayers - 1; ++p)
            {
                ArrayList<Card> holeCards = new ArrayList<Card>();
                holeCards.add(deck.dealTopCard());
                holeCards.add(deck.dealTopCard());

                playerHoleCards.add(holeCards);

            }

            ArrayList<Card> boardCards = new ArrayList<Card>();
            for(int boardCardIdx = 0; boardCardIdx < BOARD_CARDS; ++boardCardIdx)
            {
                boardCards.add(deck.dealTopCard());
            }

            dealingTimer.stopTimer();
             // find the winner
            handFactoryTimer.startTimer();
            IHand myHand = handFactory.getMap(new HoleCards(holeCard1, holeCard2), boardCards);
            handFactoryTimer.stopTimer();
            
            evaluateTimer.startTimer();
            int myScore = handEvaluator.evaluateHand(myHand);
            evaluateTimer.stopTimer();
            boolean opponentWin = false;
            int numDrawingPlayers = 0;
            
            for(ArrayList<Card> playerCards : playerHoleCards)
            {
                 handFactoryTimer.startTimer();
                IHand opponentHand = handFactory.getMap(playerCards.get(0), playerCards.get(1), boardCards);
                handFactoryTimer.stopTimer();
                
                evaluateTimer.startTimer();
                int opponentScore = handEvaluator.evaluateHand(opponentHand);
                evaluateTimer.stopTimer();
                if(opponentScore > myScore)
                {
                    opponentWin = true;
                    break;
                }
                else if(opponentScore == myScore)
                {
                    numDrawingPlayers++;
                }
                        
            } 
            
            double myWinnings = 0;
            if(opponentWin)
            {
                myWinnings = -1;
                numLose++;
            }
            else if(numDrawingPlayers > 0)
            {
                myWinnings = (double) (numPlayers - 1) / (double) numDrawingPlayers;
                numDraws++;
            }
            else
            {
                 myWinnings = numPlayers - 1;
                 numWins++;
            }
            
         
            
            statsTimer.startTimer();
            
            stats.addValue(myWinnings);
            numIterations++;
            error = stats.getStandardDeviation() / stats.getMean();
            statsTimer.stopTimer();

            if(stats.getMean() == 0)
            {
                continue;
            }

            if(numIterations % 10000 == 0)
            {
                long totalTicks = System.currentTimeMillis() - generationStart;
                double percentHand = (double)handFactoryTimer.totalTime() / (double)totalTicks * 100.0d;
                double percentEval = (double)evaluateTimer.totalTime / (double)totalTicks* 100.0d;
                double percentDeal = (double)dealingTimer.totalTime / (double)totalTicks* 100.0d;                
                double percentStats = (double)statsTimer.totalTime / (double)totalTicks* 100.0d;                ;
                log.debug(String.format("sd %.2g Mean %.2g W: %.2g %% D: %.2g %% L: %.2g %% Iterations: %s of which Hand %.2g %% Evaluation %.2g %% Stats  %.2g %%, Dealing %.2g %%, Avg eval time: %s",stats.getStandardDeviation(),  stats.getMean(), (double)numWins/(double)numIterations * 100.0d, (double)numDraws/(double)numIterations* 100.0d, (double)numLose/(double)numIterations* 100.0d,  numIterations, percentHand, percentEval,  percentStats ,percentDeal, evaluateTimer.avgTime() ));
            }
        }
        log.debug("num operations = " +  numIterations);
        
    }

   
}
                                                             */