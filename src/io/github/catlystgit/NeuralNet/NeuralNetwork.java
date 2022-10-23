package io.github.catlystgit.NeuralNet;

import io.github.catlystgit.NeuralNet.Neuron.Neuron;
import io.github.catlystgit.NeuralNet.Neuron.NeuronInput;

public abstract class NeuralNetwork {
    public Neuron[][] layers;
    public final int inputs;
    public final int results;
    public double learningRate;
    public int period;

    public NeuralNetwork(int inputs, int[] layerSizes, double learningRate) {
        this.inputs = inputs;
        this.learningRate = learningRate;
        results = layerSizes[0];
        layers = new Neuron[layerSizes.length][];
        for(int i = 0; i != layerSizes.length; i++) {
            int layerSize = layerSizes[i];
            Neuron[] layer = layers[i] = new Neuron[layerSize];
            for(int j = 0; j != layerSize; j++)
                layer[j] = makeNeuron(i);
        }
    }

    public abstract Neuron makeNeuron(int layer);
    public abstract void set(double[] values);
    public double[] get() {
        period++;
        double[] results = new double[this.results];
        int index = 0;
        for(Neuron neuron : layers[0])
            results[index++] = neuron.get();
        return results;
    }
    public double[] get(double[] values) {
        set(values);
        return get();
    }
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
}
