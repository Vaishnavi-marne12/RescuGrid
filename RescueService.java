package com.rescuegrid;

import java.util.ArrayList;

public class RescueService {
    private ArrayList<Request> requests = new ArrayList<>();
    private ArrayList<Shelter> shelters = new ArrayList<>();
    private ArrayList<Volunteer> volunteers = new ArrayList<>();

    public void addRequest(Request r) { requests.add(r); }
    public void addShelter(Shelter s) { shelters.add(s); }
    public void addVolunteer(Volunteer v) { volunteers.add(v); }

    public ArrayList<Request> getRequests() { return requests; }
    public ArrayList<Shelter> getShelters() { return shelters; }
    public ArrayList<Volunteer> getVolunteers() { return volunteers; }

    // Sorts a copy of the list so the most urgent request comes first
    public ArrayList<Request> getSortedByUrgency() {
        ArrayList<Request> sorted = new ArrayList<>(requests);
        for (int i = 0; i < sorted.size() - 1; i++) {
            for (int j = 0; j < sorted.size() - i - 1; j++) {
                if (sorted.get(j).getUrgency() < sorted.get(j + 1).getUrgency()) {
                    Request temp = sorted.get(j);
                    sorted.set(j, sorted.get(j + 1));
                    sorted.set(j + 1, temp);
                }
            }
        }
        return sorted;
    }

    public boolean verifyRequest(int id) {
        for (Request r : requests) {
            if (r.getId() == id) {
                r.setVerified(true);
                return true;
            }
        }
        return false;
    }

    // Overloading: same name, different parameters
    public ArrayList<Request> find(String location) {
        ArrayList<Request> result = new ArrayList<>();
        for (Request r : requests) {
            if (r.getLocation().equalsIgnoreCase(location)) {
                result.add(r);
            }
        }
        return result;
    }

    public ArrayList<Request> find(int minUrgency) {
        ArrayList<Request> result = new ArrayList<>();
        for (Request r : requests) {
            if (r.getUrgency() >= minUrgency) {
                result.add(r);
            }
        }
        return result;
    }

    public boolean assignVolunteer(int requestId) {
        for (Request r : requests) {
            if (r.getId() == requestId) {
                for (Volunteer v : volunteers) {
                    if (v.isAvailable()) {
                        v.setAvailable(false);
                        r.setStatus("ASSIGNED");
                        System.out.println("Assigned " + v.getName() + " to request #" + requestId);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}