package com.launchcode.foodmagnet.controllers;

import com.launchcode.foodmagnet.models.data.RestaurantData;
import com.launchcode.foodmagnet.models.restaurant.Restaurant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("")
public class HomeController {
    @GetMapping("")
    public String index(Model model){

        //loads photo array with restaurants from seattle
        ArrayList<Restaurant> restaurantArrayList = RestaurantData.getRestaurantsNearby("Brooklyn");
        Restaurant restaurant = RestaurantData.getRestaurantDetails(restaurantArrayList.get(2).getPlace_id());

        ArrayList<String> photos = new ArrayList<>();

//        for (Restaurant restauranta : restaurantArrayList) {
//            photos.add(restauranta.getAllPhotos().get(0));
//        }

        photos.add("https://lh3.googleusercontent.com/places/ANJU3DsdS7iA5_qnpbcehYUO7b6uQalG8qk0fRTzLuDUu0bT1hCnqcf4v0LBePkCF24-YU4WDrzGfaEO9HScrq4DJDNNCm3Te_Fgckk=s1600-w1600\n");
        photos.add("https://lh3.googleusercontent.com/places/ANJU3DsAaDjtlS8Z7Ycy9oVTuFwXB1Ln3nLTjRkDMr2DX3dXEtHvjNmqy-5KJOF7wx35KEaHBjL6DxYK5R8kPE98aC4Aq7YC5FgVbAk=s1600-w1600\n");
        photos.add("https://lh3.googleusercontent.com/places/ANJU3DvNoNyloOL_hM8IKPLCYx0SGGcVfSIIloNW0A90WKAxNIIQvRJp7Z8ZKRgzIn2kCQq7wIRoRttld3qS9e4FJkcCfpthT96-Kgw=s1600-w1600\n");
        photos.add("https://lh3.googleusercontent.com/places/ANJU3DtMFM7-Ak7cvH4T-NfeVIDX600T6bozCHY67SHwnDTqR_Od6Idy2VBQDe4uqHrlupTMg4zDFwtvTQYNAuBnmgdc17ci25nE630=s1600-w1600\n");
        photos.add("https://lh3.googleusercontent.com/places/ANJU3DuLCD2Yh79wrwcwNxrmL8AxjUyP-wpSt0eSuxBmUId3Ufor5ky-_BHAYMEqu1JDUYTr-AtylEikikNyzFLA-jEWlIJCrTSlYig=s1600-w1600\n");

        photos.add("https://lh3.googleusercontent.com/places/ANJU3DtKvBV2ezZty9_ULJRrvPZiDVeeSSMy5AlSYGyqlZ18av6XK3CxUoTiqKKsY-akzIMRBpWT0wGdR4fdNqhDYsXpOCQ6cZ-L8NI=s1600-w1600\n");
        photos.add("https://lh3.googleusercontent.com/places/ANJU3DtmgkJUpzuASz2n8cmhsnmPaB_ytqzSc-wN4HF1Gzu06eVeEhAv2aKxQD8kBC5I3UuJUJ6hqRxIXnXkRNzLTX108XXdALwpsMY=s1600-w1600\n");
        photos.add("https://lh3.googleusercontent.com/places/ANJU3DuNGJqcR7TkibELl8rblD7pyADyUTNGdRB3sLFE248RAc_G2cAqp_c9ffJr8MSYAGl3-tFqDeUn6ghY1A2awukF8fNufavxAGY=s1600-w1500\n");
        photos.add("https://lh3.googleusercontent.com/places/ANJU3Dvt4V6Nd5ivA7E5-KMSwUbDy1qmnkR3DvOC4VFPGGVxCuKtWaISWOXzCNkqjFas5_r7O9vp3sCPkOJDtk9QXX2DP3EkDzZ5Bl4=s1600-w1600\n");
        photos.add("https://lh3.googleusercontent.com/places/ANJU3Ds8PvpO56Nddg5FQVFtPxyQBmY3QvnrCBfXBYNTGxnQdElqhEi3PT-F8JSCylVKMgPLQkOQVn_vHU3CTbxxX0dPG7TQF8faAaA=s1600-w1600\n");

        photos.add("https://lh3.googleusercontent.com/places/ANJU3DtGW1j3twuYuZ4Xyf5lpRGmBvoGDhAcgubchjAEdUmd6olMGeP9u9PgFJ2yyJYQasl7oV9xvgifm-Zi2QGrBuuL-HOg6dMPp0o=s1600-w1600\n");
        photos.add("https://lh3.googleusercontent.com/places/ANJU3DsBRpfOb0a7FJ9r2RcYJI34hhEtFHwlzgz136c-HyzSZAPin7wnkl7HgGkO7WT1OKREMGQLZaGTtKvuL8DwCX5oGI9oqW3lAZY=s1600-w1600");
        //loads restaurants from la
        HashMap<String, HashMap<String, Object>> fieldMap = new HashMap<>();

        for (Restaurant restaurantb : RestaurantData.getRestaurantsNearby("Seattle")) {
            fieldMap.put(restaurantb.getName(), restaurantb.getAllFields());
        }

        model.addAttribute("photos", photos);
        model.addAttribute("restaurants", fieldMap);

        return "home";
    }
}