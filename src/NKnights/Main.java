package NKnights;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        NKnights nKnights = new NKnights(4);
        List solutionsDFS = nKnights.solveDFS();
        List solutionsBFS = nKnights.solveBFS();
        System.out.println("Solution using DFS :");
        solutionsDFS.forEach(System.out::println);
        System.out.println("Solution using BFS :");
        solutionsBFS.forEach(System.out::println);

    }
}
