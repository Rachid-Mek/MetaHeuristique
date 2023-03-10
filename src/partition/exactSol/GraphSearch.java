package partition.exactSol;

import java.util.*;
import java.util.function.Function;

public class GraphSearch<T> {
    Set<Vertex<T>> visited = new HashSet<Vertex<T>>();
    Function<Vertex<T>, Void> func;

    // constructor accepts a method that will be called on each vertex
    public GraphSearch(Function<Vertex<T>, Void> func) {
        this.func = func;
    }

    public void dfs(Vertex<T> v) {
        // iterative version of DFS
        Stack<Vertex<T>> stack = new Stack<Vertex<T>>();
        stack.push(v);
        while (!stack.isEmpty()) {
            Vertex<T> current = stack.pop();
            if (isVertexVisited(current))
                return;

            // call the function on the vertex
            func.apply(current);
            setVertexVisited(current);
            // add neighbors in reverse order
            List<Vertex<T>> neighbors = current.getNeighbors();
            for (int i = neighbors.size() - 1; i >= 0; i--) {
                stack.push(neighbors.get(i));
            }
        }
    }

    public void bfs(Vertex<T> v) {
        // TODO
    }

    boolean isVertexVisited(Vertex<T> v) {
        return visited.contains(v);
    }

    void setVertexVisited(Vertex<T> v) {
        visited.add(v);
    }
}
