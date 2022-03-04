module us.group41.propertytycoon {
    requires javafx.controls;
    requires javafx.fxml;


    opens us.group41.propertytycoon to javafx.fxml;
    exports us.group41.propertytycoon;
}