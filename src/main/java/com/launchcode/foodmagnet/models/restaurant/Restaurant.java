package com.launchcode.foodmagnet.models.restaurant;

import com.launchcode.foodmagnet.models.data.RestaurantData;

import java.util.ArrayList;
import java.util.HashMap;

public class Restaurant {

    private String name;
    private String place_id;
    private String business_status;
    private PlaceOpeningHours opening_hours;
    private String website;
    private String formatted_phone_number;
    private PlaceEditorialSummary editorial_summary;

    private int price_level;
    private int rating;
    private ArrayList<String> types;
    private int user_ratings_total;
    private String vicinity;

    private Geometry geometry;

    private String icon;
    private String icon_background_color;
    private String icon_mask_base_uri;

    private ArrayList<Photo> photos;

    private PlusCode plus_code;
    private String reference;
    private String scope;

    //Search filter fields
    private String serves_breakfast;
    private String serves_lunch;
    private String serves_dinner;

    public Restaurant() {}

    //used to demo displaying restaurant data
    public HashMap<String, Object> getAllFields() {
        HashMap<String, Object> allFields = new HashMap<>();


        allFields.put("name: ", name);
        allFields.put("place_id: ", place_id);
        allFields.put("business_status: ", business_status);
        allFields.put("opening_hours: ", opening_hours);
        allFields.put("price_level: ", price_level);
        allFields.put("rating: ", rating);
        allFields.put("types: ", types);
        allFields.put("user_ratings_total: ", user_ratings_total);
        allFields.put("vicinity: ", vicinity);
        allFields.put("geometry: ", geometry);
        allFields.put("icon: ", icon);
        allFields.put("icon_background_color: ", icon_background_color);
        allFields.put("icon_mask_base_uri: ", icon_mask_base_uri);
        allFields.put("photos: ", getAllPhotos());
        allFields.put("plus_code: ", plus_code);
        allFields.put("reference: ", reference);
        allFields.put("scope: ", scope);
        allFields.put("serves_breakfast: ", serves_breakfast);
        allFields.put("serves_lunch: ", serves_lunch);
        allFields.put("serves_dinner: ", serves_dinner);

        return allFields;
    }


    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFormatted_phone_number() {
        return formatted_phone_number;
    }

    public void setFormatted_phone_number(String formatted_phone_number) {
        this.formatted_phone_number = formatted_phone_number;
    }

    public PlaceEditorialSummary getEditorial_summary() {
        return editorial_summary;
    }

    public void setEditorial_summary(PlaceEditorialSummary editorial_summary) {
        this.editorial_summary = editorial_summary;
    }

    public int getPrice_level() {
        return price_level;
    }

    public void setPrice_level(int price_level) {
        this.price_level = price_level;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<String> types) {
        this.types = types;
    }

    public int getUser_ratings_total() {
        return user_ratings_total;
    }

    public void setUser_ratings_total(int user_ratings_total) {
        this.user_ratings_total = user_ratings_total;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public PlusCode getPlus_code() {
        return plus_code;
    }

    public void setPlus_code(PlusCode plus_code) {
        this.plus_code = plus_code;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String placeId) {
        this.place_id = placeId;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    //returns the photo_reference attribute from every Photo in this.Photos
    public ArrayList<String> getPhotoReferenceList() {
        ArrayList<String> photoReferenceList = new ArrayList<>();

        if (photos != null) {
            for (Photo photo : photos) {
                photoReferenceList.add(photo.getPhoto_reference());
            }
        }

        return photoReferenceList;

    }

    //Uses photo references to get direct URL link to each photo
    public ArrayList<String> getAllPhotos() {
        ArrayList<String> photoReferenceList = getPhotoReferenceList();
        ArrayList<String> photoURLs = new ArrayList<>();

        for (String photoReference : photoReferenceList) {
            photoURLs.add(RestaurantData.getPhoto(photoReference));

        }
        if (photoURLs.isEmpty()) photoURLs.add("No photos available");
        return photoURLs;

    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    public PlaceOpeningHours getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(PlaceOpeningHours opening_hours) {
        this.opening_hours = opening_hours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon_mask_base_uri() {
        return icon_mask_base_uri;
    }

    public void setIcon_mask_base_uri(String icon_mask_base_uri) {
        this.icon_mask_base_uri = icon_mask_base_uri;
    }

    public String getIcon_background_color() {
        return icon_background_color;
    }

    public void setIcon_background_color(String icon_background_color) {
        this.icon_background_color = icon_background_color;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getBusiness_status() {
        return business_status;
    }

    public void setBusiness_status(String business_status) {
        this.business_status = business_status;
    }

    //Getters and setters for search fields

    public String getServes_breakfast() {
        return serves_breakfast;
    }

    public void setServes_breakfast(String serves_breakfast) {
        this.serves_breakfast = serves_breakfast;
    }

    public String getServes_lunch() {
        return serves_lunch;
    }

    public void setServes_lunch(String serves_lunch) {
        this.serves_lunch = serves_lunch;
    }

    public String getServes_dinner() {
        return serves_dinner;
    }

    public void setServes_dinner(String serves_dinner) {
        this.serves_dinner = serves_dinner;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", place_id='" + place_id + '\'' +
                ", business_status='" + business_status + '\'' +
                ", opening_hours=" + opening_hours +
                ", price_level=" + price_level +
                ", rating=" + rating +
                ", types=" + types +
                ", user_ratings_total=" + user_ratings_total +
                ", vicinity='" + vicinity + '\'' +
                ", geometry=" + geometry +
                ", icon='" + icon + '\'' +
                ", icon_background_color='" + icon_background_color + '\'' +
                ", icon_mask_base_uri='" + icon_mask_base_uri + '\'' +
                ", photos=" + photos +
                ", plus_code=" + plus_code +
                ", reference='" + reference + '\'' +
                ", scope='" + scope + '\'' +
                ", serves_breakfast='" + serves_breakfast + '\'' +
                ", serves_lunch='" + serves_lunch + '\'' +
                ", serves_dinner='" + serves_dinner + '\'' +
                '}';
    }
}
