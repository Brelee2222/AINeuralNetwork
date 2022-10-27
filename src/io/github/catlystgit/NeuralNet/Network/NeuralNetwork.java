package io.github.catlystgit.NeuralNet.Network;

import io.github.catlystgit.NeuralNet.Neuron.Neuron;
import io.github.catlystgit.NeuralNet.Neuron.NeuronInput;

public abstract class NeuralNetwork {
    public Neuron[][] layers;
    public final int inputs;
    public final int results;
    public double randWeightRange;
    public double learningRate;

    public NeuralNetwork(int inputs, int[] layerSizes, double randWeightRange, double learningRate) {
        this.inputs = inputs;
        this.learningRate = learningRate;
        this.randWeightRange = randWeightRange;
        results = layerSizes[0];
        makeNetwork(layerSizes);
    }

    // Makes the network
    public abstract void makeNetwork(int[] layerSizes);

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

    public abstract void setValue(int index, double value);

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

    public void inputFeedback(double[] answers, double accuracy, int maxEpoch, double randRange) {
        double[] values = new double[inputs];
        for(int input = 0; input != inputs; input++)
            values[input] = (Math.random() - 0.5) * randRange * 2;
        double[] results = get(values);

        Neuron[] terminalNeurons = layers[0];
        for(int i = 0; i != terminalNeurons.length; i++)
            terminalNeurons[i].setErrSignal(logBase(answers[i], Math.E) - logBase(results[i], Math.E));

        for(Neuron[] neurons : layers) for(Neuron neuron : neurons) {
            double errSignal = neuron.getErrSignal();
            for (NeuronInput input : neuron.getInputs()) {
                Neuron from = input.getSource();

                if(from != null) {
                    double rawsult = from.getResult();
                    from.addErrSignal(errSignal * rawsult);
                    from.setResult(rawsult + input.getWeight() * learningRate * errSignal);
                }
            }
        }
        for(Neuron neuron : layers[layers.length-1]) {
            double errSignal = neuron.getErrSignal();
            for (NeuronInput input : neuron.getInputs()) {

                double rawsult = Math.log(1/input.get()-1)/Math.log(Math.E);

                for(int inputIndex = 0; inputIndex != neuron.getInputs().length-1; inputIndex++)
                    setValue(inputIndex, neuron.output(rawsult + input.getWeight() * learningRate * errSignal));
            }
        }
    }

    public double logBase(double value, double base) {
        return Math.log(value) / Math.log(base);
    }
}
