package com.library.app;

import com.library.app.dialogs.*;
import com.library.model.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryFrame extends JFrame {
    private BooksTableModel booksTableModel;
    private JTable booksTable;

    public LibraryFrame() {
        setTitle("Library Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the table model and table
        booksTableModel = new BooksTableModel();
        booksTable = new JTable(booksTableModel);

        // Create a scroll pane for the table
        JScrollPane tableScrollPane = new JScrollPane(booksTable);

        // Initialize buttons
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        // Initialize search bar
        JTextField searchBar = new JTextField(20);

        // Add a document listener to the search bar
        searchBar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchBooks();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchBooks();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchBooks();
            }

            private void searchBooks() {
                String searchText = searchBar.getText();
                if (searchText.isEmpty()) {
                    booksTableModel.refreshData();
                } else {
                    booksTableModel.searchBooksByName(searchText);
                }
            }
        });

        // Set action listeners for the buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddBookDialog addBookDialog = new AddBookDialog(LibraryFrame.this);
                addBookDialog.setVisible(true);
                // Refresh the table after adding a book
                refreshBooksTable();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = booksTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Book selectedBook = booksTableModel.getBookAt(selectedRow);
                    EditBookDialog editBookDialog = new EditBookDialog(LibraryFrame.this, selectedBook);
                    editBookDialog.setVisible(true);
                    // Refresh the table after editing the book
                    refreshBooksTable();
                } else {
                    JOptionPane.showMessageDialog(LibraryFrame.this, "Please select a book to edit.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = booksTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Book selectedBook = booksTableModel.getBookAt(selectedRow);
                    DeleteBookDialog deleteBookDialog = new DeleteBookDialog(LibraryFrame.this, selectedBook);
                    deleteBookDialog.setVisible(true);
                    // Refresh the table after deleting the book
                    refreshBooksTable();
                } else {
                    JOptionPane.showMessageDialog(LibraryFrame.this, "Please select a book to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Create a panel for buttons and search bar
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JPanel buttonsSubPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonsSubPanel.add(addButton);
        buttonsSubPanel.add(editButton);
        buttonsSubPanel.add(deleteButton);

        JPanel searchBarPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchBarPanel.add(new JLabel("Search:"));
        searchBarPanel.add(searchBar);

        buttonPanel.add(buttonsSubPanel, BorderLayout.WEST);
        buttonPanel.add(searchBarPanel, BorderLayout.EAST);

        // Add components to the frame
        add(buttonPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        // Refresh the table with initial data
        refreshBooksTable();
    }

    private void refreshBooksTable() {
        booksTableModel.refreshData();
    }



}
