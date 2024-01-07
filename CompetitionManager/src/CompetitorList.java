import java.io.*;
import java.util.ArrayList;
import java.util.List;

class CompetitorList {
    private List<Boxer> boxers;



    public CompetitorList() {
        this.boxers = new ArrayList<>();
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

                // Decide whether the boxer is a Beginner or Advanced based on their overall score
                Boxer newBoxer;
                double overallScore = calculateOverallScore(scores);
                if (overallScore > 5) {
                    newBoxer = new AdvancedBoxer(competitorNumber, name, age, gender, country, scores);
                } else {
                    newBoxer = new BeginnerBoxer(competitorNumber, name, age, gender, country, scores);
                }

                boxers.add(newBoxer);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // Calculate overall score for a boxer
    private double calculateOverallScore(List<Integer> scores) {
        return scores.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }


    // Display full details table
    public void displayFullDetailsTable() {
        System.out.println("\n\nFull Details Table:");
        for (Boxer boxer : boxers) {
            System.out.println(boxer.getFullDetails());
        }
    }

    // Display details of the Boxer with the highest overall score
    public void displayHighestOverallScore() {
        Boxer highestScorer = boxers.stream()
                .max((c1, c2) -> Double.compare(c1.getOverallScore(), c2.getOverallScore()))
                .orElse(null);

        if (highestScorer != null) {
            System.out.println("\n\nCompetitor with the highest overall score:");
            System.out.println(highestScorer.getFullDetails());
        }
    }

    // Write Summary Statistics and Frequency Report to a file, overwriting existing data
    public void writeSummaryAndFrequencyReport(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, false))) {
            // Write Summary Statistics
            writer.println("******************************");
            writer.println("*      Summary Statistics    *");
            writer.println("\n\n******************************");
            double averageOverallScore = boxers.stream()
                    .mapToDouble(Boxer::getOverallScore)
                    .average()
                    .orElse(0.0);
            writer.printf("Average Overall Score of All Competitors: %.2f%n", averageOverallScore);

            // Write Individual Stats
            writer.println("\n************************************");
            writer.println("*         Individual Stats         *");
            writer.println("************************************");
            writer.printf("| %-5s | %-25s | %-10s | %-7s | %-10s | %-14s |%n", "Number", "Name", "Age", "Gender", "Country", "Overall");
            writer.println("|--------|---------------------------|------------|---------|------------|----------------|");

            for (Boxer competitor : boxers) {
                writer.printf("| %-6d | %-25s | %-10d | %-7s | %-10s | %-14f |%n",
                        competitor.getCompetitorNumber(), competitor.getName(), competitor.getAge(),
                        competitor.getGender(), competitor.getCountry(), competitor.getOverallScore());
                writer.println("|--------|---------------------------|------------|---------|------------|----------------|");

                // Display individual scores
                writer.printf("| %-6s | %-25s | %-10s | %-7s | %-10s | %-14s |%n", "", "", "", "", "", "Scores");
                writer.println("|--------|---------------------------|------------|---------|------------|----------------|");
                writer.printf("| %-6s | %-25s | %-10s | %-7s | %-10s | %-14s |%n", "", "", "", "", "", competitor.getScores());

                // Display additional details for specific boxers
                if (competitor instanceof AdvancedBoxer || competitor instanceof BeginnerBoxer) {
                    writer.printf("| %-6s | %-25s | %-10s | %-7s | %-10s | %-12s |%n", "", "", "", "", "", competitor.getBoxerTypeDetails());
                    writer.println("|--------|---------------------------|------------|---------|------------|----------------|");
                }
            }

            // Write Frequency Report
            writer.println("\n************************************");
            writer.println("*         Frequency Report         *");
            writer.println("************************************");

            // Determine the maximum score to set the array size dynamically
            int maxScore = boxers.stream()
                    .flatMapToInt(comp -> comp.getScores().stream().mapToInt(Integer::intValue))
                    .max()
                    .orElse(0);

            int[] scoreFrequency = new int[maxScore + 1];
            for (Boxer competitor : boxers) {
                for (int score : competitor.getScores()) {
                    scoreFrequency[score]++;
                }
            }

            for (int i = 0; i < scoreFrequency.length; i++) {
                writer.printf("Score %-3d: %d times%n", i, scoreFrequency[i]);
            }

            // Highlight Competitor with the Highest Score
            writer.println("\n********************************************");
            writer.println("* Competitor with the highest overall score *");
            Boxer highestScorer = boxers.stream()
                    .max((c1, c2) -> Double.compare(c1.getOverallScore(), c2.getOverallScore()))
                    .orElse(null);

            if (highestScorer != null) {
                writer.println(highestScorer.getFullDetails());
                writer.println("************************************");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCompetitor(Boxer competitor) {
        boxers.add(competitor);
    }
    public String generateReport() {
        StringBuilder report = new StringBuilder();

        // Overall scores of all competitors
        report.append("\n\nOverall Scores of All Competitors:\n");
        double averageOverallScore = boxers.stream()
                .mapToDouble(Boxer::getOverallScore)
                .average()
                .orElse(0.0);
        report.append(averageOverallScore);


        // Frequency Report
        report.append("\nFrequency Report:\n");
        int maxScore = boxers.stream()
                .flatMapToInt(comp -> comp.getScores().stream().mapToInt(Integer::intValue))
                .max()
                .orElse(0);

        int[] scoreFrequency = new int[maxScore + 1];
        for (Boxer competitor : boxers) {
            for (int score : competitor.getScores()) {
                scoreFrequency[score]++;
            }
        }

        for (int i = 0; i < scoreFrequency.length; i++) {
            report.append("Score ").append(i).append(": ").append(scoreFrequency[i]).append(" times\n");
        }
        report.append("\n");

        // Competitor with the Highest Score
        report.append("Competitor with the highest overall score:\n");
        Boxer highestScorer = boxers.stream()
                .max((c1, c2) -> Double.compare(c1.getOverallScore(), c2.getOverallScore()))
                .orElse(null);

        if (highestScorer != null) {
            report.append(highestScorer.getFullDetails()).append("\n");
        }
        report.append("\n");

        // Competitor with the Lowest Score
        report.append("Competitor with the lowest overall score:\n");
        Boxer lowestScorer = boxers.stream()
                .min((c1, c2) -> Double.compare(c1.getOverallScore(), c2.getOverallScore()))
                .orElse(null);

        if (lowestScorer != null) {
            report.append(lowestScorer.getFullDetails()).append("\n");
        }

        return report.toString();
    }

    public List<Boxer> getBoxers() {
        return boxers;
    }

    // Get advanced boxers
    public List<Boxer> getAdvancedBoxers() {
        List<Boxer> advancedBoxers = new ArrayList<>();
        for (Boxer competitor : boxers) {
            if (competitor instanceof AdvancedBoxer) {
                advancedBoxers.add(competitor);
            }
        }
        return advancedBoxers;
    }

    // Get beginner boxers
    public List<Boxer> getBeginnerBoxers() {
        List<Boxer> beginnerBoxers = new ArrayList<>();
        for (Boxer competitor : boxers) {
            if (competitor instanceof BeginnerBoxer) {
                beginnerBoxers.add(competitor);
            }
        }
        return beginnerBoxers;
    }


    // Allow the user to enter a competitor number and display short details
    public void displayShortDetails(int competitorNumber) {
        Boxer boxer = findCompetitorByNumber(competitorNumber);
        if (boxer != null) {
            System.out.println("Short Details for Competitor " + competitorNumber + ":");
            System.out.println(boxer.getShortDetails());
        } else {
            System.out.println("Competitor with number " + competitorNumber + " not found.");
        }
    }

    // Helper method to find a competitor by number
    private Boxer findCompetitorByNumber(int competitorNumber) {
        return boxers.stream()
                .filter(boxer -> boxer.getCompetitorNumber() == competitorNumber)
                .findFirst()
                .orElse(null);
    }

    // Check errors in the input file
    public void checkErrorsInInputFile() {
        for (Boxer boxer : boxers) {
            // Check for missing or invalid data
            if (boxer.getCompetitorNumber() <= 0 ||
                    boxer.getName().isEmpty() ||
                    boxer.getAge() <= 0 ||
                    boxer.getGender().isEmpty() ||
                    boxer.getCountry().isEmpty() ||
                    boxer.getScores().isEmpty()) {
                System.out.println("Error in competitor data: " + boxer.getFullDetails());
            }
        }
    }

}

