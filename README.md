# 🌤 SimpleWeatherApp

A sleek Java Swing GUI application that shows real-time weather information for any location. Powered by Open-Meteo’s free weather APIs and a lightweight geocoding service.

---

## 🚀 Features

✅ Search for any city and instantly see:
- Current temperature
- Weather condition (Clear, Cloudy, Rain, Snow)
- Humidity percentage
- Windspeed in km/h

✅ Beautiful, responsive GUI built with Java Swing

✅ Fetches live data using:
- Open-Meteo Forecast API
- Open-Meteo Geocoding API

---

## 🖥 Screenshots

| Search | Weather Info |
|--------|--------------|
| ![Search Screenshot](assets/search_screenshot.png) | ![Weather Screenshot](assets/weather_screenshot.png) |

---

## 🔧 How to Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/SimpleWeatherApp.git
   cd SimpleWeatherApp
   ```

2. **Compile the project**
   ```bash
   javac -cp .:json-simple-1.1.1.jar src/*.java
   ```

3. **Run the application**
   ```bash
   java -cp .:json-simple-1.1.1.jar src/WeatherAppGui
   ```

> 💡 Make sure to include the `json-simple` library in your classpath. You can download it from [here](https://code.google.com/archive/p/json-simple/).

---

## ⚙ Technologies Used

- Java 17
- Java Swing (GUI)
- JSON Simple (for parsing API responses)
- Open-Meteo APIs

---

## 🌍 API Credits

- [Open-Meteo Weather API](https://open-meteo.com/)
- [Open-Meteo Geocoding API](https://open-meteo.com/en/docs/geocoding-api)

---

## 📜 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## 🤝 Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

---

## ❤️ Acknowledgements

Thanks to Open-Meteo for providing free and robust weather APIs!
