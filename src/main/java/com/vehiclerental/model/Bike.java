package com.vehiclerental.model;

//Bike class inherits Vehicle
public class Bike extends Vehicle {
    private boolean hasGear; //Specific property

    public Bike(int id,String name,double price,boolean hasGear){
        super(id,name,price);
        this.hasGear = hasGear;
    }
    public boolean hasGear(){
        return hasGear;
    }
}
