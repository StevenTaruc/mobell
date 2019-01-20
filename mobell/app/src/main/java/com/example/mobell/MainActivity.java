package com.example.mobell;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.net.*;
import java.io.*;
import com.google.android.gms.common.internal.Constants;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
//import com.squareup.okhttp.MediaType;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.RequestBody;
//import com.squareup.okhttp.Response;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static final String LOCATION_MESSAGE = "com.example.mobell.LOCATION";
    public static final String REQUEST_MESSAGE = "com.example.mobell.REQUEST";
    protected GeoDataClient mGeoDataClient;
    protected PlaceDetectionClient mPlaceDetectionClient;
    // TODO: Change this!!!!!
    public static String retString = "";
    protected boolean mLocationPermissionGranted;
    protected final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;

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
    public String getStore() {
        final String TAG = "getStore";
        try {
            getLocationPermission();
            if (mLocationPermissionGranted) {
                Task<PlaceLikelihoodBufferResponse> placeResult = mPlaceDetectionClient.getCurrentPlace(null);
                Log.d(TAG, "0x5E710CA7 set location success");
                placeResult.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                        PlaceLikelihoodBufferResponse likelyPlaces = null;
                        if(task.isSuccessful() && task.getResult() != null) {
                            Log.d(TAG, "0x4A110001 task result success, task.isSuccessful is: " + task.isSuccessful() + "; result is null: " + (task.getResult() == null));
                            likelyPlaces = task.getResult();
                            Log.d(TAG, "0x4A110002 task result success, task.isSuccessful is: " + task.isSuccessful() + "; result is null: " + (task.getResult() == null));
                        }
                        Log.d(TAG, "0x7A547A54 task result success, task.isSuccessful is: " + task.isSuccessful() + "; result is null: " + (task.getResult() == null));
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
                        Log.d(TAG, "0x07070707 likelyPlaces released!");
                    }
                });
                Log.d(TAG , "0x73555111; (middle) retString = " + retString);
                // return retString;
            }
            else {
                return "LOCATION PERMISSION NEEDED FOR APP TO FUNCTION!";
            }
        }
        catch(SecurityException e) {
            Log.e(TAG, "0xFEE1SBAD SECURITYEXCEPTION IN getStore " + e);
        }
        catch(Exception e) {
            Log.e(TAG, "0x8BADF00D UNHANDLED EXCEPTION IN getStore " + e);
        }

        Log.d(TAG , "0xDEADBEEF [permission granted is: " + mLocationPermissionGranted + "] (" + retString + ")");
        Log.d(TAG , "0x73555111; (end) retString = " + retString);
        if (retString.length() <= 0) {
            return "Unable to acquire location, please try again later (you have already enabled the \"Location\" permission)";
        }
        return retString;
   }

   // END
    /*
   private class AsyncTaskRunner extends AsyncTask<Void, Void, Void> {

       @Override
       protected Void doInBackground(Void... voids) {
           getStore();
           return null;
       }
   }
   */

//    /** Called when the user taps the Send button */
//    public void sendMessage(View view) {
//        String coordinate = "35.280895799999996,-120.66354899999999";
//        String resp = getStore();
//        // new AsyncTaskRunner().execute();
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String requestDetails = editText.getText().toString();
//
//        intent.putExtra(LOCATION_MESSAGE, resp);
//        intent.putExtra(REQUEST_MESSAGE, requestDetails);
//        startActivity(intent);
//    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    public void sendNotification(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        String requestDetails = editText.getText().toString();
        Log.d("Test", "test");


        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);

        // get selected radio button from radioGroup
        int selectedId = radioSexGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioSexButton = (RadioButton) findViewById(selectedId);

        final String buttonStr = radioSexButton.getText().toString();


        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    OkHttpClient client = new OkHttpClient();
                    JSONObject json=new JSONObject();
                    JSONObject dataJson=new JSONObject();
                    dataJson.put("body",buttonStr);
                    dataJson.put("title","Customer[3e423440] Needs Assistance");
                    json.put("notification",dataJson);
//                    json.put("to",regToken);
                    json.put("to","/topics/weather");
                    RequestBody body = RequestBody.create(JSON, json.toString());
                    Request request = new Request.Builder()
                            .header("Authorization","key="+ "KEY")
                            .url("https://fcm.googleapis.com/fcm/send")
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                    String finalResponse = response.body().string();
                    Log.d("Final URL: ",finalResponse);
                }catch (Exception e){
                    //Log.d(TAG,e+"");
                }
                return null;
            }
        }.execute();
    }
}
