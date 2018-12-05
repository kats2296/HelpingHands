package com.helpinghands.weather_forecasting;

import java.util.ArrayList;

public class Data {

    private ArrayList<Days> Days ;

    public ArrayList<Days> getDays() {
        return Days;
    }

    public void setDays(ArrayList<Days> days) {
        Days = days;
    }

    @Override
    public String toString() {
        return "Data{" +
                "Days=" + Days +
                '}';
    }
}
