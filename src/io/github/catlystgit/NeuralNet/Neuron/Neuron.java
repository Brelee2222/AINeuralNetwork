package io.github.catlystgit.NeuralNet.Neuron;

public interface Neuron {
    NeuronInput[] getInputs();
    double output(double input);
    double compute();
    double getValue();
    double get();
    double getErrSignal();
    void setErrSignal(double signal);
    void addErrSignal(double signal);
}