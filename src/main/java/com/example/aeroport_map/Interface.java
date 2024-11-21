package com.example.aeroport_map;

import javafx.application.Application;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.input.PickResult;

public class Interface extends Application {
    private World world; // Instance de World pour accéder aux aéroports

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Terre pas plate ^^");

        // Crée une instance de Earth
        Earth earth = new Earth();

        // Charge les aéroports
        world = new World("C:\\Users\\mateo\\Tp_Java\\Aeroport_Map\\src\\main\\resources\\Data\\airport-codes_no_comma.csv");

        // Crée la scène
        Scene scene = new Scene(earth, 800, 600, true);

        // Ajoute une caméra
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-1000);
        camera.setNearClip(0.1);
        camera.setFarClip(2000.0);
        camera.setFieldOfView(35);
        scene.setCamera(camera);

        // EventHandler pour la fonctionnalité du clic droit
        scene.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                PickResult pickResult = event.getPickResult();

                if (pickResult.getIntersectedNode() != null) {
                    // Récupérer les coordonnées de texture (normalisées entre 0 et 1)
                    double x = pickResult.getIntersectedTexCoord().getX();
                    double y = pickResult.getIntersectedTexCoord().getY();

                    // Conversion des coordonnées de texture en latitude et longitude
                    double longitude = (x - 0.5) * 360; // [0, 1] -> [-180, 180]
                    double latitude = (0.5 - y) * 180;  // [0, 1] -> [-90, 90]

                    // Recherche de l'aéroport le plus proche
                    Aeroport nearest = world.findNearest(longitude, latitude);

                    // Affiche les informations
                    System.out.println("Clicked on: Latitude = " + latitude + ", Longitude = " + longitude);
                    System.out.println("Nearest Airport: " + nearest);

                    // Ajoute une sphère rouge à l'emplacement de l'aéroport
                    earth.displayRedSphere(nearest);
                }
            }
        });


        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
