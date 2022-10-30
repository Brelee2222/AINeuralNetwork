package io.github.catlystgit.NeuralNet.Neuron;

public interface NeuronInput {
    double get(); // return the value of input * weight
    double getInput(); // returns input (should default to 1 if it is a bias)
    double getWeight(); // returns the weight
    void setWeight(double weight); // sets the weight
    void setInput(double input); // sets input (optional) for input feedback
    void setTargetInput(double input); // Inputfeeback
    double getTargetInput(); // For input feedback
    Neuron getSource(); // returns the source neuron
}
