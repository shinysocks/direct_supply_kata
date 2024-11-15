package kata;

import org.json.JSONObject;
import java.io.IOException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * Location
 * stores methods to query Google's Geolocation and weather APIs
 * contains latitude, longitude, timezone, and weather data for location
 */
class Location {
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
	 * OpenMeteoAPIException thrown when API encounters an exception
	 */
	class OpenMeteoAPIException extends Exception {
        OpenMeteoAPIException(String message) {
            super(message);
        }
	}

    public void query() throws HttpException, IOException {
        final String url = String.format("https://api.open-meteo.com/v1/forecast?" +
            "latitude=%s" +
            "&longitude=%s" +
            "&current=temperature_2m,weather_code&hourly=precipitation,wind_speed_10m" +
            "&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset" +
            "&temperature_unit=fahrenheit&wind_speed_unit=mph&precipitation_unit=inch" +
            "&timezone=%s&forecast_days=1", this.latitude, this.longitude, this.timezone);

        final GetMethod get = new GetMethod(url);
        new HttpClient().executeMethod(get);

        this.weather = new Weather(new JSONObject(get.getResponseBodyAsString()));

        get.releaseConnection();
    }

    /**
     * run GeoLocation query for provided address
     * @param address
     * @param apiKey 
     * @throws HttpException
     * @throws IOException
     * @throws GeolocateAPIException 
     */
    public void geolocate(String address, String apiKey) throws HttpException, IOException, GeolocateAPIException {
        final String BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json";

        address = String.join("+", address.split(" "));
        final String url = String.format("%s?address=%s&key=%s", BASE_URL, address, apiKey);
        final GetMethod get = new GetMethod(url);

        new HttpClient().executeMethod(get);

        JSONObject res = new JSONObject(get.getResponseBodyAsString());

        if (res.has("error_message")) {
            throw new GeolocateAPIException(res.getString("error_message"));
        }

        get.releaseConnection();

        double[] coords = getCoords(res);
        this.name = getLocationName(res);
        this.latitude = coords[0];
        this.longitude = coords[1];
        this.timezone = TimezoneMapper.latLngToTimezoneString(coords[0], coords[1]);
    }

    /**
     * return location name from response JSONObject
     * @param json
     * @return String
     */
    private String getLocationName(JSONObject json) {
        return json.getJSONArray("results")
            .getJSONObject(0)
            .getString("formatted_address");
    }

    /**
     * return latitude and longitude coords from response JSONObject
     * @param json
     * @return
     */
    private double[] getCoords(JSONObject json) {
        JSONObject c = json.getJSONArray("results")
            .getJSONObject(0)
            .getJSONObject("geometry")
            .getJSONObject("location");

        return new double[] { c.getDouble("lat"), c.getDouble("lng") };
    }

    @Override
    public String toString() {
        return name + " @ [ " + latitude + ", " + longitude + " ] + " + timezone;
    }

}
