import java.util.List;

public abstract class Boxer {
    private int competitorNumber;
    private String name;
    private int age;
    private String gender;
    private String country;
    private List<Integer> scores;

    // Constructor
    public Boxer(int competitorNumber, String name, int age, String gender, String country, List<Integer> scores) {
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


    // Abstract method for calculating overall score
    public abstract double getOverallScore();


    // Method to get full details including all scores and type of boxer
    public String getFullDetails() {
        String boxerTypeDetails = getBoxerTypeDetails(); // Get type of boxer details

        return String.format("\nBoxer number %d, name %s, age %d, gender %s, country %s.\n%s is a %d-year-old competitor and received these scores: %s.\nThis gives them an overall score of %.2f.\n%s",
                competitorNumber, name, age, gender, country, name, age, scores, getOverallScore(), boxerTypeDetails);
    }


    // Abstract method for getting specific details based on the competitor type
    public abstract String getBoxerTypeDetails();


    // Method to get short details including type of boxer
    public String getShortDetails() {
        String boxerTypeDetails = getBoxerTypeDetails(); // Get type of boxer details

        return String.format("Boxer %d (%s) has overall score %.2f. %s", competitorNumber, getInitials(), getOverallScore(), boxerTypeDetails);
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
