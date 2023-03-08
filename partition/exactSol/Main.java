package partition.exactSol;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        int sets[][] = { { 31, 10, 20, 19, 4, 3, 6 },
                { 25, 35, 45, 5, 25, 3, 2, 2 },
                { 3, 4, 3, 1, 3, 2, 3, 2, 1 },
                { 2, 10, 3, 8, 5, 7, 9, 5, 3, 2 },
                { 484, 114, 205, 288, 506, 503, 201, 127, 410 },
                { 23, 31, 29, 44, 53, 38, 63, 85, 89, 82 },
                { 771, 121, 281, 854, 885, 734, 486, 1003, 83, 62 },
                { 70, 73, 77, 80, 82, 87, 90, 94, 98, 106, 110, 113, 115, 118, 120 },
                { 382745, 799601, 909247, 729069, 467902, 44328, 34610, 698150, 823460, 903959, 853665, 551830, 610856,
                        670702, 488960, 951111, 323046, 446298, 931161, 31385, 496951, 264724, 224916, 169684 } };
        for (int[] arr : sets) {
            PartitionSet instance = new PartitionSet(arr);
            System.out.print("Set: ");
            printSet(arr);
            System.out.println("\n partition: " + instance.solve().get(0).toString());
            System.out.println("\n Best difference: " + instance.bestDifference + "\nvisited before sol: "
                    + instance.numOfVisitedNodesBeforeSolution + "\ntotal visited: "
                    + instance.numOfVisitedNodes + "\ntime: " + instance.excutionTime.getSeconds());

            System.out.println("\n\n############################\n\n\n");
        }
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

    public static void printSet(int[] a) {
        for (int el : a) {
            System.out.print(el + ",");
        }
    }
}