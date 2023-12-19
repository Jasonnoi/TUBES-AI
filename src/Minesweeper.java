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


    public static void main(String[] args) {
        System.out.println("Hello, World!");

        int boardSize = sc.nextInt();
        Hashtable<Integer, Integer> inputBoard = new Hashtable<>();
        inputBoard = inputBoardValue(inputBoard, boardSize);
        Set<Integer> arrIndexValue = inputBoard.keySet();
        // Create an instance of the Genetic class
        Genetic genetic = new Genetic(boardSize);

        // Get the initial population of BoardState objects
        BoardState[] population = genetic.initializePopulation();

        // Print the initial population
        for (BoardState boardState : population) {
            genetic.countFitnessKromosom(boardState, inputBoard, arrIndexValue);
        }

        BoardState[] selectedPopulation = genetic.rankSelection(population, 2);
        System.out.println();
        // Print the selected population after tournament selection

        for (BoardState boardState : selectedPopulation) {
            boardState.printBoard();
            System.out.println("Score kromosom : " + boardState.getFitness());
            System.out.println();
        }

        int[] arr = selectedPopulation[0].getBoard();
        int[] arr2 = selectedPopulation[1].getBoard();
        genetic.performCrossOver(arr, arr2);

    }
}
