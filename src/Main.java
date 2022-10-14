public class Main {
    public static void main(String[] args) {
        Learning thing = new AnKLearning("AnK.txt", "AnKTests.txt");
        thing.train();
        thing.test();
    }
}