package junitdemo;

public class CheckoutDetails {
    public CheckoutDetails(String street, String city, String state, String country, String zipCode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
    }

    public CheckoutDetails() {
        this.street = "Schoenebergerstrasse 99";
        this.city = "Schwarzenberg";
        this.state = "Schwarzenberg";
        this.country = "Germany";
        this.zipCode = "08355";
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getZipCode() {
        return zipCode;
    }

    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}