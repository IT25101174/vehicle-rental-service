package com.vehiclerental.model;

public class Vehicle {

        private int id;
        private String brand;
        private String type;
        private double pricePerDay;
        private boolean available;

        //default constructor usage
        // we want to create an object without passing any values
        public Vehicle() {

        }

        public Vehicle(int id, String brand, String type, double pricePerDay, boolean available) {
            this.id = id;
            this.brand = brand;
            this.type = type;
            this.pricePerDay = pricePerDay;
            this.available = available;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getPricePerDay() {
            return pricePerDay;
        }

        public void setPricePerDay(double pricePerDay) {
            this.pricePerDay = pricePerDay;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }
    }
