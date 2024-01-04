import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class CompetitorList {
    private List<Competitor> competitors;

    public CompetitorList() {
        this.competitors = new ArrayList<>();
    }

    // Read competitors from CSV file
    public void readCompetitorsFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int competitorNumber = Integer.parseInt(data[0].trim());
                String name = data[1].trim();
                int age = Integer.parseInt(data[2].trim());
                String gender = data[3].trim();
                String country = data[4].trim();

                List<Integer> scores = new ArrayList<>();
                for (int i = 5; i < data.length; i++) {
                    scores.add(Integer.parseInt(data[i].trim()));
                }

                Competitor competitor = new Competitor(competitorNumber, name, age, gender, country, scores);
                competitors.add(competitor);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // Display full details table
    public void displayFullDetailsTable() {
        System.out.println("Full Details Table:");
        for (Competitor competitor : competitors) {
            System.out.println(competitor.getFullDetails());
        }
    }

    // Display details of the competitor with the highest overall score
    public void displayHighestOverallScore() {
        Competitor highestScorer = competitors.stream()
                .max((c1, c2) -> Double.compare(c1.getOverallScore(), c2.getOverallScore()))
                .orElse(null);

        if (highestScorer != null) {
            System.out.println("Competitor with the highest overall score:");
            System.out.println(highestScorer.getFullDetails());
        }
    }

    // Write Summary Statistics to a file
    public void writeSummaryStatistics(String fileName) {
//        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
//
//            double averageOverallScore = competitors.stream()
//                    .mapToDouble(Competitor::getOverallScore)
//                    .average()
//                    .orElse(0.0);
//
//            writer.println("Average Overall Score: " + averageOverallScore);
//
//            // Add more statistics as needed
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    public void writeFrequencyReport(String fileName) {
//        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
//            int[] scoreFrequency = new int[4];
//            for (Competitor competitor : competitors) {
//                for (int score : competitor.getScores()) {
//                    scoreFrequency[score]++;
//                }
//            }
//
//            for (int i = 0; i < scoreFrequency.length; i++) {
//                writer.println("Score " + i + ": " + scoreFrequency[i] + " times");
//            }
//
//            // Add more frequency information as needed
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }



    // Allow the user to enter a competitor number and display short details
    public void displayShortDetails(int competitorNumber) {
        Competitor competitor = findCompetitorByNumber(competitorNumber);
        if (competitor != null) {
            System.out.println("Short Details for Competitor " + competitorNumber + ":");
            System.out.println(competitor.getShortDetails());
        } else {
            System.out.println("Competitor with number " + competitorNumber + " not found.");
        }
    }

    // Helper method to find a competitor by number
    private Competitor findCompetitorByNumber(int competitorNumber) {
        return competitors.stream()
                .filter(competitor -> competitor.getCompetitorNumber() == competitorNumber)
                .findFirst()
                .orElse(null);
    }

    // Check errors in the input file
    public void checkErrorsInInputFile() {
        for (Competitor competitor : competitors) {
            // Check for missing or invalid data
            if (competitor.getCompetitorNumber() <= 0 ||
                    competitor.getName().isEmpty() ||
                    competitor.getAge() <= 0 ||
                    competitor.getGender().isEmpty() ||
                    competitor.getCountry().isEmpty() ||
                    competitor.getScores().isEmpty()) {
                System.out.println("Error in competitor data: " + competitor.getFullDetails());
            }
        }
    }

}

