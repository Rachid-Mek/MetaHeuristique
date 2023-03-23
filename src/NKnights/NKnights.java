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
     * the configuration is valid, i_e no knight attacks another
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
    public List<List<Integer>> solveBFS() {
        // TODO
        // solutions list
        List<List<Integer>> solutions = new ArrayList<List<Integer>>();
        Queue<List<Integer>> queue = new LinkedList<List<Integer>>();
        // start with an empty board
        queue.offer(new ArrayList<Integer>());
        while (!queue.isEmpty()) {
            List<Integer> current = queue.poll();
            if (!isValid(current))
                continue;
            if (current.size() == this.n) {
                solutions.add(current);
                continue;
            }
            List<Integer> children[] = getChildren(current);
            for (List<Integer> child : children) {
                queue.offer(child);
            }
        }
        return solutions;
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

    // solveH1 est une solution qui utilise une heuristique qui calcule le nombre de conflits entre les pions
    public List<List<Integer>> solveH1() {
        // solutions list
        List<List<Integer>> solutions = new ArrayList<List<Integer>>();
        PriorityQueue<List<Integer>> queue = new PriorityQueue<List<Integer>>(Comparator.comparingInt(this::f1));
        // start with an empty board
        queue.offer(new ArrayList<Integer>());
        while (!queue.isEmpty()) {
            List<Integer> current = queue.poll();
            if (!isValid(current))
                continue;
            if (current.size() == this.n) {
                solutions.add(current);
                continue;
            }
            List<Integer> children[] = getChildren(current);
            for (List<Integer> child : children) {
                queue.offer(child);
            }
        }
        return solutions;
    }

    int g(List<Integer> configuration) {
        return configuration.size();
    }

    // nombre de conflits entre les reines pour une configuration donnée
    int h1(List<Integer> configuration) {
        int conflit = 0;
        for (int i = 0; i < configuration.size(); i++) {
            for (int j = i + 1; j < configuration.size(); j++) {
                if (Math.abs(i - j) == Math.abs(configuration.get(i) - configuration.get(j))
                        || Objects.equals(configuration.get(i), configuration.get(j))) {
                    conflit++;
                }
            }
        }
        return conflit;
    }
    int f1(List<Integer> configuration) {
        return g(configuration) + h1(configuration);
    }

    // suggest another heuristic function h2 to use it in solveH2 that is admissible with comments and explain why it is admissible

    // h2 est une heuristique qui calcule le nombre de cases attaquées par les pions
    int h2(List<Integer> configuration) {
        int menace = 0;
        for (int i = 0; i < configuration.size(); i++) {
            for (int j = i + 1; j < configuration.size(); j++) {
                if (Math.abs(i - j) == Math.abs(configuration.get(i) - configuration.get(j))) {
                    menace++;
                }
            }
        }
        return menace;
    }


    int f2(List<Integer> configuration) {
        return g(configuration) + h2(configuration);
    }

    public List<List<Integer>> solveH2() {
        // solutions list
        List<List<Integer>> solutions = new ArrayList<List<Integer>>();
        PriorityQueue<List<Integer>> queue = new PriorityQueue<List<Integer>>(Comparator.comparingInt(this::f2));
        // start with an empty board
        queue.offer(new ArrayList<Integer>());
        while (!queue.isEmpty()) {
            List<Integer> current = queue.poll();
            if (!isValid(current))
                continue;
            if (current.size() == this.n) {
                solutions.add(current);
                continue;
            }
            List<Integer> children[] = getChildren(current);
            for (List<Integer> child : children) {
                queue.offer(child);
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
