package partition.notExactSol;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int[] arr ={ 382745, 799601, 909247, 729069, 467902, 44328, 34610, 698150, 823460, 903959, 853665, 551830, 610856,
                670702, 488960, 951111, 323046, 446298, 931161, 31385, 496951, 264724, 224916, 169684 } ;
        System.out.println("Array : " + Arrays.toString(arr)); // to string pour afficher le tableau sous form de chaine de caractere
        List<Integer>[] partition = Partition.partitionement(arr);
        System.out.println("S1 = " + partition[0]);
        System.out.println("S2 = " + partition[1]);
        System.out.println("D = " + Partition.difference(partition[0], partition[1]));
    }
}
