package com.spstudio.entity;

/**
 * Created by sp on 14/01/2017.
 */
public class Bound {
    public double south;
    public double west;
    public double north;
    public double east;

    public Bound(){

    }

    public Bound(double south, double west, double north, double east){
        this.east = east;
        this.south = south;
        this.north = north;
        this.west = west;
    }

    public double getSouth() {
        return south;
    }
    public void setSouth(double south) {
        this.south = south;
    }
    public double getWest() {
        return west;
    }
    public void setWest(double west) {
        this.west = west;
    }
    public double getNorth() {
        return north;
    }
    public void setNorth(double north) {
        this.north = north;
    }
    public double getEast() {
        return east;
    }
    public void setEast(double east) {
        this.east = east;
    }
}