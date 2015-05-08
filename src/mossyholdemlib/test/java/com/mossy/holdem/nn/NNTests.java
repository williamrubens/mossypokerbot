package com.mossy.holdem.nn;

import org.deeplearning4j.datasets.iterator.DataSetIterator;
import org.deeplearning4j.datasets.iterator.impl.IrisDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.Layer;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.distribution.UniformDistribution;
import org.deeplearning4j.nn.conf.override.ClassifierOverride;
import org.deeplearning4j.nn.layers.factory.LayerFactories;
import org.deeplearning4j.nn.layers.feedforward.rbm.RBM;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.api.IterationListener;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by willrubens on 07/05/15.
 */
public class NNTests {

    @Test
    public void play() throws Exception
    {
        Nd4j.MAX_SLICES_TO_PRINT = -1;
        Nd4j.MAX_ELEMENTS_PER_SLICE = -1;
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .iterations(100)
                .layer(new org.deeplearning4j.nn.conf.layers.RBM())
                .weightInit(WeightInit.DISTRIBUTION).dist(new UniformDistribution(0,1))
                .activationFunction("tanh").momentum(0.9)
                .optimizationAlgo(OptimizationAlgorithm.LBFGS)
                .constrainGradientToUnitNorm(true).k(1).regularization(true).l2(2e-4)
                .visibleUnit(RBM.VisibleUnit.GAUSSIAN).hiddenUnit(RBM.HiddenUnit.RECTIFIED)
                .lossFunction(LossFunctions.LossFunction.RMSE_XENT)
                .nIn(4).nOut(3).list(2)
                .hiddenLayerSizes(new int[]{3})
                .override(1, new ClassifierOverride(1)).build();

        NeuralNetConfiguration conf2 = new NeuralNetConfiguration.Builder()
                .layer(new org.deeplearning4j.nn.conf.layers.RBM())
                .nIn(784).nOut(600).applySparsity(true).sparsity(0.1)
                .build();

        Layer l = LayerFactories.getFactory(conf2).create(conf2,
                Arrays.<IterationListener>asList(new ScoreIterationListener(2)));



        MultiLayerNetwork d = new MultiLayerNetwork(conf);


        DataSetIterator iter = new IrisDataSetIterator(150, 150);

        DataSet next = iter.next();

        Nd4j.writeTxt(next.getFeatureMatrix(),"iris.txt","\t");

        next.normalizeZeroMeanZeroUnitVariance();

        SplitTestAndTrain testAndTrain = next.splitTestAndTrain(110);
        DataSet train = testAndTrain.getTrain();

        d.fit(train);




        DataSet test = testAndTrain.getTest();


        Evaluation eval = new Evaluation();
        INDArray output = d.output(test.getFeatureMatrix());
        eval.eval(test.getLabels(),output);
        log.info("Score " + eval.stats());


    }


    }
}
