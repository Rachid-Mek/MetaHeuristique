package partition.exactSol;

public class Main {

    public static void main(String[] args) {
        PartitionSet partitionSet = new PartitionSet(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 });
        System.out.println(partitionSet.solve().toString());
        System.out.println("Best difference: " + partitionSet.bestDifference);
    }
}