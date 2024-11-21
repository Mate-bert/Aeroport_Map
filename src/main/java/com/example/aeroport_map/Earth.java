package com.example.aeroport_map;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

public class Earth extends Group {
    private Group rotatingGroup; // Groupe pour la Terre et les sphères qui tournent
    private Sphere sphere;
    private Rotate ry; // Rotation autour de l'axe Y

    public Earth() {
        // Crée un groupe pour les objets tournants
        rotatingGroup = new Group();

        // Crée une sphère de rayon 300 pixels
        sphere = new Sphere(300);

        // Applique une texture
        PhongMaterial earthMaterial = new PhongMaterial();
        earthMaterial.setDiffuseMap(new Image("file:src/main/resources/Data/earth_lights_4800.png")); // Texture de la Terre
        sphere.setMaterial(earthMaterial);

        // Ajoute la rotation autour de l'axe Y
        ry = new Rotate(0, Rotate.Y_AXIS);
        rotatingGroup.getTransforms().add(ry);

        // Ajoute la sphère Terre au groupe tournant
        rotatingGroup.getChildren().add(sphere);

        // Ajoute le groupe tournant à la scène principale
        this.getChildren().add(rotatingGroup);

        // Animation pour la rotation
        startRotation();
    }

    private void startRotation() {
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long time) {
                double angle = (time / 1_000_000_000.0) * 360 / 30; // 360° en 30 secondes
                ry.setAngle(angle % 360);
            }
        };
        animationTimer.start();
    }

    public Sphere getSphere() {
        return sphere;
    }

    // Méthode pour créer une sphère rouge pour un aéroport
    public Sphere createSphere(Aeroport a, Color color) {
        // Rayon de la Terre
        double R = 300;

        // Conversion des coordonnées géographiques en radians
        double latitude = Math.toRadians(a.getLatitude());
        double longitude = Math.toRadians(a.getLongitude());

        // Calcul des coordonnées 3D
        double x = R * Math.cos(latitude) * Math.sin(longitude);
        double y = -R * Math.sin(latitude); // Utilisez le "-" pour aligner avec la convention de JavaFX
        double z = -R * Math.cos(latitude) * Math.cos(longitude);

        // Création de la sphère colorée
        Sphere airportSphere = new Sphere(5); // Rayon de la sphère = 5
        PhongMaterial material = new PhongMaterial(color);
        airportSphere.setMaterial(material);

        // Positionnement de la sphère
        airportSphere.setTranslateX(x);
        airportSphere.setTranslateY(y);
        airportSphere.setTranslateZ(z);

        return airportSphere;
    }




    // Méthode pour afficher une sphère rouge à l'emplacement de l'aéroport le plus proche
    public void displayRedSphere(Aeroport a) {
        Sphere redSphere = createSphere(a, Color.RED);
        rotatingGroup.getChildren().add(redSphere); // Ajoute la sphère rouge au groupe tournant
    }
}
