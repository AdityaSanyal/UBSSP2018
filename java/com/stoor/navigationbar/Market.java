package com.stoor.navigationbar;


public class Market {
    private int id;
    private String name;
    private String description;
    private double officeLocation;
    private byte[] image;

    public Market(String name,
                String description,
                double officeLocation,
                byte[] image,
                int id) {
        this.name = name;
        this.description = description;
        this.officeLocation = officeLocation;
        this.image = image;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public double getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(double officeLocation) {
        this.officeLocation = officeLocation;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
