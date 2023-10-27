import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditAuthorGUI {
    private JPanel editAuthorPanel;
    private JTextField authorNameField;
    private JButton saveAuthorButton;

    private static JFrame frame;

    private long authorID;
    private String authorName;

    public static void editAuthorGUI(String path) {
        // Creates the GUI to create a new author
        frame = new JFrame("New Author");
        frame.setContentPane(new EditAuthorGUI(path).editAuthorPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
    }

    public EditAuthorGUI(String path){
        authorNameField.setText("");

        saveAuthorButton.addActionListener(new ActionListener() {  // Calls the process to save the new author
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditAuthor(path, -1, authorNameField.getText());  // Handles saving the data to the databse
                frame.dispose();
            }
        });

    }

    public static void editAuthorGUI(JTable table, int row, String path) {
        // Creates the GUI to edit books
        frame = new JFrame("Edit Author ID: " + table.getValueAt(row, 0));
        frame.setContentPane(new EditAuthorGUI(table, row, path).editAuthorPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
    }

    public EditAuthorGUI(JTable table, int row, String path){
        int id = Integer.parseInt(table.getValueAt(row, 0).toString());
        String name = (String) table.getValueAt(row, 1);

        // Populate the fields with existing data
        authorNameField.setText(name);

        saveAuthorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditAuthor(path, id, name); // Handles saving new info to the database
                frame.dispose();
            }
        });

    }
}
