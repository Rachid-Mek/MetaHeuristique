package partition.heuristique;
import java.util.*;

public class PartitionAStar {

    public static int depthCost(ArrayList<Integer> S1) {
        return S1.size();
    }
    //la somme minimale nécessaire pour atteindre la solution
    public static int minSumHeuristic(ArrayList<Integer> S, ArrayList<Integer> S1) {
        int sumS1 = 0;
        for (int i : S1) {
            sumS1 += i;
        }
        int sumS2 = 0;
        for (int i : S) {
            if (!S1.contains(i)) {
                sumS2 += i;
            }
        }
        return Math.abs(sumS1 - sumS2);
    }

}