package kata;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.httpclient.HttpException;

public class Main {
    public static void main(String[] args) {
        // TODO: read environment variables for Geolocation API key

        if (args.length == 0) {
            System.err.println("At least one location must be provided.");
            System.exit(1);
        }

        Location[] locations = new Location[args.length - 1];

        for (int i = 1; i < args.length; i++) {
            locations[i - 1] = new Location();
            try {
                locations[i - 1].geolocate(args[i])
            } catch (HttpException _) {
                System.err.println("HTTP exception for address: " + args[i]);
            } catch (IOException _) {
                System.err.println("IOException for address: " + args[i]);
            } catch (Geolocate.GeolocateAPIException e) {
                System.err.println(e.getMessage());
            }
        }

        for (Geolocate.Place p : geolocate.getPlaces()) {

            System.out.println(p); // replace with api query
        }

        try {
            Sheet sheet = new Sheet();
            sheet.writeToFile(new FileOutputStream("weather.xlsx"));
            sheet.close();
        } catch (IOException _) {
            System.err.println("unable to write to weather.xlsx");
        }
    }
}
