import com.ddbb.AUTHORS;
import com.ddbb.AllBooksAuthors;
import com.ddbb.PUBLISHERS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MainGUI {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JTextField pathField;
    private JTable bookTable;
    private JTextField searchField;
    private JButton searchButton;
    private JScrollPane searchScrollPane;

    private JButton pathButton;
    private JButton createBookButton;
    private JButton createAuthorButton;
    private JButton createPublisherButton;
    private JPanel searchPanel;
    private JPanel allBooksPanel;
    private JPanel allAuthorsPanel;
    private JPanel createPanel;
    private JButton reloadButton;
    private JTable allBooksTable;
    private JScrollPane allBooksScrollPane;
    private JTable booksByAuthorTable;
    private JScrollPane booksByAuthorScrollPane;
    private JRadioButton bubbleSortRadio;
    private JRadioButton mergeSortRadio;
    private ButtonGroup radioButtonGroup;
    private JComboBox<String> sortByComboBox;
    private JPanel editAuthorsPanel;
    private JPanel editPublishersPanel;
    private JTable editAuthorsTable;
    private JScrollPane editAuthorsScrollPane;
    private JTable editPublishersTable;
    private JScrollPane editPublishersScrollPane;

    public MainGUI() {
        // Radio Buttons
        radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(bubbleSortRadio);
        radioButtonGroup.add(mergeSortRadio);
        bubbleSortRadio.setSelected(true);

        //Combo Box
        sortByComboBox.addItem("ID");
        sortByComboBox.addItem("Title");
        sortByComboBox.addItem("Author Name");

        pathButton.addActionListener(new ActionListener() { // Path for the database
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser((String) "src/main/resources");
                if (fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
                    String path = String.valueOf(fc.getSelectedFile());
                    pathField.setText(path);
                    UpdateGUITables();
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateGUITables();
            }

        });

        bookTable.addMouseListener(new MouseAdapter() {  // Allows entries to be selected to edit
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                JTable table = (JTable) e.getSource();
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);
                if(e.getClickCount() == 2 && table.getSelectedRow() != -1 && row != -1){
                    EditBooksGUI.editBookGUI(table, table.getSelectedRow(), pathField.getText());
                }
            }
        });

        editAuthorsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                super.mousePressed(e);
                JTable table = (JTable) e.getSource();
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);
                if(e.getClickCount() == 2 && table.getSelectedRow() != -1 && row != -1){
                    EditAuthorGUI.editAuthorGUI(table, table.getSelectedRow(), pathField.getText());
                }
            }
        });

        editPublishersTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                JTable table = (JTable) e.getSource();
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);
                if(e.getClickCount() == 2 && table.getSelectedRow() != -1 && row != -1){
                    EditPublisherGUI.editPublisherGUI(table, table.getSelectedRow(), pathField.getText());
                }
            }
        });

        createBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditBooksGUI.editBooksGUI(pathField.getText());
            }
        });

        createAuthorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditAuthorGUI.editAuthorGUI(pathField.getText());
            }
        });

        createPublisherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditPublisherGUI.editPublisherGUI(pathField.getText());
            }
        });

        reloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateGUITables();
            }
        });
    }

    private void populateBookListWithAuthors(List<AllBooksAuthors> bookList, JTable table) {
        // Populates the table in the GUI
        ((DefaultTableModel) table.getModel()).setRowCount(0);
        for (int i = 0; i < bookList.size(); i++) {
            Object[] par = new Object[]{
                    bookList.get(i).getId(),
                    bookList.get(i).getTITLE(),
                    bookList.get(i).getAUTHOR_NAMES(),
                    bookList.get(i).getYEAR_OF_PUBLICATION(),
                    bookList.get(i).getPUBLISHER_NAME(),
                    bookList.get(i).getSUBJECT()
            };
            ((DefaultTableModel) table.getModel()).addRow(par);
            table.setDefaultEditor(Object.class, null);
        }
    }

    private void populateIDNameList(List<IDNamePair> authorList, JTable table){
        ((DefaultTableModel) table.getModel()).setRowCount(0);
        for (int i = 0; i < authorList.size(); i++){
            Object[] par = new Object[]{
                    authorList.get(i).getId(),
                    authorList.get(i).getName()
            };
            ((DefaultTableModel) table.getModel()).addRow(par);
            table.setDefaultEditor(Object.class, null);
        }
    }


    public void UpdateGUITables(){
        // List books for search
        final List<AllBooksAuthors> bookList = CustomListConversion.Companion.searchBooksAuthorsToAllBooksAuthors(
                DatabaseController.Companion.searchBooksInDB(pathField.getText(), searchField.getText()));

        // List all books
        final List<AllBooksAuthors> allBookList = DatabaseController.Companion.allBooksWithAuthorsFromDB(
                pathField.getText());

        // List books by author
        final List<AllBooksAuthors> bookListByAuthor = DatabaseController.Companion.allBooksWithAuthorsFromDB(
                pathField.getText());

        // All Authors
        final List<IDNamePair> authorList = CustomListConversion.Companion.authorsToPair(
                DatabaseController.Companion.allAuthorsFromDB(pathField.getText()));

        //All publishers
        final List<IDNamePair> publisherList = CustomListConversion.Companion.publishersToPair(
                DatabaseController.Companion.allPublishersFromDB(pathField.getText()));


        if(bubbleSortRadio.isSelected()) {
            if (sortByComboBox.getSelectedIndex() == 0 && bookList.size() > 1) { // ID
                BubbleSort.sortBooks((ArrayList<AllBooksAuthors>) bookList, SortBy.BookID);
            }
            else if (sortByComboBox.getSelectedIndex() == 1 && bookList.size() > 1) { // Title
                BubbleSort.sortBooks((ArrayList<AllBooksAuthors>) bookList, SortBy.BookTitle);
            }
            else if (sortByComboBox.getSelectedIndex() == 2 && bookList.size() > 1) { // Author Name
                BubbleSort.sortBooks((ArrayList<AllBooksAuthors>) bookList, SortBy.BookAuthor);
            }
            BubbleSort.sortBooks((ArrayList<AllBooksAuthors>) allBookList, SortBy.BookTitle);
            BubbleSort.sortBooks((ArrayList<AllBooksAuthors>) bookListByAuthor, SortBy.BookAuthor);


        }
        else if(mergeSortRadio.isSelected()){
            if(sortByComboBox.getSelectedIndex() == 0 && bookList.size() > 1) { // ID
                MergeSort.sort((ArrayList<AllBooksAuthors>) bookList, 0, bookList.size()-1,
                        SortBy.BookID);
            }
            else if(sortByComboBox.getSelectedIndex() == 1 && bookList.size() > 1) { // Title
                MergeSort.sort((ArrayList<AllBooksAuthors>) bookList, 0, bookList.size()-1,
                        SortBy.BookTitle);
            }
            else if(sortByComboBox.getSelectedIndex() == 2 && bookList.size() > 1) { // Author Name
                MergeSort.sort((ArrayList<AllBooksAuthors>) bookList, 0, bookList.size()-1,
                        SortBy.BookAuthor);
            }
            MergeSort.sort((ArrayList<AllBooksAuthors>) allBookList, 0, allBookList.size()-1,
                    SortBy.BookTitle);
            MergeSort.sort((ArrayList<AllBooksAuthors>) bookListByAuthor, 0, allBookList.size()-1,
                    SortBy.BookAuthor);
        }

        BubbleSort.sortIDNamePair((ArrayList<IDNamePair>)authorList, SortBy.Name);
        BubbleSort.sortIDNamePair((ArrayList<IDNamePair>)publisherList, SortBy.Name);

        populateBookListWithAuthors(bookList, bookTable);
        populateBookListWithAuthors(allBookList, allBooksTable);
        populateBookListWithAuthors(bookListByAuthor, booksByAuthorTable);
        populateIDNameList(authorList, editAuthorsTable);
        populateIDNameList(publisherList, editPublishersTable);
    }


    private void createUIComponents() {
        // Creates all the tables and puts them into a scroll pane
        Object[] cols = {"id", "title", "authors" ,"year", "publisher", "subject"};
        bookTable = new JTable(new DefaultTableModel(cols, 0));
        searchScrollPane = new JScrollPane(bookTable);
        allBooksTable = new JTable(new DefaultTableModel(cols, 0));
        allBooksScrollPane = new JScrollPane(allBooksTable);
        booksByAuthorTable = new JTable(new DefaultTableModel(cols, 0));
        booksByAuthorScrollPane = new JScrollPane(booksByAuthorTable);
        Object[] idNameCols = {"id", "name"};
        editAuthorsTable = new JTable(new DefaultTableModel(idNameCols, 0));
        editAuthorsScrollPane = new JScrollPane(editAuthorsTable);
        editPublishersTable = new JTable(new DefaultTableModel(idNameCols, 0));
        editPublishersScrollPane = new JScrollPane(editPublishersTable);
    }


    public static void main(String[] args) {
        // Starts the GUI
        JFrame frame = new JFrame("Library Management System");
        frame.setContentPane(new MainGUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
