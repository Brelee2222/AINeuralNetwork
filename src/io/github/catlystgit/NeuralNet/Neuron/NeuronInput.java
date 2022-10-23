package io.github.catlystgit.NeuralNet.Neuron;

public interface NeuronInput {
    double get();
    double getInput();
    double getWeight();
    void setWeight(double weight);
    Neuron getSource();
}
