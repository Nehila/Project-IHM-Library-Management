package com.library.app.dialogs;

import com.library.db.Database;
import com.library.model.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddBookDialog extends JDialog {

    private JTextField titleField;
    private JTextField authorField;
    private JTextField genreField;
    private JTextField yearField;
    private JButton addButton;
    private JButton cancelButton;

    public AddBookDialog(Frame parent) {
        super(parent, "Add Book", true);
        setLayout(new BorderLayout());

        // Initialize input fields, buttons, and other components
        titleField = new JTextField(20);
        authorField = new JTextField(20);
        genreField = new JTextField(20);
        yearField = new JTextField(20);
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");

        // Set action listeners for the buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String author = authorField.getText();
                String genre = genreField.getText();
                int year = Integer.parseInt(yearField.getText());

                // Perform CRUD operation for adding the book
                try (Connection connection = Database.getConnection()) {
                    String query = "INSERT INTO books (title, author, genre, year) VALUES (?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, title);
                    preparedStatement.setString(2, author);
                    preparedStatement.setString(3, genre);
                    preparedStatement.setInt(4, year);
                    preparedStatement.executeUpdate();

                    JOptionPane.showMessageDialog(AddBookDialog.this, "Book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(AddBookDialog.this, "Error adding book: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Add components to the dialog
        JPanel fieldsPanel = new JPanel(new GridLayout(4, 2));
        fieldsPanel.add(new JLabel("Title:"));
        fieldsPanel.add(titleField);
        fieldsPanel.add(new JLabel("Author:"));
        fieldsPanel.add(authorField);
        fieldsPanel.add(new JLabel("Genre:"));
        fieldsPanel.add(genreField);
        fieldsPanel.add(new JLabel("Year:"));
        fieldsPanel.add(yearField);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(addButton);
        buttonsPanel.add(cancelButton);

        add(fieldsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }
}
