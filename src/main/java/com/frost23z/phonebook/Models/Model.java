package com.frost23z.phonebook.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class Model {
    private static Model instance;
    private Connection connection;
    private ObservableList<Contact> contacts;

    private Model() {
        contacts = FXCollections.observableArrayList();
        initializeDatabase();
        loadContacts();
    }

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    private void initializeDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:phonebook.db");
            createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables() {
        String createContactsTable = """
                    CREATE TABLE IF NOT EXISTS contacts (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT NOT NULL,
                        phone TEXT NOT NULL,
                        email TEXT,
                        address TEXT
                    )
                """;

        String createUsersTable = """
                    CREATE TABLE IF NOT EXISTS users (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        username TEXT UNIQUE NOT NULL,
                        password TEXT NOT NULL
                    )
                """;

        try {
            Statement statement = connection.createStatement();
            statement.execute(createContactsTable);
            statement.execute(createUsersTable);

            // Create default admin user if not exists
            String checkAdmin = "SELECT COUNT(*) FROM users WHERE username = 'admin'";
            ResultSet rs = statement.executeQuery(checkAdmin);
            if (rs.next() && rs.getInt(1) == 0) {
                String insertAdmin = "INSERT INTO users (username, password) VALUES ('admin', 'admin')";
                statement.execute(insertAdmin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean authenticateUser(String username, String password) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerUser(String username, String password) {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void loadContacts() {
        contacts.clear();
        String query = "SELECT * FROM contacts ORDER BY name";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Contact contact = new Contact(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                );
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addContact(Contact contact) {
        String query = "INSERT INTO contacts (name, phone, email, address) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, contact.getName());
            statement.setString(2, contact.getPhone());
            statement.setString(3, contact.getEmail());
            statement.setString(4, contact.getAddress());
            statement.executeUpdate();
            loadContacts(); // Refresh the list
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateContact(Contact contact) {
        String query = "UPDATE contacts SET name = ?, phone = ?, email = ?, address = ? WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, contact.getName());
            statement.setString(2, contact.getPhone());
            statement.setString(3, contact.getEmail());
            statement.setString(4, contact.getAddress());
            statement.setInt(5, contact.getId());
            statement.executeUpdate();
            loadContacts(); // Refresh the list
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteContact(int contactId) {
        String query = "DELETE FROM contacts WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, contactId);
            statement.executeUpdate();
            loadContacts(); // Refresh the list
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ObservableList<Contact> getContacts() {
        return contacts;
    }

    public static class Contact {
        private int id;
        private String name;
        private String phone;
        private String email;
        private String address;

        public Contact(int id, String name, String phone, String email, String address) {
            this.id = id;
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.address = address;
        }

        public Contact(String name, String phone, String email, String address) {
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.address = address;
        }

        // Getters and setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        @Override
        public String toString() {
            return name + " - " + phone;
        }
    }
}
