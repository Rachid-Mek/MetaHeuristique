package partition.GA;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int[] set = {1, 2, 3,2, 4, 5, 6, 7, 8, 9, 10};
        Genetique genetique = new Genetique(set);
        List<ArrayList<Integer>> result = genetique.solve();
        ArrayList<Integer> s1 = result.get(0);
        ArrayList<Integer> s2 = result.get(1);
        System.out.println("s1 = " + s1);
        System.out.println("s2 = " + s2);
        System.out.println("Diff : " +genetique.fitness(s1));
    }
}