package com.example.aeroport_map;

public class Aeroport {
    private String IATA;
    private String Name;
    private String Country;
    private double Latitude;
    private double Longitude;

    public Aeroport(String IATA, String Name, String Country, double Latitude, double Longitude) {
        this.IATA = IATA;
        this.Country = Country;
        this.Name = Name;
        this.Longitude = Longitude;
        this.Latitude = Latitude;
    }

    public String getIATA() {
        return IATA;
    }

    public String getName() {
        return Name;
    }

    public String getCountry() {
        return Country;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public double calculDistance(Aeroport other) {
        double theta1 = Math.toRadians(this.Latitude);
        double phi1 = Math.toRadians(this.Longitude);
        double theta2 = Math.toRadians(other.Latitude);
        double phi2 = Math.toRadians(other.Longitude);

        double distance = Math.pow(theta2 - theta1, 2) +
                Math.pow((phi2 - phi1) * Math.cos((theta2 + theta1) / 2), 2);

        return Math.sqrt(distance); // retourne la norme simplifiée (sans le rayon de la Terre)
    }


    @Override
    public String toString() {
        return "Aeroport{" +
                "IATA='" + IATA + '\'' +
                ", Name='" + Name + '\'' +
                ", Country='" + Country + '\'' +
                ", Latitude=" + Latitude +
                ", Longitude=" + Longitude +
                '}';
    }



}