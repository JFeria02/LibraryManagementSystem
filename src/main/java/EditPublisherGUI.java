import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditPublisherGUI {
    private JPanel editPublisherPanel;
    private JTextField publisherNameField;
    private JButton savePublisherButton;

    private static JFrame frame;

    private long publisherID;
    private String publisherName;

    public static void editPublisherGUI(String path) {
        // Creates the GUI to create a new publisher
        frame = new JFrame("New Publisher");
        frame.setContentPane(new EditPublisherGUI(path).editPublisherPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
    }

    public EditPublisherGUI(String path){
        publisherNameField.setText("");

        savePublisherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditPublisher(path, -1, publisherNameField.getText()); // Handles saving the new publisher
                frame.dispose();
            }
        });

    }

    public static void editPublisherGUI(JTable table, int row, String path) {
        // Creates the GUI to edit books
        frame = new JFrame("Edit Publisher ID: " + table.getValueAt(row, 0));
        frame.setContentPane(new EditPublisherGUI(table, row, path).editPublisherPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
    }

    public EditPublisherGUI(JTable table, int row, String path){
        int id = Integer.parseInt(table.getValueAt(row, 0).toString());
        String name = (String) table.getValueAt(row, 1);

        // Populate the fields with existing data
        publisherNameField.setText(name);

        savePublisherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditPublisher(path, id, name); // Handles saving new info to the database
                frame.dispose();
            }
        });

    }
}
