package NKnights.MetaH.PSO;

import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

public class Pso {
    private static int n; // Taille du problème
    private int nbIterations; // Nombre d'itérations à effectuer
    private int nbParticles; // Nombre de particules
    private double c1; // Facteur d'attraction de la particule envers sa propre meilleure position
    private double c2; // Facteur d'attraction de la particule envers la meilleure position globale
    private double w; // Facteur d'inertie
    private int[] bestPosition; // Meilleure position globale trouvée
    private int bestFitness; // Meilleure fitness trouvée
    private ArrayList<Particle> swarm; // Ensemble des particules
    private Random rand; // Générateur de nombres aléatoires

    public Pso(int n, int nbIterations, int nbParticles, double c1, double c2, double w) {
        Pso.n = n;
        this.nbIterations = nbIterations;
        this.nbParticles = nbParticles;
        this.c1 = c1;
        this.c2 = c2;
        this.w = w;
        this.bestPosition = new int[n];
        this.bestFitness = Integer.MAX_VALUE;
        this.swarm = new ArrayList<Particle>();
        this.rand = new Random();
    }

    // Initialise la position des particules aléatoirement
    public void initializeSwarm() {
        for (int i = 0; i < nbParticles; i++) {
            Particle p = new Particle(n);
            swarm.add(p);
            // Mise à jour des paramètres de la particule
            p.setFitness(computeFitness(p.getPosition()));
            p.setBestPosition(p.getPosition().clone());
            p.setBestFitness(p.getFitness());
        }
    }

    // Évaluation de fitness (nombre de conflits) d'une position
    public static int computeFitness(int[] position) {
        int fitness = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (position[i] == position[j] || Math.abs(position[i] - position[j]) == j - i) {
                    fitness++;
                }
            }
        }
        return fitness;
    }

    // Met à jour la meilleure position globale trouvée
    public void updateBestPosition() {
        for (Particle p : swarm) {
            if (p.getBestFitness() < bestFitness) {
                bestFitness = p.getBestFitness();
                bestPosition = p.getBestPosition().clone();
            }
        }
    }

    public void updateVelocity(Particle p) {
        double[] newVelocity = new double[n];
        double r1 = rand.nextDouble();
        double r2 = rand.nextDouble();
        for (int i = 0; i < n; i++) {
            double cognitiveComponent = c1 * r1 * (p.getBestPosition()[i] - p.getPosition()[i]);
            double socialComponent = c2 * r2 * (bestPosition[i] - p.getPosition()[i]);
            newVelocity[i] = w * p.getVelocity()[i] + cognitiveComponent + socialComponent;
        }
        p.setVelocity(newVelocity);
    }

    public void updatePosition(Particle p) {
        int[] newPosition = new int[n];
        for (int i = 0; i < n; i++) {
            newPosition[i] = (int) Math.round(p.getPosition()[i] + p.getVelocity()[i]);
        }
            for (int i = 0; i < n; i++) {
                if (newPosition[i] < 0) {
                    newPosition[i] = 0;
                } else if (newPosition[i] > n - 1) {
                    newPosition[i] = n - 1;
                }
            }
            hillClimbing(p);
}
    private void hillClimbing(Particle p){
        int[] newPosition = Arrays.copyOf(p.getPosition(), n);
        int currentFitness = computeFitness(newPosition);
        int bestFitness = currentFitness;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == newPosition[i]) continue;

                int[] neighborPosition = Arrays.copyOf(newPosition, n);
                neighborPosition[i] = j;
                int neighborFitness = computeFitness(neighborPosition);

                if (neighborFitness < bestFitness) {
                    bestFitness = neighborFitness;
                    newPosition = neighborPosition;
                }
            }
        }
        // Update the position of the particle
        p.setPosition(newPosition);
        // Update the best position of the particle
        if (bestFitness < p.getBestFitness()) {
            p.setBestFitness(bestFitness);
            p.setBestPosition(newPosition.clone());
        }
    }

    // Exécute l'algorithme PSO
    public void solvePso() {
        // Initialize the swarm
        initializeSwarm();

        // Main loop of the algorithm
        for (int i = 0; i < nbIterations; i++) {
            // Update the best position found so far (global best)
            updateBestPosition();
            // Update the position and velocity of each particle
            for (Particle p : swarm) {
                // Solution found
                if (Pso.CountConflict(p.getBestPosition()) == 0) {
                    System.out.println("Solution found: " + Arrays.toString(bestPosition));
                    System.out.println("Conflicts: " + Pso.CountConflict(bestPosition));
                    return;
                }
                // Update the velocity of the particle
                 updateVelocity(p);

                //update the position of the particle
                updatePosition(p);
                // si la nouvelle position est meilleure que la meilleure position de la particule
               if(bestFitness < p.getBestFitness()){
                   p.setBestFitness(bestFitness);
                   p.setBestPosition(p.getPosition().clone());
               }
            }
            System.out.println("Iteration " + i + " best fitness: " + bestFitness);
        }
    }
    // Retourne la meilleure fitness trouvée
    public int getBestFitness() {
        return bestFitness;
    }

    public static void performSensitivityAnalysis(int boardSize) {
        // Set the parameter ranges to explore
        int[] numIterations = {100, 500, 1000, 5000};
        int[] numParticles = {20, 50, 100};
        double[] c1 = {1.0, 1.5, 2.0};
        double[] c2 = {1.0, 1.5, 2.0};
        double[] w = {0.4, 0.7, 1.0};

        // Initialize the best fitness and corresponding parameters
        double bestFitness = Double.POSITIVE_INFINITY;
        int bestIterations = 0;
        int bestParticles = 0;
        double bestC1 = 0.0;
        double bestC2 = 0.0;
        double bestW = 0.0;

        // Loop over all parameter combinations
        for (int i = 0; i < numIterations.length; i++) {
            for (int j = 0; j < numParticles.length; j++) {
                for (int k = 0; k < c1.length; k++) {
                    for (int l = 0; l < c2.length; l++) {
                        for (int m = 0; m < w.length; m++) {
                            // Create a new PSO instance with the current parameters
                            Pso pso = new Pso(boardSize, numIterations[i], numParticles[j], c1[k], c2[l], w[m]);

                            // Run the PSO algorithm
                            pso.solvePso();

                            // Check if the current solution is better than the previous best
                            if (pso.getBestFitness() < bestFitness) {
                                bestFitness = pso.getBestFitness();
                                bestIterations = numIterations[i];
                                bestParticles = numParticles[j];
                                bestC1 = c1[k];
                                bestC2 = c2[l];
                                bestW = w[m];
                            }
                        }
                    }
                }
            }
        }

        // Print the best parameters and corresponding fitness
        System.out.println("Best parameters found: ");
        System.out.println("Number of iterations: " + bestIterations);
        System.out.println("Number of particles: " + bestParticles);
        System.out.println("Acceleration constant c1: " + bestC1);
        System.out.println("Acceleration constant c2: " + bestC2);
        System.out.println("Inertia weight w: " + bestW);
        System.out.println("Best fitness: " + bestFitness);
    }


    public static void main(String[] args) {

        // Set the problem parameters
        int boardSize = 40;
        int numIterations = 500;
        int numParticles =300;
        double c1 =2;
        double c2 = 2;
        double w = 0.9;


        long startTime = System.currentTimeMillis();
        // Create a PSO instance
        Pso pso = new Pso(boardSize, numIterations, numParticles, c1, c2, w);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;


        // Run the PSO algorithm
        pso.solvePso();
        System.out.println("Execution time: " + executionTime + " ms");

    }
    public static int CountConflict(int[] position) {
        int conflict = 0;
        for (int i = 0; i < position.length; i++) {
            for (int j = i + 1; j < position.length; j++) {
                if (position[i] == position[j]) {
                    conflict++;
                }
                if (Math.abs(position[i] - position[j]) == Math.abs(i - j)) {
                    conflict++;
                }
            }
        }
        return conflict;
    }

}