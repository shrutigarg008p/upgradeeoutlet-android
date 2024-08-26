package com.eoutlet.Eoutlet.pojo;

import java.util.HashMap;
import java.util.Map;

public class UpgradedGetAddressList {
    private String address_id;
    private String firstname;
    private String lastname;
    private String city;
    private Object area;
    private String street;
    private String telephone;
    private String country;
    private String country_id;
    private String latitude;
    private String longitude;
    private Integer default_billing;
    private Integer default_shipping;



    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Object getArea() {
        return area;
    }

    public void setArea(Object area) {
        this.area = area;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public Integer getDefault_billing() {
        return default_billing;
    }

    public void setDefault_billing(Integer default_billing) {
        this.default_billing = default_billing;
    }

    public Integer getDefault_shipping() {
        return default_shipping;
    }

    public void setDefault_shipping(Integer default_shipping) {
        this.default_shipping = default_shipping;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

}
