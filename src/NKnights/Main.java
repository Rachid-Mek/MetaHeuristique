package NKnights;

import NKnights.NQueen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // create a file object to write the results
            File csvFile = new File("results.csv");
            FileWriter csvWriter = new FileWriter(csvFile);

            // write headers to the csv file
            csvWriter.append("N");
            csvWriter.append(",");
            csvWriter.append("Execution Time (ms)");
            csvWriter.append(",");
            csvWriter.append("Generated Nodes");
            csvWriter.append(",");
            csvWriter.append("Developed Nodes");
            csvWriter.append("\n");

            // solve the problem for different values of N
            for (int n = 8; n <= 14; n++) {
                NQueen nQueen = new NQueen(n);
                List<List<Integer>> solutions;
                Duration executionTime;
                long generatedNodes;
                long developedNodes;
                System.out.println("Solving for N = " + n);
                // solve using BFS
                solutions = nQueen.solveBFS();
                executionTime = nQueen.getExcutionTime();
                generatedNodes = nQueen.getNumOfGeneratedNodes();
                developedNodes = nQueen.getNumOfDevelopedNodes();

                // write the results to the csv file
                csvWriter.append(String.valueOf(n));
                csvWriter.append(",");
                csvWriter.append(String.valueOf(executionTime.toMillis()));
                csvWriter.append(",");
                csvWriter.append(String.valueOf(generatedNodes));
                csvWriter.append(",");
                csvWriter.append(String.valueOf(developedNodes));
                csvWriter.append("\n");
            }

            // close the csv file
            csvWriter.flush();
            csvWriter.close();

            System.out.println("Results written to resultsH1.csv");
        } catch (IOException e) {
            System.out.println("An error occurred while writing the results to the file.");
            e.printStackTrace();
        }
    }
}