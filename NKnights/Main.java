package NKnights;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        NKnights nKnights = new NKnights(5);
        List solutions = nKnights.solveDFS();
        solutions.forEach(System.out::println);
    }
}
