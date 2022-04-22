module us.group41.propertytycoon {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires org.apache.commons.text;


    opens us.group41.propertytycoon to javafx.fxml;
    exports us.group41.propertytycoon;
}