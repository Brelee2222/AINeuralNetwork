package io.github.brelee2222.NeuralNetwork.utils.Catlyst;

import io.github.brelee2222.NeuralNetwork.Network.NeuralNetwork;
import io.github.brelee2222.NeuralNetwork.Neuron.Neuron;
import io.github.brelee2222.NeuralNetwork.Neuron.NeuronInput;

public class CatlystNeuralNet extends NeuralNetwork {
    // Catlyst is my signature
    // This Network is more experimental for different sizes, amounts of inputs, and layers and learning rate
    // It allows the network to do several operations in sequence effectively allowing interactions between inputs
    // This Neural Network will disable and enable connections by itself
    // No predefined layers

    // Layers : Ability to think/perform neuron interactions
    // LayerSize : Capacity to think
    // Inputs : how many sensors
    // Results : terminals
    // RandWeight : weight randomness

    public final int results;
    public final int layers;
    public final double randWeight;

    private CatlystNeuralNet(int inputs, int[] layerSizes, double randWeight, double learningRate) {
        this.randWeight = randWeight;
        this.learningRate = learningRate;
        this.inputs = inputs;
        this.results = layerSizes[0];
        this.layers = layerSizes.length;
        this.values = new double[inputs];

        neuronLayers = new Neuron[layers][];
        Neuron[] prevLayer = neuronLayers[layers-1] = new Neuron[layerSizes[layers-1]];
        int layerSize = layerSizes[layers-1];
        for(int i = 0; i != layerSize; i++) {
            NeuronInput[] neuronInputs = new NeuronInput[inputs+1];
            neuronInputs[inputs] = new Bias();
            for(int j = 0; j != inputs; j++)
                neuronInputs[j] = new SensorInput(j);
            prevLayer[i] = new NetNeuron(neuronInputs);
        }
        for(int i = layers-2; i != -1; i--) {
            int prevLayerSize = layerSize;
            Neuron[] neuronLayer = neuronLayers[i] = new Neuron[layerSize = layerSizes[i]];
            for(int j = 0; j != layerSize; j++) {
                NeuronInput[] neuronInputs = new NeuronInput[prevLayerSize+1];
                neuronInputs[prevLayerSize] = new Bias();
                for(int l = 0; l != prevLayerSize; l++)
                    neuronInputs[l] = new Input(prevLayer[l]);
                neuronLayer[j] = new NetNeuron(neuronInputs);
            }
            prevLayer = neuronLayer;
        }
    }

    public static CatlystNeuralNet makeNetwork(int inputs, int results, int layers, int layerSize, double randWeight, double learningRate) {
        int[] layerSizes = new int[layers];
        layerSizes[0] = results;
        for(int i = 1; i != layers; i++)
            layerSizes[i] = layerSize;
        return new CatlystNeuralNet(inputs, layerSizes, randWeight, learningRate);
    }
    public static CatlystNeuralNet makeNetwork(int inputs, int[] layerSizes, double randWeight, double learningRate) {
        return new CatlystNeuralNet(inputs, layerSizes, randWeight, learningRate);
    }

    public double getRandWeight() {
        return (Math.random()-0.5) * 2 * randWeight;
    }

    public class Bias implements NeuronInput {
        private double weight;

        public Bias() {
            weight = getRandWeight();
        }

        private Bias(double weight) {
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
        public Neuron getFrom() {
            return null;
        }
    }
    public class SensorInput implements NeuronInput {
        private final int index;
        private double weight;

        public SensorInput(int index) {
            this.index = index;
            weight = getRandWeight();
        }

        private SensorInput(int index, double weight) {
            this.index = index;
            this.weight = weight;
        }

        @Override
        public double get() {
            return getInput()*getWeight();
        }

        @Override
        public double getInput() {
            return values[index];
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
        public Neuron getFrom() {
            return null;
        }
    }
    public class Input implements NeuronInput { // I didn't feel like calling it NeuronInputWasTaken
        private final Neuron source;
        private double weight;

        public Input(Neuron source) {
            this.source = source;
            weight = getRandWeight();
        }
        public Input(Neuron source, double weight) {
            this.source = source;
            this.weight = weight;
        }

        @Override
        public double get() {
            return getInput()*getWeight();
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
        public Neuron getFrom() {
            return source;
        }
    }
    public class NetNeuron extends Neuron {

        public NetNeuron(NeuronInput... inputs) {
            super(inputs);
        }

        @Override
        public int getPeriod() {
            return period;
        }
    }
}
