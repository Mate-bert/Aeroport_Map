package com.example.aeroport_map;

public class MainTest {
    public static void main(String[] args) {
        String filePath = "src/main/resources/Data/airport-codes_no_comma.csv";
        World w = new World(filePath);

        System.out.println("Found "+w.getList().size()+" airports.");
        Aeroport paris = w.findNearest(2.316,48.866);
        Aeroport cdg = w.findByCode("CDG");
        double distance = w.distance(2.316,48.866,paris.getLongitude(),paris.getLatitude());
        System.out.println(paris);
        System.out.println(distance);
        double distanceCDG = w.distance(2.316,48.866,cdg.getLongitude(),cdg.getLatitude());
        System.out.println(cdg);
        System.out.println(distanceCDG);
    }
}
