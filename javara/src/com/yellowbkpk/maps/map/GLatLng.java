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

    public void setLatitude(double lat) {
        latitude = lat;
    }
    
    public void setLongitude(double lng) {
        longitude = lng;
    }

    public double distanceTo(GLatLng other) {
        double dlat = (other.latitude - latitude);
        double dlng = (other.longitude - longitude);
        return Math.sqrt(dlat*dlat + dlng*dlng);
    }

}
