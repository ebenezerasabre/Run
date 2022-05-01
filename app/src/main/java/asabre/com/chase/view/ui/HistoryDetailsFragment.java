package asabre.com.chase.view.ui;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import asabre.com.chase.R;
import asabre.com.chase.service.model.RideRequest;
import asabre.com.chase.viewmodel.HomeViewModel;

public class HistoryDetailsFragment extends Fragment implements BaseFragment, OnMapReadyCallback {
    private static final String TAG = HistoryDetailsFragment.class.getSimpleName();

    private ImageView endRideBack;
    private TextView endRideStartPoint;
    private TextView endRideEndRide;
    private TextView endRidePaymentType;
    private TextView endRideFee;
    private TextView endRideDriverName;
    private TextView endRideDate;



    // for google map
    private static GoogleMap mGoogleMap;
    private static PolylineOptions mLineOptions = new PolylineOptions();
    private static Polyline mPolyline;

    // new variables
    ArrayList<LatLng> mMarkerPoints = new ArrayList<>();

    private MapView mMapView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_details, container, false);
        init(view);


        initMap(view, savedInstanceState);

        return view;
    }

    private void initMap(View view, @NonNull Bundle saveInstanceState){
        mMapView = view.findViewById(R.id.requestMapView);
        mMapView.onCreate(saveInstanceState);
        mMapView.getMapAsync(this);
    }

    private void init(View view){
        endRideBack = view.findViewById(R.id.rideDetailsBack);
        endRideStartPoint = view.findViewById(R.id.endRideStartPoint);
        endRideEndRide = view.findViewById(R.id.endRideEndPoint);
        endRidePaymentType = view.findViewById(R.id.endRidePaymentType);
        endRideFee = view.findViewById(R.id.endRideFee);
        endRideDriverName = view.findViewById(R.id.endRideDriverName);
        endRideDate = view.findViewById(R.id.endRideDate);

    }






    private void setData(){
        RideRequest rideRequest = HomeViewModel.mRideRequestDetails;
        endRideStartPoint.setText(rideRequest.getEntryPoint().split("&")[1]);
        endRideEndRide.setText(rideRequest.getExitPoint().split("&")[1]);
        endRidePaymentType.setText(String.format(Locale.US, "%s", "Cash"));
        endRideFee.setText(String.format(Locale.US, "%s", rideRequest.getFee()));
        endRideDriverName.setText(String.format(Locale.US, "%S", rideRequest.getdName()));
        endRideDate.setText(String.format(Locale.US, "%S", rideRequest.getCreatedAt().substring(0, 10)));

    }

    private void goBack(){
        endRideBack.setOnClickListener(view -> {
            loadRequestFragment();
        });
    }
    private void reset(){
        endRideBack.setOnClickListener(null);
    }

    private void loadRequestFragment(){
        if(getActivity() != null){
            HistoryFragment historyFragment = new HistoryFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.replace(R.id.conHome, historyFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(getActivity() != null){
            try {
                MapsInitializer.initialize(this.getActivity());
            } catch (Exception e){
                e.printStackTrace();
            }
            mGoogleMap = googleMap;
            if (mGoogleMap != null){
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
                if(mLineOptions != null){
                    mGoogleMap.addPolyline(mLineOptions);
                }
                mapActions();

            }

        }
    }

    private void mapActions(){
        double lat1 = Double.parseDouble(HomeViewModel.mRideRequestDetails.getEntryPoint().split("&")[2]);
        double lng1 = Double.parseDouble(HomeViewModel.mRideRequestDetails.getEntryPoint().split("&")[3]);

        double lat2 = Double.parseDouble(HomeViewModel.mRideRequestDetails.getExitPoint().split("&")[2]);
        double lng2 = Double.parseDouble(HomeViewModel.mRideRequestDetails.getExitPoint().split("&")[3]);

        LatLng entryPoint = new LatLng(lat1, lng1);
        LatLng exitPoint = new LatLng(lat2, lng2);

        drawRoute(entryPoint, exitPoint);
        createMarkerPoints(entryPoint, exitPoint);

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(entryPoint, 15));

    }

    private void createMarkerPoints(LatLng start, LatLng end) {
        if(mMarkerPoints.size() > 1){
            mMarkerPoints.clear();
//            mGoogleMap.clear();
            mGoogleMap.clear();
        }

        // adding to markerPoint
        mMarkerPoints.add(start);
        mMarkerPoints.add(end);

        // creating markerOptions
        MarkerOptions options = new MarkerOptions().title("start")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        MarkerOptions options1 = new MarkerOptions().title("end")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        // Setting the position of the marker
        options.position(start);
        options1.position(end);

        mGoogleMap.addMarker(options);
        mGoogleMap.addMarker(options1);
    }


    private void drawRoute(LatLng origin, LatLng destination){
        String url = ExtraClass.setDirectionsUrl(origin, destination);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);
    }



    /**  A class to download data from Google Directions URL */
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: downloading task background " + Arrays.toString(strings));
            String data = "";
            try {
                // Fetching the data from web service
//                data = downloadUrl(strings[0]);
                data = ExtraClass.downloadUrl(strings[0]);
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


    /*  A class to parse the Google places in JSON format
     */

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {


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
                    Toast.makeText(getContext(), "No points", Toast.LENGTH_LONG).show();
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
                    lineOptions.width(5);
                    lineOptions.color(Color.RED);

                    mLineOptions = lineOptions;
                }

                if(mPolyline != null){
                    mPolyline.remove();
                }
                mPolyline = mGoogleMap.addPolyline(mLineOptions);

                Log.d(TAG, "onPostExecute: Total distance is " + distance.split(" ")[0]);
                Log.d(TAG, "onPostExecute: Total duration is " + duration.split(" ")[0]);

                // distance = 0.8 km
                // distance = 4 mins

            }
        }
    }


    private void setVisibility(){
        if(getActivity() != null){
            Log.d(TAG, "Going back to request");
            MainActivity.mHistoryTrack = MainActivity.HistoryTrack.HISTORY;
            getActivity().findViewById(R.id.conHistory).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.conHistoryDetails).setVisibility(View.GONE);

            removeThisFragment();
        }
    }

    private void removeThisFragment(){
        if(getActivity() != null){
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.conHistoryDetails);
            if(fragment != null){
                getActivity().getSupportFragmentManager().beginTransaction()
                        .remove(fragment)
                        .commit();
                Log.d(TAG, "removeThisFragment: called");
            }
        }
    }



    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        setData();
        goBack();
        mMapView.onStart();
    }


    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
        reset();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(MainActivity.mTrackMain == MainActivity.TrackMain.HISTORY){
            Log.d(TAG, "onBackPressed: pressed");
            setVisibility();
        }
    }


}
