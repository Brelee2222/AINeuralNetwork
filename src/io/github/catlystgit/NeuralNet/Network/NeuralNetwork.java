package io.github.catlystgit.NeuralNet.Network;

import io.github.catlystgit.NeuralNet.Neuron.Neuron;
import io.github.catlystgit.NeuralNet.Neuron.NeuronInput;
// DOCUMENTATION NOT ADDED YET
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
        update(results);
        return results;
    }

    //Update array instead of creating new array
    public void update(double[] results) {
        int index = 0;
        for(Neuron neuron : layers[0])
            results[index++] = neuron.get();
    }

    // Get method with set built-in
    public double[] get(double[] values) {
        set(values);
        return get();
    }

    public abstract double getValue(int index); // Gets sensor input by index

    public abstract void setValue(int index, double value);

    // Learns from answers returns errorsigs
    public void learn(double[] correctAnswers) {
        Neuron[] terminalNeurons = layers[0];
        for(int i = 0; i != terminalNeurons.length; i++) {
            Neuron neuron = terminalNeurons[i];
            neuron.setErrSignal(correctAnswers[i] - neuron.get());
        }
        for(Neuron[] neurons : layers) for(Neuron neuron : neurons) {
            double errSignal = neuron.getErrSignal();
            for (NeuronInput input : neuron.getInputs()) {
                Neuron source = input.getSource();
                double weight = input.getWeight();
                if(source != null)
                    source.addErrSignal(errSignal * weight);
                input.setWeight(weight + input.getInput() * learningRate * errSignal);
            }
        }
    }

    public double[] getErrSig(double[] correctAnswers) {
        Neuron[] terminalNeurons = layers[0];
        for(int i = 0; i != terminalNeurons.length; i++) {
            Neuron neuron = terminalNeurons[i];
            neuron.setErrSignal(correctAnswers[i] - neuron.get());
        }
        for(int layerIndex = 0; layerIndex != layers.length-1; layerIndex++) {
            Neuron[] layer = layers[layerIndex];
            for(Neuron neuron : layer) {
                double errSignal = neuron.getErrSignal();
                for (NeuronInput input : neuron.getInputs()) {
                    Neuron source = input.getSource();
                    double weight = input.getWeight();
                    if (source != null)
                        source.addErrSignal(errSignal * weight);
                    input.setWeight(weight + input.getInput() * learningRate * errSignal);
                }
            }
        }

        double[] errSigs = new double[inputs];

        for(Neuron neuron : layers[layers.length-1]) {
            NeuronInput[] neuronInputs = neuron.getInputs();
            for(int index = 0; index != neuronInputs.length-1; index++) {
                NeuronInput neuronInput = neuronInputs[index];
                errSigs[index] += neuronInput.get();
            }
        }

        for(int index = 0; index != errSigs.length; index++) {
            double sensorValue = getValue(index);
            errSigs[index] *= sensorValue * (1 - sensorValue);
        }
        return errSigs;
    }
}