import java.util.Arrays;
import java.util.Hashtable;
import java.util.Random;
import java.util.Set;
// import java.lang.Math;

public class Genetic {
    private static final int POPULATION_SIZE = 10;
    private int boardSize;

    public Genetic(int boardSize) {
        this.boardSize = boardSize;
    }

    public BoardState[] initializePopulation() {
        BoardState[] population = new BoardState[POPULATION_SIZE];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population[i] = createRandomBoardState();
        }
        return population;
    }

    private BoardState createRandomBoardState() {
        BoardState boardState = new BoardState(this.boardSize);
        Random random = new Random();

        // Randomly set values for some cells (0 or 1)
        for (int i = 0; i < 15; i++) {
            int index = random.nextInt(25);
            boardState.setCell(index, 1);
        }

        return boardState;
    }


    //menghitung ftsness dari setiap angka yg diketahui pada board
    //dengan cara menghitung jumlah "kotak hitam" disekitar angka tsb termasuk dirinya
    //lalu dibagi dengan angka tsb
    private float countFitnessValue(Hashtable<Integer, Integer> inputBoard, int index, BoardState kromosom) {
        float hasil = -1;
        int valueIndex = inputBoard.get(index);

        int self = kromosom.getSelf(index);
        int left = kromosom.getLeft(index);
        int right = kromosom.getRight(index);
        int top = kromosom.getTop(index);
        int bottom = kromosom.getBottom(index);
        int bottomLeft = kromosom.getBottomLeft(index);
        int bottomRight = kromosom.getBottomRight(index);
        int topLeft = kromosom.getTopLeft(index);
        int topRight = kromosom.getTopRight(index);

        int sum = self + left + right + top + bottom + bottomLeft + bottomRight + topLeft + topRight;

        if (sum <= valueIndex) {
            hasil = (float) sum/valueIndex;
        }
        return hasil;
    }

    public void countFitnessKromosom(BoardState kromosom, Hashtable<Integer, Integer> inputBoard, Set<Integer> setOfIndexInput){
        float scoreKromosom = 0;
        for (int index : setOfIndexInput) {
            scoreKromosom += countFitnessValue(inputBoard, index, kromosom);
        }
        if (scoreKromosom < 0) {
            kromosom.setFitness(0);
        } else {

            kromosom.setFitness(scoreKromosom);
        }
    }

    public BoardState[] rankSelection(BoardState[] population, int numSelections) {
        int populationSize = population.length;
        BoardState[] selectedPopulation = new BoardState[numSelections];
        Random random = new Random();

        // Assign probabilities based on rank and fitness
        double totalFitness = 0.0;
        for (int i = 0; i < populationSize; i++) {
            totalFitness += population[i].getFitness();
        }

        for (int i = 0; i < populationSize; i++) {
            double probability = population[i].getFitness() / totalFitness;
            population[i].setFitnessProbability(probability);
        }

        // Perform rank selection
        for (int i = 0; i < numSelections; i++) {
            double randomValue = random.nextDouble();
            double cumulativeProbability = 0.0;

            for (int j = 0; j < populationSize; j++) {
                cumulativeProbability += population[j].getFitnessProbability();

                if (randomValue <= cumulativeProbability) {
                    selectedPopulation[i] = population[j];
                    break;
                }
            }
        }

        return selectedPopulation;
    }

    public void performCrossOver(int[] chromosome1, int[] chromosome2) {

        System.out.println("Before Crossover:");
        System.out.println(Arrays.toString(chromosome1));
        System.out.println(Arrays.toString(chromosome2));

        // Perform one-point crossover
        int crossoverPoint = 12;
        int[] child1 = Arrays.copyOf(chromosome1, chromosome1.length);
        int[] child2 = Arrays.copyOf(chromosome2, chromosome2.length);
        onePointCrossover(child1, child2, crossoverPoint);

        System.out.println("\nAfter One-Point Crossover:");
        System.out.println(Arrays.toString(child1));
        System.out.println(Arrays.toString(child2));

        // Store children in an array
        int[][] childrenArray = new int[8][child1.length];

        // Perform mutation for 8 children
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            int[] newChild = Arrays.copyOf(i % 2 == 0 ? child1 : child2, child1.length);
            mutate(newChild, random.nextInt(newChild.length));
            childrenArray[i] = newChild;
        }

        System.out.println("\nAfter Mutation for 8 Children:");
        for (int i = 0; i < 8; i++) {
            System.out.println(Arrays.toString(childrenArray[i]));
        }
    }

    // One-point crossover
    public void onePointCrossover(int[] chromosome1, int[] chromosome2, int crossoverPoint) {
        for (int i = crossoverPoint; i < chromosome1.length; i++) {
            int temp = chromosome1[i];
            chromosome1[i] = chromosome2[i];
            chromosome2[i] = temp;
        }
    }

    // Mutation

    public void mutate(int[] chromosome, int index) {
        chromosome[index] = 1 - chromosome[index];
    }

}
