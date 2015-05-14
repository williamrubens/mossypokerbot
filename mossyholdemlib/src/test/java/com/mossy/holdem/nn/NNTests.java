package com.mossy.holdem.nn;

import com.googlecode.fannj.Fann;
import com.googlecode.fannj.Trainer;
import com.googlecode.fannj.Layer;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by willrubens on 07/05/15.
 */
public class NNTests {

    @Test
    public void play() throws Exception
    {

        Layer inLayer = Layer.create(4);
        Layer middleLayer = Layer.create(8);
        Layer outLayer = Layer.create(4);

        Fann fann = new Fann(Arrays.asList(inLayer,middleLayer,  outLayer));

        Trainer trainer = new Trainer(fann);

        trainer.train("/Users/willrubens/dev/poker-hhdb/mydb/fann_data0.txt", 1000, 1, 0.01f);


        fann.save("/Users/willrubens/dev/mossyholdem/mossyholdemlib/src/test/resources/nn.txt");



    }
}

