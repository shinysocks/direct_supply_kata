package kata;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONObject;

/**
 * Geolocate
 * Utilize Google's Geolocation API to store names and coordinates for different places
 */
class Geolocate {
    private final ArrayList<Place> places = new ArrayList<Place>();
    private final String BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json";
    private String apiKey;

	/**
	 * GeolocateAPIException thrown when API encounters an exception
	 */
	public class GeolocateAPIException extends Exception {
        public GeolocateAPIException(String message) {
            super(message);
        }
	}

    /**
     * store name and coordinates for a geolocation from query
     */
    class Place {
        private String name;
        private double latitude;
        private double longitude;
        private String timezone;

        Place(String name, double latitude, double longitude, String timezone) {
            this.name = name;
            this.latitude = latitude;
            this.longitude = longitude;
            this.timezone = timezone;
        }

        public String getName() {
            return name;
        }

		public double getLatitude() {
			return latitude;
		}

		public double getLongitude() {
			return longitude;
		}

		public String getTimezone() {
			return timezone;
		}

        @Override
        public String toString() {
            return name + " @ [ " + latitude + ", " + longitude + " ] + " + timezone;
        }
    }

    /**
     * Geolocate constructor
     * @param API_KEY
     */
    public Geolocate(String API_KEY) {
        this.apiKey = API_KEY;
    }

    /**
     * run query for provided address
     * @param address
     * @throws HttpException
     * @throws IOException
     * @throws GeolocateAPIException 
     */
    public void query(String address) throws HttpException, IOException, GeolocateAPIException {
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
        String tz = TimezoneMapper.latLngToTimezoneString(coords[0], coords[1]);
        places.add(new Place(getLocationName(res), coords[0], coords[1], tz));
    }

    /**
     * return location name from response JSONObject
     * @param json
     * @return
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

    /**
     * return an array of Place(s)
     * @return Place[]
     */
    public Place[] getPlaces() {
        return places.toArray(Place[]::new);
    }

}
