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
        Set<Integer> arr = inputBoard.keySet();
        for (int index : arr) {
            System.out.println(index);
        }
        // Create an instance of the Genetic class
        Genetic genetic = new Genetic(boardSize);

        // Get the initial population of BoardState objects
        BoardState[] initialPopulation = genetic.initializePopulation();

        // Print the initial population
        System.out.println("Initial Population:");
        for (BoardState boardState : initialPopulation) {
            boardState.printBoard();
            System.out.println();
        }
    }
}
