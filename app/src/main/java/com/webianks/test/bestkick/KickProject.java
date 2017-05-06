package com.webianks.test.bestkick;

/**
 * Created by R Ankit on 06-05-2017.
 */

class KickProject {

    private String title;
    private String pleadge;
    private String backers;
    private String noOfDaysToGo;

    public void setBackers(String backers) {
        this.backers = backers;
    }

    public void setNoOfDaysToGo(String noOfDaysToGo) {
        this.noOfDaysToGo = noOfDaysToGo;
    }

    public void setPleadge(String pleadge) {
        this.pleadge = pleadge;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackers() {
        return backers;
    }

    public String getNoOfDaysToGo() {
        return noOfDaysToGo;
    }

    public String getPleadge() {
        return pleadge;
    }

    public String getTitle() {
        return title;
    }
}
