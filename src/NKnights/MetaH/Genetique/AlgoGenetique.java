package NKnights.MetaH.Genetique;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class AlgoGenetique {

    private int n; // nombre de reines
    private int populationSize; // taille de la population
    private int maxGenerations; // nombre maximum de générations
    private double mutationRate; // taux de mutation
    private Random random;

    public AlgoGenetique(int n, int populationSize, int maxGenerations, double mutationRate) {
        this.n = n;
        this.populationSize = populationSize;
        this.maxGenerations = maxGenerations;
        this.mutationRate = mutationRate;
        this.random = new Random();
    }

    public ArrayList<Node>  generatePopulation() {
        ArrayList<Node> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            ArrayList<Integer> sol = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                sol.add((int) (Math.random() * n)); // Valeur aléatoire
            }
            int fitness = fitness(sol);
            population.add(new Node(sol, fitness));
        }
        return population;
    }

    private int fitness(ArrayList<Integer> sol) {
        int conflit = 0;
        for (int i = 0; i < sol.size(); i++) {
            for (int j = i + 1; j < sol.size(); j++) {
                if (sol.get(i) == sol.get(j)) {
                    conflit++;
                }
                if (Math.abs(sol.get(i) - sol.get(j)) == Math.abs(i - j)) {
                    conflit++;
                }
            }
        }
        return conflit;
    }

    private ArrayList<Node> selection(ArrayList<Node> population) {
        ArrayList<Node> selectedPopulation = new ArrayList<>();
        int n = population.size(); // taille de la population
        int k = (int) Math.round(n * 0.2); // nombre d'individus à sélectionner
        population.sort(new Comparator<Node>() { //on trie la population selon la fonction d'évaluation
            @Override
            public int compare(Node o1, Node o2) {
                return Integer.compare(o1.f, o2.f);
            }
        });
        for (int i = 0; i < k; i++) {
            selectedPopulation.add(population.get(i));
        }
        return selectedPopulation;
    }

    private ArrayList<Node> crossover(ArrayList<Node> population) {
        ArrayList<Node> newPopulation = new ArrayList<>();
        for (int i = 0; i < population.size(); i++) {
            for (int j = i + 1; j < population.size(); j++) {
                ArrayList<Integer> sol1 = population.get(i).sol;
                ArrayList<Integer> sol2 = population.get(j).sol;
                int index = random.nextInt(n);
                ArrayList<Integer> newSol1 = new ArrayList<>();
                ArrayList<Integer> newSol2 = new ArrayList<>();
                for (int k = 0; k < index; k++) {
                    newSol1.add(sol1.get(k));
                    newSol2.add(sol2.get(k));
                }
                for (int k = index; k < n; k++) {
                    newSol1.add(sol2.get(k));
                    newSol2.add(sol1.get(k));
                }
                newPopulation.add(new Node(newSol1, fitness(newSol1)));
                newPopulation.add(new Node(newSol2, fitness(newSol2)));
            }
        }
        return newPopulation;
    }

    private ArrayList<Node> mutation(ArrayList<Node> population) {
        ArrayList<Node> newPopulation = new ArrayList<>();
        for (Node node : population) {
            ArrayList<Integer> sol = node.sol;
            for (int j = 0; j < sol.size(); j++) {
                if (Math.random() < mutationRate) {
                    sol.set(j, (int) (Math.random() * n));
                }
            }
            newPopulation.add(new Node(sol, fitness(sol)));
        }
        return newPopulation;
    }

    private ArrayList<Node> replace(ArrayList<Node> population, ArrayList<Node> newPopulation) {
        population.addAll(newPopulation);
        population.sort(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Integer.compare(o1.f, o2.f);
            }
        });
        population.subList(populationSize, population.size()).clear();
        return population;
    }

    //selectioner le meilleur selon le fitness
    private Node getBestSol(ArrayList<Node> population) {
        Node bestSol = population.get(0);
        for (Node sol : population) {
            if (sol.f < bestSol.f) {
                bestSol = sol;
            }
        }
        return bestSol;
    }

    public void solve() {
        ArrayList<Node> population = generatePopulation();
        int generation = 0;
        while (generation < maxGenerations) {
            if (getBestSol(population).f == 0) {
                Node best = getBestSol(population);
                System.out.println("Solution : " + best.sol);
                System.out.println("Conflict : " + best.f);
                return;
            }
            ArrayList<Node> selectedPopulation = selection(population);
            ArrayList<Node> newPopulation = crossover(selectedPopulation);
            newPopulation = mutation(newPopulation);
            // On garde seulement les meilleurs individus
            population = replace(population, newPopulation);
            generation++;
        }
        Node best = getBestSol(population);
        System.out.println("Solution : " + best.sol);
        System.out.println("Conflict : " + best.f);
    }

    //variation de parametre et affichage de la solution + fitness + temps d'execution
    public static void variateParameters(){
        int[] sizeBoard ={30,50};
        int[] sizePopulation ={300,500};
        int[] maxGenerations ={1000,2000};
        double[] mutationRate ={0.3,0.5};
        for (int value : sizeBoard) {
            for (int i : sizePopulation) {
                for (int maxGeneration : maxGenerations) {
                    for (double v : mutationRate) {
                        AlgoGenetique algoGenetique = new AlgoGenetique(value, i, maxGeneration, v);
                        long startTime = System.currentTimeMillis();
                        algoGenetique.solve();
                        long endTime = System.currentTimeMillis();
                        System.out.println("Temps d'execution : " + (endTime - startTime) + " ms");
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        AlgoGenetique algoGenetique = new AlgoGenetique(50, 1000, 1000, 0.3);
        long startTime = System.currentTimeMillis();
        algoGenetique.solve();
        long endTime = System.currentTimeMillis();
        System.out.println("Temps d'execution : " + (endTime - startTime) + " ms");
    }

    public int conflict(ArrayList<Integer> sol) {
        int conflit = 0;
        for (int i = 0; i < sol.size(); i++) {
            for (int j = i + 1; j < sol.size(); j++) {
                if (sol.get(i) == sol.get(j)) {
                    conflit++;
                }
                else if (Math.abs(sol.get(i) - sol.get(j)) == Math.abs(i - j)) {
                    conflit++;
                }
            }
        }
        return conflit;
    }

}