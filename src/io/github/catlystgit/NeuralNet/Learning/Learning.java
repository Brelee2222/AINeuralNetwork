package io.github.catlystgit.NeuralNet.Learning;

import io.github.catlystgit.NeuralNet.Network.NeuralNetwork;
// Not condoned to be used for a primary learner.
public abstract class Learning {
    public NeuralNetwork network;
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

    public void train() {
        double accuracy;
        int epoch = 0;
        do {
            accuracy = 0.0;
            for(Case currentCase : trainingCases) {
                accuracy += getAccuracy(network.get(currentCase.inputs), currentCase.answers);
                network.learn(currentCase.answers);
            }
            System.out.println("accuracy: " + (accuracy /= trainingCases.length) + "   epoch: " + epoch);
        } while(accuracy < accThresh && maxEpoch != ++epoch);
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