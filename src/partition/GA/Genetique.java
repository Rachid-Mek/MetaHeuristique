package partition.GA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Genetique {
    final int maxGenerations = 10;
    ArrayList<Integer> set;
    private int setSum;

    public Genetique(int[] set) {
        this.setSum = 0;
        this.set = new ArrayList<>();
        for (int i : set) {
            this.set.add(i);
            this.setSum += i;
        }
    }
    public ArrayList<Node> generateInitialPopulation(int m) {
        ArrayList<Node> population = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            ArrayList<Integer> s1 = new ArrayList<>();
            for (Integer integer : set) {
                if (Math.random() < 0.5) {
                    s1.add(integer);
                }
            }
            int fit = fitness(s1);
            population.add(new Node(s1, fit));
        }
        return population;
    }

    // fonction d'évaluation (fitness)
    int fitness(ArrayList<Integer> s1) {
        int sumS1 = 0;
        for (int i : s1) {
            sumS1 += i;
        }
        return Math.abs(this.setSum - 2 * sumS1);
    }

    // sélection
    public ArrayList<Node> selection(ArrayList<Node> population) {
        ArrayList<Node> selected = new ArrayList<>();
        int n = population.size();
        for (Node node : population) {
            int r = (int) (Math.random() * n);
            if (node.f < population.get(r).f) {
                selected.add(node);
            } else {
                selected.add(population.get(r));
            }
        }
        return selected;
    }
    private ArrayList<Integer> setDifference(ArrayList<Integer> s1, ArrayList<Integer> s2) {
        ArrayList<Integer> diff = new ArrayList<>(s1);
        for (int i : s2) {
            diff.removeIf(x -> x == i); // on enlève les éléments de s2 from s1 pour avoir la différence entre les deux
        }
        return diff;
    }
    // croisement
    public Node crossOver(Node parent1, Node parent2) {
        ArrayList<Integer> childS1 = new ArrayList<>();
        int n = parent1.s1.size();
        int crossoverPoint = (int) (Math.random() * n); // point de croisement aléatoire
        for (int i = 0; i < crossoverPoint; i++) {
            childS1.add(parent1.s1.get(i));
        }
        for (int i = crossoverPoint; i < n; i++) {
            childS1.add(parent2.s1.get(i));
        }
        int childFitness = fitness(childS1);
        return new Node(childS1, childFitness);
    }

    // mutation
    public Node mutation(Node child) {
        ArrayList<Integer> childS1 = child.s1;
        ArrayList<Integer> childS2 = setDifference(set, childS1);
        int n = childS1.size();
        int mutationPoint = (int) (Math.random() * n); // point de mutation aléatoire
        int element = childS1.get(mutationPoint);
        childS1.remove(mutationPoint);
        childS2.add(element);
        int childFitness = fitness(childS1);
        return new Node(childS1, childFitness);
    }

}
