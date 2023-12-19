import java.util.Arrays;
import java.util.Random;

public class Crossover {

    public static void main(String[] args) {
        int[] chromosome1 = { 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1 };
        int[] chromosome2 = { 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0 };

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
    public static void onePointCrossover(int[] chromosome1, int[] chromosome2, int crossoverPoint) {
        for (int i = crossoverPoint; i < chromosome1.length; i++) {
            int temp = chromosome1[i];
            chromosome1[i] = chromosome2[i];
            chromosome2[i] = temp;
        }
    }

    // Mutation
    public static void mutate(int[] chromosome, int index) {
        chromosome[index] = 1 - chromosome[index]; // Flip the bit (0 to 1 or 1 to 0)
    }
}
