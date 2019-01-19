package com.example.mobell;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import java.net.*;
import java.io.*;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.mobell.MESSAGE";
    protected GeoDataClient mGeoDataClient;
    protected PlaceDetectionClient mPlaceDetectionClient;
    // TODO: Change this!!!!!
    public static String retString = "";
    protected boolean mLocationPermissionGranted;
    protected final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        // TODO :: WHAT TO DO?
        // updateLocationUI();
    }

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
    // public String getStore() {
    public void getStore() {
        final String TAG = "getStore";
        try {
            getLocationPermission();
            if (mLocationPermissionGranted) {
                Task<PlaceLikelihoodBufferResponse> placeResult = mPlaceDetectionClient.getCurrentPlace(null);
                Log.d(TAG, "0x5E710C set location success");
                placeResult.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                        PlaceLikelihoodBufferResponse likelyPlaces = null;
                        if(task.isSuccessful() && task.getResult() != null) {
                            Log.d(TAG, "0x4A11001 task result success, task.isSuccessful is: " + task.isSuccessful() + " result is null: " + (task.getResult() == null));
                            likelyPlaces = task.getResult();
                            Log.d(TAG, "0x4A11002 task result success, task.isSuccessful is: " + task.isSuccessful() + " result is null: " + (task.getResult() == null));
                        }
                        Log.d(TAG, "0x7A54 task result success, task.isSuccessful is: " + task.isSuccessful() + " result is null: " + (task.getResult() == null));
                        retString = likelyPlaces.get(0).getPlace().getName().toString();
                            /*
                        for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                            retString = String.format("'%s', Place '%s' has likelihood: %g",
                                    retString,
                                    placeLikelihood.getPlace().getName(),
                                    placeLikelihood.getLikelihood());
                        }
                                    */
                        likelyPlaces.release();
                        Log.d(TAG, "0x707 likelyPlaces released!");
                    }
                });
                // return retString;
            }
        }
        catch(SecurityException e) {
            Log.e(TAG, "0xFEE1SBAD SECURITYEXCEPTION IN getStore " + e);
        }
        catch(Exception e) {
            Log.e(TAG, "0x8BADF00D UNHANDLED EXCEPTION IN getStore " + e);
        }

        Log.d("getStore" , "0xDEADBEEF [permission granted is: " + mLocationPermissionGranted + "] (" + retString + ")");
        // return retString;
   }

   // END
   private class AsyncTaskRunner extends AsyncTask<Void, Void, Void> {

       @Override
       protected Void doInBackground(Void... voids) {
           getStore();
           return null;
       }
   }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        String coordinate = "35.280895799999996,-120.66354899999999";
        // String resp = getStore();
        new AsyncTaskRunner().execute();
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();

        // intent.putExtra(EXTRA_MESSAGE, resp);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
