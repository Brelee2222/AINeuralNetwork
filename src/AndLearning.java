public class AndLearning extends Learning {

    public AndLearning() {
        super(new AndNetwork(), 0, 4, 2, 2, "AndConditional.txt", "AndConditional.txt");
    }

    @Override
    public double getAccuracy(double[] results, double[] answers) {
        boolean result = results[0] < results[1];
        boolean answer = answers[0] < answers[1];
        return result == answer ? 1.0 : 0.0;
    }
}
