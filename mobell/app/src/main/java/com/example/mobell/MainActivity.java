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
    }

    // START

   public String getStore(String coordinate) {
      String req =
         "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?" +
         "input=store&" +
         "inputtype=textquery&" +
         "fields=formatted_address,name&" +
         "locationbias=circle:20@" + coordinate + "&" +
         "key=" + YOUR_API_KEY;
      try {
         URL url = new URL(req);
         HttpURLConnection con = (HttpURLConnection)url.openConnection();
         try {
            con.setRequestMethod("GET");
         }
         catch (ProtocolException e) {
            System.out.println("ProtocolException: " + e);
         }
         int status = con.getResponseCode();

         System.out.println("status: " + status);

         BufferedReader in = new BufferedReader(
           new InputStreamReader(con.getInputStream()));
         String inputLine;
         StringBuffer content = new StringBuffer();
         while ((inputLine = in.readLine()) != null) {
             content.append(inputLine);
         }
         in.close();
         con.disconnect();

         System.out.println(con.getResponseMessage());
         return content;
      }
      catch (Exception e) {
         return "Exception: " + e;
      }
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
