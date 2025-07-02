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
    private JSONObject weatherData;

    public WeatherAppGui() {
        super("Weather App");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(450, 650);
        setLocationRelativeTo(null);

        setLayout(null);

        setResizable(false);

        addGuiComponents();

    }

    // to add components
    private void addGuiComponents() {
        //search field
        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(15, 15, 351, 45);

        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(searchTextField);

        // location label
        JLabel locationLabel =  new JLabel("Search a location above");
        locationLabel.setBounds(0, 75, 450, 54);
        locationLabel.setFont(new Font("Dialog",Font.BOLD, 24));
        locationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(locationLabel);

        // weather image
        JLabel weatherConditionImage = new JLabel(loadImage("src/assets/cloudy.png"));
        weatherConditionImage.setBounds(0, 125, 450, 217);
        add(weatherConditionImage);

        // temperature text
        JLabel temperatureText = new JLabel("_ _ C");
        temperatureText.setBounds(0, 350, 450, 54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        // weather conditions description
        JLabel weatherConditionsDesc = new JLabel("Cloudy");
        weatherConditionsDesc.setBounds(0, 405, 450, 36);
        weatherConditionsDesc.setFont(new Font("Dialog", Font.PLAIN, 32));
        weatherConditionsDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionsDesc);

        // humidity image
        JLabel humidityImage = new JLabel(loadImage("src/assets/humidity.png"));
        humidityImage.setBounds(15, 500, 74, 66);
        add(humidityImage);

        // humidity text
        JLabel humidityText = new JLabel("<html><b>Humidity</b> __%</html>");
        humidityText.setBounds(90, 500, 85, 55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humidityText);

        // windSpeed image
        JLabel windSpeedImage = new JLabel(loadImage("src/assets/windspeed.png"));
        windSpeedImage.setBounds(220, 500, 74, 66);
        add(windSpeedImage);

        // windSpeed text
        JLabel windSpeedText = new JLabel("<html><b>Windspeed</b> __km/h</html>");
        windSpeedText.setBounds(310, 500, 85, 55);
        windSpeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(windSpeedText);

        //search button
        JButton searchButton = new JButton(loadImage("src/assets/search.png"));

        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 13, 47, 45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = searchTextField.getText();
                if (userInput.replaceAll("\\s", "").length() <= 0) return;

                weatherData = WeatherApp.getWeatherData(userInput);


                // update gui

                // weather image
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

                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(temperature + " C");

                weatherConditionsDesc.setText(weatherCondition);

                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                double windspeed = (double) weatherData.get("windspeed");
                windSpeedText.setText("<html><b>WindSpeed</b> " + windspeed + "km/h</html>");


            }
        });
        add(searchButton);


    }

    // to add images to our gui components
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