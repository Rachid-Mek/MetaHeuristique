package NKnights.MetaH.PSO;
import java.util.ArrayList;

public class Particle {
    private ArrayList<Integer> position; // particle position (queens' positions)
    private ArrayList<Integer> velocity; // particle velocity
    private int fitness; // particle fitness value

    public Particle(ArrayList<Integer> position, ArrayList<Integer> velocity, int fitness) {
        this.position = position;
        this.velocity = velocity;
        this.fitness = fitness;
    }

    public ArrayList<Integer> getPosition() {
        return position;
    }

    public void setPosition(ArrayList<Integer> position) {
        this.position = position;
    }

    public ArrayList<Integer> getVelocity() {
        return velocity;
    }

    public void setVelocity(ArrayList<Integer> velocity) {
        this.velocity = velocity;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }
}