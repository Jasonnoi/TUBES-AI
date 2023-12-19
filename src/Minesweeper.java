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
        BoardState[] initialPopulation = genetic.initializePopulation();

        // Print the initial population
        System.out.println("Initial Population:");
        for (BoardState boardState : initialPopulation) {
            genetic.countFitnessKromosom(boardState, inputBoard, arrIndexValue);
            System.out.println("Score kromosom : " + boardState.getFitness());
            System.out.println();
        }

        System.out.println("Rank Selection Result: ");
        BoardState[] selectedPopulation = genetic.rankSelection(initialPopulation, 2);

        // Print the selected population after tournament selection
        System.out.println("Selected Population after Rank Selection:");
        for (BoardState boardState : selectedPopulation) {
            System.out.println("Score kromosom : " + boardState.getFitness());
            System.out.println();
        }

    }
}
