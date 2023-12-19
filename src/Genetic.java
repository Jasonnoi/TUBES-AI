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

    public BoardState[] tournamentSelection(BoardState[] population, int numSelections) {
        int populationSize = population.length;
        BoardState[] selectedPopulation = new BoardState[numSelections];
        Random random = new Random();

        for (int i = 0; i < numSelections; i++) {
            // Randomly select individuals for two tournament groups
            int indexGroup1 = random.nextInt(populationSize);
            int indexGroup2 = random.nextInt(populationSize);

            // Choose the individual with the higher fitness score
            if (population[indexGroup1].getFitness() > population[indexGroup2].getFitness()) {
                selectedPopulation[i] = population[indexGroup1];
            } else {
                selectedPopulation[i] = population[indexGroup2];
            }
        }

        return selectedPopulation;
    }
}
