package com.yellowbkpk.maps.map;

public class GLatLng {

    private double latitude;
    private double longitude;

    public GLatLng(double lat, double lng) {
        latitude = lat;
        longitude = lng;
    }

    public double getLatitude() {
        return latitude;
    }
    
    public double getLongitude() {
        return longitude;
    }
    
    public String toString() {
        return "("+latitude+","+longitude+")";
    }

}
