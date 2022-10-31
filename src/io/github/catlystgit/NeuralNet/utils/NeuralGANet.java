package io.github.catlystgit.NeuralNet.utils;

import io.github.catlystgit.NeuralNet.Network.NeuralNetwork;
// WIP
public class NeuralGANet extends CatlystNeuralNetwork {
    public NeuralNetwork generator;

    public NeuralGANet(int inputs, int[] layerSizes, double randWeightRange, double learningRate, NeuralNetwork generator) {
        super(inputs, layerSizes, randWeightRange, learningRate);
        this.generator = generator;
    }
}
