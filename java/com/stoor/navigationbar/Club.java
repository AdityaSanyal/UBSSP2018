package com.stoor.navigationbar;


public class Club {
    private int id;
    private String name;
    private String description;
    private String EstablishedYear;
    private String officeHours;
    private String officeLocation;
    private byte[] image;

    public Club(String name,
                String description,
                String EstablishedYear,
                String officeHours,
                String officeLocation,
                byte[] image,
                int id) {
        this.name = name;
        this.description = description;
        this.EstablishedYear = EstablishedYear;
        this.officeHours = officeHours;
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

    public String getEstablishedYear() {
        return EstablishedYear;
    }

    public void setEstablishedYear(String EstablishedYear) { this.EstablishedYear = EstablishedYear;
    }

    public String getOfficeHours() {
        return officeHours;
    }

    public void setOfficeHours(String officeHours) {
        this.officeHours = officeHours;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
