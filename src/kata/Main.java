package kata;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.httpclient.HttpException;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("At least one location must be provided.");
            System.exit(1);
        }

        Geolocate geolocate = new Geolocate(args[0]);

        for (int i = 1; i < args.length; i++) {
            try {
                geolocate.query(args[i]);
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
            Sheet sheet = new Sheet("noah");
            sheet.close();
        } catch (IOException _) {
            System.err.println("unable to write to weather.xlsx");
        }
    }
}
