package com.verbinteractive.flightinformation;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** Called when the user clicks "Go"  */
    public void getFlightInformation(View view) {

        // Get the text from text box
        EditText editText = (EditText) findViewById(R.id.flight_id);
        String flightId = editText.getText().toString();

        // Get the main activity's "layout" so that we can add stuff to it
        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_layout);

        // Create textView object, that will become the flight information
        final TextView textView = new TextView(this);

        // Give the text view it's  default style
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        // Make the new text view show up below the "Go" button
        p.addRule(RelativeLayout.BELOW, R.id.go_button);
        textView.setLayoutParams(p);

        final FlightInfo info = new FlightInfo(flightId);

        @TargetApi(Build.VERSION_CODES.KITKAT)
        final class DownloadJSONTask extends AsyncTask<String, Void, String> {
            protected String doInBackground(String... endpoint) {

                String json = "";

                try {
                    URL urlObj = new URL(endpoint[0]);

                    try(InputStreamReader in = new InputStreamReader(urlObj.openStream(), "UTF-8")) {
                        BufferedReader reader = new BufferedReader(in);
                        for (String line; (line = reader.readLine()) != null;) {
                            json = json + line;
                            System.out.println(line);
                        }
                    }
                    catch (IOException e) {

                    }
                }
                catch (MalformedURLException e) {
                    System.out.println(e.getCause());
                }

                return json;
                //long returnVal = 1;
                //return returnVal;
            }

            protected void onProgressUpdate(Integer... progress) {
                //setProgressPercent(progress[0]);
            }

            protected void onPostExecute(String result) {
                System.out.println("Remote fetching done ");

                info.setRawJSON(result);
                info.fetchFlightInfo();

                textView.setText(info.toAirportCode);

                // Go on ahead and add it to the view
                layout.addView(textView);

            }
        }

        new DownloadJSONTask().execute( info.getStatsEndpoint() );




        //ImageView jet = (ImageView)layout.findViewById(R.id.jet_image);
        //AnimationSet animationSet = new AnimationSet(true);
        //Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.jet_fly);
        //Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.jet_rotate);
        //Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.jet_fly_out);
        //animationSet.addAnimation(animation1);
        //animationSet.addAnimation(animation2);
        //animation3.setStartOffset(1500);
        //animationSet.addAnimation(animation3);
        //jet.startAnimation(animationSet);


    }


}
