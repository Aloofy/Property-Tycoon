module us.group41.propertytycoon {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens us.group41.propertytycoon to javafx.fxml;
    exports us.group41.propertytycoon;
}