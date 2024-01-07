import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CompetitorInputDialog extends JDialog {

    private JTextField competitorNumberField;
    private JTextField nameField;
    private JTextField ageField;
    private JTextField genderField;
    private JTextField countryField;
    private JTextField scoresField;

    private JButton okButton;
    private JButton cancelButton;

    private boolean okClicked = false;
    private Boxer competitor;

    public CompetitorInputDialog(Frame parent) {
        super(parent, "Add Competitor", true);

        // Initialize components
        competitorNumberField = new JTextField();
        nameField = new JTextField();
        ageField = new JTextField();
        genderField = new JTextField();
        countryField = new JTextField();
        scoresField = new JTextField();

        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        // Set layout
        setLayout(new GridLayout(7, 2));
        add(new JLabel("Competitor Number:"));
        add(competitorNumberField);
        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Age:"));
        add(ageField);
        add(new JLabel("Gender:"));
        add(genderField);
        add(new JLabel("Country:"));
        add(countryField);
        add(new JLabel("Scores (comma-separated):"));
        add(scoresField);
        add(okButton);
        add(cancelButton);

        //  action listeners
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okClicked = true;
                competitor = createCompetitorFromInput();
                CompetitorInputDialog.this.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CompetitorInputDialog.this.dispose();
            }
        });

        // Set dialog properties
        setSize(300, 200);
        setLocationRelativeTo(parent);
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public Boxer getCompetitor() {
        return competitor;
    }

    private Boxer createCompetitorFromInput() {
        int competitorNumber = Integer.parseInt(competitorNumberField.getText());
        String name = nameField.getText();
        int age = Integer.parseInt(ageField.getText());
        String gender = genderField.getText();
        String country = countryField.getText();
        List<Integer> scores = parseScores(scoresField.getText());

        // Create a new competitor
        return new BeginnerBoxer(competitorNumber, name, age, gender, country, scores);
    }

    private List<Integer> parseScores(String scoresString) {
        String[] scoreArray = scoresString.split(",");
        List<Integer> scores = new ArrayList<>();
        for (String score : scoreArray) {
            scores.add(Integer.parseInt(score.trim()));
        }
        return scores;
    }
}
