package com.vehiclerental.model;

public class AdminUser extends User
{
    private String adminRole;

    public AdminUser()
    {
        super();
    }

    public AdminUser(int id, String name, String email, String password, String adminRole)
    {
        super(id, name, email, password);
        this.adminRole = adminRole;
    }

    public String getAdminRole()
    {
        return adminRole;
    }

    public void setAdminRole(String adminRole)
    {
        this.adminRole = adminRole;
    }

    public void manageSystem()
    {
        System.out.println("Admin " + getName() + " is managing the system.");
    }
}
