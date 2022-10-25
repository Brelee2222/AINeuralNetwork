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

            double[][][] weights = new double[layers][][];
            for(int i = 0; i != layers; i++)
                weights[i] = new double[inpStream.readInt()][];
            for(int k = 0; k != layers-1; k++) {
                int prevLayer = weights[k+1].length+1;
                double[][] layer = weights[k];
                for(int i = 0; i != layer.length; i++) {
                    double[] neuronWeight = layer[i] = new double[prevLayer];
                    for(int j = 0; j != prevLayer; j++)
                        neuronWeight[j] = inpStream.readDouble();
                }
            }
            double[][] layer = weights[layers-1];
            for(int i = 0; i != layer.length; i++) {
                double[] neuronWeight = layer[i] = new double[inputs+1];
                for(int j = 0; j != neuronWeight.length; j++)
                    neuronWeight[j] = inpStream.readDouble();
            }
            return new CatlystNeuralNetwork(inputs, weights, 0, 0.0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
