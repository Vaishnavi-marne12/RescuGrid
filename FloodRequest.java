package com.rescuegrid;

public class FloodRequest extends Request {
    private int waterLevel;

    public FloodRequest(int id, String location, int people, int waterLevel) {
        super(id, location, people);
        this.waterLevel = waterLevel;
    }

    @Override
    public String getType() { return "FLOOD"; }

    @Override
    public String getExtra() { return "" + waterLevel; }

    @Override
    public int getUrgency() {
        return super.getUrgency() + waterLevel * 8;
    }
}