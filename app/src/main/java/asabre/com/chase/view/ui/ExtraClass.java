package asabre.com.chase.view.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import asabre.com.chase.R;
import asabre.com.chase.viewmodel.HomeViewModel;


public class ExtraClass {
    private static final String TAG = ExtraClass.class.getSimpleName();

    /** form url for google place api */
    public static String mGetCoords(String placeId){
        String output = "json";
        String fields = "fields=geometry";
        String place_id = "place_id=" + placeId;
        String key = "key=" + AppConstants.API_KEY;
        String parameters = place_id + "&" + fields + "&" +  key;

        return "https://maps.googleapis.com/maps/api/place/details/" + output +"?" + parameters;
    }


/** form url for google direction api */
    public static String setDirectionsUrl(LatLng origin, LatLng dest){

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        String mode = "mode=driving";

        // key
//        String key = "key=" + getString(R.string.google_maps_api_key);
        String key = "key=" + AppConstants.API_KEY;

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode + "&" +  key;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        Log.d(TAG, "getDirectionsUrl: origin lat:lng --> " + origin.latitude + " : " + origin.longitude);
        Log.d(TAG, "getDirectionsUrl: destination lat:lng --> " + dest.latitude + " : " + dest.longitude);

        return url;

    }


    /** A method to download json data from url */
    public static String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // connecting to url
            urlConnection.connect();

            // Reading data from url
            inputStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();

            String line = "";
            while((line = br.readLine()) != null){
                sb.append(line);
            }
            data = sb.toString();

            br.close();
        } catch (Exception e) {
            Log.d(TAG, "Exception on download" + e.getMessage());
        } finally {
            inputStream.close();
            urlConnection.disconnect();
        }
        return data;
    }




    /**  A class to download data from Google Directions URL */
    public static class DownloadTask extends AsyncTask<String, Void, String> {
//        private final String TAG = MapFragment.DownloadTask.class.getSimpleName();
        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: downloading task background " + Arrays.toString(strings));
            String data = "";
            try {
                // Fetching the data from web service
                data = downloadUrl(strings[0]);
                Log.d(TAG, "doInBackground: The data to fetch from web service " + data);
            } catch (Exception e){
                Log.d(TAG, "doInBackground: Task failed" + e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of doInBackground()
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ParserTask parserTask = new ParserTask();
            // Invokes the thread for parsing the JSON data
            parserTask.execute(s);
            Log.d(TAG, "onPostExecute: downloading task in background completed");
        }
    }

    /*  A class to p arse the Google places in JSON format
     */

    public static class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
//        private final String TAG = MapFragment.ParserTask.class.getSimpleName();

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: ParserTask called");

            JSONObject jsonObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jsonObject = new JSONObject(strings[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            super.onPostExecute(result);
            Log.d(TAG, "onPostExecute: ParserTask onPostExecute called");

            ArrayList<LatLng> points = new ArrayList<>();
            PolylineOptions lineOptions = new PolylineOptions();
            MarkerOptions markerOptions = new MarkerOptions();
            String distance = "";
            String duration = "";


            if(null != result){

                if(result.size() < 1){
//                    Toast.makeText(getContext(), "No points", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onPostExecute: No points ");
                }

                // Traversing though all routes
                for(int i=0;i<result.size();i++){
                    points = new ArrayList<>();
                    lineOptions = new PolylineOptions();

                    // fetching i-th route
                    List<HashMap<String, String>> path = result.get(i);

                    // fetching all the points in i-th route
                    for(int j=0;j<path.size();j++){
                        HashMap<String, String> point = path.get(j);

                        if(j == 0){
                            // Get distance from the list
                            distance = point.get("distance");
                            continue;
                        } else if(j == 1){
                            // Get duration from the list
                            duration = point.get("duration");
                            continue;
                        }

                        double lat = Double.parseDouble(point.get("lat"));
                        double lng = Double.parseDouble(point.get("lng"));
                        LatLng position = new LatLng(lat, lng);

                        points.add(position);
                    }

                    // Adding all the points in the route to LineOptions
                    lineOptions.addAll(points);
                    lineOptions.width(2);
                    lineOptions.color(Color.RED);

                    HomeViewModel.mLineOptions = lineOptions;
                }
                // Drawing polyline in the Google Map for the i-th route
                    if(HomeViewModel.mPolyline != null){
                        HomeViewModel.mPolyline.remove();
                    }
                    HomeViewModel.mPolyline = HomeViewModel.mGoogleMap.addPolyline(lineOptions);

                Log.d(TAG, "onPostExecute: Total distance is " + distance);
                Log.d(TAG, "onPostExecute: Total duration is " + duration);

            }
        }
    }


    public static BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable){
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public static String userGoOnlineObj(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("userId", HomeViewModel.userEntity.get_id());
            obj.put("userType", HomeViewModel.userEntity.getUserType());
            obj.put("socketId", HomeViewModel.socketId);
        } catch (JSONException e){
            e.printStackTrace();
        }
        return obj.toString();
    }

    public static String driverGoOnlineObj(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("driverId", HomeViewModel.userEntity.get_id());
            obj.put("userType", HomeViewModel.userEntity.getUserType());
            obj.put("socketId", HomeViewModel.socketId);
            obj.put("city", HomeViewModel.userEntryPoint.split("&")[0].toLowerCase());
            obj.put("lat", HomeViewModel.userEntryPoint.split("&")[2]);
            obj.put("lng",  HomeViewModel.userEntryPoint.split("&")[3]);
            obj.put("rideType", "car");
        } catch (JSONException e){
            e.printStackTrace();
        }
        return obj.toString();
    }



}






