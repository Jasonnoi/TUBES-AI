import java.util.Arrays;
import java.util.Random;

public class TournamentSelectionExample {
    public static void main(String[] args) {
        // Assuming the scores are provided in an array
        double[] scores = { 3.8511906, 4.375, 4.2083335, 6.4940476, 7.904762, 4.791667, 5.4523816, 5.142857, 6.7440476,
                8.839286 };

        // Tournament selection with 2 groups
        int[] selectedIndices = tournamentSelection(scores, 2);

        // Print the selected indices
        System.out.println("Selected indices: " + Arrays.toString(selectedIndices));
    }

    public static int[] tournamentSelection(double[] scores, int numSelections) {
        int populationSize = scores.length;
        int[] selectedIndices = new int[numSelections];
        Random random = new Random();

        for (int i = 0; i < numSelections; i++) {
            // Randomly select individuals for two tournament groups
            int indexGroup1 = random.nextInt(populationSize);
            int indexGroup2 = random.nextInt(populationSize);

            // Compare the individuals in the two groups and select the one with the higher
            // score
            if (scores[indexGroup1] > scores[indexGroup2]) {
                selectedIndices[i] = indexGroup1;
            } else {
                selectedIndices[i] = indexGroup2;
            }
        }

        return selectedIndices;
    }
}
