package io.github.catlystgit.NeuralNet.utils;

import io.github.catlystgit.NeuralNet.Neuron.Neuron;
import io.github.catlystgit.NeuralNet.Network.NeuralNetwork;
import io.github.catlystgit.NeuralNet.Neuron.NeuronInput;
import java.util.Random;

public class CatlystNeuralNetwork extends NeuralNetwork {
    public double[] values;
    protected double[] answerResults;
    public Random random;

    public CatlystNeuralNetwork(int inputs, int[] layerSizes, double randWeight, double learningRate) {
        super(inputs, layerSizes, randWeight, learningRate);
    }

    @Override
    public void makeNetwork(int[] layerSizes) {
        random = new Random();
        answerResults = new double[results];
        values = new double[inputs];
        layers = new Neuron[layerSizes.length][]; // Make layers

        Neuron[] prevLayer = layers[layerSizes.length-1] = new Neuron[layerSizes[layerSizes.length-1]]; // make first layer

        for(int neuronIndex = 0; neuronIndex != prevLayer.length; neuronIndex++) { // Initialize first layer
            NeuronInput[] neuronInputs = new NeuronInput[inputs+1]; // Sensorinputs + bias
            prevLayer[neuronIndex] = new CatlystNeuron(neuronInputs); // Initialize neuron

            neuronInputs[inputs] = new Bias(); // Add the inputs
            for(int inputIndex = 0; inputIndex != inputs; inputIndex++)
                neuronInputs[inputIndex] = new SensorInput(inputIndex);
        }

        for(int layerIndex = layerSizes.length-2; layerIndex != -1; layerIndex--) {
            Neuron[] layer = layers[layerIndex] = new Neuron[layerSizes[layerIndex]];

            for(int neuronIndex = 0; neuronIndex != layer.length; neuronIndex++) {
                NeuronInput[] neuronInputs = new NeuronInput[prevLayer.length+1];
                layer[neuronIndex] = new CatlystNeuron(neuronInputs);

                neuronInputs[prevLayer.length] = new Bias();
                for(int inputIndex = 0; inputIndex != prevLayer.length; inputIndex++)
                    neuronInputs[inputIndex] = new Input(prevLayer[inputIndex]);
            }
        }
    }

    public void setRandomSeed(int seed) {
        random.setSeed(seed);
    }

    @Override
    public void set(double[] values) {
        this.values = values;
    }

    @Override
    public double[] get() {
        update(answerResults);
        return answerResults;
    }

    @Override
    public double getValue(int index) {
        return values[index];
    }

    @Override
    public void setValue(int index, double value) {
        values[index] = value;
    }

    public double getRandWeight() {
        return (random.nextDouble() - 0.5) * randWeightRange * 2;
    }

    public class CatlystNeuron implements Neuron {
        private final NeuronInput[] inputs;
        private double result;
        private int currentPeriod;
        private double errorSignal;

        public CatlystNeuron(NeuronInput... inputs) {
            this.inputs = inputs;
        }

        @Override
        public NeuronInput[] getInputs() {
            return inputs;
        }

        @Override
        public double output(double input) {
            return 1 / (1 + Math.exp(-input));
        }

        @Override
        public double compute() {
            double value = 0;
            for(NeuronInput input : inputs)
                value += input.get();
            return value;
        }

        @Override
        public double get() {
            return result;
        }

        @Override
        public void update() {
            errorSignal = 0;
            result = output(compute());
        }

        @Override
        public double getErrSignal() {
            return errorSignal * result * (1 - result);
        }

        @Override
        public void setErrSignal(double signal) {
            errorSignal = signal;
        }

        @Override
        public void addErrSignal(double signal) {
            errorSignal += signal;
        }

    }
    public class SensorInput implements NeuronInput {
        protected double weight;
        public final int sensorIndex;

        public SensorInput(int sensorIndex) {
            this.sensorIndex = sensorIndex;
            this.weight = getRandWeight();
        }
        public SensorInput(int sensorIndex, double weight) {
            this.sensorIndex = sensorIndex;
            this.weight = weight;
        }

        @Override
        public double get() {
            return weight*values[sensorIndex];
        }

        @Override
        public double getInput() {
            return values[sensorIndex];
        }

        @Override
        public double getWeight() {
            return weight;
        }

        @Override
        public void setWeight(double weight) {
            this.weight = weight;
        }

        @Override
        public Neuron getSource() {
            return null;
        }
    }
    public class Bias implements NeuronInput {
        protected double weight;

        public Bias() {
            this.weight = getRandWeight();
        }

        public Bias(double weight) {
            this.weight = weight;
        }

        @Override
        public double get() {
            return weight;
        }

        @Override
        public double getInput() {
            return 1;
        }

        @Override
        public double getWeight() {
            return weight;
        }

        @Override
        public void setWeight(double weight) {
            this.weight = weight;
        }

        @Override
        public Neuron getSource() {
            return null;
        }
    }
    public class Input implements NeuronInput {
        protected double weight;
        public final Neuron source;

        public Input(Neuron source) {
            this.source = source;
            this.weight = getRandWeight();
        }

        public Input(Neuron source, double weight) {
            this.source = source;
            this.weight = weight;
        }

        @Override
        public double get() {
            return getInput()*weight;
        }

        @Override
        public double getInput() {
            return source.get();
        }

        @Override
        public double getWeight() {
            return weight;
        }

        @Override
        public void setWeight(double weight) {
            this.weight = weight;
        }

        @Override
        public Neuron getSource() {
            return source;
        }
    }
}
