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

    public BoardState[] initializePopulation(int solution) {
        BoardState[] population = new BoardState[POPULATION_SIZE];
        long seed = (long) solution;
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population[i] = createRandomBoardState(seed);
            seed++;
        }
        return population;
    }

    private BoardState createRandomBoardState(long seed) {
        BoardState boardState = new BoardState(this.boardSize);
        Random random = new Random(seed);
        int blackCells = (this.boardSize / 2) + 3;
        // Randomly set values for some cells (0 or 1)
        for (int i = 0; i < blackCells; i++) {
            int index = random.nextInt(this.boardSize * this.boardSize);
            boardState.setCell(index, 1);
        }

        return boardState;
    }

    // menghitung ftsness dari setiap angka yg diketahui pada board
    // dengan cara menghitung jumlah "kotak hitam" disekitar angka tsb termasuk
    // dirinya
    // lalu dibagi dengan angka tsb
    private float countFitnessValue(Hashtable<Integer, Integer> inputBoard, int index, BoardState kromosom,
            int optimum) {
        float hasil = 0.1f;
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
        if (valueIndex == 0) {
            if (sum == 0) {
                hasil = 1;
            }
        } else if (valueIndex != 0 && sum <= valueIndex) {
            if (sum == valueIndex) {
                hasil = 1;

            } else {

                hasil = (float) sum / (float) valueIndex;
            }
        }
        return hasil;
    }

    public void countFitnessKromosom(BoardState kromosom, Hashtable<Integer, Integer> inputBoard,
            Set<Integer> setOfIndexInput, int optimum) {
        float scoreKromosom = 0;
        for (int index : setOfIndexInput) {
            scoreKromosom += countFitnessValue(inputBoard, index, kromosom, optimum);
        }

        kromosom.setFitness(scoreKromosom);

    }

    public BoardState[] rankSelection(BoardState[] population, int numSelections, long seed) {
        int populationSize = population.length;
        BoardState[] selectedPopulation = new BoardState[numSelections];
        Random random = new Random(seed);

        // Array untuk menyimpan peringkat kebugaran
        double[] fitnessRanks = new double[populationSize];

        // Mengurutkan populasi berdasarkan kebugaran
        Arrays.sort(population, (a, b) -> Double.compare(b.getFitness(), a.getFitness()));

        // Memberikan peringkat berdasarkan urutan kebugaran
        double totalRank = 0.0;
        for (int i = 0; i < populationSize; i++) {
            // Misalnya, gunakan peringkat linear
            fitnessRanks[i] = populationSize - i;
            totalRank += fitnessRanks[i];
        }

        // Menghitung probabilitas seleksi berdasarkan peringkat
        for (int i = 0; i < populationSize; i++) {
            fitnessRanks[i] /= totalRank;
        }

        // Mengakumulasikan probabilitas untuk roulette wheel
        for (int i = 1; i < populationSize; i++) {
            fitnessRanks[i] += fitnessRanks[i - 1];
        }

        // Melakukan seleksi
        for (int i = 0; i < numSelections; i++) {
            double rouletteSpin = random.nextDouble();
            for (int j = 0; j < populationSize; j++) {
                if (rouletteSpin <= fitnessRanks[j]) {
                    selectedPopulation[i] = population[j];
                    break;
                }
            }
        }

        return selectedPopulation;
    }

    public BoardState[] performCrossOver(int[] chromosome1, int[] chromosome2, long seed) {

        long tmp = seed;
        Random random = new Random(tmp);
        int chromosomeLength = this.boardSize * this.boardSize;

        // Menentukan crossoverPoint1 dan crossoverPoint2
        int crossoverPoint1 = random.nextInt(chromosomeLength);
        int crossoverPoint2 = random.nextInt(chromosomeLength);
        onePointCrossover(chromosome1, chromosome2, crossoverPoint1, crossoverPoint2);

        // Store children in an array
        int mutation = POPULATION_SIZE - 2;

        // Store children in an array
        int[][] childrenArray = new int[mutation][chromosome1.length];


        long uniqeseed = 3;
        // Per form mutation for 8 children
        for (int i = 0; i < mutation; i++) {
            int[] newChild = Arrays.copyOf(i % 2 == 0 ? chromosome1 : chromosome2, chromosome1.length);
            mutate(newChild, random.nextInt(newChild.length));
            childrenArray[i] = newChild;
            tmp /= uniqeseed;
            tmp = seed + uniqeseed;
            uniqeseed += 3;
        }

        BoardState[] newPop = new BoardState[POPULATION_SIZE];

        for (int i = 0; i < mutation; i++) {
            BoardState newState = new BoardState(this.boardSize);
            newState.setBoard(childrenArray[i]);
            newPop[i] = newState;
        }
        BoardState parent = new BoardState(this.boardSize);
        BoardState parent2 = new BoardState(this.boardSize);
        parent.setBoard(chromosome1);
        parent2.setBoard(chromosome2);
        newPop[POPULATION_SIZE - 2] = parent;
        newPop[POPULATION_SIZE - 1] = parent2;
        return newPop;
    }

    // One-point crossover
    public void onePointCrossover(int[] chromosome1, int[] chromosome2, int crossoverPoint1, int crossoverPoint2) {
        if (crossoverPoint1 > crossoverPoint2) {
            // Tukar titik jika crossoverPoint1 lebih besar dari crossoverPoint2
            int temp = crossoverPoint1;
            crossoverPoint1 = crossoverPoint2;
            crossoverPoint2 = temp;
        }

        for (int i = crossoverPoint1; i < crossoverPoint2; i++) {
            // Menukar elemen antara dua titik crossover
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
