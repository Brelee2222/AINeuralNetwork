package io.github.brelee2222.Neuron;

public abstract class Neuron {
    public NeuronInput[] inputs;
    private double errSignal;
    private double result;
    private int currentPeriod = 0;

    public Neuron(NeuronInput... inputs) {
        this.inputs = inputs;
    }

    public static double output(double input) {
        return 1/(1 + Math.pow(Math.E, -input));
    }
    public double compute() {
        double sum = 0.0;
        for(NeuronInput input : inputs)
            sum += input.get();
        return sum;
    }
    public double getValue() {
        return output(compute());
    }
    public double get() {
        int period = getPeriod();
        if(currentPeriod == period)
            return result;
        errSignal = 0;
        currentPeriod = period;
        return result = getValue();
    }
    public double getErrSignal() {
        return errSignal * result * (1 - result);
    }
    public void addErrSignal(double signal) {
        errSignal += signal;
    }
    public void setErrSignal(double signal) {
        errSignal = signal;
    }
    public abstract int getPeriod();
}
