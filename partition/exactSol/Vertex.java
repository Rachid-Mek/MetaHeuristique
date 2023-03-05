package partition.exactSol;

import java.util.*;

public class Vertex<T> {
    T data;
    List<Vertex<T>> neighbors;

    public Vertex(T data, List<Vertex<T>> neighbors) {
        this.data = data;
        this.neighbors = neighbors;
    }

    public Vertex(T data) {
        this.data = data;
        this.neighbors = new ArrayList<Vertex<T>>();
    }

    public List<Vertex<T>> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(Vertex<T> v) {
        // TODO
        this.neighbors.add(v);
    }

}
