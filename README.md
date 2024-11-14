# Direct Supply Kata 2024 - Weather Data to Excel
## Noah Dinan

### __overview__
>For this project, I wanted the user to be able to input as many locations as they wanted
>and recieve a table showing the weather for each location. This would make comparing 
>weather in several locations trivial. I decided to use java for the project because of the
>easy to use Apache POI library for generating xlxs files, and because of its platform independence.

>The weather data pulled from the Open Meteo API is based on what data I find useful to know in
>my everyday life. One unique aspect of this is the "bikeability" scale which calculates how good
>the weather is for biking the next day based on precipitation and wind speed.



### __features__
 - pulls data for each specified location
    - current weather information
    - high and low temperatures of the day
    - weather information for tomorrow
    - high and low temperatures for tomorrow
    - current moon phase
    - time of sunrise and sunset
    - bikeability for the day
 - outputs an excel sheet containing weather data

### __usage__
>obtain or generate an API key for Google's Geolocation API
>    - visit [console.cloud.google.com](https://console.cloud.google.com/google/maps-apis/credentials)

---

>run with jarfile
>```
>java -jar kata.jar "GOOGLE_GEOLOCATION_API_KEY"
>```

---

>run with gradle
>```
>gradle run --args="GOOGLE_GEOLOCATION_API_KEY"
>```

### __projects used__
1. [open-meteo api](https://github.com/open-meteo/open-meteo)
2. [google geolocation api](https://developers.google.com/maps/documentation/geolocation/overview)
3. [apache poi](https://github.com/apache/poi) 
4. [LatLongToTimezone](https://github.com/drtimcooper/LatLongToTimezone) for timezone calculations (TimezoneMapper.java)

