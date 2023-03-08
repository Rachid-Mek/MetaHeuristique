package partition.exactSol;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        int arr[] = new int[] { 70, 73, 77, 80, 82, 87, 90, 94, 98, 106, 110, 113, 115, 118, 120 };
        // int arr[] = generateArray(8, 1000);
        PartitionSet partitionSet = new PartitionSet(arr);
        System.out.println(partitionSet.solve().toString());
        System.out.println("Best difference: " + partitionSet.bestDifference + " visited "
                + partitionSet.numOfVisitedNodesBeforeSolution);

        // for (int i = 5; i < 10; i++) {
        // int set[] = generateArray(i, 1000);
        // testSet(set);
        // System.out.println("\n\n");
        // }
    }

    public static int[] generateArray(int n, int max) {
        int[] array = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(max) + 1;
        }
        return array;
    }

    public static void testSet(int[] arr) {
        PartitionSet partitionSet = new PartitionSet(arr);
        System.out.println("Set: " + partitionSet.set.toString());
        System.out.println(partitionSet.solve().toString());
        System.out.println("Best difference: " + partitionSet.bestDifference);
    }
}