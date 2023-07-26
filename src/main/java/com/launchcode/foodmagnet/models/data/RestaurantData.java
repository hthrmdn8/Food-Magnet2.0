package com.launchcode.foodmagnet.models.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.launchcode.foodmagnet.models.data.jsonparsing.JsonMapper;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;

public class RestaurantData {


    private static String apiKey = "AIzaSyDEZTRZbutBSK8bPHkTv4ion_kPCKGSTIc";
    private static HttpClient client = HttpClient.newHttpClient();

    //Takes in place_id argument, returns usable restaurant pojo with photos and information about the restaurant
    public static Restaurant getRestaurant() throws URISyntaxException, IOException, InterruptedException {

        HttpResponse<String> response = getPlaceDetails();

        JsonNode node = JsonMapper.parse(response.body());
        JsonNode result = node.get("result");
        Restaurant restaurant = JsonMapper.fromJson(result, Restaurant.class);

        return restaurant;
    }

    //pareses json response from nearbySearch method into an array of Restaurant pojos
    public static ArrayList<Restaurant> getRestaurantList() throws IOException, InterruptedException {

        HttpResponse<String> response = nearbySearch();

        JsonNode node = JsonMapper.parse(response.body());
        JsonNode results = node.get("results");

        ArrayList<Restaurant> restaurants = new ArrayList<>();

        for (JsonNode result : results) {
            Restaurant restaurant = JsonMapper.fromJson(result, Restaurant.class);
            restaurants.add(restaurant);
        }

        return restaurants;
    }

    //parses response from placePhotoRequest into url linking a jpeg image
    public static String getPhoto(String photoReference) throws IOException, InterruptedException, URISyntaxException {

        HttpResponse<String> photoResponse = placePhotoRequest(photoReference);
        Document doc = Jsoup.parse(photoResponse.body());
        String htmlAttachment = String.valueOf(doc.getElementsByTag("A").unwrap());
        String imageUrl = htmlAttachment.substring(9, htmlAttachment.length() - 6);

        return imageUrl;
    }

    //builds Place Details request with place_id argument and returns an HttpResponse<String>
    public static HttpResponse<String> getPlaceDetails() throws URISyntaxException, IOException, InterruptedException {

        URI build = new URIBuilder()
                .setScheme("https")
                .setHost("maps.googleapis.com")
                .setPath("maps/api/place/details/json")
                .addParameter("place_id", "ChIJ-SF4shmz2IcROVewwNljeZQ")
                .addParameter("fields", "name,photos")
                .addParameter("key", apiKey)
                .build();

        HttpRequest detailsRequest = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(build)
                .build();

        HttpResponse<String> response = client.send(detailsRequest, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    //sends get request to google's place search for restaurants within a specified area, returns json with details about each restaurant
    public static HttpResponse<String> nearbySearch() throws IOException, InterruptedException {

        final String POSTS_API_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=38.627003%2C-90.199402&radius=500&type=restaurant&fields=name%2Cplace_id&key=AIzaSyDEZTRZbutBSK8bPHkTv4ion_kPCKGSTIc";

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(POSTS_API_URL))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    /* builds uri for placing a photo request with photoReference argument,
       sends request and returns html string with url to the image included */
    public static HttpResponse<String> placePhotoRequest(String photoReference) throws IOException, InterruptedException, URISyntaxException {

        URI build = new URIBuilder()
                .setScheme("https")
                .setHost("maps.googleapis.com")
                .setPath("maps/api/place/photo")
                .addParameter("maxwidth", "1600")
                .addParameter("photo_reference", photoReference)
                .addParameter("key", apiKey)
                .build();

        //final String coolPicture = "Aaw_FcIGfI9V8MCQ-nfshJomgBM51BPK6jK86LUfLh7t761SEVV3Rq9XklL-kZYFsfW-maaM8ScuTNWPmMbglwwk2vUPysEWer9NUo3P3HIvxZnqH-BSwp64P9jRedzAzhT5VRIQVsi8bdnKr64BKpa1QI6uX0Pk9QagyJa1DoR1QooXt0n3";
        //final String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=Aap_uEA7vb0DDYVJWEaX3O-AtYp77AaswQKSGtDaimt3gt7QCNpdjp1BkdM6acJ96xTec3tsV_ZJNL_JP-lqsVxydG3nh739RE_hepOOL05tfJh2_ranjMadb3VoBYFvF0ma6S24qZ6QJUuV6sSRrhCskSBP5C1myCzsebztMfGvm7ij3gZT&key=AIzaSyDEZTRZbutBSK8bPHkTv4ion_kPCKGSTIc";

        HttpRequest photoRequest = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(build)
                .build();

        HttpResponse<String> photoResponse = client.send(photoRequest, HttpResponse.BodyHandlers.ofString());

        return photoResponse;
    }
}
