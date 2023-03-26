package com.library.app;

import com.library.db.Database;
import com.library.model.Book;

import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BooksTableModel extends AbstractTableModel {

    private List<Book> books = new ArrayList<>();
    private String[] columnNames = {"ID", "Title", "Author", "Genre", "Year"};

    public BooksTableModel() {
        refreshData();
    }

    public void refreshData() {
        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM books")) {

            books.clear();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String genre = resultSet.getString("genre");
                int year = resultSet.getInt("year");

                Book book = new Book(id, title, author, genre, year);
                books.add(book);
            }
            fireTableDataChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getRowCount() {
        return books.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Book book = books.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return book.getId();
            case 1:
                return book.getTitle();
            case 2:
                return book.getAuthor();
            case 3:
                return book.getGenre();
            case 4:
                return book.getYear();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Book getBookAt(int rowIndex) {
        return books.get(rowIndex);
    }

    // Add this method to the BooksTableModel class

    public void searchBooksByName(String searchText) {
        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM books WHERE title LIKE '%" + searchText + "%'")) {

            books.clear();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String genre = resultSet.getString("genre");
                int year = resultSet.getInt("year");

                Book book = new Book(id, title, author, genre, year);
                books.add(book);
            }
            fireTableDataChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
