# Library Management App

A simple library management application built using Java Swing and MySQL. This application allows users to manage books in a library by adding, editing, deleting, and searching for books.

## Features

- Login authentication
- Add, edit, and delete books
- Search books by title
- Display all books in a table

## Technologies

- Java Swing for the user interface
- MySQL for the database
- JDBC for database connectivity

## Components

- `LibraryFrame.java`: The main window of the application, featuring the books table, search bar, and buttons for adding, editing, and deleting books. The books table displays all books stored in the database, and the search bar enables users to filter books by title. The add, edit, and delete buttons open their respective dialogs for managing books in the library.

- `Book.java`: A model class representing a book with its properties (ID, title, author, publisher, and publish date). This class serves as a data structure to store and manipulate book information throughout the application.

- `BooksTableModel.java`: A custom table model for displaying books in a `JTable`. This class extends `AbstractTableModel` and implements the required methods to display, update, and interact with the books data in a table format.

- `Database.java`: A utility class for database connectivity and CRUD operations. It provides methods for connecting to a MySQL database, as well as methods for adding, editing, deleting, and fetching books. It also includes a method to validate user credentials during login.

- `LoginDialog.java`: A login dialog for user authentication. Users must enter a valid username and password to access the main application. The dialog checks the provided credentials against the `users` table in the database.

- `AddBookDialog.java`: A dialog for adding a new book to the library. Users can enter the book's title, author, publisher, and publish date. Upon submission, the book information is added to the database and displayed in the books table.

- `EditBookDialog.java`: A dialog for editing an existing book's information. The selected book's details are pre-filled in the input fields, allowing users to modify them as needed. Once the changes are submitted, the updated book information is saved to the database and displayed in the books table.

- `DeleteBookDialog.java`: A confirmation dialog for deleting a book from the library. Users must confirm their intention to delete the selected book. If confirmed, the book is removed from the database and the books table is updated accordingly.

## Prerequisites

- Java JDK 8 or later
- MySQL Server 5.7 or later

## Setup

1. Clone the repository.
2. Import the project into your preferred Java IDE.
3. Set up a MySQL database and create the required tables (see `Database Setup` section below).
4. Update the `Database.java` file with your database connection details (host, port, database, username, and password).
5. Build and run the application.

## Database Setup

Create a new MySQL database and import the following SQL schema:

```sql
CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publisher VARCHAR(255) NOT NULL,
    publish_date DATE NOT NULL
);

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);
```

Add a user to the users table for testing the login functionality:

```sql
INSERT INTO users (username, password) VALUES ('admin', 'password')
```


