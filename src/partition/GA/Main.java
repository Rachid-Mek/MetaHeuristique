package partition.GA;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int[] set = {4, 5, 6, 7, 8, 9 ,10, 11};
        Genetique genetique = new Genetique(set);
        List<ArrayList<Integer>> result = genetique.solve();
        ArrayList<Integer> s1 = result.get(0);
        ArrayList<Integer> s2 = result.get(1);
        System.out.println("s1 = " + s1);
        System.out.println("s2 = " + s2);
        //affichage de la différence entre les deux ensembles
        int sumS1 = 0;
        int sumS2 = 0;
        for (int i = 0; i < s1.size(); i++) {
            sumS1 += s1.get(i);
        }
        for (int i = 0; i < s2.size(); i++) {
            sumS2 += s2.get(i);
        }
        System.out.println("diff = " + Math.abs(sumS1 - sumS2));
    }
}