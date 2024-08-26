
package com.eoutlet.Eoutlet.pojo;

import java.io.Serializable;


public class OrderShipping implements Serializable
{

    private String firstname;
    private String lastname;
    private OrderStreet_ street;
    private String city;
    private String countryId;
    private String region;
    private String telephone;
    private final static long serialVersionUID = -2062069208651835287L;

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

    public OrderStreet_ getStreet() {
        return street;
    }

    public void setStreet(OrderStreet_ street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
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
