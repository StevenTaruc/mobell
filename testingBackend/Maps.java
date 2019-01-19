import java.net.*;
import java.io.*;

public class Maps {

   public static String coordinate = "35.280895799999996,-120.66354899999999";
/*
   public static String req =
      "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
      "location=" + coordinate + "&" +
      "radius=50&" +
      "key=" + YOUR_API_KEY;
*/
   public static String req =
      "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?" +
      "input=store&" +
      "inputtype=textquery&" +
      "fields=formatted_address,name&" +
      "locationbias=circle:20@" + coordinate + "&" +
      "key=" + YOUR_API_KEY;

   public static void main(String[] args) {
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
         System.out.println(content);
      }
      catch (Exception e) {
         System.out.println("Exception: " + e);
      }
   }
}
