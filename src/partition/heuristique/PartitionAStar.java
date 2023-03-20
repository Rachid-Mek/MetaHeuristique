package partition.heuristique;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class PartitionAStar {
    ArrayList<Integer> set;
    private int setSum;
    // liste ouverte
    private PriorityQueue<Node> nonVisitedNodes = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
    public Duration excutionTime;

    public PartitionAStar(int[] set) {
        this.setSum = 0;
        this.set = new ArrayList<>();
        for (int i : set) {
            this.set.add(i);
            this.setSum += i;
        }
    }

    public List<ArrayList<Integer>> partition() {
        //S1 =[]
        ArrayList<Integer> initialS1 = new ArrayList<>();
        Node initialState = new Node(initialS1, f(initialS1));
        Instant startTime = Instant.now(); // start the timer
        nonVisitedNodes.add(initialState);
        while (!nonVisitedNodes.isEmpty()) {
            Node current = nonVisitedNodes.poll();
            if (h(current.s1)==0) {
                // stop the timer
                this.excutionTime = Duration.between(startTime, Instant.now());
                return new ArrayList<>(Arrays.asList(current.s1, setDifference(set, current.s1)));
            }
            ArrayList<Integer>[] successors = generateSuccessors(current.s1);
            // ajouter les successeurs à la liste ouverte
            for (ArrayList<Integer> s : successors) {
                Node successor = new Node(s, f(s));
                nonVisitedNodes.add(successor);
            }
        }
        return null;
    }

    public static int g(ArrayList<Integer> s1) {
        return s1.size();
    }

    // la somme minimale entre s1 et s2 nécessaire pour atteindre la solution
    public int h(ArrayList<Integer> s1) {
        int sumS1 = 0;
        for (int i : s1) {
            sumS1 += i;
        }
        return Math.abs(this.setSum - 2 * sumS1);
    }

    public int f(ArrayList<Integer> s1) {
        return g(s1) + h(s1);
    }

    // set difference pour avoir les éléments de s2 qui ne sont pas dans s1
    private ArrayList<Integer> setDifference(ArrayList<Integer> s1, ArrayList<Integer> s2) {
        ArrayList<Integer> diff = new ArrayList<>(s1);
        for (int i : s2) {
            diff.removeIf(x -> x == i); // on enlève les éléments de s2 from s1 pour avoir la différence entre les deux
        }
        return diff;
    }

    // générer les successeurs possible de s
    public ArrayList<Integer>[] generateSuccessors(ArrayList<Integer> s) {
        // prendre la différence entre set et s pour avoir les éléments qui ne sont pas dans s
        ArrayList<Integer> diff = setDifference(set, s);
        // ajouter les éléments de diff à s pour avoir les successeurs
        ArrayList<Integer>[] successors = new ArrayList[diff.size()];
        for (int i = 0; i < diff.size(); i++) {
            ArrayList<Integer> newS = new ArrayList<>(s);
            newS.add(diff.get(i));
            successors[i] = newS;
        }
        return successors;
    }
}