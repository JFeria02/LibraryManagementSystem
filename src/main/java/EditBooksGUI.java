import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditBooksGUI {

    private JPanel editBookPanel;
    private JTextField authorsEntryField;
    private JTextField publisherEntryField;
    private JTextField yearOfPublicationEntryField;
    private JTextField subjectEntryField;
    private JTextField titleEntryField;
    private JButton saveButton;

    private long id;
    private String title;
    private String authorNames;
    private int yearOfPublication;
    private String publisher;
    private String subject;

    private static JFrame frame;

    public static void editBookGUI(JTable table, int row, String path) {
        // Creates the GUI to edit books
        frame = new JFrame("Edit Book ID: " + table.getValueAt(row, 0));
        frame.setContentPane(new EditBooksGUI(table, row, path).editBookPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
    }


    public EditBooksGUI(JTable table, int row, String path){
        id = (long) table.getValueAt(row, 0);
        title = (String) table.getValueAt(row, 1);
        authorNames = (String) table.getValueAt(row, 2);
        yearOfPublication = ((Long) table.getValueAt(row, 3)).intValue();
        publisher = (String) table.getValueAt(row, 4);
        subject = (String) table.getValueAt(row, 5);

        // Populate the fields with existing data
        titleEntryField.setText(title);
        authorsEntryField.setText(authorNames);
        yearOfPublicationEntryField.setText(String.valueOf(yearOfPublication));
        publisherEntryField.setText(publisher);
        subjectEntryField.setText(subject);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditBook(path, (int) id, titleEntryField.getText(), authorsEntryField.getText(),
                        Integer.parseInt(yearOfPublicationEntryField.getText()), publisherEntryField.getText(),
                        subjectEntryField.getText()); // Handles saving new info to the database
                frame.dispose();
            }
        });

    }


    public static void editBooksGUI(String path){
        // Creates the GUI to create a new book
        frame = new JFrame("New Book");
        frame.setContentPane(new EditBooksGUI(path).editBookPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
    }

    public EditBooksGUI(String path){
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditBook(path, -1, titleEntryField.getText(), authorsEntryField.getText(),
                        Integer.parseInt(yearOfPublicationEntryField.getText()), publisherEntryField.getText(),
                        subjectEntryField.getText()); // Handles saving the new book to the database
                frame.dispose();
            }
        });
    }


}
