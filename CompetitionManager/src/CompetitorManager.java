import javax.swing.*;
import java.io.*;

public class CompetitorManager {
//    public static void main(String[] args) {
//        CompetitorList competitorList = new CompetitorList();
//
//        // Read competitors from CSV file
//        String csvFilePath = "RunCompetitor.csv";
//        competitorList.readCompetitorsFromCSV(csvFilePath);
//
//        // Write Summary Statistics to finalreport.txt
//        competitorList.writeSummaryAndFrequencyReport("finalreport.txt");
//
//        // Allow the user to enter a competitor number and display short details
//        int competitorNumberToCheck = 100;
//        displayCompetitorDetails(competitorList, competitorNumberToCheck);
//
//        // Display competitor with the highest overall score
//        competitorList.displayHighestOverallScore();
//
//        // Display full details table
//        competitorList.displayFullDetailsTable();
//
//        // Check errors in the input file
//        competitorList.checkErrorsInInputFile();
//    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CompetitorGUI();
            }
        });
    }

    // Display short details for a competitor based on the competitor number
    private static void displayCompetitorDetails(CompetitorList competitorList, int competitorNumber) {
        competitorList.displayShortDetails(competitorNumber);
    }
}
