package partition.GA;

import java.util.ArrayList;

public class Node {
    ArrayList<Integer> sol;
    int f; // fitness


    public Node(ArrayList<Integer> sol, int f ) {
        this.sol = sol;
        this.f = f; // fitness
    }
}