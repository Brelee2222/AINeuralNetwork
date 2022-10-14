import java.util.function.DoubleSupplier;

public class NetTemplate extends NeuralNetwork { // Basic NeuralNet
    public double randomWeight() {
        return Math.random() * 0.1 - 0.05;
    }

    public class Bias implements NeuronInput {
        public double weight = randomWeight();

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
        public double setWeight(double weight) {
            return this.weight = weight;
        }

        @Override
        public Neuron getFrom() {
            return null;
        }
    }
    public class Input implements NeuronInput {
        public final DoubleSupplier value;
        public final Neuron sourceNeuron;
        public double weight = randomWeight();

        //Why not have no source arguement
        public Input(DoubleSupplier value) {
            this.value = value;
            this.sourceNeuron = null;
        }
        public Input(DoubleSupplier value, Neuron sourceNeuron) {
            this.value = value;
            this.sourceNeuron = sourceNeuron;
        }

        @Override
        public double get() {
            return getInput() * getWeight();
        }

        @Override
        public double getInput() {
            return value.getAsDouble();
        }

        @Override
        public double getWeight() {
            return weight;
        }

        @Override
        public double setWeight(double weight) {
            return this.weight = weight;
        }

        @Override
        public Neuron getFrom() {
            return sourceNeuron;
        }
    }
}
