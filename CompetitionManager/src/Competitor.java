import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Competitor {
    private int competitorNumber;
    private String name;
    private int age;
    private String gender;
    private String country;
    private List<Integer> scores; // Change Set<Integer> to List<Integer>

    // Constructor
    public Competitor(int competitorNumber, String name, int age, String gender, String country, List<Integer> scores) {
        this.competitorNumber = competitorNumber;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.country = country;
        this.scores = scores;
    }

    // Getter and Setter methods

    public int getCompetitorNumber() {
        return competitorNumber;
    }

    public void setCompetitorNumber(int competitorNumber) {
        this.competitorNumber = competitorNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }

    // Method to calculate overall score based on the average
    public double getOverallScore() {
        int totalScores = scores.stream().mapToInt(Integer::intValue).sum();
        return (double) totalScores / scores.size();
    }

    // Method to get full details including all scores
    public String getFullDetails() {
        return String.format("\n\nCompetitor number %d, name %s, age %d, gender %s, country %s.\n%s is a %d-year-old competitor and received these scores: %s.\nThis gives them an overall score of %.2f.",
                competitorNumber, name, age, gender, country, name, age, scores, getOverallScore());
    }

    // Method to get short details
    public String getShortDetails() {
        return String.format("CN %d (%s) has overall score %.2f.", competitorNumber, getInitials(), getOverallScore());
    }
    private String getInitials() {
        StringBuilder initials = new StringBuilder();
        String[] nameParts = name.split(" ");
        for (String part : nameParts) {
            initials.append(part.charAt(0));
        }
        return initials.toString().toUpperCase();
    }

}
