public class Main {
    public static void main(String[] args) {
        Learning ank = new AnKLearning("AnK.txt", "AnKTests.txt");
        ank.train();

        ank.test();
    }
}