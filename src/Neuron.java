public class Neuron {
    public NeuronInput[] inputs; // Allows the ability to make stupidly complex networks. Improves efficiency
    private double errSignal;
    private double result = Double.NaN;

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
        return Double.isNaN(result) ? result = getValue() : result;
    }
    public void reset() { // resets the values so they don't get reprocessed
        errSignal = 0;
        result = Double.NaN;
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
}
