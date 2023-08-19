package com.launchcode.foodmagnet.models.data.jsonData;

import com.launchcode.foodmagnet.models.data.RestaurantData;
import com.launchcode.foodmagnet.models.restaurant.Location;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class ApiRequests {

    private static HttpClient client = HttpClient.newHttpClient();

    private static String apiKey = "AIzaSyANidBPMmKkwYJU2bZvK0RMqmrMAfyxjwE";

    //builds Place Details request with place_id argument and returns a HttpResponse<String>
    public static HttpResponse<String> placeDetailsRequest(String place_id) throws URISyntaxException, IOException, InterruptedException {

        //final String demoId = "ChIJ-SF4shmz2IcROVewwNljeZQ";

        URI build = new URIBuilder()
                .setScheme("https")
                .setHost("maps.googleapis.com")
                .setPath("maps/api/place/details/json")
                .addParameter("place_id", place_id)
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
    public static HttpResponse<String> placeNearbySearchRequest(String address) throws IOException, InterruptedException, URISyntaxException {

        //final String POSTS_API_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=38.627003%2C-90.199402&radius=500&type=restaurant&fields=name%2Cplace_id&key=AIzaSyDEZTRZbutBSK8bPHkTv4ion_kPCKGSTIc";

        Location coordinates = RestaurantData.getCoordinates(address); if (coordinates == null) return null;
        final String location = coordinates.getLatString() + "," + coordinates.getLngString();

        URI build = new URIBuilder()
                .setScheme("https")
                .setHost("maps.googleapis.com")
                .setPath("maps/api/place/nearbysearch/json")
                .addParameter("location", location)
                .addParameter("radius", "500")
                .addParameter("type", "restaurant")
                .addParameter("fields", "name,place_id")
                .addParameter("key", apiKey)
                .build();


        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(build)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    //sends a nearby search request with a keyword parameter intended to specify cuisine
    public static HttpResponse<String> placeKeywordRequest(String address, String keyword) throws URISyntaxException, IOException, InterruptedException {

        Location coordinates = RestaurantData.getCoordinates(address); if (coordinates == null) return null;
        final String location = coordinates.getLatString() + "," + coordinates.getLngString();

        URI build = new URIBuilder()
                .setScheme("https")
                .setHost("maps.googleapis.com")
                .setPath("maps/api/place/nearbysearch/json")
                .addParameter("keyword", keyword)
                .addParameter("location", location)
                .addParameter("radius", "2000")
                .addParameter("type", "restaurant")
                .addParameter("fields", "name,place_id")
                .addParameter("key", apiKey)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(build)
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

    public static HttpResponse<String> placeGeocodingRequest(String address) throws URISyntaxException, IOException, InterruptedException {

        URI build = new URIBuilder()
                .setScheme("https")
                .setHost("maps.googleapis.com")
                .setPath("maps/api/geocode/json")
                .addParameter("address", address)
                .addParameter("key", apiKey)
                .build();

        HttpRequest geocodeRequest = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(build)
                .build();

        HttpResponse<String> geocodeResponse = client.send(geocodeRequest, HttpResponse.BodyHandlers.ofString());

        return geocodeResponse;
    }
}
