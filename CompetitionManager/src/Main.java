import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> scores1 = new ArrayList<>(List.of(5, 4, 5, 4));
        Boxer boxer1 = new AdvancedBoxer(100, "Keith John Talbot", 21, "Male", "UK", scores1);


        System.out.println(boxer1.getFullDetails());
        System.out.println(boxer1.getShortDetails());
    }
}
