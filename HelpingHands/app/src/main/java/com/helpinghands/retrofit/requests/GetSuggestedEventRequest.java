package com.helpinghands.retrofit.requests;

public class GetSuggestedEventRequest {

    int month;
    String district;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
