import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;

public abstract class Learning {
    public final NeuralNetwork network;
    public final int inputs;
    public final int answers;
    public Case[] testCases;
    public Case[] trainingCases;
    public double accThresh = 0.99973;
    public double maxEpoch = -1;

    public Learning(NeuralNetwork network, int tests, int trainings, int inputs, int answers, String trainingPath, String testingPath) { // training data path and testing data path
        this.network = network;
        this.inputs = inputs;
        this.answers = answers;
        trainingCases = new Case[trainings];
        testCases = new Case[tests];
        try {
            Scanner training = new Scanner(new File(trainingPath));
            for(int i = 0; i != trainings; i++){
                StringTokenizer caseData = new StringTokenizer(training.nextLine(), ",");
                double[] caseInputs = new double[inputs];
                double[] caseAnswers = new double[answers];
                for(int j = 0; j != inputs; j++)
                    caseInputs[j] = Double.parseDouble(caseData.nextToken());
                for(int j = 0; j != answers; j++)
                    caseAnswers[j] = Double.parseDouble(caseData.nextToken());
                trainingCases[i] = new Case(caseInputs, caseAnswers);
            }
            Scanner testing = new Scanner(new File(testingPath));
            for(int i = 0; i != tests; i++){
                StringTokenizer caseData = new StringTokenizer(testing.nextLine(), ",");
                double[] caseInputs = new double[inputs];
                double[] caseAnswers = new double[answers];
                for(int j = 0; j != inputs; j++)
                    caseInputs[j] = Double.parseDouble(caseData.nextToken());
                for(int j = 0; j != answers; j++)
                    caseAnswers[j] = Double.parseDouble(caseData.nextToken());
                testCases[i] = new Case(caseInputs, caseAnswers);
            }
        } catch (Exception e) {
            System.err.println(e);
            for(StackTraceElement element : e.getStackTrace())
                System.err.println(element);
            System.exit(0);
        }
    }

    public void train() {
        double accuracy = 0.0;
        int epoch = 0;
        while(accuracy < accThresh && maxEpoch != epoch) {
            accuracy = 0.0;
            for(Case currentCase : trainingCases) {
                accuracy += getAccuracy(network.get(currentCase.inputs), currentCase.answers);
                network.learn(currentCase.answers);
            }
            System.out.println("accuracy: " + (accuracy /= trainingCases.length) + "   epoch: " + ++epoch);
        }
    }
    public void test() {
        double accuracy = 0.0;
        for(Case currentCase : testCases)
            accuracy += getAccuracy(network.get(currentCase.inputs), currentCase.answers);
        System.out.println("accuracy: " + (accuracy / testCases.length));
    }

    public abstract double getAccuracy(double[] results, double[] answers);

    public class Case {
        public Case(double[] inputs, double[] answers) {
            this.inputs = inputs;
            this.answers = answers;
        }

        public double[] inputs;
        public double[] answers;
    }
}