package partition.GA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Genetique {
    final int maxGenerations = 1000;
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
    public ArrayList<Node> selection(ArrayList<Node> population) {
        ArrayList<Node> selectedPopulation = new ArrayList<>();
        int totalFitness = 0;
        for (Node node : population) {
            totalFitness += node.f;
        }
        while (selectedPopulation.size() < population.size()) {
            int r = (int) (Math.random() * totalFitness);
            int sum = 0;
            for (Node node : population) {
                sum += node.f;
                if (sum >= r) {
                    selectedPopulation.add(node);
                    break;
                }
            }
        }
        return selectedPopulation;
    }

    // croisement
    public Node crossOver(Node parent1, Node parent2) {
        ArrayList<Integer> childSol = new ArrayList<>();
        int n = parent1.sol.size();
        int crossoverPoint = (int) (Math.random() * n); // point de croisement aléatoire
        for (int i = 0; i < crossoverPoint; i++) {
            childSol.add(parent1.sol.get(i));
        }
        for (int i = crossoverPoint; i < n; i++) {
            childSol.add(parent2.sol.get(i));
        }
        int childFitness = fitness(childSol);
        return new Node(childSol, childFitness);
    }

    // mutation
    public Node mutation(Node node) {
        ArrayList<Integer> sol = node.sol;
        int size = sol.size();
        int mutationIndex = (int) (Math.random() * size); // Index aléatoire à muter
        int mutatedValue = sol.get(mutationIndex) == 0 ? 1 : 0; // Inversion de la valeur à l'index
        sol.set(mutationIndex, mutatedValue);
        int fitness = fitness(sol);
        return new Node(sol, fitness);
    }

    //remplacement de la population en prend que les meilleurs individus de la population par rapport au fitness

    public ArrayList<Node> remplacement(ArrayList<Node> population, ArrayList<Node> newPopulation) {
        // Combinez les deux listes triées par ordre croissant de fitness
        ArrayList<Node> combined = new ArrayList<>();
        combined.addAll(population);
        combined.addAll(newPopulation);
        Collections.sort(combined, Comparator.comparingInt(n -> n.f)); // Tri par ordre croissant de fitness
        // Sélectionnez les N meilleurs individus
        int n = population.size();
        ArrayList<Node> selected = new ArrayList<>(combined.subList(0, n));
        return selected;
    }
    // fonction principale qui retourn la solution sol et s2 en utilisant les fonctions précédentes

    public ArrayList<Integer> getBestSolution(ArrayList<Node> population) {
        int min = Integer.MAX_VALUE;
        ArrayList<Integer> bestSolution = new ArrayList<>();
        for (Node node : population) {
            if (node.f < min) {
                min = node.f;
                bestSolution = node.sol;
            }
        }
        return bestSolution;
    }

    public List<ArrayList<Integer>> solve() {
        // Génération de la population initiale
        ArrayList<Node> population = generateInitialPopulation(100);
        int generation = 0;
        while (generation < maxGenerations) {
            // Sélection des meilleurs individus
            population = selection(population);

            // Création d'une nouvelle population à partir de croisements et mutations
            ArrayList<Node> newPopulation = new ArrayList<>();
            for (int i = 0; i < population.size(); i++) {
                Node parent1 = population.get(i);
                Node parent2 = population.get((i + 1) % population.size());
                Node child = crossOver(parent1, parent2);
                child = mutation(child);
                newPopulation.add(child);
            }

            // Remplacement de l'ancienne population par la nouvelle
            population = remplacement(population, newPopulation);
            generation++;
        }
        ArrayList<Integer> bestSol = getBestSolution(population);
        ArrayList<Integer> s1 = new ArrayList<>();
        ArrayList<Integer> s2 = new ArrayList<>();
        for (int i = 0; i < set.size(); i++) {
            if (bestSol.get(i) == 0) {
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
