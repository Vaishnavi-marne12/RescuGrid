package com.rescuegrid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileStorage {
    private static final String FILE_NAME = "data/requests.txt";

    // Writes every request as one line of text
    public static void save(ArrayList<Request> requests) {
        new File("data").mkdir();   // creates the data folder if it is missing
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME));
            for (Request r : requests) {
                writer.println(r.getType() + "," + r.getId() + "," + r.getLocation() + ","
                        + r.getPeople() + "," + r.isVerified() + "," + r.getStatus() + ","
                        + r.getExtra());
            }
            writer.close();
            System.out.println("Data saved to file.");
        } catch (Exception e) {
            System.out.println("Could not save: " + e.getMessage());
        }
    }

    // Reads the file line by line and rebuilds the request objects
    public static ArrayList<Request> load() {
        ArrayList<Request> list = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return list;   // nothing saved yet
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0];
                int id = Integer.parseInt(parts[1]);
                String location = parts[2];
                int people = Integer.parseInt(parts[3]);
                boolean verified = Boolean.parseBoolean(parts[4]);
                String status = parts[5];

                Request r = null;
                if (type.equals("FLOOD")) {
                    r = new FloodRequest(id, location, people, Integer.parseInt(parts[6]));
                } else if (type.equals("MEDICAL")) {
                    r = new MedicalRequest(id, location, people, Boolean.parseBoolean(parts[6]));
                }

                if (r != null) {
                    r.setVerified(verified);
                    r.setStatus(status);
                    list.add(r);
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Could not load: " + e.getMessage());
        }
        return list;
    }
}