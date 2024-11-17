package kata;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Location
 * stores methods to query Google's Geolocation and Open Meteo weather APIs
 * contains name, latitude, longitude, timezone, and weather data for location
 */
class Location {
    public boolean HAS_RESULTS = false;
    private String name;
    private double latitude;
    private double longitude;
    private String timezone;
    private Weather weather;

    /**
     * GeolocateAPIException thrown when API encounters an exception
     */
    class GeolocateAPIException extends Exception {
        GeolocateAPIException(String message) {
            super(message);
        }
    }

    /**
     * query the Open Meteo API based on the coordinates
     * from geolocate()
     * @throws HttpException
     * @throws IOException
     */
    public void query() throws HttpException, IOException {
        final String url = String.format("https://api.open-meteo.com/v1/forecast?" +
            "latitude=%s" +
            "&longitude=%s" +
            "&current=temperature_2m,weather_code,is_day&hourly=precipitation,wind_speed_10m" +
            "&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset" +
            "&temperature_unit=fahrenheit&wind_speed_unit=mph&precipitation_unit=inch" +
            "&timezone=%s&forecast_days=1", this.latitude, this.longitude, this.timezone);

        final GetMethod get = new GetMethod(url);
        new HttpClient().executeMethod(get);

        String res = new String(get.getResponseBodyAsStream().readAllBytes());
        this.weather = new Weather(new JSONObject(res));
        get.releaseConnection();
    }

    /**
     * run GeoLocation query for provided address
     * @param address
     * @throws HttpException
     * @throws IOException
     * @throws GeolocateAPIException 
     * @throws JSONException
     * @throws IllegalArgumentException
     */
    public void geolocate(String address) throws HttpException, IOException, GeolocateAPIException, JSONException, IllegalArgumentException {
        final String BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json";

        address = String.join("+", address.split(" "));
        final String url = String.format("%s?address=%s&key=%s", BASE_URL, address, Main.API_KEY);
        final GetMethod get = new GetMethod(url);

        new HttpClient().executeMethod(get);

        JSONObject res = new JSONObject(get.getResponseBodyAsString());

        if (res.has("error_message")) {
            throw new GeolocateAPIException(res.getString("error_message"));
        }

        if (res.getJSONArray("results").length() == 0) {
            throw new JSONException("No results for " + address);
        }

        get.releaseConnection();

        double[] coords = getCoords(res);
        this.name = getLocationName(res);
        this.latitude = coords[0];
        this.longitude = coords[1];
        this.timezone = TimezoneMapper.latLngToTimezoneString(coords[0], coords[1]);

        Log.info("found " + this);
        HAS_RESULTS = true;
    }

    /**
     * return location name from response JSON Object
     * @param json
     * @return String
     */
    private String getLocationName(JSONObject json) {
        return json.getJSONArray("results")
            .getJSONObject(0)
            .getString("formatted_address");
    }

    /**
     * return latitude and longitude coords from response JSON Object
     * @param json
     * @return double[]
     */
    private double[] getCoords(JSONObject json) {
        JSONObject c = json.getJSONArray("results")
            .getJSONObject(0)
            .getJSONObject("geometry")
            .getJSONObject("location");

        return new double[] { c.getDouble("lat"), c.getDouble("lng") };
    }

    /**
     * returns the entries for this location's excel column
     * @return String[]
     */
    public String[] getData() {
        return new String[] {
            this.name,
            weather.getCurrentTemperature(),
            weather.getCurrentDescription(),
            weather.getTodayHigh(),
            weather.getTodayLow(),
            weather.isBikeable(),
            weather.getSunrise(),
            weather.getSunset()
        };
    }

    @Override
    public String toString() {
        return name + " @ [" + latitude + ", " + longitude + "]";
    }
}
