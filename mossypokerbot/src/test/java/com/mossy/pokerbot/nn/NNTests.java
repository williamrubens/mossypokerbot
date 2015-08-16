package com.mossy.pokerbot.nn;

import com.googlecode.fannj.Fann;
import com.googlecode.fannj.Trainer;
import com.googlecode.fannj.Layer;
import org.testng.annotations.Test;

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
//        Layer middleLayer1 = Layer.create(8);
//        Layer middleLayer2 = Layer.create(8);
        Layer outLayer = Layer.create(3);

        Fann fann = new Fann(Arrays.asList(inLayer,middleLayer,  outLayer));

        Trainer trainer = new Trainer(fann);

        trainer.train("/Users/willrubens/dev/poker-hhdb/mydb/fann_data1.txt", 100, 10, 0.01f);

        fann.save("/Users/willrubens/dev/mossyholdem/mossyholdemlib/src/test/resources/nn.txt");



    }
}

