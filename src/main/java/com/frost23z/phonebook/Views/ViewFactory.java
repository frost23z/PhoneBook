package com.frost23z.phonebook.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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
            stage.setTitle("Phone Book");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
