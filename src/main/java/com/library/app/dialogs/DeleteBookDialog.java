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

public class DeleteBookDialog extends JDialog {

    private JButton deleteButton;
    private JButton cancelButton;
    private Book book;

    public DeleteBookDialog(Frame parent, Book book) {
        super(parent, "Delete Book", true);
        setLayout(new BorderLayout());

        this.book = book;

        // Initialize buttons and other components
        deleteButton = new JButton("Delete");
        cancelButton = new JButton("Cancel");

        // Set action listeners for the buttons
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform CRUD operation for deleting the book
                try (Connection connection = Database.getConnection()) {
                    String query = "DELETE FROM books WHERE id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, book.getId());
                    preparedStatement.executeUpdate();

                    JOptionPane.showMessageDialog(DeleteBookDialog.this, "Book deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(DeleteBookDialog.this, "Error deleting book: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        JPanel messagePanel = new JPanel();
        messagePanel.add(new JLabel("Are you sure you want to delete the book: " + book.getTitle() + "?"));

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(cancelButton);

        add(messagePanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }
}
