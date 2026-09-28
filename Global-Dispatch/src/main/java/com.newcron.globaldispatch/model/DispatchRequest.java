package com.newcron.globaldispatch.model;

import java.time.LocalDate;
import java.util.List;

public class DispatchRequest {

    private String shipperOrderId;
    private LocalDate pickupDate;
    private LocalDate deliveryDate;
    private double price;
    private List<Stop> stops;
    private List<Vehicle> vehicles;
    private String transportationReleaseNotes;

    public DispatchRequest() {
    }

    public String getShipperOrderId() {
        return shipperOrderId;
    }

    public void setShipperOrderId(String shipperOrderId) {
        this.shipperOrderId = shipperOrderId;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public String getTransportationReleaseNotes() {
        return transportationReleaseNotes;
    }

    public void setTransportationReleaseNotes(String transportationReleaseNotes) {
        this.transportationReleaseNotes = transportationReleaseNotes;
    }

    public static class Stop {

        private int stopNumber;
        private String city;
        private String state;
        private String postalCode;

        public Stop() {
        }

        public int getStopNumber() {
            return stopNumber;
        }

        public void setStopNumber(int stopNumber) {
            this.stopNumber = stopNumber;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }
    }

    public static class Vehicle {

        private String year;
        private String make;
        private String model;

        public Vehicle() {
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getMake() {
            return make;
        }

        public void setMake(String make) {
            this.make = make;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }
    }
}