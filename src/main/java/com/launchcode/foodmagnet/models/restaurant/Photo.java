package com.launchcode.foodmagnet.models.restaurant;

import java.util.Arrays;

public class Photo {

    private int height;
    private int width;

    private String[] html_attributions;
    private String photo_reference;

    public Photo() {}

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String[] getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(String[] html_attributions) {
        this.html_attributions = html_attributions;
    }

    public String getPhoto_reference() {
        return photo_reference;
    }

    public void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "height=" + height +
                ", width=" + width +
                ", html_attributions=" + Arrays.toString(html_attributions) +
                ", photo_reference='" + photo_reference + '\'' +
                '}';
    }
}
