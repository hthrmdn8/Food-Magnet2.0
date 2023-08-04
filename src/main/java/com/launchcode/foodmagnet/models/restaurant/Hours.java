package com.launchcode.foodmagnet.models.restaurant;

public class Hours {

    private boolean open_now;

    public Hours() {}

    public boolean isOpen_now() {
        return open_now;
    }

    public void setOpen_now(boolean open_now) {
        this.open_now = open_now;
    }

    @Override
    public String toString() {
        return "Hours{" +
                "open_now=" + open_now +
                '}';
    }
}
