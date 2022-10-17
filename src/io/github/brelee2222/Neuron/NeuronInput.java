package io.github.brelee2222.Neuron;

import io.github.brelee2222.Neuron.Neuron;

public interface NeuronInput {
    double get(); // returns the current processed value of the input and weight
    double getInput(); // returns the raw value/input
    double getWeight(); // returns the weight
    void setWeight(double weight); // sets the weight to the given value and returns it
    Neuron getFrom(); //returns the io.github.brelee2222.Neuron.Neuron source. returns null if it has no neuron
//    double getULR(); // returns the unique learning rate of the input Deprecated
}
