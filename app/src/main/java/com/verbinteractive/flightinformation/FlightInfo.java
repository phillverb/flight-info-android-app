package com.verbinteractive.flightinformation;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import org.json.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.IOException;

/**
 * Created by VERB Dev 1 on 01-30-2015.
 */
// 1st: Argument type of doInBackground
// 3rd: Return value of doInBackground, matches the parameter type of onPostExecute
class FlightInfo {
    public String airline;
    public String fromAirportCode;
    public String toAirportCode;
    public String flightId;
    private String flightStatsAppId = "f6bb4fb8";
    private String flightStatsAppKey = "8ad7d5af80461aabd6f4ff384a9b1921";
    private String rawJSON;

    /*
    public boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
    */

    public FlightInfo(String flightId) {
        this.flightId = flightId;
    }

    public void setRawJSON(String json) {
        this.rawJSON = json;
    }

    /**
     * Gets the string URL that should be called to get the flight information
     *
     * @param void
     * @return String
     */
    public String getStatsEndpoint() {
        return "https://api.flightstats.com/flex/flightstatus/rest/v2/json/flight/status/"+this.flightId+"?appId="+this.flightStatsAppId+"&appKey=" + this.flightStatsAppKey;
    }

    /**
     * Fetches flight information via a flight API
     *
     * @param void
     * @return void
     */
    public void fetchFlightInfo() {
        JSONObject obj = null;
        try {
            System.out.println(this.rawJSON);
            obj = new JSONObject(this.rawJSON);
            JSONObject flightStats = obj.getJSONObject("flightStatus");
            this.fromAirportCode = flightStats.getString("departureAirportFsCode");
            this.toAirportCode = flightStats.getString("arrivalAirportFsCode");
        }
        catch (JSONException e) {

        }
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

            try(InputStream is = urlObj.openStream()) {
                /*try(InputStreamReader in = new InputStreamReader(urlObj.openStream(), "UTF-8")) {
                    BufferedReader reader = new BufferedReader(in);
                    for (String line; (line = reader.readLine()) != null;) {
                        json = json + line;
                        System.out.println(line);
                    }
                }
                catch (IOException e) {

                }*/
            }
            catch(IOException e) {

            }


            /*
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlObj.openStream(), "UTF-8"))) {
                for (String line; (line = reader.readLine()) != null;) {
                    json = json + line;
                    System.out.println(line);
                }
            }
            catch (IOException e) {

            }
            */
        }
        catch (MalformedURLException e) {
           // return e.getCause();
            System.out.println(e.getCause());
            //System.out.println("Phill Test");
        }

        return json;
    }
}
