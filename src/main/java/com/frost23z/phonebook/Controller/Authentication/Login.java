package com.frost23z.phonebook.Controller.Authentication;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bt_action.setOnAction(e -> {
            String user = tf_user.getText();
            String pass = tf_pass.getText();
            if (user.equals("admin") && pass.equals("admin")) {

            } else {
                lb_error.setText("Invalid username or password");
                lb_error.setVisible(true);
            }
        });
        bt_switch.setOnAction(e -> {
            clear();
            if (bt_switch.getText().equals("Register")) {
                lb_welcome.setText("Join us!");
                lb_info.setText("Create an Account");
                bt_action.setText("Register");
                lb_ques.setText("Already have an account?");
                bt_switch.setText("Login");
                tf_repass = new PasswordField();
                tf_repass.setPromptText("Re-enter password");
                tf_root.getChildren().add(2, tf_repass);
            } else {
                lb_welcome.setText("Welcome back!");
                lb_info.setText("Login to your account");
                bt_action.setText("Login");
                lb_ques.setText("Don't have an account?");
                bt_switch.setText("Register");
                tf_root.getChildren().remove(tf_repass);
            }
        });

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
