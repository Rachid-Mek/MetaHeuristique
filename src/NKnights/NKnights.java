package NKnights;

import java.util.*;

public class NKnights {
    int n;
    List<List<Integer>> solutions;

    public NKnights(int n) {
        this.n = n;
        this.solutions = new ArrayList<List<Integer>>();
    }

    /**
     * TAkes a chess board and a configuration of knights and returns true if
     * the configuration is valid, i.e no knight attacks another
     * 
     * @param configuration
     * @return
     */
    private boolean isValid(List<Integer> configuration) {
        for (int i = 0; i < configuration.size(); i++) {
            for (int j = i + 1; j < configuration.size(); j++) {
                if (Math.abs(i - j) == Math.abs(configuration.get(i) - configuration.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<List<Integer>> solveDFS() {
        // TODO
        // solutions list
        List<List<Integer>> solutions = new ArrayList<List<Integer>>();
        Stack<List<Integer>> stack = new Stack<List<Integer>>();
        // start with an empty board
        stack.push(new ArrayList<Integer>());
        while (!stack.isEmpty()) {
            List<Integer> current = stack.pop();
            if (!isValid(current))
                continue;
            if (current.size() == this.n) {
                solutions.add(current);
                continue;
            }
            List<Integer> children[] = getChildren(current);
            for (List<Integer> child : children) {
                stack.push(child);
            }
        }
        return solutions;
    }

    private List<Integer>[] getChildren(List<Integer> configuration) {
        int size = this.n - configuration.size();
        // get indecies that are not in the configuration
        List<Integer> indecies = new ArrayList<Integer>();
        for (int i = 0; i < this.n; i++) {
            if (!configuration.contains(i)) {
                indecies.add(i);
            }
        }
        List<Integer>[] children = new List[size];
        for (int i = 0; i < size; i++) {
            List<Integer> child = new ArrayList<Integer>(configuration);
            child.add(indecies.get(i));
            children[i] = child;
        }
        return children;
    }
}
