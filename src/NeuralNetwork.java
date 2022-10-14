public class NeuralNetwork {
    public Neuron[][] neuronLayers; // Makes it think more
    public double[] values; // the sensor values. Named so to not get confused with NeuronInputs in general
    public double learningRate;

    public void set(double[] values) {
        this.values = values;
    }
    public double[] get() {
        for(Neuron[] neuronLayer : neuronLayers)
            for(Neuron neuron : neuronLayer)
                neuron.reset(); //make value updatable
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
            double result = neuron.get();
            neuron.setErrSignal(correctAnswers[i] - result);
        }
        for(Neuron[] neurons : neuronLayers) for(Neuron neuron : neurons) {
            double errSignal = neuron.getErrSignal();
            for (NeuronInput input : neuron.inputs) {
                Neuron from = input.getFrom();
                if(from != null)
                    from.addErrSignal(errSignal * input.getWeight());
                input.setWeight(input.getWeight() + errSignal * input.getInput() * learningRate);
            }
        }
    }
}
