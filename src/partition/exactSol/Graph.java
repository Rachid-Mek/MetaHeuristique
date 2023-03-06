package partition.exactSol;

import java.util.*;
import java.util.function.Function;

class Graph<T> {
    // vertices of the graph
    List<Vertex<T>> vertices;

    public Graph() {
        vertices = new ArrayList<Vertex<T>>();
    }

    public void addVertex(Vertex<T> v) {
        // TODO
        vertices.add(v);
    }

    public void addEdge(Vertex<T> v1, Vertex<T> v2) {
        // TODO
        v1.addNeighbor(v2);
    }

    public void dfs(Vertex<T> v, Function<Vertex<T>, Void> f) {
        // iterative version of dfs
        Set<Vertex<T>> visited = new HashSet<Vertex<T>>();
        Stack<Vertex<T>> stack = new Stack<Vertex<T>>();
        stack.push(v);
        while (!stack.isEmpty()) {
            Vertex<T> current = stack.pop();
            if (!visited.contains(current)) {
                f.apply(current);
                visited.add(current);
                for (Vertex<T> neighbor : current.getNeighbors()) {
                    stack.push(neighbor);
                }
            }
        }

    }
}