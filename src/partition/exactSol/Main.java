package partition.exactSol;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        int sets[][] = { {748 ,348 ,699 ,597 ,502} ,
                {645 ,813 ,530 ,37 ,881 ,232 ,164 ,185 ,395 ,464 ,} ,
                {227 ,151 ,238 ,287 ,902 ,724 ,769 ,165 ,390 ,912 ,649 ,163 ,380 ,885 ,444 ,} ,
                {989 ,174 ,834 ,904 ,820 ,848 ,184 ,272 ,626 ,936 ,765 ,301 ,231 ,204 ,395 ,614 ,709 ,45 ,807 ,926 ,} ,
                {123456789, 234567890, 345678901, 61975308, 456789012, 567890123, 678901234, 789012345, 890123456, 901234567, 1012345678, 1123456789},
                {544 ,591 ,550 ,659 ,223 ,432 ,933 ,871 ,713 ,257 ,230 ,501 ,711 ,547 ,805 ,596 ,937 ,98 ,638 ,520 ,881 ,855 ,537 ,970 ,967 ,} ,
                {173 ,118 ,212 ,775 ,712 ,813 ,855 ,645 ,570 ,816 ,424 ,493 ,913 ,477 ,103 ,552 ,760 ,14 ,279 ,782 ,253 ,176 ,797 ,586 ,85 ,696 ,279 ,862 ,9 ,287 ,} ,
                {42 ,879 ,367 ,467 ,300 ,940 ,101 ,164 ,180 ,255 ,564 ,338 ,3 ,701 ,68 ,980 ,475 ,503 ,457 ,716 ,255 ,645 ,127 ,568 ,98 ,630 ,971 ,14 ,902 ,358 ,885 ,406 ,480 ,530 ,355 ,} ,
                {82 ,196 ,747 ,173 ,687 ,978 ,694 ,868 ,410 ,503 ,360 ,763 ,327 ,345 ,529 ,434 ,57 ,659 ,696 ,937 ,102 ,409 ,787 ,54 ,305 ,863 ,416 ,855 ,761 ,856 ,214 ,756 ,643 ,794 ,126 ,819 ,181 ,564 ,929 ,591 ,} ,
                {671 ,562 ,375 ,501 ,216 ,934 ,154 ,368 ,650 ,642 ,301 ,732 ,50 ,191 ,394 ,263 ,281 ,730 ,878 ,832 ,125 ,762 ,593 ,108 ,133 ,773 ,356 ,471 ,367 ,364 ,482 ,605 ,889 ,340 ,628 ,3 ,549 ,337 ,975 ,192 ,363 ,851 ,869 ,372 ,478 ,},
                {364,832,397,455,207,739,876,322,619,230,956,348,641,834,646,653,106,360,797,240,761,187,641,542,318,461,572,867,929,51,834,921,250,102,911,227,881,545,256,58,535,981,789,396,49,545,510,651,831,625,},
                {950,624,801,595,27,432,461,585,542,855,831,271,738,137,177,775,779,509,687,70,627,517,635,184,79,471,597,235,358,909,698,747,415,194,586,943,422,465,322,883,630,350,469,352,982,591,547,574,160,193,430,260,925,122,708,},
                {304,30,519,591,862,668,370,895,162,702,348,223,641,345,802,904,537,398,345,174,748,216,257,605,608,84,587,830,787,440,143,956,288,886,523,475,212,652,370,848,839,623,787,42,590,917,298,588,480,126,224,226,796,317,802,266,162,177,956,24,398,126,605,954,594},
                {5,868,363,461,287,181,307,279,582,635,55,476,206,839,977,891,13,654,477,29,930,736,449,977,161,508,38,276,375,799,501,313,339,218,704,651,735,257,178,316,905,102,166,942,610,338,912,219,960,636,382,557,340,726,751,64,645,574,35,214,293,411,530,243,913,482,540,924,181,241}, };
        List<String> rows = new ArrayList<>();
        int i = 1;
        for (int [] set : sets  ) {
            PartitionSet instance = new PartitionSet(set);
            System.out.print("Set: ");
            printSet(set);
            System.out.println("\n partition: " + instance.solve());
            String row = set.length + "," + instance.excutionTime.toSeconds() + "\n";
            rows.add(row);
            System.out.print(i);
            i++;
            System.out.println("\n\n############################\n\n\n");
        }
        // saving on csv file
        FileWriter csvWriter;
        try {
            csvWriter = new FileWriter("dataDFS.csv");
            csvWriter.append("Instance,Execution Time (s)\n");
            for (String row : rows) {
                csvWriter.append(row);
            }
            csvWriter.flush();
            csvWriter.close();
            System.out.println("Data saved to data.csv");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the CSV file: " + e.getMessage());
        }
    }

    public static int[] generateArray(int n, int max) {
        int[] array = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(max) + 1;
        }
        return array;
    }

    public static void testSet(int[] arr) {
        PartitionSet partitionSet = new PartitionSet(arr);
        System.out.println("Set: " + partitionSet.set.toString());
        System.out.println(partitionSet.solve().toString());
        System.out.println("Best difference: " + partitionSet.bestDifference);
    }

    public static void printSet(int[] a) {
        for (int el : a) {
            System.out.print(el + ",");
        }

    }
}