package io.github.catlystgit.NeuralNet.Neuron;

public interface Neuron {
    NeuronInput[] getInputs();
    double output(double input);
    double compute();
    double get(); // returns the input * weight and activation
    void update(); // sets result
    double getErrSignal(); // returns the errSignal of the neuron
    void setErrSignal(double signal); // gives an error signal. Should give a different result from getErrSignal()
    void addErrSignal(double signal); // adds err signal
}