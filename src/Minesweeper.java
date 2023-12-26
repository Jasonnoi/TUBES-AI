import java.util.*;

public class Minesweeper {
    private static final Scanner sc = new Scanner(System.in);

    public static Hashtable<Integer, Integer> inputBoardValue(Hashtable<Integer, Integer> inputBoard, int boardSize) {
        for (int i = 0; i < boardSize * boardSize; i++) {
            int value = sc.nextInt();
            if (value != -1) {
                inputBoard.put(i + 1, value);
            }
        }
        return inputBoard;
    }

    public static BoardState[] createEvolution(int boardSize, BoardState[] selectedPopulation,
            Hashtable<Integer, Integer> inputBoard, Set<Integer> arrIndexValue, long seed) {
        BoardState[] population = new BoardState[boardSize];
        Genetic genetic = new Genetic(boardSize);

        int[] arr = selectedPopulation[0].getBoard();
        int[] arr2 = selectedPopulation[1].getBoard();

        BoardState[] secondGenartion = genetic.performCrossOver(arr, arr2, seed);
        for (BoardState state : secondGenartion) {
            genetic.countFitnessKromosom(state, inputBoard, arrIndexValue, 0);

        }
        population = secondGenartion;
        return population;
    }

    public static void main(String[] args) {
        System.out.println("Hello, World!");

        int boardSize = sc.nextInt();
        Hashtable<Integer, Integer> inputBoard = new Hashtable<>();
        inputBoard = inputBoardValue(inputBoard, boardSize);
        Set<Integer> arrIndexValue = inputBoard.keySet();
        int solution = arrIndexValue.size();

        // Create an instance of the Genetic class
        Genetic genetic = new Genetic(boardSize);

        // Get the initial population of BoardState objects
        BoardState[] population = genetic.initializePopulation(solution);

        // Print the initial population
        for (BoardState boardState : population) {
            genetic.countFitnessKromosom(boardState, inputBoard, arrIndexValue, 0);
        }
        boolean cond = true;
        int i = 0;
        long startTime = System.currentTimeMillis();
        long seed1 = 17;
        long seed2 = 23;
        while (cond) {
            BoardState[] selectedPopulation = genetic.rankSelection(population, 2, seed1);

            for (BoardState boardState : population) {
                if (boardState.getFitness() >= solution) {
                    System.out.println();
                    boardState.printBoard();
                    System.out.println("KROMOSOM SCORE : " + boardState.getFitness());
                    System.out.println("GENERASI KE " + i);
                    long endTime = System.currentTimeMillis();
                    long elapsedTime = endTime - startTime;
                    System.out.println("Elapsed Time: " + elapsedTime + " milliseconds");
                    System.out.println(seed2);
                    cond = false;


                }
            }
            i++;
            population = createEvolution(boardSize, selectedPopulation, inputBoard, arrIndexValue, seed2);
            seed1++;
            seed2 += 121;
        }

        // Print the selected population after tournament selection

    }
}
