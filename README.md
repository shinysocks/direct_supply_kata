# Direct Supply Kata 2024 - Weather Data to Excel
## Noah Dinan

### __overview__
>For this project, I wanted the user to be able to input as many locations as they wanted
>and recieve a table showing the weather for each location. This would make comparing 
>weather in several locations trivial. I decided to use java for the project because of the
>easy to use Apache POI library for generating xlsx files, and because of its platform independence.

>One unique aspect of this project is the "Should I go biking?" column
>which determines how suitable the weather in each location is for a ride
>based on amount of precipitation, wind speed, and temperature.

### __features__
 - pulls data for each specified location
    - current weather information
    - high and low temperatures of the day
    - bikeability for the day
    - time of sunrise and sunset
 - outputs an excel sheet containing weather data

### __next steps__
 - get current moon phase
 - 

### __usage__
>obtain or generate an API key for Google's Geolocation API
>    - visit [console.cloud.google.com](https://console.cloud.google.com/google/maps-apis/credentials)

---

>run with jarfile
>```
>GEOLOCATION_API_KEY="GOOGLE_GEOLOCATION_KEY" java -jar kata.jar "San Diego" "San Francisco" "Los Angeles" "Sacramento"
>```

---

>run with gradle
>```
>GEOLOCATION_API_KEY="GOOGLE_GEOLOCATION_KEY" gradle run --args="milwaukee madison green+bay chicago antarctica"
>```

> [!Note]
> Spaces in location names should be replaced with '+' to ensure
> they are parsed as a single argument.

### __projects used__
1. [open-meteo api](https://github.com/open-meteo/open-meteo)
2. [google's geolocation api](https://developers.google.com/maps/documentation/geolocation/overview)
3. [apache poi](https://github.com/apache/poi) 
4. [LatLongToTimezone](https://github.com/drtimcooper/LatLongToTimezone) for timezone calculations (TimezoneMapper.java)


### __screenshots__
 - 

