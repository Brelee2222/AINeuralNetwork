package io.github.brelee2222.NeuralNetwork;

import io.github.brelee2222.NeuralNetwork.Network.NeuralNetwork;

public abstract class Learning {
    public final NeuralNetwork network;
    public final int inputs;
    public final int answers;
    public Case[] testCases;
    public Case[] trainingCases;
    public double accThresh = 1;
    public double maxEpoch = 1000;

    public Learning(NeuralNetwork network, int inputs, int answers) { // training data path and testing data path
        this.inputs = inputs;
        this.answers = answers;
        this.network = network;
    }

    public double train() {
        double accuracy = 0.0;
        int epoch = 0;
        while(accuracy < accThresh && maxEpoch != epoch) {
            accuracy = 0.0;
            for(Case currentCase : trainingCases) {
                accuracy += getAccuracy(network.get(currentCase.inputs), currentCase.answers);
                network.learn(currentCase.answers);
            }
            System.out.println("accuracy: " + (accuracy /= trainingCases.length) + "   epoch: " + ++epoch);
        }
        return accuracy;
    }
    public void test() {
        double accuracy = 0.0;
        for(Case currentCase : testCases)
            accuracy += getAccuracy(network.get(currentCase.inputs), currentCase.answers);
        System.out.println("accuracy: " + (accuracy / testCases.length));
    }

    public abstract double getAccuracy(double[] results, double[] answers);

    public class Case {
        public Case(double[] inputs, double[] answers) {
            this.inputs = inputs;
            this.answers = answers;
        }

        public double[] inputs;
        public double[] answers;
    }
}