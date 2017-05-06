package com.webianks.test.bestkick;


import java.io.Serializable;

/**
 * Created by R Ankit on 06-05-2017.
 */

class KickProject{


    private int serialNumber;
    private String blurb;
    private String by;
    private String country;
    private String state;
    private String type;
    private String currency;
    private String location;

    private int percentageFunded;
    private String url;

    private String title;
    private String pledge;
    private String backers;
    private String end_time;

    void setBackers(String backers) {
        this.backers = backers;
    }

    void setNoOfDaysToGo(String end_time) {
        this.end_time = end_time;
    }

    void setPledge(String pledge) {
        this.pledge = pledge;
    }

    void setTitle(String title) {
        this.title = title;
    }

    String getBackers() {
        return backers;
    }

    String getNoOfDaysToGo() {
        return end_time;
    }

    String getPledge() {
        return pledge;
    }

    String getTitle() {
        return title;
    }

    void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    void setBy(String by) {
        this.by = by;
    }

    void setCountry(String country) {
        this.country = country;
    }

    void setCurrency(String currency) {
        this.currency = currency;
    }

    void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    void setLocation(String location) {
        this.location = location;
    }

    void setPercentageFunded(int percentageFunded) {
        this.percentageFunded = percentageFunded;
    }

    void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    void setState(String state) {
        this.state = state;
    }

    void setType(String type) {
        this.type = type;
    }

    void setUrl(String url) {
        this.url = url;
    }

    String getEnd_time() {
        return end_time;
    }

    int getPercentageFunded() {
        return percentageFunded;
    }

    int getSerialNumber() {
        return serialNumber;
    }

    String getBlurb() {
        return blurb;
    }

    String getBy() {
        return by;
    }

    String getCountry() {
        return country;
    }

    String getCurrency() {
        return currency;
    }

    String getLocation() {
        return location;
    }

    String getState() {
        return state;
    }

    String getType() {
        return type;
    }

    String getUrl() {
        return url;
    }
}
