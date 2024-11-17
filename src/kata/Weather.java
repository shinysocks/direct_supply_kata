package kata;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

class Weather {
    private boolean isDay;
    private double currentTemperature;
    private int currentWeatherCode;
    private double todayHigh;
    private double todayLow;
    private String sunrise;
    private String sunset;
    private JSONArray windHourly;
    private JSONArray precipitationHourly;
    private JSONObject weatherDescriptions;

    public Weather(JSONObject json) {
        JSONObject current = json.getJSONObject("current");
        currentTemperature = current.getDouble("temperature_2m");
        currentWeatherCode = current.getInt("weather_code");
        isDay = current.getInt("is_day") == 1 ? true : false;

        JSONObject daily = json.getJSONObject("daily");
        todayHigh = daily.getJSONArray("temperature_2m_max").getDouble(0);
        todayLow = daily.getJSONArray("temperature_2m_min").getDouble(0);
        sunrise = (String) daily.getJSONArray("sunrise").get(0);
        sunset = (String) daily.getJSONArray("sunset").get(0);

        windHourly = json.getJSONObject("hourly").getJSONArray("wind_speed_10m");
        precipitationHourly = json.getJSONObject("hourly").getJSONArray("precipitation");

        loadWeatherDescriptions();
    }

    private void loadWeatherDescriptions() {
        byte[] bytes = new byte[0];
        try {
            InputStream is = getClass().getResourceAsStream("/descriptions.json");
            bytes = is.readAllBytes();
        } catch (IOException e) {
            Log.warn("unable to access 'descriptions.json' file");
        }
        weatherDescriptions = new JSONObject(new String(bytes));
    }

    public String getCurrentTemperature() {
        return "" + currentTemperature + "°";
    }

    public String getCurrentDescription() {
        try {
            JSONObject description = weatherDescriptions.getJSONObject("" + currentWeatherCode);
            if (isDay) {
                return description.getJSONObject("day").getString("description");
            }
            return description.getJSONObject("night").getString("description");
        } catch (JSONException _) {
            return "?";
        }
    }

    public String getTodayHigh() {
        return "" + todayHigh + "°";
    }

    public String getTodayLow() {
        return "" + todayLow + "°";
    }

    public String getSunrise() {
        return sunrise.substring(11);
    }

    public String getSunset() {
        return sunset.substring(11);
    }

    public String isBikeable() {
        double totalPrecipitation = 0;
        double maxWindSpeed = 0;
        for (int i = 0; i < precipitationHourly.length(); i++) {
            totalPrecipitation += precipitationHourly.getDouble(i);
        }

        for (Object s : windHourly) {
            double speed = Double.parseDouble(((BigDecimal) s).toString());
            if (speed > maxWindSpeed) {
                maxWindSpeed = speed;
            }
        }

        // average temperature is below freezing
        if ((todayLow + todayHigh) / 2 < 32) {
            return "Yes, but dress warm.";
        }

        // more than an inch of precipitation
        if (totalPrecipitation > 1.0) {
            return "Yes, but expect some rain.";
        }

        // max wind speed over 20 mph
        if (maxWindSpeed > 20.0) {
            return "Yes, but it may be windy.";
        }

        return "Yes!";
    }
}
