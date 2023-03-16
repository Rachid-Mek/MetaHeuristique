package partition.heuristique;

public class Main {
    public static void main(String[] args) {
        int arr[] = { 70, 73, 77, 80, 82, 87, 90, 94, 98, 106, 110, 113, 115, 118, 120 };
        PartitionAStar partitionAStar = new PartitionAStar(arr);

        partitionAStar.partition().forEach(System.out::println);
    }

}
