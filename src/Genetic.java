import java.util.Random;

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
        System.out.println();

        return boardState;
    }

}
