package io.github.catlystgit.NeuralNet.utils;

import io.github.catlystgit.NeuralNet.Neuron.Neuron;
import io.github.catlystgit.NeuralNet.Neuron.NeuronInput;

public class CatlystGAN extends CatlystNeuralNetwork {
    public final CatlystNeuralNetwork GAN;

    public CatlystGAN(int inputs, int[] layerSizes, double randWeight, double learningRate, double randGAN) {
        super(inputs, layerSizes, randWeight, learningRate);
        GAN = new CatlystNeuralNetwork(inputs, layerSizes, randGAN, learningRate);
    }

    @Override
    public void learn(double[] correctAnswers) {
        double[] errSigs = learnGAN(correctAnswers);
        Neuron[] terminalNeurons = layers[0];
        for(int terminalIndex = 0; terminalIndex != terminalNeurons.length; terminalIndex++)
            terminalNeurons[terminalIndex].setErrSignal(errSigs[terminalIndex]);
        for (Neuron[] neurons : layers)
            for (Neuron neuron : neurons) {
                double errSignal = neuron.getErrSignal();
                for (NeuronInput input : neuron.getInputs()) {
                    Neuron from = input.getSource();
                    double weight = input.getWeight();
                    if (from != null)
                        from.addErrSignal(errSignal * weight);
                    input.setWeight(weight + input.getInput() * learningRate * errSignal);
                }
            }
    }


    public double[] learnGAN(double[] correctAnswers) {
        Neuron[] terminalNeurons = layers[0];
        for (int i = 0; i != terminalNeurons.length; i++) {
            Neuron neuron = terminalNeurons[i];
            neuron.setErrSignal(correctAnswers[i] - neuron.get());
        }
        for (int layerIndex = 0; layerIndex != layers.length - 1; layerIndex++) {
            Neuron[] layer = layers[layerIndex];
            for (Neuron neuron : layer) {
                double errSignal = neuron.getErrSignal();
                for (NeuronInput input : neuron.getInputs()) {
                    Neuron from = input.getSource();
                    double weight = input.getWeight();
                    if (from != null)
                        from.addErrSignal(errSignal * weight);
                    input.setWeight(weight + input.getInput() * learningRate * errSignal);
                }
            }
        }
        double[] errSigs = new double[inputs];
        Neuron[] lastLayer = layers[layers.length-1];
        for(int neuronIndex = 0; neuronIndex != lastLayer.length; neuronIndex++) {
            Neuron neuron = lastLayer[neuronIndex];
            double errSignal = neuron.getErrSignal();
            NeuronInput[] neuronInputs = neuron.getInputs();
            for (int inputIndex = 0; inputIndex != neuronInputs.length-1; inputIndex++) {
                NeuronInput input = neuronInputs[inputIndex];
                double weight = input.getWeight();
                errSigs[inputIndex] += errSignal * weight;
                input.setWeight(weight + input.getInput() * learningRate * errSignal);
            }
            NeuronInput bias = neuronInputs[neuronInputs.length-1];
            double weight = bias.getWeight();
            bias.setWeight(weight + bias.getInput() * learningRate * errSignal);
        }
        for(int index = 0; index != inputs; index++) {
            double value = getValue(index);
            errSigs[index] *= value * (1-value);
        }
        return errSigs;
    }
}
