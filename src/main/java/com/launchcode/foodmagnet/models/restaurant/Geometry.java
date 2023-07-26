package com.launchcode.foodmagnet.models.restaurant;

public class Geometry {

    private Location location;

    private Bounds bounds;

    private Viewport viewport;

    public Geometry() {}

    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    @Override
    public String toString() {
        return "Geometry{" +
                "location=" + location +
                ", bounds=" + bounds +
                ", viewport=" + viewport +
                '}';
    }
}
