package partition.GA;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Genetique {
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
            ArrayList<Integer> sol = new ArrayList<>();
            for (int j = 0; j < set.size(); j++) {
                sol.add((int) (Math.random() * 2)); // Valeur aléatoire 0 ou 1
            }
            int fitness = fitness(sol);
            population.add(new Node(sol, fitness));
        }
        return population;
    }


    // fonction d'évaluation (fitness)
    public int fitness(ArrayList<Integer> sol) {
        int sumS1 = 0;
        int sumS2 = 0;
        for (int i = 0; i < sol.size(); i++) {
            if (sol.get(i) == 0) {
                sumS1 += set.get(i);
            } else {
                sumS2 += set.get(i);
            }
        }
        return Math.abs(sumS1 - sumS2);
    }

    // sélection
    public ArrayList<Node> selection(double rate, ArrayList<Node> population) {
        ArrayList<Node> selectedPopulation = new ArrayList<>();
        int n = population.size();
        int k = (int) Math.round(n * rate);
        int minFitness = Integer.MAX_VALUE;
        Node selectedNode = null;
        for (Node node : population) {
            if (node.f < minFitness) {
                minFitness = node.f;
                selectedNode = node;
            }
        }

        if (selectedNode != null) {
            selectedPopulation.add(selectedNode);
        }

        return selectedPopulation;
    }

    // croisement
    public ArrayList<Node> crossOver(Node parent1, Node parent2) {
        int n = parent1.sol.size();
        int crossoverPoint = (int) (Math.random() * (n - 1)) + 1; // Choix aléatoire du point de croisement
        ArrayList<Integer> child1Sol = new ArrayList<>();
        ArrayList<Integer> child2Sol = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (i < crossoverPoint) {
                child1Sol.add(parent1.sol.get(i));
                child2Sol.add(parent2.sol.get(i));
            } else {
                child1Sol.add(parent2.sol.get(i));
                child2Sol.add(parent1.sol.get(i));
            }
        }
        Node child1 = new Node(child1Sol, fitness(child1Sol));
        Node child2 = new Node(child2Sol, fitness(child2Sol));
        ArrayList<Node> children = new ArrayList<>();
        children.add(child1);
        children.add(child2);
        return children;
    }

    // mutation
    public Node mutation(Node node) {
        ArrayList<Integer> newSol = new ArrayList<>(node.sol);
        for (int i = 0; i < newSol.size(); i++) {
            newSol.set(i, 1 - newSol.get(i)); // Inversion de la valeur du bit
        }
        Node mutatedNode = new Node(newSol, fitness(newSol));
        return mutatedNode;
    }

    //remplacement de la population en prend que les meilleurs individus de la population par rapport au fitness

    public ArrayList<Node> replacement(ArrayList<Node> population, ArrayList<Node> children) {
        ArrayList<Node> mergedPopulation = new ArrayList<>();
        mergedPopulation.addAll(population);
        mergedPopulation.addAll(children);

    // Tri de la population fusionnée par ordre croissant de fitness
        mergedPopulation.sort(new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) {
            return Integer.compare(o1.f, o2.f);
        }
    });

    // Sélection des meilleurs individus pour la prochaine génération
    ArrayList<Node> newPopulation = new ArrayList<>();
        for (int i = 0; i < population.size(); i++) {
        newPopulation.add(mergedPopulation.get(i));
    }

        return newPopulation;
}
    // fonction principale qui retourn la solution sol et s2 en utilisant les fonctions précédentes

    public Node getBestSolution(ArrayList<Node> population) {
        Node bestSolution = population.get(0);
        for (int i = 1; i < population.size(); i++) {
            if (population.get(i).f < bestSolution.f) {
                bestSolution = population.get(i);
            }
        }
        return bestSolution;
    }

    public List<ArrayList<Integer>> solve() {
        int m = 100; // Taille de la population
        double rate = 0.6; // Taux de sélection
        int maxIterations = 1000; // Nombre d'itérations
        ArrayList<Node> population = generateInitialPopulation(m);
        for (int i = 0; i < maxIterations; i++) {
            ArrayList<Node> selectedPopulation = selection(rate, population);
            ArrayList<Node> children = new ArrayList<>();
            for (int j = 0; j < selectedPopulation.size(); j++) {
                Node parent1 = selectedPopulation.get(j);
                Node parent2 = selectedPopulation.get((j + 1) % selectedPopulation.size());
                ArrayList<Node> childrenOfParents = crossOver(parent1, parent2);
                children.addAll(childrenOfParents);
            }
            for (int j = 0; j < children.size(); j++) {
                children.set(j, mutation(children.get(j)));
            }
            population = replacement(population, children);
        }
        Node bestSolution = getBestSolution(population);
        ArrayList<Integer> s1 = new ArrayList<>();
        ArrayList<Integer> s2 = new ArrayList<>();
        for (int i = 0; i < bestSolution.sol.size(); i++) {
            if (bestSolution.sol.get(i) == 0) {
                s1.add(set.get(i));
            } else {
                s2.add(set.get(i));
            }
        }
        List<ArrayList<Integer>> result = new ArrayList<>();
        result.add(s1);
        result.add(s2);
        return result;
    }
}
