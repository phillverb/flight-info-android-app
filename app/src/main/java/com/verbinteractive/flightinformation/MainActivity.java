package com.verbinteractive.flightinformation;

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
        String flight_id = editText.getText().toString();

        // Create FlightInfo object based on flight ID given
        FlightInfo info = new FlightInfo(flight_id);

        // Get the main activity's "layout" so that we can add stuff to it
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_layout);

        // Create textView object, that will become the flight information
        final TextView textView = new TextView(this);

        // Set the text of the new text view
        textView.setText("Going to: " + info.fromAirportCode);

        // Give the text view it's  default style
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        // Make the new text view show up below the "Go" button
        p.addRule(RelativeLayout.BELOW, R.id.go_button);
        textView.setLayoutParams(p);

        // Go on ahead and add it to the view
        layout.addView(textView);



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
