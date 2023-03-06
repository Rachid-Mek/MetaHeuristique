package partition.exactSol;

import java.util.*;

public class PartitionSet {
    List<Integer> set;
    int sum;
    List<Integer> bestSolution;
    int bestDifference;

    PartitionSet(int[] set) {
        this.set = new ArrayList<Integer>();
        for (int i = 0; i < set.length; i++) {
            this.set.add(set[i]);
        }
        this.sum = Arrays.stream(set).sum();
    }

    private Vertex<List<Integer>> constructGraph() {
        // construct states graph
        Vertex<List<Integer>> root = new Vertex<List<Integer>>(new ArrayList<Integer>());
        addNeighbors(root, set);
        return root;
    }

    private void addNeighbors(Vertex<List<Integer>> v, List<Integer> setList) {
        // add neighbors that contains the current element data and one from the set
        if (setList.size() == 0) {
            return;
        }
        for (int i = 0; i < setList.size(); i++) {
            // create a list that contains v.data and the current element
            // copy the data from v.data to list
            List<Integer> list = new ArrayList<Integer>(v.data);
            list.add(setList.get(i));
            // create a new vertex with the list as data
            Vertex<List<Integer>> newVertex = new Vertex<List<Integer>>(list);
            v.addNeighbor(newVertex);
            // add neighbors to the new vertex setList - set[i]
            List<Integer> newSetList = new ArrayList<Integer>(setList);
            newSetList.remove(i);
            addNeighbors(newVertex, newSetList);
        }
    }

    public List<List<Integer>> solve() {
        // construct the graph
        Vertex<List<Integer>> root = constructGraph();
        // search for a solution
        // sum of the set using stream
        bestDifference = sum;
        DFS<List<Integer>, Void> search = new DFS<List<Integer>, Void>(v -> {
            if (isBetterSolution(v.data)) {
                bestSolution = v.data;
                bestDifference = Math.abs(sum - 2 * bestSolution.stream().mapToInt(Integer::intValue).sum());
            }
            return null;
        });

        search.dfs(root);
        // get the second set, i e set - bestSolution. Set may contain duplicates, so
        // remove just the first occurrence
        List<Integer> secondSet = new ArrayList<Integer>(set);
        for (int i = 0; i < bestSolution.size(); i++) {
            secondSet.remove(bestSolution.get(i));
        }
        // return the two sets
        List<List<Integer>> solution = new ArrayList<List<Integer>>();
        solution.add(bestSolution);
        solution.add(secondSet);
        return solution;
    }

    private boolean isBetterSolution(List<Integer> solution) {
        // check if the solution is better than the current best solution
        // it is better if |sum - 2 * sum(solution)| < |sum - 2 * sum(bestSolution)|
        int solutionSum = solution.stream().mapToInt(Integer::intValue).sum();
        return Math.abs(sum - 2 * solutionSum) < bestDifference;
    }
}