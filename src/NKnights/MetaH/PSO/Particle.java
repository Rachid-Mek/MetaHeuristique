package NKnights.MetaH.PSO;

import java.util.Random;

public class Particle {
    private int[] position; // Position de la particule
    private int[] bestPosition; // Meilleure position trouvée par la particule (Pbest)
    private int fitness; // Fitness de la position actuelle de la particule
    private int bestFitness; // Meilleure fitness trouvée par la particule
    private double[] velocity; // Vitesse de la particule

    public Particle(int n) {
        this.position = new int[n];
        this.bestPosition = new int[n];
        this.fitness = Integer.MAX_VALUE;
        this.bestFitness = Integer.MAX_VALUE;
        this.velocity = new double[n];
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            position[i] = rand.nextInt(n);
            velocity[i] = rand.nextDouble(n);
        }
    }

    // Retourne la position de la particule
    public int[] getPosition() {
        return position;
    }

    // Modifie la position de la particule
    public void setPosition(int[] position) {
        this.position = position;
    }

    // Retourne la meilleure position trouvée par la particule
    public int[] getBestPosition() {
        return bestPosition;
    }

    // Modifie la meilleure position trouvée par la particule
    public void setBestPosition(int[] bestPosition) {
        this.bestPosition = bestPosition;
    }

    // Retourne la fitness de la position actuelle de la particule
    public int getFitness() {
        return fitness;
    }

    // Modifie la fitness de la position actuelle de la particule
    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    // Retourne la meilleure fitness trouvée par la particule
    public int getBestFitness() {
        return bestFitness;
    }

    // Modifie la meilleure fitness trouvée par la particule
    public void setBestFitness(int bestFitness) {
        this.bestFitness = bestFitness;
    }

    // Retourne la vitesse de la particule
    public double[] getVelocity() {
        return velocity;
    }

    // Modifie la vitesse de la particule
    public void setVelocity(double[] velocity) {
        this.velocity = velocity;
    }
}