package com.launchcode.foodmagnet.models.restaurant;

import java.util.Arrays;

public class PlaceOpeningHours {

    private Boolean open_now;

    private String[] weekday_text;

    public PlaceOpeningHours() {}

    public Boolean getOpen_now() {
        return open_now;
    }

    public void setOpen_now(Boolean open_now) {
        this.open_now = open_now;
    }

    public String[] getWeekday_text() {
        return weekday_text;
    }

    public void setWeekday_text(String[] weekday_text) {
        this.weekday_text = weekday_text;
    }

    @Override
    public String toString() {
        return "PlaceOpeningHours{" +
                "open_now=" + open_now +
                ", weekday_text=" + Arrays.toString(weekday_text) +
                '}';
    }
}
