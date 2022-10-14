public interface NeuronInput {
    double get(); // returns the current processed value of the input and weight
    double getInput(); // returns the raw value/input
    double getWeight(); // returns the weight
    void setWeight(double weight); // sets the weight to the given value and returns it
    Neuron getFrom(); //returns the Neuron source. returns null if it has no neuron
}
