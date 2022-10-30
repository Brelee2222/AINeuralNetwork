package io.github.catlystgit.NeuralNet.Neuron;

public interface Neuron {
    NeuronInput[] getInputs();
    double output(double input);
    double compute();
    double get(); // returns the input * weight and activation
    double getErrSignal(); // returns the errSignal of the neuron
    double getRawErrSignal();
    double getResult(); // returns unchanged activation
    void setResult(double result); // sets the result value for get (used for input feedback)
    void setErrSignal(double signal); // gives an error signal. Should give a different result from getErrSignal()
    void addErrSignal(double signal); // adds err signal
    void setTargetResult(double result); // Inputfeeback
    double getTargetResult(); // For input feedback
    NeuronInput getBias();
}