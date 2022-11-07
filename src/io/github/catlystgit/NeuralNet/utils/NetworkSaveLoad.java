package io.github.catlystgit.NeuralNet.utils;

import io.github.catlystgit.NeuralNet.Network.NeuralNetwork;
import io.github.catlystgit.NeuralNet.Neuron.Neuron;
import io.github.catlystgit.NeuralNet.Neuron.NeuronInput;

import java.io.*;

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
            for(Neuron[] layer : network.layers)
                for(Neuron neuron : layer)
                    for(NeuronInput input : neuron.getInputs())
                        dataWriter.writeDouble(input.getWeight());
            dataWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static NeuralNetwork read(String path) {
        System.out.println("reading save net");

        try {
            DataInputStream inpStream = new DataInputStream(new FileInputStream(path));

            int inputs = inpStream.readInt();
            int layers = inpStream.readInt();

            int[] layerSizes = new int[layers];

            for(int i = 0; i != layers; i++)
                layerSizes[i] = inpStream.readInt();

            CatlystNeuralNetwork nn = new CatlystNeuralNetwork(inputs, layerSizes, 0, 0.0);

            for(Neuron[] layer : nn.layers)
                for(Neuron neuron : layer)
                    for(NeuronInput input : neuron.getInputs())
                        input.setWeight(inpStream.readDouble());
            inpStream.close();
            return nn;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}