package com.frost23z.phonebook.Controller.PhoneBook;

import com.frost23z.phonebook.Models.Model;
import com.frost23z.phonebook.Views.ViewFactory;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PhoneBookMain implements Initializable {
    public TextField tf_search;
    public TableView<Model.Contact> table_contacts;
    public TableColumn<Model.Contact, String> col_name;
    public TableColumn<Model.Contact, String> col_phone;
    public TableColumn<Model.Contact, String> col_email;
    public TableColumn<Model.Contact, String> col_address;
    public Button bt_add;
    public Button bt_edit;
    public Button bt_delete;
    public Button bt_logout;
    public Label lb_total;

    private Model model;
    private ViewFactory viewFactory;
    private FilteredList<Model.Contact> filteredContacts;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = Model.getInstance();
        viewFactory = new ViewFactory();

        // Set up table columns
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("address"));

        // Set up filtered list for search functionality
        filteredContacts = new FilteredList<>(model.getContacts());
        table_contacts.setItems(filteredContacts);

        // Set up search functionality
        tf_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredContacts.setPredicate(contact -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return contact.getName().toLowerCase().contains(lowerCaseFilter) ||
                        contact.getPhone().toLowerCase().contains(lowerCaseFilter) ||
                        (contact.getEmail() != null && contact.getEmail().toLowerCase().contains(lowerCaseFilter));
            });
            updateContactCount();
        });

        // Set up button actions
        bt_add.setOnAction(e -> addContact());
        bt_edit.setOnAction(e -> editContact());
        bt_delete.setOnAction(e -> deleteContact());
        bt_logout.setOnAction(e -> logout());

        // Update contact count
        model.getContacts().addListener((javafx.collections.ListChangeListener<Model.Contact>) c -> updateContactCount());
        updateContactCount();

        // Enable/disable edit and delete buttons based on selection
        table_contacts.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            bt_edit.setDisable(newSelection == null);
            bt_delete.setDisable(newSelection == null);
        });

        bt_edit.setDisable(true);
        bt_delete.setDisable(true);
    }

    private void addContact() {
        ContactDialog dialog = new ContactDialog("Add New Contact", null);
        Optional<Model.Contact> result = dialog.showAndWait();
        result.ifPresent(contact -> {
            if (model.addContact(contact)) {
                showAlert("Success", "Contact added successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to add contact!", Alert.AlertType.ERROR);
            }
        });
    }

    private void editContact() {
        Model.Contact selectedContact = table_contacts.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            ContactDialog dialog = new ContactDialog("Edit Contact", selectedContact);
            Optional<Model.Contact> result = dialog.showAndWait();
            result.ifPresent(contact -> {
                contact.setId(selectedContact.getId());
                if (model.updateContact(contact)) {
                    showAlert("Success", "Contact updated successfully!", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error", "Failed to update contact!", Alert.AlertType.ERROR);
                }
            });
        }
    }

    private void deleteContact() {
        Model.Contact selectedContact = table_contacts.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Contact");
            alert.setHeaderText("Are you sure you want to delete this contact?");
            alert.setContentText(selectedContact.getName() + " - " + selectedContact.getPhone());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (model.deleteContact(selectedContact.getId())) {
                    showAlert("Success", "Contact deleted successfully!", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error", "Failed to delete contact!", Alert.AlertType.ERROR);
                }
            }
        }
    }

    private void logout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure you want to logout?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) bt_logout.getScene().getWindow();
            stage.close();
            viewFactory.loginView();
        }
    }

    private void updateContactCount() {
        int total = filteredContacts.size();
        lb_total.setText("Total Contacts: " + total);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Inner class for contact dialog
    private static class ContactDialog extends Dialog<Model.Contact> {
        private TextField nameField;
        private TextField phoneField;
        private TextField emailField;
        private TextArea addressField;

        public ContactDialog(String title, Model.Contact contact) {
            setTitle(title);
            setHeaderText("Enter contact information:");

            // Create form fields
            nameField = new TextField();
            nameField.setPromptText("Full Name");

            phoneField = new TextField();
            phoneField.setPromptText("Phone Number");

            emailField = new TextField();
            emailField.setPromptText("Email Address");

            addressField = new TextArea();
            addressField.setPromptText("Address");
            addressField.setPrefRowCount(3);

            // Pre-fill if editing
            if (contact != null) {
                nameField.setText(contact.getName());
                phoneField.setText(contact.getPhone());
                emailField.setText(contact.getEmail() != null ? contact.getEmail() : "");
                addressField.setText(contact.getAddress() != null ? contact.getAddress() : "");
            }

            // Create layout
            VBox content = new VBox(10);
            content.getChildren().addAll(
                    new Label("Name:"), nameField,
                    new Label("Phone:"), phoneField,
                    new Label("Email:"), emailField,
                    new Label("Address:"), addressField
            );

            getDialogPane().setContent(content);
            getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Enable/disable OK button based on required fields
            Button okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
            okButton.disableProperty().bind(
                    nameField.textProperty().isEmpty().or(phoneField.textProperty().isEmpty())
            );

            // Convert result
            setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
                    return new Model.Contact(
                            nameField.getText().trim(),
                            phoneField.getText().trim(),
                            emailField.getText().trim().isEmpty() ? null : emailField.getText().trim(),
                            addressField.getText().trim().isEmpty() ? null : addressField.getText().trim()
                    );
                }
                return null;
            });
        }
    }
}
