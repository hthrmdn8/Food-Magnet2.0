package com.launchcode.foodmagnet.models.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.launchcode.foodmagnet.models.data.jsonparsing.JsonMapper;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class RestaurantData {


    private static HttpClient client = getDefaultHttpClient();

    private static HttpClient getDefaultHttpClient() {
        HttpClient defaultHttpClient = HttpClient.newHttpClient();

        return defaultHttpClient;
    }

    public static ArrayList<Restaurant> getRestaurants() throws IOException, InterruptedException {

        HttpResponse<String> response = searchFor();

        JsonNode node = JsonMapper.parse(response.body());
        JsonNode results = node.get("results");

        ArrayList<Restaurant> restaurants = new ArrayList<>();

        for (JsonNode result : results) {
            Restaurant restaurant = JsonMapper.fromJson(result, Restaurant.class);
            restaurants.add(restaurant);
        }

        return restaurants;
    }

    public static HttpResponse<String> searchFor() throws IOException, InterruptedException {

        final String POSTS_API_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=38.627003%2C-90.199402&radius=500&type=restaurant&fields=name%2Cplace_id&key=AIzaSyDEZTRZbutBSK8bPHkTv4ion_kPCKGSTIc";

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(POSTS_API_URL))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }


    public static String getPhoto() throws IOException, InterruptedException {

        URIBuilder builder = new URIBuilder()
                .setScheme("https")
                .setHost("maps.googleapis.com")
                .setPath("maps/api/place/photo")
                .addParameter("maxwidth", "1600")
                .addParameter("photo_reference", "Aaw_FcKcOv6yFJE_QXYun65EcewbTcUHqJIXhZ-X7Vpxvd52BeChWwSpZtG39PLScV9diaJ6L9Eu8717aw-2i6ZwWeeby3OErt1BUTFk3IVestx4KxqKB-RFBPsCyalR9vt7WLKcINF1yeOe72-oBaLXgWHYkqYZE5S_pUTAIHCeyNJk7DOD")
                .addParameter("key", "AIzaSyDEZTRZbutBSK8bPHkTv4ion_kPCKGSTIc");

        final String POSTS_IMAGE_URL = builder.toString();

        final String coolPicture = "Aaw_FcIGfI9V8MCQ-nfshJomgBM51BPK6jK86LUfLh7t761SEVV3Rq9XklL-kZYFsfW-maaM8ScuTNWPmMbglwwk2vUPysEWer9NUo3P3HIvxZnqH-BSwp64P9jRedzAzhT5VRIQVsi8bdnKr64BKpa1QI6uX0Pk9QagyJa1DoR1QooXt0n3";
        final String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=Aap_uEA7vb0DDYVJWEaX3O-AtYp77AaswQKSGtDaimt3gt7QCNpdjp1BkdM6acJ96xTec3tsV_ZJNL_JP-lqsVxydG3nh739RE_hepOOL05tfJh2_ranjMadb3VoBYFvF0ma6S24qZ6QJUuV6sSRrhCskSBP5C1myCzsebztMfGvm7ij3gZT&key=AIzaSyDEZTRZbutBSK8bPHkTv4ion_kPCKGSTIc";

        HttpRequest photoRequest = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(POSTS_IMAGE_URL))
                .build();

        HttpResponse<String> photoResponse = client.send(photoRequest, HttpResponse.BodyHandlers.ofString());

        Document doc = Jsoup.parse(photoResponse.body());
        String htmlAttachment = String.valueOf(doc.getElementsByTag("A").unwrap());


        return htmlAttachment.substring(9, htmlAttachment.length() - 6);
    }
}
