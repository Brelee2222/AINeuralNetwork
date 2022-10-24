package io.github.catlystgit.Neuron;

public interface Neuron {
    NeuronInput[] getInputs();
    double output(double input);
    double compute();
    double get(); // returns the input * weight
    double getErrSignal(); // returns the errSignal of the neuron
    void setErrSignal(double signal); // gives an error signal. Should give a different result from getErrSignal()
    void addErrSignal(double signal); // adds err signal
}