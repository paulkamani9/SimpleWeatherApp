package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class WeatherApp {
    // Fetches weather data JSON for a given location name
    public static JSONObject getWeatherData(String locationName) {
        JSONArray locationData = getLocationData(locationName);
        // Retrieve the first matching location object

        JSONObject location = (JSONObject) locationData.get(0);
        double latitude = (double) location.get("latitude");
        double longitude = (double) location.get("longitude");
        String country = (String) location.get("country");
        String name = (String) location.get("name");

        // Build URL for weather forecast API with selected parameters
        String urlString = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude
                + "&hourly=temperature_2m,weather_code,wind_speed_10m,relative_humidity_2m&timezone=auto";

        try {
            HttpURLConnection conn = fetchApiResponse(urlString);

            // Check HTTP response code
            if (conn.getResponseCode() != 200) {
                System.out.println("Error: Could not connect to API");
                return null;
            }

            // Read the JSON response
            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(conn.getInputStream());
            while (scanner.hasNext()) {
                resultJson.append(scanner.nextLine());
            }
            scanner.close();
            conn.disconnect();

            // Parse JSON response
            JSONParser parser = new JSONParser();
            JSONObject resultsJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));
            JSONObject hourly = (JSONObject) resultsJsonObj.get("hourly");

            // Find the current hour index in the time array
            JSONArray time = (JSONArray) hourly.get("time");
            int index = findIndexOfCurrentTime(time);

            // Extract temperature, weather code, humidity, windspeed for current hour
            JSONArray temperatureData = (JSONArray) hourly.get("temperature_2m");
            double temperature = (double) temperatureData.get(index);

            JSONArray weathercode = (JSONArray) hourly.get("weather_code");
            String weatherCondition = convertWeatherCode((long) weathercode.get(index));

            JSONArray relativeHumidity = (JSONArray) hourly.get("relative_humidity_2m");
            long humidity = (long) relativeHumidity.get(index);

            JSONArray windspeedData = (JSONArray) hourly.get("wind_speed_10m");
            double windspeed = (double) windspeedData.get(index);

            // Build JSON object to return
            JSONObject weatherData = new JSONObject();
            weatherData.put("temperature", temperature);
            weatherData.put("weather_condition", weatherCondition);
            weatherData.put("humidity", humidity);
            weatherData.put("windspeed", windspeed);
            weatherData.put("country", country);
            weatherData.put("name", name);

            return weatherData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Fetches location data (lat, lon) for the given place name
    public static JSONArray getLocationData(String locationName) {
        locationName = locationName.replaceAll(" ", "+");
        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" + locationName
                + "&count=10&language=en&format=json";

        try {
            HttpURLConnection conn = fetchApiResponse(urlString);

            if (conn.getResponseCode() != 200) {
                System.out.println("Error: Could not connect to API");
                return null;
            }

            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(conn.getInputStream());
            while (scanner.hasNext()) {
                resultJson.append(scanner.nextLine());
            }
            scanner.close();
            conn.disconnect();

            JSONParser parser = new JSONParser();
            JSONObject resultsJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));

            return (JSONArray) resultsJsonObj.get("results");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Creates HTTP connection and sets GET method
    private static HttpURLConnection fetchApiResponse(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            return conn;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Finds the index in the time array that matches the current hour
    private static int findIndexOfCurrentTime(JSONArray timeList) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");
        String formattedDateTime = currentDateTime.format(formatter);

        for (int i = 0; i < timeList.size(); i++) {
            String time = (String) timeList.get(i);
            if (time.equalsIgnoreCase(formattedDateTime)) {
                return i;
            }
        }
        return 0;
    }

    // Converts weather code number into a string description
    private static String convertWeatherCode(long weathercode){
        String weatherCondition = "";
        if(weathercode == 0L){
            weatherCondition = "Clear";
        } else if(weathercode <= 3L && weathercode > 0L){
            weatherCondition = "Cloudy";
        } else if ((weathercode >= 51L && weathercode <=67L) || (weathercode >=80L && weathercode <=99L)){
            weatherCondition = "Rain";
        } else if(weathercode >= 71L && weathercode <=77L) {
            weatherCondition = "Snow";
        }
        return weatherCondition;
    }
}
