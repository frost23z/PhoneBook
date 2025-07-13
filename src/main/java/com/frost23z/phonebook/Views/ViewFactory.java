package com.frost23z.phonebook.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewFactory {
    private AnchorPane loginContainer;

    public ViewFactory() {
    }

    public void loginView() {
        try {
            FXMLLoader infoLoader = new FXMLLoader(getClass().getResource("/com/frost23z/phonebook/FXML/Authentication/Info.fxml"));
            VBox info = infoLoader.load();

            FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/com/frost23z/phonebook/FXML/Authentication/Login.fxml"));
            VBox login = loginLoader.load();

            HBox hBox = new HBox(info, login);
            HBox.setHgrow(info, javafx.scene.layout.Priority.ALWAYS);
            HBox.setHgrow(login, javafx.scene.layout.Priority.ALWAYS);
            hBox.setPrefSize(800, 600);

            Scene scene = new Scene(hBox);
            Stage stage = new Stage();
            stage.setTitle("Phone Book - Login");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void phoneBookView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/frost23z/phonebook/FXML/PhoneBook/Main.fxml"));
            BorderPane root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Phone Book Manager");
            stage.setScene(scene);
            stage.setMinWidth(900);
            stage.setMinHeight(600);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
