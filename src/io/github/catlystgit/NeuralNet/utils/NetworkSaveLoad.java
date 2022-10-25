package io.github.catlystgit.NeuralNet.utils;

import io.github.catlystgit.NeuralNet.Catlyst.CatlystNeuralNetwork;
import io.github.catlystgit.NeuralNet.Network.NeuralNetwork;
import io.github.catlystgit.NeuralNet.Neuron.Neuron;
import io.github.catlystgit.NeuralNet.Neuron.NeuronInput;

import java.io.*;
import java.nio.file.Files;

public class NetworkSaveLoad {
    /**
     * Save format
     * 1. inputs (int)
     * 2. layers (int)
     * 3. layerSize (int) * layers
     * 4. Neuron inputs (double)...
     */

    public static void save(NeuralNetwork network, String path) {
        try {
            DataOutputStream dataWriter = new DataOutputStream(new FileOutputStream(path));
            dataWriter.writeInt(network.inputs);
            dataWriter.writeInt(network.layers.length);
            for(Neuron[] layer : network.layers)
                dataWriter.writeInt(layer.length);
            for(Neuron[] layer : network.layers) for(Neuron neuron : layer) for(NeuronInput input : neuron.getInputs())
                dataWriter.writeDouble(input.getWeight());
            dataWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static NeuralNetwork read(String path) {

        try {
            DataInputStream inpStream = new DataInputStream(new FileInputStream(path));

            int inputs = inpStream.readInt();
            int layers = inpStream.readInt();

            int[] layerSizes = new int[layers];
            int totalWeights = 0;

            int prevSize = 0;
            for(int i = 0; i != layers; i++)
                totalWeights += prevSize * ((layerSizes[i] = prevSize = inpStream.readInt())+1);
            totalWeights += layerSizes[layers-1] * (inputs+1);

            double[] weights = new double[totalWeights];

            for(int i = 0; i != totalWeights; i++)
                weights[i] = inpStream.readDouble();
            return new CatlystNeuralNetwork(inputs, layerSizes, weights, 0, 0.0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
