package partition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Partition {
    public static int[] generateArray(int n, int max) {
        int[] array = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(max) + 1;
        }
        return array;
    }
    public static List<Integer>[] partitionement(int[] S) {
        // on tri notre tableau dans l'ordre croissant avant de faire le partitionnement
        Arrays.sort(S);
        reverse(S);
        List<Integer> S1 = new ArrayList<>();
        List<Integer> S2 = new ArrayList<>();
        int sumS1 = 0, sumS2 = 0;

        //en parcoure le tableau et en fait le partitionement selon la somme des deux sous tableaux

        for (int j : S) {   // pour j = 0 a n
            if (sumS1 <= sumS2) {
                S1.add(j);
                sumS1 += j;
            } else {
                S2.add(j);
                sumS2 += j;
            }
        }
        List<Integer>[] partition = new List[2]; // 2 tableau
        partition[0] = S1;
        partition[1] = S2;
        return partition;
    }
    private static void reverse(int[] arr) {
        for (int i = 0, j=arr.length-1; i<j ; i++, j--) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
    //methode pour afficher la difference entre les deux sous ensembles
    public static int difference(List<Integer> S1, List<Integer> S2) {
        int sumS1 = 0, sumS2 = 0;
        for (int x : S1) {
            sumS1 += x;
        }
        for (int x : S2) {
            sumS2 += x;
        }
        return Math.abs(sumS1 - sumS2);
    }
}

