package kata;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.json.JSONException;

public class Main {
    public static final String API_KEY = System.getenv("GEOLOCATION_API_KEY");

    public static void main(String[] args) {

        if (API_KEY == null) {
            Log.fatal("No API key provided");
        } else {
            Log.info("Geolocation Key: " + API_KEY);
        }

        if (args.length == 0) {
            Log.fatal("At least one location must be provided.");
        }

        Location[] locations = new Location[args.length];

        try {
            for (int i = 0; i < args.length; i++) {
                locations[i] = new Location();

                try {
                    if (args[i].length() == 0) {
                        throw new IllegalArgumentException();
                    }
                    locations[i].geolocate(args[i]);
                    locations[i].query();
                } catch (JSONException e) {
                    Log.warn(e.getMessage());
                } catch (IllegalArgumentException _) {
                    Log.warn("Bad argument: '" + args[i] + "'");
                }
            }
        } catch (HttpException _) {
            Log.fatal("Encountered a fatal HTTP exception, likely an internal server error");
        } catch (IOException _) {
            Log.fatal("Unable to connect to the internet."); 
        } catch (Location.GeolocateAPIException e) {
            Log.fatal(e.getMessage()); 
        }

        try {
            Sheet sheet = new Sheet(locations);
            sheet.writeToFile(new FileOutputStream("weather.xlsx"));
            sheet.close();
        } catch (IOException _) {
            Log.fatal("unable to write to weather.xlsx");
        }
    }
}
