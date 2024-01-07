import java.util.List;

public class AdvancedBoxer extends Boxer {

    public AdvancedBoxer(int competitorNumber, String name, int age, String gender, String country, List<Integer> scores) {
        super(competitorNumber, name, age, gender, country, scores);
    }

    @Override
    public double getOverallScore() {
        // calculation for overall score for advanced boxers
        double overallScore = getScores().stream()
                .mapToInt(score -> score > 5 ? 2 * score : score)
                .average()
                .orElse(0.0);

        return overallScore;
    }

    @Override
    public String getBoxerTypeDetails() {
        return "Advanced Boxer";
    }
}
