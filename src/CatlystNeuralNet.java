public class CatlystNeuralNet extends NeuralNetwork {
    // Catlyst is my signature
    // This Network is more experimental for different sizes, amounts of inputs, and layers and learning rate
    // It allows the network to do several operations in sequence effectively allowing interactions between inputs

    // Layers : Ability to think/perform neuron interactions
    // LayerSize : Capacity to think
    // Inputs : how many sensors
    // Results : terminals
    // RandWeight : weight randomness

    public final int inputs;
    public final int results;
    public final int layers;
    public final int layerSize;
    public final double randWeight;

    public CatlystNeuralNet(int inputs, int results, int layers, int layerSize, double randWeight, double learningRate) {
        init(
                this.inputs = inputs,
                this.results = results,
                this.layers = layers,
                this.layerSize = layerSize,
                this.randWeight = randWeight,
                this.learningRate = learningRate
        );
    }
    public CatlystNeuralNet(int inputs, int results, int layers, int layerSize) {
        init(
                this.inputs = inputs,
                this.results = results,
                this.layers = layers,
                this.layerSize = layerSize,
                this.randWeight = 0.05,
                this.learningRate = 1
        );
    }

    private void init(int inputs, int results, int layers, int layerSize, double randWeight, double learningRate) {
        values = new double[inputs];
        neuronLayers = new Neuron[layers][];
        Neuron[] prevLayer = neuronLayers[layers-1] = new Neuron[layerSize];
        Neuron[] terminalLayer = neuronLayers[0] = new Neuron[results];
        for(int i = 0; i != layerSize; i++) {
            NeuronInput[] neuronInputs = new NeuronInput[inputs+1];
            neuronInputs[inputs] = new Bias();
            for(int j = 0; j != inputs; j++)
                neuronInputs[j] = new SensorInput(j);
            prevLayer[i] = new Neuron(neuronInputs);
        }
        for(int i = layers-2; i != 0; i--) {
            Neuron[] neuronLayer = neuronLayers[i] = new Neuron[layerSize];
            for(int j = 0; j != layerSize; j++) {
                NeuronInput[] neuronInputs = new NeuronInput[layerSize+1];
                neuronInputs[layerSize] = new Bias();
                for(int l = 0; l != layerSize; l++)
                    neuronInputs[l] = new Input(prevLayer[l]);
                neuronLayer[j] = new Neuron(neuronInputs);
            }
            prevLayer = neuronLayer;
        }
        for(int i = 0; i != results; i++) {
            NeuronInput[] neuronInputs = new NeuronInput[layerSize+1];
            neuronInputs[layerSize] = new Bias();
            for(int j = 0; j != layerSize; j++)
                neuronInputs[j] = new Input(prevLayer[j]);
            terminalLayer[i] = new Neuron(neuronInputs);
        }
    }

    public double getRandWeight() {
        return (Math.random()-0.5) * randWeight;
    }

    public class Bias implements NeuronInput {
        private double weight = getRandWeight();

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
        private double weight = getRandWeight();

        public SensorInput(int index) {
            this.index = index;
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
        private double weight = getRandWeight();

        public Input(Neuron source) {
            this.source = source;
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
}
