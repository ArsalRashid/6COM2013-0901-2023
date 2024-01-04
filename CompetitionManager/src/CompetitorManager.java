import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class CompetitorManager {
    public static void main(String[] args) {
        CompetitorList competitorList = new CompetitorList();

        // Read competitors from CSV file
        String csvFilePath = "RunCompetitor.csv";
        competitorList.readCompetitorsFromCSV(csvFilePath);

        // Write Summary Statistics to finalreport.txt
        competitorList.writeSummaryStatistics("finalreport.txt");

        // Write Frequency Report to finalreport.txt
        competitorList.writeFrequencyReport("finalreport.txt");

        // Allow the user to enter a competitor number
        int competitorNumberToCheck = 100; // Replace with user input
        competitorList.displayShortDetails(competitorNumberToCheck);

        competitorList.displayFullDetailsTable();


        // Check errors in the input file
        competitorList.checkErrorsInInputFile();
    }
}
