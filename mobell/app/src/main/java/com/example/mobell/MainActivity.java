package com.example.mobell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.net.*;
import java.io.*;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.mobell.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
    }

    // START
    public String getStore() {
      String retString = "";
      Task<PlaceLikelihoodBufferResponse> placeResult = mPlaceDetectionClient.getCurrentPlace(null);
      placeResult.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
         @Override
         public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
            PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();
            for (PlaceLikelihood placeLikelihood : likelyPlaces) {
               retString = retString + String.format("Place '%s' has likelihood: %g",
               placeLikelihood.getPlace().getName(),
               placeLikelihood.getLikelihood()));
            }
            likelyPlaces.release();
         }
      });
      return retString;
   }

   // END

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        String coordinate = "35.280895799999996,-120.66354899999999";
        String resp = getStore(coordinate);
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();

        intent.putExtra(EXTRA_MESSAGE, resp);
        startActivity(intent);
    }
}
