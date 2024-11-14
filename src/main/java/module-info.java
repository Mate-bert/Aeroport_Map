module com.example.aeroport_map {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    opens com.example.aeroport_map to javafx.fxml;
    exports com.example.aeroport_map;
}