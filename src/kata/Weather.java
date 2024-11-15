package kata;

import java.util.Date;
import org.json.JSONObject;

class Weather {
    private final String URL = "";
    private JSONObject data;
    private int currentTemperature;
    private String currentDescription;
    private int todayHigh;
    private int todayLow;
    private int tomorrowHigh;
    private int tomorrowLow;
    private String tomorrowDescription;
    private String moonPhase;
    private Date sunrise;
    private Date sunset;
    private int bikeability;

    public Weather(JSONObject json) {
        System.out.println(json.toString(4));
    }

	public String getURL() {
		return URL;
	}

	public JSONObject getData() {
		return data;
	}

	public int getCurrentTemperature() {
		return currentTemperature;
	}

	public String getCurrentDescription() {
		return currentDescription;
	}

	public int getTodayHigh() {
		return todayHigh;
	}

	public int getTodayLow() {
		return todayLow;
	}

	public int getTomorrowHigh() {
		return tomorrowHigh;
	}

	public int getTomorrowLow() {
		return tomorrowLow;
	}

	public String getTomorrowDescription() {
		return tomorrowDescription;
	}

	public String getMoonPhase() {
		return moonPhase;
	}

	public Date getSunrise() {
		return sunrise;
	}

	public Date getSunset() {
		return sunset;
	}

	public int getBikeability() {
		return bikeability;
	}
}
