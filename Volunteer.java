package com.rescuegrid;

public class Volunteer extends Person {
    private String skill;
    private boolean available = true;

    public Volunteer(String name, String phone, String skill) {
        super(name, phone);
        this.skill = skill;
    }

    public String getSkill() { return skill; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String getRole() { return "Volunteer"; }

    @Override
    public String toString() {
        return getRole() + ": " + getName() + " | Skill: " + skill + " | Available: " + available;
    }
}