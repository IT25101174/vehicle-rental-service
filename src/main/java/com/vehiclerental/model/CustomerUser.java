package com.vehiclerental.model;

public class CustomerUser extends User
{
    private String customerType;

    public CustomerUser()
    {
        super();
    }

    public CustomerUser(int id, String name, String email, String password, String customerType)
    {
        super(id, name, email, password, "customer");
        this.customerType = customerType;
    }

    public String getCustomerType()
    {
        return customerType;
    }

    public void setCustomerType(String customerType)
    {
        this.customerType = customerType;
    }

    public void bookVehicle()
    {
        System.out.println("Customer " + getName() + " is booking a vehicle.");
    }
}
