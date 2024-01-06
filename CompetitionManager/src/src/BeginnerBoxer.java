import java.util.List;

public class BeginnerBoxer extends Boxer {

    public BeginnerBoxer(int competitorNumber, String name, int age, String gender, String country, List<Integer> scores) {
        super(competitorNumber, name, age, gender, country, scores);
    }

    @Override
    public double getOverallScore() {
        // Custom calculation for overall score for beginner boxers
        return getScores().stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }

    @Override
    public String getBoxerTypeDetails() {
        return "Beginner Boxer";
    }
}
