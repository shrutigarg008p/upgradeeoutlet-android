
package com.eoutlet.Eoutlet.pojo;

import java.io.Serializable;

public class OrederPayment implements Serializable
{

    private String firstname;
    private String lastname;
    private OrderStreet street;
    private String city;
    private String country_Id;
    private String region;
    private String telephone;
    private final static long serialVersionUID = -1576023404419677489L;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public OrderStreet getStreet() {
        return street;
    }

    public void setStreet(OrderStreet street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryId() {
        return country_Id;
    }

    public void setCountryId(String countryId) {
        this.country_Id = countryId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}
