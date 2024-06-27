module com.frost23z.phonebook {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.frost23z.phonebook to javafx.fxml;
    exports com.frost23z.phonebook;
    exports com.frost23z.phonebook.Login;
    exports com.frost23z.phonebook.Models;
    exports com.frost23z.phonebook.Views;
}