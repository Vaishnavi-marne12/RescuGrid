package com.rescuegrid;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static RescueService service = new RescueService();
    static int nextId = 1;

    public static void main(String[] args) {
        // load saved requests from the file
        for (Request r : FileStorage.load()) {
            service.addRequest(r);
            if (r.getId() >= nextId) {
                nextId = r.getId() + 1;
            }
        }

        // sample data so the demo is never empty
        service.addShelter(new Shelter("Govt School", "Kothrud", 100));
        service.addShelter(new Shelter("Community Hall", "Baner", 50));
        service.addVolunteer(new Volunteer("Amit", "9876543210", "First Aid"));
        service.addVolunteer(new Volunteer("Priya", "9123456780", "Boat Rescue"));

        int choice = -1;
        while (choice != 0) {
            System.out.println("\n===== RescueGrid =====");
            System.out.println("1. Add flood request");
            System.out.println("2. Add medical request");
            System.out.println("3. View requests (most urgent first)");
            System.out.println("4. Verify a request");
            System.out.println("5. View shelters");
            System.out.println("6. Admit people to shelter");
            System.out.println("7. View volunteers");
            System.out.println("8. Assign volunteer to request");
            System.out.println("9. Search requests by location");
            System.out.println("10. Search requests by minimum urgency");
            System.out.println("0. Save and exit");
            choice = readInt("Enter choice: ");

            switch (choice) {
                case 1: addFlood(); break;
                case 2: addMedical(); break;
                case 3: printList(service.getSortedByUrgency()); break;
                case 4: verify(); break;
                case 5: viewShelters(); break;
                case 6: admit(); break;
                case 7: viewVolunteers(); break;
                case 8: assign(); break;
                case 9: printList(service.find(readText("Location: "))); break;
                case 10: printList(service.find(readInt("Minimum urgency: "))); break;
                case 0: FileStorage.save(service.getRequests()); System.out.println("Stay safe!"); break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    static void addFlood() {
        String location = readText("Location: ");
        int people = readInt("Number of people: ");
        int level = readInt("Water level (feet): ");
        service.addRequest(new FloodRequest(nextId, location, people, level));
        System.out.println("Request #" + nextId + " added.");
        nextId++;
    }

    static void addMedical() {
        String location = readText("Location: ");
        int people = readInt("Number of people: ");
        String answer = readText("Is anyone critical? (y/n): ");
        boolean critical = answer.equalsIgnoreCase("y");
        service.addRequest(new MedicalRequest(nextId, location, people, critical));
        System.out.println("Request #" + nextId + " added.");
        nextId++;
    }

    static void verify() {
        int id = readInt("Request id to verify: ");
        if (service.verifyRequest(id)) {
            System.out.println("Request verified.");
        } else {
            System.out.println("Request not found.");
        }
    }

    static void viewShelters() {
        ArrayList<Shelter> list = service.getShelters();
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }

    static void admit() {
        viewShelters();
        int number = readInt("Shelter number: ");
        if (number < 1 || number > service.getShelters().size()) {
            System.out.println("Invalid shelter.");
            return;
        }
        int count = readInt("How many people: ");
        if (service.getShelters().get(number - 1).admit(count)) {
            System.out.println("Admitted successfully.");
        } else {
            System.out.println("Not enough space in this shelter.");
        }
    }

    static void viewVolunteers() {
        for (Volunteer v : service.getVolunteers()) {
            System.out.println(v);
        }
    }

    static void assign() {
        int id = readInt("Request id: ");
        if (!service.assignVolunteer(id)) {
            System.out.println("Could not assign (request not found or no volunteer free).");
        }
    }

    static void printList(ArrayList<Request> list) {
        if (list.isEmpty()) {
            System.out.println("No requests found.");
            return;
        }
        for (Request r : list) {
            System.out.println(r);
        }
    }

    static int readInt(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    static String readText(String message) {
        System.out.print(message);
        return sc.nextLine().trim();
    }
}