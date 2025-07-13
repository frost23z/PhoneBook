module com.frost23z.phonebook {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.frost23z.phonebook to javafx.fxml;
    opens com.frost23z.phonebook.Controller.Authentication to javafx.fxml;
    opens com.frost23z.phonebook.Controller.PhoneBook to javafx.fxml;
    opens com.frost23z.phonebook.Models to javafx.base;

    exports com.frost23z.phonebook;
    exports com.frost23z.phonebook.Controller.Authentication;
    exports com.frost23z.phonebook.Controller.PhoneBook;
    exports com.frost23z.phonebook.Models;
    exports com.frost23z.phonebook.Views;
}