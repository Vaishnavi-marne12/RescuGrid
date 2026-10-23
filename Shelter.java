package com.rescuegrid;

public class Shelter {
    private String name;
    private String location;
    private int capacity;
    private int occupied = 0;

    public Shelter(String name, String location, int capacity) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
    }

    public boolean admit(int count) {
        if (occupied + count > capacity) {
            return false;
        }
        occupied = occupied + count;
        return true;
    }

    public int getAvailable() { return capacity - occupied; }

    @Override
    public String toString() {
        return name + " (" + location + ") - " + getAvailable() + " of " + capacity + " beds free";
    }
}