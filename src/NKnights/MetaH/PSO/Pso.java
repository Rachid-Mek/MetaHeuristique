package NKnights.MetaH.PSO;

import java.util.ArrayList;
import java.util.Random;

public class Pso {
    int n; // number of queens
    int m; // number of particles
    int maxIterations; // maximum number of iterations
    int maxVelocity; // maximum velocity
    int w; // inertia weight
    int c1; // cognitive weight
    int c2; // social weight

    public Pso(int n, int m, int maxIterations, int maxVelocity, int w, int c1, int c2) {
        this.n = n;
        this.m = m;
        this.maxIterations = maxIterations;
        this.maxVelocity = maxVelocity;
        this.w = w;
        this.c1 = c1;
        this.c2 = c2;
    }

    private int fitness(ArrayList<Integer> sol) {
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

    public ArrayList<Integer> generateRandomSolution() {
        ArrayList<Integer> solution = new ArrayList<Integer>();
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            solution.add(rand.nextInt(n)); // add a random number between 0 and n-1
        }
        return solution;
    }
    private ArrayList<Integer> generateRandomVelocity() {
        ArrayList<Integer> vel = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            vel.add((int) (Math.random() * maxVelocity));
        }
        return vel;
    }

    public ArrayList<Particle> generateSwarm() {
        ArrayList<Particle> swarm = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            ArrayList<Integer> position = generateRandomSolution();
            ArrayList<Integer> velocity = generateRandomVelocity();
            int fitness = fitness(position);
            swarm.add(new Particle(position, velocity, fitness));
        }
        return swarm;
    }

    public Particle getGlobalBest(ArrayList<Particle> swarm) {
        Particle globalBest = swarm.get(0);
        for (int i = 1; i < swarm.size(); i++) {
            Particle particle = swarm.get(i);
            if (particle.getFitness() < globalBest.getFitness()) {
                globalBest = particle;
            }
        }
        return globalBest;
    }

    private void updateVelocity(Particle p, ArrayList<Integer> globalBest) {
        ArrayList<Integer> newVelocity = new ArrayList<>();
        ArrayList<Integer> currentPosition = p.getPosition();
        ArrayList<Integer> currentVelocity = p.getVelocity();
        for (int i = 0; i < n; i++) {
            int personalBest = p.getPosition().get(i);
            int globalBestPosition = globalBest.get(i);
            int currentPositionValue = currentPosition.get(i);
            int currentVelocityValue = currentVelocity.get(i);
            int r1 = (int) (Math.random() * 10);
            int r2 = (int) (Math.random() * 10);
            int newVelocityValue = (int) (w * currentVelocityValue
                    + c1 * r1 * (personalBest - currentPositionValue)
                    + c2 * r2 * (globalBestPosition - currentPositionValue));

            if (newVelocityValue > maxVelocity) {
                newVelocityValue = maxVelocity;
            } else if (newVelocityValue < -maxVelocity) {
                newVelocityValue = -maxVelocity;
            }
            newVelocity.add(newVelocityValue);
        }
        p.setVelocity(newVelocity);
    }

    //update position
    private void updatePosition(Particle p) {
        ArrayList<Integer> newPosition = new ArrayList<>();
        ArrayList<Integer> currentPosition = p.getPosition();
        ArrayList<Integer> currentVelocity = p.getVelocity();
        for (int i = 0; i < n; i++) {
            int newPositionValue = (currentPosition.get(i) + currentVelocity.get(i)) % n;
            if (newPositionValue < 0) {
                newPositionValue = n + newPositionValue;
            }
            newPosition.add(newPositionValue);
        }
        p.setPosition(newPosition);
    }
    //update fitness
    private void updateFitness(Particle p) {
        int newFitness = fitness(p.getPosition());
        p.setFitness(newFitness);
    }
    //update particle
    private void updateParticle(Particle p, ArrayList<Integer> globalBest) {
        updateVelocity(p, globalBest);
        updatePosition(p);
        updateFitness(p);
    }
    //update swarm
    private void updateSwarm(ArrayList<Particle> swarm, ArrayList<Integer> globalBest) {
        for (Particle particle : swarm) {
            updateParticle(particle, globalBest);
        }
    }
    //solve
    public ArrayList<Integer> solve() {
        ArrayList<Particle> swarm = generateSwarm();
        ArrayList<Integer> globalBest = getGlobalBest(swarm).getPosition();
        for (int i = 0; i < maxIterations; i++) {
            updateSwarm(swarm, globalBest);
            ArrayList<Integer> currentGlobalBest = getGlobalBest(swarm).getPosition();
            if (fitness(currentGlobalBest) < fitness(globalBest)) {
                globalBest = currentGlobalBest;
            }
        }
        return globalBest;
    }

    public static void main(String[] args) {
        Pso pso = new Pso(10, 500, 10000, 3, 1, 2, 2);
        ArrayList<Integer> solution = pso.solve();
        System.out.println(solution);
        System.out.println(pso.conflict(solution));
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
