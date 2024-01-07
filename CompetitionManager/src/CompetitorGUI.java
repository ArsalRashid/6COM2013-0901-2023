import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CompetitorGUI extends JFrame {

    private DefaultTableModel tableModel;
    private JTable table;
    private JTextArea textArea;
    private CompetitorList competitorList;

    public CompetitorGUI() {
        super("Competitor Report");

        competitorList = new CompetitorList();
        // Initialize components
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Allow editing only in the Scores column
            }
        };
        table = new JTable(tableModel);
        textArea = new JTextArea();
        textArea.setRows(20);  // Set the number of rows
        textArea.setColumns(30);  // Set the number of columns
        JButton loadButton = new JButton("Load CSV");
        JButton addButton = new JButton("Add Competitor");
        JButton removeButton = new JButton("Remove Competitor");
        JButton viewDetailsButton = new JButton("View Competitor Details");
        JButton viewFullDetailsButton = new JButton("View Full Details");
        JButton viewShortDetailsButton = new JButton("View Short Details");

        // Set layout
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loadButton);
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(viewFullDetailsButton);
        buttonPanel.add(viewShortDetailsButton);
        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(new JScrollPane(textArea), BorderLayout.SOUTH);

        // Add action listener to the load button
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(CompetitorGUI.this);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();

                    competitorList.readCompetitorsFromCSV(filePath);

                    // Update the table
                    updateTable();

                    // Display competitor names in the text area
                    displayCompetitorNames();
                }
            }
        });

        // action listener to the add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a dialog to get information about the new competitor
                CompetitorInputDialog dialog = new CompetitorInputDialog(CompetitorGUI.this);
                dialog.setVisible(true);

                // Check if the user clicked "OK" in the dialog
                if (dialog.isOkClicked()) {
                    // Get the new competitor from the dialog
                    Boxer newCompetitor = dialog.getCompetitor();

                    // Add the new competitor to the list
                    competitorList.addCompetitor(newCompetitor);

                    // Update the table and display names
                    updateTable();
                    displayCompetitorNames();
                }
            }
        });

        // action listener to the remove button
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Remove the selected row from the tableModel
                    tableModel.removeRow(selectedRow);
                    // After removal, display names
                    displayCompetitorNames();
                }
            }
        });

        // action listener to the viewDetailsButton
        viewDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a dialog to get attribute selection from the user
                String[] attributes = {"Male Boxer", "Female Boxer", "Beginner Boxer", "Advanced Boxer"};
                String selectedAttribute = (String) JOptionPane.showInputDialog(
                        CompetitorGUI.this,
                        "Select Attribute:",
                        "View Competitor Details",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        attributes,
                        attributes[0]
                );

                if (selectedAttribute != null) {
                    displayDetailsForAttribute(selectedAttribute);
                }
            }
        });

        // Add action listener to the viewFullDetailsButton
        viewFullDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int competitorNumber = (int) tableModel.getValueAt(selectedRow, 0);

                    // Find the competitor with the selected competitor number
                    Boxer selectedCompetitor = null;
                    for (Boxer competitor : competitorList.getBoxers()) {
                        if (competitor.getCompetitorNumber() == competitorNumber) {
                            selectedCompetitor = competitor;
                            break;
                        }
                    }

                    // Display full details in a dialog or text area
                    if (selectedCompetitor != null) {
                        String fullDetails = selectedCompetitor.getFullDetails();
                        JTextArea detailsTextArea = new JTextArea(fullDetails);
                        detailsTextArea.setEditable(false);
                        JOptionPane.showMessageDialog(CompetitorGUI.this, new JScrollPane(detailsTextArea),
                                "Full Details for Competitor " + competitorNumber, JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        });


        // action listener to the viewShortDetailsButton
        viewShortDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();

                if (selectedRow != -1) {
                    int competitorNumber = (int) tableModel.getValueAt(selectedRow, 0);

                    // Find the competitor with the selected competitorNumber
                    Boxer selectedCompetitor = competitorList.getBoxers().stream()
                            .filter(competitor -> competitor.getCompetitorNumber() == competitorNumber)
                            .findFirst()
                            .orElse(null);

                    if (selectedCompetitor != null) {
                        // Display short details of the selected competitor
                        JTextArea detailsTextArea = new JTextArea(selectedCompetitor.getShortDetails());
                        detailsTextArea.setEditable(false);
                        JOptionPane.showMessageDialog(CompetitorGUI.this, new JScrollPane(detailsTextArea),
                                "Short Competitor Details", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        });


        // Add table model listener for instant update when Scores column is edited
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (column == 5) { // Check if the change is in the Scores column
                    updateOverallScore(row);
                }
            }
        });

        // Enable sorting
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);

        // Set JFrame properties
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    private void displayDetailsForAttribute(String selectedAttribute) {
        // Create a dialog or update text area with details for competitors with the selected attribute
        StringBuilder details = new StringBuilder();
        details.append("Names of Competitors with ").append(selectedAttribute).append(":\n\n");

        for (Boxer competitor : competitorList.getBoxers()) {
            if (("Male Boxer".equals(selectedAttribute) && competitor.getGender().equals("Male")) ||
                    ("Female Boxer".equals(selectedAttribute) && competitor.getGender().equals("Female")) ||
                    ("Beginner Boxer".equals(selectedAttribute) && competitor instanceof BeginnerBoxer) ||
                    ("Advanced Boxer".equals(selectedAttribute) && competitor instanceof AdvancedBoxer)) {
                details.append(competitor.getName()).append("\n");
            }
        }

        JTextArea detailsTextArea = new JTextArea(details.toString());
        detailsTextArea.setEditable(false);
        JOptionPane.showMessageDialog(CompetitorGUI.this, new JScrollPane(detailsTextArea),
                "Competitor Names", JOptionPane.PLAIN_MESSAGE);
    }


    private void updateTable() {
        // Clear the table
        tableModel.setColumnCount(0);
        tableModel.setRowCount(0);

        // Add column headers
        tableModel.addColumn("Competitor Number");
        tableModel.addColumn("Name");
        tableModel.addColumn("Age");
        tableModel.addColumn("Gender");
        tableModel.addColumn("Country");
        tableModel.addColumn("Scores");
        tableModel.addColumn("Overall Score");
        tableModel.addColumn("Type");

        // Add rows
        for (Boxer competitor : competitorList.getBoxers()) {
            tableModel.addRow(new Object[]{
                    competitor.getCompetitorNumber(),
                    competitor.getName(),
                    competitor.getAge(),
                    competitor.getGender(),
                    competitor.getCountry(),
                    competitor.getScores(),
                    competitor.getOverallScore(),
                    (competitor instanceof AdvancedBoxer) ? "Advanced" : "Beginner"
            });
        }
    }

    private void displayCompetitorNames() {
        textArea.setText(""); // Clear previous content

        // Append names of each type to the text area
        textArea.append("Advanced Boxers:\n");
        for (Boxer competitor : competitorList.getAdvancedBoxers()) {
            textArea.append(competitor.getName() + "\n");
        }

        textArea.append("\nBeginner Boxers:\n");
        for (Boxer competitor : competitorList.getBeginnerBoxers()) {
            textArea.append(competitor.getName() + "\n");
        }

        // Append the report content
        textArea.append("\n\nSummary and Frequency Report:\n");
        String report = competitorList.generateReport();
        textArea.append(report);
    }

    private void updateOverallScore(int row) {
        List<Integer> updatedScores = parseScores(tableModel.getValueAt(row, 5).toString());
        Boxer competitor = competitorList.getBoxers().get(row);
        competitor.setScores(updatedScores);
        tableModel.setValueAt(competitor.getOverallScore(), row, 6);
        displayCompetitorNames();
    }

    private List<Integer> parseScores(String scoresString) {
        String[] scoreArray = scoresString.replaceAll("\\[|\\]", "").split(",");

        List<Integer> scores = new ArrayList<>();
        for (String score : scoreArray) {
            scores.add(Integer.parseInt(score.trim()));
        }
        return scores;
    }

}
