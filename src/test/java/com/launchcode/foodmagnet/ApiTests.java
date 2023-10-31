package com.launchcode.foodmagnet;

import com.fasterxml.jackson.databind.JsonNode;
import com.launchcode.foodmagnet.models.data.RestaurantData;
import com.launchcode.foodmagnet.models.data.apiData.ApiRequests;
import com.launchcode.foodmagnet.models.data.apiData.JsonMapper;
import com.launchcode.foodmagnet.models.restaurant.Location;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
class ApiTests {

    // Nearby place search tests //

    @Test
    void placeNearbySearchRequestReturnsSuccessfulStatusCode() throws IOException, URISyntaxException, InterruptedException {
        int statusCode = ApiRequests.placeNearbySearchRequest("St. Louis").statusCode();
        assertEquals( "Response code: " + statusCode, 200, statusCode);
    }

    @Test
    void placeNearbySearchRequestReturnsStatusOK() throws IOException, URISyntaxException, InterruptedException {
        HttpResponse<String> response = ApiRequests.placeNearbySearchRequest("St. Louis");

        JsonNode node = JsonMapper.parse(response.body());
        String status = node.get("status").asText();
        System.out.println(node.get("results"));

        assertEquals("status: " + status, "OK", status);
    }

    @Test
    void getRestaurantsNearbySuccessfullyParsesJsonIntoPojos() throws IOException, URISyntaxException, InterruptedException {
        Restaurant testRestaurant = RestaurantData.getRestaurantsNearby("St. Louis").get(0);

        assertNotNull("testRestaurant name: " + testRestaurant.getName(), testRestaurant.getName());
    }


    // Place details search tests //

    @Test
    void placeDetailsRequestReturnsSuccessfulStatusCode() throws URISyntaxException, IOException, InterruptedException {
        int statusCode = ApiRequests.placeDetailsRequest("ChIJ-SF4shmz2IcROVewwNljeZQ").statusCode();

        assertEquals("Status Code: " + statusCode, 200, statusCode);
    }

    @Test
    void placeDetailsRequestReturnsStatusOK() throws URISyntaxException, IOException, InterruptedException {
        HttpResponse<String> response = ApiRequests.placeDetailsRequest("ChIJ-SF4shmz2IcROVewwNljeZQ");

        JsonNode node = JsonMapper.parse(response.body());
        String status = node.get("status").asText();
        System.out.println(node.get("results"));

        assertEquals("status: " + status, "OK", status);
    }

    @Test
    void getRestaurantDetailsSuccessfullyParsesJsonIntoPojos() throws IOException, URISyntaxException, InterruptedException {
        ArrayList<Restaurant> testRestaurantList = RestaurantData.getRestaurantsNearby("St. Louis");
        Restaurant testRestaurant = RestaurantData.getRestaurantDetails(testRestaurantList.get(0).getPlace_id());

        assertNotNull("restaurant name: " + testRestaurant.getName(), testRestaurant.getName());
    }


    // Place photo search tests //

    @Test
    void placePhotoRequestReturnsSuccessfulStatusCode() throws IOException, URISyntaxException, InterruptedException {
        int statusCode = ApiRequests.placePhotoRequest("Aaw_FcIGfI9V8MCQ-nfshJomgBM51BPK6jK86LUfLh7t761SEVV3Rq9XklL-kZYFsfW-maaM8ScuTNWPmMbglwwk2vUPysEWer9NUo3P3HIvxZnqH-BSwp64P9jRedzAzhT5VRIQVsi8bdnKr64BKpa1QI6uX0Pk9QagyJa1DoR1QooXt0n3").statusCode();

        assertEquals("Status Code: " + statusCode, 302, statusCode);
    }

    @Test
    void getPhotoSuccessfullyReturnsPhotoUrl() throws IOException, URISyntaxException, InterruptedException {
        String photoURL = RestaurantData.getPhoto("Aaw_FcIGfI9V8MCQ-nfshJomgBM51BPK6jK86LUfLh7t761SEVV3Rq9XklL-kZYFsfW-maaM8ScuTNWPmMbglwwk2vUPysEWer9NUo3P3HIvxZnqH-BSwp64P9jRedzAzhT5VRIQVsi8bdnKr64BKpa1QI6uX0Pk9QagyJa1DoR1QooXt0n3");

        assertEquals("returned URL" + photoURL, "https://lh3.googleusercontent.com/places/ANXAkqErKViTTymrIZuICNsNlqq9faMDRLsjQw5LGFjj60kDziY-piJstEXQoob-2DagLUpTLFyiQZWWwvXPnkBlMohNRL0bwr29R6s=s1600-w1600", photoURL);
    }

    // Geocoder tests //

    @Test
    void placeGeocodingRequestReturnsSuccessfulStatusCode() throws URISyntaxException, IOException, InterruptedException {
       int statusCode = ApiRequests.placeGeocodingRequest("St. Louis").statusCode();

       assertEquals("Status code: " + statusCode, 200, statusCode);
    }

    @Test
    void placeGeocodingRequestReturnsStatusOK() throws URISyntaxException, IOException, InterruptedException {
        HttpResponse<String> response = ApiRequests.placeGeocodingRequest("St. Louis");

        JsonNode node = JsonMapper.parse(response.body());
        String status = node.get("status").asText();

        assertEquals("Status: " + status, "OK", status);
    }

    @Test
    void getCoordinateSuccessfullyRetrievesLatLngFromJsonResponse() throws URISyntaxException, IOException, InterruptedException {
        Location testLocation = RestaurantData.getCoordinates("St. Louis");
        double lat = testLocation.getLat();
        double lng = testLocation.getLng();

        assertEquals("result returned from calling getCoordinates: ", testLocation.getClass(), Location.class);
        assertEquals("lat: " + lat, 38.6270025, lat);
        assertEquals("lng: " + lng, -90.19940419999999, lng);
    }



}
