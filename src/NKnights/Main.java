package NKnights;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        NKnights nKnights = new NKnights(19);
        List solutionsDFS = nKnights.solveDFS();
        List solutionsBFS = nKnights.solveBFS();
        List solutionsH1 = nKnights.solveH2();
//        System.out.println("Solution using DFS :");
//        solutionsDFS.forEach(System.out::println);
        System.out.println("Solution using BFS :");
        solutionsBFS.forEach(System.out::println);
        System.out.println("Solution using SolveH2:");
        solutionsH1.forEach(System.out::println);

    }
}
