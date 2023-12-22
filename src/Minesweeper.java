import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

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

    public void creatingGeneration() {

    }

    public static void main(String[] args) {
        System.out.println("SELAMAT DATANG DI AI MINESWEEPER");
        File file = new File("input.txt");
        System.out.println(file);
        try {
            Scanner fileScanner = new Scanner(file);

            int boardSize = 0;
            int row = 0;

            // Baca file untuk menghitung jumlah baris
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (!line.isEmpty()) {
                    row++;
                }
            }

            // Kembalikan fileScanner ke awal
            fileScanner.close();
            fileScanner = new Scanner(file);

            // Baca file untuk menghitung jumlah kolom
            if (fileScanner.hasNextLine()) {
                String firstLine = fileScanner.nextLine();
                String[] values = firstLine.split(" ");
                boardSize = values.length;
            }

            // Cetak hasil deteksi boardSize
            System.out.println("Deteksi boardSize: " + boardSize);

            // Membaca inputBoard
            Hashtable<Integer, Integer> inputBoard = new Hashtable<>();
            Set<Integer> arrIndexValue;

            for (int i = 0; i < row; i++) {
                String line = fileScanner.nextLine();
                String[] values = line.split(" ");

                for (int j = 0; j < boardSize; j++) {
                    int value = Integer.parseInt(values[j]);
                    if (value != -1) {
                        inputBoard.put(i * boardSize + j + 1, value);
                    }
                }
            }

            // Mencetak inputBoard
            System.out.println("Input Board:");
            for (int key : inputBoard.keySet()) {
                System.out.println(key + ": " + inputBoard.get(key));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // int boardSize = sc.nextInt();
        // Hashtable<Integer, Integer> inputBoard = new Hashtable<>();
        // inputBoard = inputBoardValue(inputBoard, boardSize);
        // Set<Integer> arrIndexValue = inputBoard.keySet();
        // int solution = arrIndexValue.size();

        // // Create an instance of the Genetic class
        // Genetic genetic = new Genetic(boardSize);

        // // Get the initial population of BoardState objects
        // BoardState[] population = genetic.initializePopulation(solution);

        // // Print the initial population
        // for (BoardState boardState : population) {
        // genetic.countFitnessKromosom(boardState, inputBoard, arrIndexValue, 0);
        // }
        // boolean cond = true;
        // int i = 0;
        // long startTime = System.currentTimeMillis();
        // long seed1 = 17;
        // long seed2 = 23;
        // while (cond) {
        // BoardState[] selectedPopulation = genetic.rankSelection(population, 2,
        // seed1);

        // for (BoardState boardState : population) {
        // if (boardState.getFitness() >= solution) {
        // System.out.println();
        // boardState.printBoard();
        // System.out.println("KROMOSOM SCORE : " + boardState.getFitness());
        // System.out.println("GENERASI KE " + i);
        // long endTime = System.currentTimeMillis();
        // long elapsedTime = endTime - startTime;
        // System.out.println("Elapsed Time: " + elapsedTime + " milliseconds");
        // System.out.println(seed2);
        // cond = false;

        // }
        // }
        // i++;
        // population = createEvolution(boardSize, selectedPopulation, inputBoard,
        // arrIndexValue, seed2);
        // seed1++;
        // seed2 *= 7;
        // }

        // Print the selected population after tournament selection

    }
}
