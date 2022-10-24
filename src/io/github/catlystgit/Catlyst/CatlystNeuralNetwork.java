package io.github.catlystgit.Catlyst;

import io.github.catlystgit.Neuron.Neuron;
import io.github.catlystgit.Network.NeuralNetwork;
import io.github.catlystgit.Neuron.NeuronInput;

import java.util.Random;

public class CatlystNeuralNetwork extends NeuralNetwork {
    private int period;
    private double[] values;
    public Random random = new Random();

    public CatlystNeuralNetwork(int inputs, int[] layerSizes, double randWeight, double learningRate) {
        super(inputs, layerSizes, randWeight, learningRate);
    }

    public void setRandomSeed(int seed) {
        random.setSeed(seed);
    }

    @Override
    public void makeNetwork() {
        values = new double[inputs];

        Neuron[] prevLayer = layers[layers.length-1];
        for(int i = 0; i != prevLayer.length; i++) {
            NeuronInput[] neuronInputs = new NeuronInput[inputs+1];
            neuronInputs[inputs] = new Bias();
            for(int j = 0; j != inputs; j++)
                neuronInputs[j] = new SensorInput(j);
            prevLayer[i] = new CatlystNeuron(neuronInputs);
        }
        for(int i = layers.length-2; i != 0; i--) {
            Neuron[] layer = layers[i];
            for(int j = 0; j != layer.length; j++) {
                NeuronInput[] neuronInputs = new NeuronInput[prevLayer.length+1];
                neuronInputs[prevLayer.length] = new Bias();
                int k = 0;
                for(Neuron neuron : prevLayer)
                    neuronInputs[k++] = new Input(neuron);
                layer[j] = new CatlystNeuron(neuronInputs);
            }
            prevLayer = layer;
        }
    }

    @Override
    public void set(double[] values) {
        this.values = values;
    }

    @Override
    public double[] get() {
        period++;
        return super.get();
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
            return 1 / (1 + Math.exp(input));
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
            if(currentPeriod != period) {
                currentPeriod = period;
                errorSignal = 0;
                return result = getValue();
            }
            return result;
        }

        public double getValue() {
            return output(compute());
        }

        @Override
        public double getErrSignal() {
            return errorSignal * result * (result - 1);
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
        protected double weight = getRandWeight();
        public final int sensorIndex;

        public SensorInput(int sensorIndex) {
            this.sensorIndex = sensorIndex;
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
        protected double weight = getRandWeight();

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
        protected double weight = getRandWeight();
        public final Neuron source;

        public Input(Neuron source) {
            this.source = source;
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
