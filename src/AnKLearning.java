public class AnKLearning extends Learning {
    public AnKLearning(String trainingPath, String testingPath) {
        super(new CatlystNeuralNet(64, 10, 3, 64, 0.1, 0.05), 1797, 3823, 64, 1, trainingPath, testingPath);
        for(Case currentCase : trainingCases) {
            for(int i = 0; i != currentCase.answers.length; i++) {
                int categ = (int) currentCase.answers[0];
                currentCase.answers = new double[10];
                currentCase.answers[categ] = 1;
            }
            for(int i = 0; i != currentCase.inputs.length; i++)
                currentCase.inputs[i] /= 16;
        }
        for(Case currentCase : testCases) {
            for(int i = 0; i != currentCase.answers.length; i++) {
                int categ = (int) currentCase.answers[0];
                currentCase.answers = new double[10];
                currentCase.answers[categ] = 1;
            }
            for(int i = 0; i != currentCase.inputs.length; i++)
                currentCase.inputs[i] /= 16;
        }
    }

    @Override
    public double getAccuracy(double[] results, double[] answers) {
        int index = 0;
        int categ = 0;
        double highest = 0;
        for(double result : results) {
            if(result >= highest) {
                index = categ;
                highest = result;
            }
            categ++;
        }
        return answers[index];
    }
}
