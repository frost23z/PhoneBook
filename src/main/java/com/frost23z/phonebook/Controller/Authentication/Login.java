package com.frost23z.phonebook.Controller.Authentication;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {
    public TextField tf_login_user;
    public PasswordField tf_login_pass;
    public Button bt_login;
    public Button bt_swith_reg;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bt_login.setOnAction(e -> {
            bt_login.setText("Logging in...");
            System.out.println("Login");
        });
    }
}
