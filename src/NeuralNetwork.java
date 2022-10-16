public class NeuralNetwork {
    public Neuron[][] neuronLayers; // Makes it think more
    public double[] values; // the sensor values. Named so to not get confused with NeuronInputs in general
    public double learningRate;
    public int period;

    public void set(double[] values) {
        period++;
        this.values = values;
    }
    public double[] get() {
        Neuron[] terminalNeurons = neuronLayers[0];
        double[] values = new double[terminalNeurons.length];
        for(int i = 0; i != values.length; i++)
            values[i] = terminalNeurons[i].get();
        return values;
    }
    public double[] get(double[] values) {
        set(values);
        return get();
    }
    public void learn(double[] correctAnswers) {
        Neuron[] terminalNeurons = neuronLayers[0];
        for(int i = 0; i != terminalNeurons.length; i++) {
            Neuron neuron = terminalNeurons[i];
            neuron.setErrSignal(correctAnswers[i] - neuron.get());
        }
        for(Neuron[] neurons : neuronLayers) for(Neuron neuron : neurons) {
            double errSignal = neuron.getErrSignal();
            for (NeuronInput input : neuron.inputs) {
                double weight = input.getWeight();
                Neuron from = input.getFrom();
                if(from != null)
                    from.addErrSignal(errSignal * weight);
                input.setWeight(weight + errSignal * input.getInput() * learningRate);
            }
        }
    }
}
