public class CondOp extends NetTemplate {
    public Neuron hidden1 = new NetNeuron(new Input(() -> values[0]), new Input(() -> values[1]), new Bias());
    public Neuron hidden2 = new NetNeuron(new Input(() -> values[0]), new Input(() -> values[1]), new Bias());

    public Neuron answer1 = new NetNeuron(new Input(hidden1::get, hidden1), new Input(hidden2::get, hidden2), new Bias());
    public Neuron answer2 = new NetNeuron(new Input(hidden1::get, hidden2), new Input(hidden2::get, hidden2), new Bias());

    public CondOp() {
        learningRate = 1.0;
        neuronLayers = new Neuron[][]{{answer1, answer2}, {hidden1, hidden2}};
        values = new double[]{0, 0};
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
