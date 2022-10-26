package io.github.catlystgit.NeuralNet.Network;

import io.github.catlystgit.NeuralNet.Neuron.Neuron;
import io.github.catlystgit.NeuralNet.Neuron.NeuronInput;

public abstract class NeuralNetwork {
    public Neuron[][] layers;
//    public int totalLayers; layers.length
    public final int inputs;
    public final int results;
    public double randWeightRange;
    public double learningRate;

    public NeuralNetwork(int inputs, int[] layerSizes, double randWeightRange, double learningRate) {
        this.inputs = inputs;
        this.learningRate = learningRate;
        this.randWeightRange = randWeightRange;
        results = layerSizes[0];
        layers = new Neuron[layerSizes.length][];
        for(int i = 0; i != layerSizes.length; i++)
            layers[i] = new Neuron[layerSizes[i]];
    }

    // Makes the network
    public abstract void makeNetwork();

    // Sets the sensor values
    public abstract void set(double[] values);

    // Gets values from terminal neurons
    public double[] get() {
        double[] results = new double[this.results];
        int index = 0;
        for(Neuron neuron : layers[0])
            results[index++] = neuron.get();
        return results;
    }

    // Get method with set built-in
    public double[] get(double[] values) {
        set(values);
        return get();
    }

    public abstract double getValue(int index);

    // Learns from answers
    public void learn(double[] correctAnswers) {
        Neuron[] terminalNeurons = layers[0];
        for(int i = 0; i != terminalNeurons.length; i++) {
            Neuron neuron = terminalNeurons[i];
            neuron.setErrSignal(correctAnswers[i] - neuron.get());
        }
        for(Neuron[] neurons : layers) for(Neuron neuron : neurons) {
            double errSignal = neuron.getErrSignal();
            for (NeuronInput input : neuron.getInputs()) {
                Neuron from = input.getSource();
                double weight = input.getWeight();
                if(from != null)
                    from.addErrSignal(errSignal * weight);
                input.setWeight(weight + input.getInput() * learningRate * errSignal);
            }
        }
    }

    public void inputFeedback(double[] answers) {
        double[] values = new double[inputs];
        learningRate = 0.01;
        set(values);
        for(int i = 0; i != inputs; i++)
            values[i] = (Math.random()-0.5)*0.001;
        double accuracy;

    }
}
