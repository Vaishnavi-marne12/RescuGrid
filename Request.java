package com.rescuegrid;

public abstract class Request implements Urgent {
    private int id;
    private String location;
    private int people;
    private boolean verified = false;
    private String status = "PENDING";

    public Request(int id, String location, int people) {
        this.id = id;
        this.location = location;
        this.people = people;
    }

    public int getId() { return id; }
    public String getLocation() { return location; }
    public int getPeople() { return people; }
    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public abstract String getType();
    public abstract String getExtra();

    @Override
    public int getUrgency() {
        int score = people * 5;
        if (verified) {
            score = score + 10;
        }
        return score;
    }

    @Override
    public String toString() {
        return "#" + id + " [" + getType() + "] " + location + " | People: " + people
                + " | Urgency: " + getUrgency() + " | Verified: " + verified + " | " + status;
    }
}