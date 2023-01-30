package com.example.projetsystme;

public class Point {
    double longitude;
    double latitdue;
    public  Point(double latitdue,double longitude){
        this.latitdue=latitdue;
        this.longitude=longitude;
    }
    public  double getLongitude(){
        return  this.longitude;
    }

    public double getLatitdue() {
        return latitdue;
    }
}
