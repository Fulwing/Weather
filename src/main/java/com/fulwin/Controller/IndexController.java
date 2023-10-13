package com.fulwin.Controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String indexPage(Model model) throws IOException, InterruptedException {
        RestTemplate restTemplate=new RestTemplate();

        //Temperature Humidity Part

        String WEATHER_API_URL = "https://9oc0mrwy7l.execute-api.us-east-1.amazonaws.com/getData"; //This is my API data that will include all the climate data.
        String apiUrl = "YOUR_API_ENDPOINT_FROM_AWS_API_GATEWAY";
        String tempJsonResponse = restTemplate.getForObject(apiUrl, String.class);
        String weatherJsonResponse = restTemplate.getForObject(WEATHER_API_URL, String.class);
        assert tempJsonResponse != null;
        double temperature = extractTemperatureFromJson(tempJsonResponse);
        double humidity = extractHumidityFromJson(tempJsonResponse);
        temperature = formatToDecimal(temperature, 1);
        humidity = formatToDecimal(humidity, 1);
        model.addAttribute("temp", temperature);
        model.addAttribute("hum", humidity);


        //Weather part

        String description = extractDescriptionFromJson(weatherJsonResponse);
        double maxTemp = extractMaxTempFromJson(weatherJsonResponse);
        double minTemp = extractMinTempFromJson(weatherJsonResponse);

        model.addAttribute("conditions", description);
        model.addAttribute("minTemp", minTemp);
        model.addAttribute("maxTemp", maxTemp);



        return "index";
    }

    private double formatToDecimal(double value, int decimalPlaces) {
        // Format a double value to a specific number of decimal places
        String pattern = "0." + "0".repeat(Math.max(0, decimalPlaces));
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return Double.parseDouble(decimalFormat.format(value));
    }

    private double extractTemperatureFromJson(String jsonResponse) {
        return Double.parseDouble(jsonResponse.split("\"Temperature\":\"")[1].split("\"")[0]);
    }

    private double extractHumidityFromJson(String jsonResponse) {
        return Double.parseDouble(jsonResponse.split("\"Humidity\":\"")[1].split("\"")[0]);
    }

    private String extractDescriptionFromJson(String jsonResponse) {
        return new JSONObject(jsonResponse).getString("Description");
    }

    private double extractMaxTempFromJson(String jsonResponse) {
        return new JSONObject(jsonResponse).getDouble("MaxTemp");
    }

    private double extractMinTempFromJson(String jsonResponse) {
        return new JSONObject(jsonResponse).getDouble("MinTemp");
    }
}
