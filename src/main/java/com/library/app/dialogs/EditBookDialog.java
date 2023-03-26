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

public class EditBookDialog extends JDialog {

    private JTextField titleField;
    private JTextField authorField;
    private JTextField genreField;
    private JTextField yearField;
    private JButton saveButton;
    private JButton cancelButton;
    private Book book;

    public EditBookDialog(Frame parent, Book book) {
        super(parent, "Edit Book", true);
        setLayout(new BorderLayout());

        this.book = book;

        // Initialize input fields, buttons, and other components
        titleField = new JTextField(20);
        authorField = new JTextField(20);
        genreField = new JTextField(20);
        yearField = new JTextField(20);
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        // Fill in the input fields with the book's data
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        genreField.setText(book.getGenre());
        yearField.setText(String.valueOf(book.getYear()));

        // Set action listeners for the buttons
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String author = authorField.getText();
                String genre = genreField.getText();
                int year = Integer.parseInt(yearField.getText());

                // Perform CRUD operation for editing the book
                try (Connection connection = Database.getConnection()) {
                    String query = "UPDATE books SET title = ?, author = ?, genre = ?, year = ? WHERE id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, title);
                    preparedStatement.setString(2, author);
                    preparedStatement.setString(3, genre);
                    preparedStatement.setInt(4, year);
                    preparedStatement.setInt(5, book.getId());
                    preparedStatement.executeUpdate();

                    JOptionPane.showMessageDialog(EditBookDialog.this, "Book updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(EditBookDialog.this, "Error updating book: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);

        add(fieldsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }
}
