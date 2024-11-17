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
 - write unit tests

### __usage__
>obtain or generate an API key for Google's Geolocation API
>    - to generate a key, follow the instructions [here](https://support.google.com/googleapi/answer/6158862?hl=en)

---

>run with jarfile (Unix)
>```bash
>GEOLOCATION_API_KEY="GOOGLE_GEOLOCATION_KEY" java -jar kata.jar "San Diego" "San Francisco" "Los Angeles" "Sacramento"
>```

---

>run with jarfile (Windows)
>```bash
>$env:GEOLOCATION_API_KEY="GOOGLE_GEOLOCATION_KEY" ; java -jar kata.jar "San Diego" "San Francisco" "Los Angeles" "Sacramento"
>```

---
>run with gradle
>```
>GEOLOCATION_API_KEY="GOOGLE_GEOLOCATION_KEY" gradle run --args="milwaukee madison 'green bay' chicago antarctica"
>```

> [!Note]
> One benefit of using Google's Geolocation API is that locations can be
> as specific as a street address or as general as a continent!

### __projects used__
1. [open-meteo api](https://github.com/open-meteo/open-meteo)
2. [google's geolocation api](https://developers.google.com/maps/documentation/geolocation/overview)
3. [apache poi](https://github.com/apache/poi) 
4. [LatLongToTimezone](https://github.com/drtimcooper/LatLongToTimezone) for timezone calculations (TimezoneMapper.java)


### __screenshots__
 - #### California has beautiful weather!
 >![image](https://github.com/user-attachments/assets/ca35a99d-88a2-4e6b-8883-e1dd26271387)

 - #### Some Milwaukee cities!
 >![image](https://github.com/user-attachments/assets/83cc14f0-ebd5-4bb3-914d-d553c5345c0b)

 - #### Comparing weather at both Direct Supply Locations with graceful exception handling
 >![image](https://github.com/user-attachments/assets/d07be898-0bba-4a21-9e25-ebfa7293974d)
