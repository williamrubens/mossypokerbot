package com.mossy.holdem.implementations.player;

import com.googlecode.fannj.Fann;
import com.mossy.holdem.ProbabilityTriple;
import com.mossy.holdem.interfaces.player.IPlayerModel;
import com.mossy.holdem.interfaces.player.IPlayerStatistics;
import com.mossy.holdem.interfaces.state.IGameState;

/**
 * Created by willrubens on 13/06/15.
 */
public class NeuralNetworkPlayerModel implements IPlayerModel
{
    Fann myNeuralNetwork;

    @Override
    public ProbabilityTriple calculateActionProbabilties(IPlayerStatistics playerStats, IGameState state) {

        float [] inputs = new float [] { playerStats.vpip(), playerStats.pfr()};
        float [] outputs = myNeuralNetwork.run(inputs);

        return new ProbabilityTriple(outputs[0], outputs[1], outputs[2]);
    }
}
