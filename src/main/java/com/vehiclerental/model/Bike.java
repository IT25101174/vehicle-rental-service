package com.vehiclerental.model;

//Bike class inherits from Vehicle class
public class Bike extends Vehicle {
    private boolean hasGear; //Specific property for Bike

    public Bike(int id,String brand, String type, double pricePerDay, boolean available, boolean hasGear){
        super(id, brand, type, pricePerDay, available);//Ensure the order of parameters match with Vehicle class
        this.hasGear = hasGear;
    }

    //Getter for gear status
    public boolean getHasGear(){
        return hasGear;
    }

    //Setter for gear status
    public void setHasGear(boolean hasGear){
        this.hasGear = hasGear;
    }

}
