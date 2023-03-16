package partition.heuristique;

import java.util.*;

class Node {
    ArrayList<Integer> s1;
    int f, h;

    public Node(ArrayList<Integer> s1, int f, int h) {
        this.s1 = s1;
        this.f = f;
        this.h = h;
    }
}

public class PartitionAStar {
    ArrayList<Integer> set;
    private int setSum;
    private PriorityQueue<Node> nonVisitedNodes = new PriorityQueue<Node>((n1, n2) -> Integer.compare(n1.f, n2.f));

    public PartitionAStar(int[] set) {
        this.setSum = 0;
        this.set = new ArrayList<Integer>();
        for (int i : set) {
            this.set.add(i);
            this.setSum += i;
        }
    }

    public List<ArrayList<Integer>> partition() {
        // initial state S1 = [], S2 = set
        ArrayList<Integer> initalS1 = new ArrayList<Integer>();
        Node initialState = new Node(initalS1, f(initalS1), h(initalS1));
        nonVisitedNodes.add(initialState);
        while (!nonVisitedNodes.isEmpty()) {
            Node current = nonVisitedNodes.poll();
            ArrayList<Integer> successors[] = generateSuccessors(current.s1);
            for (ArrayList<Integer> s : successors) {
                Node successor = new Node(s, f(s), h(s));
                nonVisitedNodes.add(successor);
            }
            if (isFinalState(current)) {
                return new ArrayList<ArrayList<Integer>>(Arrays.asList(current.s1, setDifference(set, current.s1)));
            }
        }
        return null;
    }

    public static int g(ArrayList<Integer> s1) {
        return s1.size();
        // int sumS1 = 0;
        // for (int i : s1) {
        // sumS1 += i;
        // }
        // return sumS1;
    }

    // la somme minimale nécessaire pour atteindre la solution
    public int h(ArrayList<Integer> s1) {
        int sumS1 = 0;
        for (int i : s1) {
            sumS1 += i;
        }
        return Math.abs(this.setSum - 2 * sumS1);
    }

    public int f(ArrayList<Integer> s1) {
        return g(s1) + h(s1);
    }

    public boolean isFinalState(Node currentState) {
        Node nextState = nonVisitedNodes.peek();
        // TODO: check this
        if (nextState == null) {
            return true;
        }
        if (currentState.h < nextState.h) {
            return true;
        }
        return false;
    }

    // set difference method
    private ArrayList<Integer> setDifference(ArrayList<Integer> s1, ArrayList<Integer> s2) {
        ArrayList<Integer> diff = new ArrayList<Integer>(s1);
        for (int i : s2) {
            diff.removeIf(x -> x == i);
        }
        return diff;
    }

    public ArrayList<Integer>[] generateSuccessors(ArrayList<Integer> s) {
        // get the difference between set and s
        ArrayList<Integer> diff = setDifference(set, s);
        // add elements of diff to s to get all successors
        // int l[] = new List[diff.size()];
        ArrayList<Integer>[] successors = new ArrayList[diff.size()];
        for (int i = 0; i < diff.size(); i++) {
            ArrayList<Integer> newS = new ArrayList<Integer>(s);
            newS.add(diff.get(i));
            successors[i] = newS;
        }

        return successors;
    }
}