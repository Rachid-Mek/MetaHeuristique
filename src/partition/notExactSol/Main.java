package partition.notExactSol;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int[] arr = Partition.generateArray(5,100);
        System.out.println("Array : " + Arrays.toString(arr)); // to string pour afficher le tableau sous form de chaine de caractere
        List<Integer>[] partition = Partition.partitionement(arr);
        System.out.println("S1 = " + partition[0]);
        System.out.println("S2 = " + partition[1]);
        System.out.println("D = " + Partition.difference(partition[0], partition[1]));
    }
}
