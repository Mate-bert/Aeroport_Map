package com.example.aeroport_map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class World {
    private List<Aeroport> list;

    public World(String fileName) {
        list = new ArrayList<>();
        try {
            BufferedReader buf = new BufferedReader(new FileReader(fileName));
            String s = buf.readLine(); // Ignorer la première ligne si c'est un en-tête
            while (s != null) {
                s = s.replaceAll("\"", ""); // Enlever les guillemets dans les champs
                String fields[] = s.split(",");
                if (fields.length > 4 && fields[1].equals("large_airport")) { // Vérifie si c'est un large_airport
                    // Crée un nouvel objet Aeroport
                    String iata = fields[9];
                    String name = fields[2];
                    String country = fields[5];
                    double latitude = Double.parseDouble(fields[12]);
                    double longitude = Double.parseDouble(fields[11]);

                    Aeroport aeroport = new Aeroport(iata, name, country, latitude, longitude);
                    list.add(aeroport);
                }
                s = buf.readLine();
            }
            buf.close();
        } catch (Exception e) {
            System.out.println("Maybe the file isn't there?");
            e.printStackTrace();
        }
    }

    public List<Aeroport> getList() {
        return list;
    }

    // Méthode pour tester le chargement des aéroports
    public static void main(String[] args) {
        World world = new World("./data/airport-codes_no_comma.csv");
        System.out.println("Nombre d'aéroports chargés : " + world.getList().size());
        for (Aeroport aeroport : world.getList()) {
            System.out.println(aeroport);
        }
    }

    public double distance(double lon1, double lat1, double lon2, double lat2) {
        double theta1 = Math.toRadians(lat1);
        double phi1 = Math.toRadians(lon1);
        double theta2 = Math.toRadians(lat2);
        double phi2 = Math.toRadians(lon2);

        double distance = Math.pow(theta2 - theta1, 2) +
                Math.pow((phi2 - phi1) * Math.cos((theta2 + theta1) / 2), 2);

        return Math.sqrt(distance); // retourne la norme simplifiée
    }

    public Aeroport findNearest(double longitude, double latitude) {
        Aeroport nearest = null;
        double minDistance = Double.MAX_VALUE;
        double earthRadius = 6371.0; // Rayon de la Terre en kilomètres

        for (Aeroport airport : list) { // Utilise "list" au lieu de "aeroportList"
            double airportLat = Math.toRadians(airport.getLatitude());
            double airportLon = Math.toRadians(airport.getLongitude());
            double clickLat = Math.toRadians(latitude);
            double clickLon = Math.toRadians(longitude);

            // Calcul de la distance haversine
            double deltaLat = clickLat - airportLat;
            double deltaLon = clickLon - airportLon;

            double a = Math.pow(Math.sin(deltaLat / 2), 2)
                    + Math.cos(airportLat) * Math.cos(clickLat) * Math.pow(Math.sin(deltaLon / 2), 2);

            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = earthRadius * c;

            // Mise à jour du minimum
            if (distance < minDistance) {
                minDistance = distance;
                nearest = airport;
            }
        }

        return nearest;
    }

    public Aeroport findByCode(String code) {
        for (Aeroport aeroport : list) {
            if (aeroport.getIATA().equalsIgnoreCase(code)) {
                return aeroport;
            }
        }
        return null; // Si aucun aéroport trouvé avec ce code
    }
}
