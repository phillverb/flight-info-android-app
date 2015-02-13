package com.verbinteractive.flightinformation;

import android.annotation.TargetApi;
import android.os.Build;

import org.json.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.IOException;

/**
 * Created by VERB Dev 1 on 01-30-2015.
 */
public class FlightInfo {
    public String airline;
    public String fromAirportCode;
    public String toAirportCode;
    public String flightId;
    private String flightStatsAppId = "f6bb4fb8";
    private String flightStatsAppKey = "8ad7d5af80461aabd6f4ff384a9b1921";

    /**
     * FlightInfo constructor.
     *
     * @param String flight_code - the flight code
     * @return void
     */
    public FlightInfo(String flight_code) {
        this.flightId = flightId;
    }

    /**
     * Fetches flight information via a flight API
     *
     * @param void
     * @return void
     */
    public String flightId() {
        return this.flightId;
    }

    /**
     * Fetches flight information via a flight API
     *
     * @param void
     * @return void
     */
    private void fetchFlightInfo() {
        JSONObject obj = null;
        try {
            //"https://api.flightstats.com/flex/flightstatus/rest/v2/json/flight/status/501124465?appId=f6bb4fb8&appKey=8ad7d5af80461aabd6f4ff384a9b1921
            obj = new JSONObject(this.getRemoteJSON("https://api.flightstats.com/flex/flightstatus/rest/v2/json/flight/status/"+this.flightId+"?appId="+this.flightStatsAppId+"&appKey=" + this.flightStatsAppKey));
            //this.toAirportCode
            this.toAirportCode = obj.getJSONObject("request").getJSONObject("flightTrack").getString("arrivalAirportFsCode");
            this.fromAirportCode = obj.getJSONObject("request").getJSONObject("flightTrack").getString("departureAirportFsCode");
        }
        catch (JSONException e) {

        }
        //System.out.println(this.getRemoteJSON("http://www.stackoverlflow.com"));
    }

    /**
     * Gets remote JSON by URL
     *
     * @param String url
     * @return void
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private String getRemoteJSON(String url) {
        String json = "";

        try {
            URL urlObj = new URL(url);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlObj.openStream(), "UTF-8"))) {
                for (String line; (line = reader.readLine()) != null;) {
                    json = json + line;
                    System.out.println(line);
                }
            }
            catch (IOException e) {

            }
        }
        catch (MalformedURLException e) {
           // return e.getCause();
            System.out.println(e.getCause());
            //System.out.println("Phill Test");
        }

        return json;
    }
}
