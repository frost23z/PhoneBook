package com.frost23z.phonebook.Controller.Authentication;

import com.frost23z.phonebook.Models.Model;
import com.frost23z.phonebook.Views.ViewFactory;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {
    public Label lb_welcome;
    public Label lb_info;
    public TextField tf_user;
    public PasswordField tf_pass;
    public PasswordField tf_repass;
    public Button bt_action;
    public Label lb_ques;
    public Label lb_error;
    public Button bt_switch;
    public VBox tf_root;

    private Model model;
    private ViewFactory viewFactory;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = Model.getInstance();
        viewFactory = new ViewFactory();

        Platform.runLater(() -> {
            tf_user.getParent().requestFocus();
        });

        bt_action.setOnAction(e -> {
            String user = tf_user.getText().trim();
            String pass = tf_pass.getText();

            if (user.isEmpty() || pass.isEmpty()) {
                showError("Please fill in all fields");
                return;
            }

            if (bt_action.getText().equals("Login")) {
                if (model.authenticateUser(user, pass)) {
                    // Close login window and open main phone book
                    Stage stage = (Stage) bt_action.getScene().getWindow();
                    stage.close();
                    viewFactory.phoneBookView();
                } else {
                    showError("Invalid username or password");
                }
            } else {
                // Register mode
                String repass = tf_repass != null ? tf_repass.getText() : "";
                if (!pass.equals(repass)) {
                    showError("Passwords do not match");
                    return;
                }

                if (model.registerUser(user, pass)) {
                    showError("Registration successful! Please login.");
                    lb_error.setStyle("-fx-text-fill: green;");
                    switchToLogin();
                } else {
                    showError("Username already exists");
                }
            }
        });

        bt_switch.setOnAction(e -> {
            clear();
            if (bt_switch.getText().equals("Register")) {
                switchToRegister();
            } else {
                switchToLogin();
            }
        });
    }

    private void switchToRegister() {
        lb_welcome.setText("Join us!");
        lb_info.setText("Create an Account");
        bt_action.setText("Register");
        lb_ques.setText("Already have an account?");
        bt_switch.setText("Login");

        if (tf_repass == null) {
            tf_repass = new PasswordField();
            tf_repass.setPromptText("Re-enter password");
        }

        if (!tf_root.getChildren().contains(tf_repass)) {
            tf_root.getChildren().add(2, tf_repass);
        }
    }

    private void switchToLogin() {
        lb_welcome.setText("Welcome back!");
        lb_info.setText("Login to your account");
        bt_action.setText("Login");
        lb_ques.setText("Don't have an account?");
        bt_switch.setText("Register");

        if (tf_repass != null && tf_root.getChildren().contains(tf_repass)) {
            tf_root.getChildren().remove(tf_repass);
        }
    }

    private void showError(String message) {
        lb_error.setText(message);
        lb_error.setVisible(true);
        lb_error.setStyle("-fx-text-fill: red;");
    }

    void clear() {
        tf_user.clear();
        tf_pass.clear();
        if (tf_repass != null) {
            tf_repass.clear();
        }
        lb_error.setText("");
        lb_error.setVisible(false);
    }
}
