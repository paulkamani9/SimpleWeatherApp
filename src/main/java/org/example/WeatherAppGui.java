package org.example;

import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAppGui extends JFrame {
    private JSONObject weatherData; // Holds the fetched weather data

    public WeatherAppGui() {
        super("Weather App");

        setDefaultCloseOperation(EXIT_ON_CLOSE); // Closes app on exit

        setSize(450, 650); // Sets the size of the window
        setLocationRelativeTo(null); // Centers the window

        setLayout(null); // Using absolute positioning

        setResizable(false); // Disable resizing

        addGuiComponents(); // Call method to add components

    }

    // to add components
    private void addGuiComponents() {
        // Search text field for user input
        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(15, 15, 351, 45);

        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(searchTextField);

        // Label to show current location
        JLabel locationLabel =  new JLabel("Search a location above");
        locationLabel.setBounds(0, 75, 450, 54);
        locationLabel.setFont(new Font("Dialog",Font.BOLD, 24));
        locationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(locationLabel);

        // Image label for weather condition icon
        JLabel weatherConditionImage = new JLabel(loadImage("src/assets/cloudy.png"));
        weatherConditionImage.setBounds(0, 125, 450, 217);
        add(weatherConditionImage);

        // Label to display temperature
        JLabel temperatureText = new JLabel("_ _ C");
        temperatureText.setBounds(0, 350, 450, 54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        // Label to describe weather condition
        JLabel weatherConditionsDesc = new JLabel("Cloudy");
        weatherConditionsDesc.setBounds(0, 405, 450, 36);
        weatherConditionsDesc.setFont(new Font("Dialog", Font.PLAIN, 32));
        weatherConditionsDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionsDesc);

        // Humidity image and text
        JLabel humidityImage = new JLabel(loadImage("src/assets/humidity.png"));
        humidityImage.setBounds(15, 500, 74, 66);
        add(humidityImage);

        JLabel humidityText = new JLabel("<html><b>Humidity</b> __%</html>");
        humidityText.setBounds(90, 500, 85, 55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humidityText);

        // Windspeed image and text
        JLabel windSpeedImage = new JLabel(loadImage("src/assets/windspeed.png"));
        windSpeedImage.setBounds(220, 500, 74, 66);
        add(windSpeedImage);

        JLabel windSpeedText = new JLabel("<html><b>WindSpeed</b> __km/h</html>");
        windSpeedText.setBounds(310, 500, 85, 55);
        windSpeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(windSpeedText);

        // Search button triggers fetching and updating weather data
        JButton searchButton = new JButton(loadImage("src/assets/search.png"));

        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 13, 47, 45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = searchTextField.getText();
                if (userInput.replaceAll("\\s", "").length() <= 0) return;

                weatherData = WeatherApp.getWeatherData(userInput);


                // Update weather condition image based on fetched data

                String weatherCondition = (String) weatherData.get("weather_condition");

                switch(weatherCondition){
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("src/assets/clear.png"));
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("src/assets/cloudy.png"));
                        break;
                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("src/assets/rain.png"));
                        break;
                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("src/assets/snow.png"));
                        break;
                }

                // Update temperature
                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(temperature + " C");

                // Update description
                weatherConditionsDesc.setText(weatherCondition);

                // Update humidity
                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                // Update windspeed
                double windspeed = (double) weatherData.get("windspeed");
                windSpeedText.setText("<html><b>WindSpeed</b> " + windspeed + "km/h</html>");


            }
        });
        add(searchButton);


    }

    // Loads an image from file and returns as ImageIcon
    private ImageIcon loadImage(String assetPath) {
        try {
            BufferedImage image = ImageIO.read(new File(assetPath));

            return new ImageIcon(image);
        } catch (IOException error) {
            error.printStackTrace();
        }

        System.out.println("Could not find resource");
        return null;
    }
}