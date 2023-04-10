package partition.GA;

import java.util.ArrayList;

public class Node {
    ArrayList<Integer> s1;
    int f; // fitness


    public Node(ArrayList<Integer> s1, int f ) {
        this.s1 = s1;
        this.f = f;
    }
}