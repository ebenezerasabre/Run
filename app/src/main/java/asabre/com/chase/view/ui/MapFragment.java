package asabre.com.chase.view.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
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
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import asabre.com.chase.R;
import asabre.com.chase.service.repository.DatabaseClient;
import asabre.com.chase.view.callback.FilterDialogCallback;
import asabre.com.chase.viewmodel.HomeViewModel;
import io.socket.client.IO;
import io.socket.client.Socket;

public class MapFragment extends Fragment implements OnMapReadyCallback, FilterDialogCallback {
    private static final String TAG = MapFragment.class.getSimpleName();

    public static GoogleMap mGoogleMap; // prev private
    private MapView mMapView;

    public static Polyline mPolyline; // prev private

    // new variables
    ArrayList<LatLng> mMarkerPoints;

    ExtendedFloatingActionButton homeExtraFAB;
    private final FilterDialogCallback mFilterDialogCallback = this;

//     ride option widgets
    RelativeLayout userRelativeLayout;
    LinearLayout rideOptionsContainer;
    LinearLayout longVehicleOption;
    LinearLayout truckOption;
    LinearLayout vanOption;


    LinearLayout cycleOptionRequest;
    TextView cycleTime;
    TextView cyclePrice;
    TextView cyclePreviousPrice;
    MaterialButton cancelRideOption;

//    internet connectivity widgets
    MaterialButton tryAgain;
    LinearLayout mEnableInternetContainer;

    // going where widgets
    LinearLayout mGoingWhereContainer;
    MaterialButton mGoingWhere;
    RecyclerView mHolderTravelHistory;

    // request ride widgets
    LinearLayout rideRequestContainer;
    MaterialButton cancelRequest;
    ImageView rideImage;

    // ride arrives widgets
    LinearLayout driverArrivesContainer;
//    ImageView rideDriverImage;
    TextView forUserDriverArrivesTime;
    TextView forUserRideDescription;
    TextView forUserRideNumber;
    TextView forUserDriverName;
    TextView forUserDriverFinishedRides;
    TextView textGoingWhere;
//    MaterialButton forUserChangeRide;
    MaterialButton forUserFinishRide;

    // driver widget
    /*

    LinearLayout forDriverPickingContainer;
    TextView forDriverPickingMsg;
    TextView forDriverUserEntryPoint;
    TextView forDriverUserExitPoint;
    TextView forDriverUserName;
    MaterialButton forDriverCallUser;

    RelativeLayout driverPointContainer;
    LinearLayout driverPassContainer;

    */

    // driver Accept / start ride widgets
    RelativeLayout driverRelativeLayout;
    LinearLayout driverAcceptRideContainer;
    LinearLayout acceptOrDeclineRideCon;
    LinearLayout callOrStartRideLineCon;

    TextView forDriverUserFirstName;
//    TextView acceptTimeToUserPickUp;
    TextView acceptDistanceToUserPickUp;
    TextView acceptEntryLocation;
    TextView acceptExitLocation;
    MaterialButton driverAcceptRide;
    MaterialButton driverDeclineRide;
    MaterialButton driverStartRide;
    MaterialButton callUser;





    // driver end ride widgets
    LinearLayout driverEndRidContainer;
    TextView endDestinationTime;
    TextView endExitLocation;
    MaterialButton driverEndRide;

    // driver default/goOnline widgets
    LinearLayout driverGoOnlineContainer;
    MaterialButton driverGoOnline;
    TextView driverTodayEarning;
    TextView driverShowEarning;
    TextView driverCurrentRating;
    TextView driverShowCurrentRating;

    ProgressBar waitingForAccept;
    ProgressBar mapLoadingProgressBar;


    // driver accept views



    private void initDriverAcceptStartRide(View view){
        waitingForAccept = view.findViewById(R.id.watingForAccept);
        mapLoadingProgressBar = view.findViewById(R.id.mapLoadingProgressBar);
        textGoingWhere = view.findViewById(R.id.textGoingWhere);
        driverRelativeLayout = view.findViewById(R.id.driverRelativeLayout);
        driverAcceptRideContainer = view.findViewById(R.id.driverAcceptRideContainer);
        acceptOrDeclineRideCon = view.findViewById(R.id.acceptOrDeclineRide);
        callOrStartRideLineCon = view.findViewById(R.id.callOrStartRide);

        forDriverUserFirstName = view.findViewById(R.id.forDriverUserFirstName);
//      acceptTimeToUserPickUp = view.findViewById(R.id.acceptTimeToUserPickUp);
        acceptDistanceToUserPickUp = view.findViewById(R.id.acceptDistanceToUserPickUp);
        acceptEntryLocation = view.findViewById(R.id.acceptEntryLocation);
        acceptExitLocation = view.findViewById(R.id.acceptExitLocation);
        driverAcceptRide = view.findViewById(R.id.driverAcceptRide);
        driverDeclineRide = view.findViewById(R.id.driverDeclineRide);
        driverStartRide = view.findViewById(R.id.driverStartRide);
        callUser = view.findViewById(R.id.callUser);

    }


    private void initDriverGoOnline(View view){
        driverGoOnlineContainer = view.findViewById(R.id.driverGoOnlineContainer);
        driverTodayEarning = view.findViewById(R.id.driverTodayEarning);
        driverShowEarning = view.findViewById(R.id.driverShowEarning);
        driverCurrentRating = view.findViewById(R.id.driverCurrentRating);
        driverShowCurrentRating = view.findViewById(R.id.driverShowCurrentRating);
    }

    private void initDriverEndRide(View view){
        driverEndRidContainer = view.findViewById(R.id.driverEndRidContainer);
        endDestinationTime = view.findViewById(R.id.endDestinationTime);
        endExitLocation = view.findViewById(R.id.endExitLocation);
        driverEndRide = view.findViewById(R.id.endDriverEndRide);
    }

    /**
     * For testing coordinates
     * @param v1
     * @param v2
     * @param v3
     * @param v4
     * @param v5
     */

    private HomeViewModel mHomeViewModel;

    private TextView showCoords;

    private Socket mSocket;



    private void setUserContainerVisibility(View v1, View v2, View v3, View v4, View v5){
        View[] myContainer = {v1, v2, v3, v4, v5};
        for(View v : myContainer){
            v.setVisibility(View.GONE);
        }
        v1.setVisibility(View.VISIBLE);

        // v6 is for driver
    }

    private void setDriverContainerVisibility(View v1, View v2, View v3){
        View[] myContainer = {v1, v2, v3};
        for(View v : myContainer){
            v.setVisibility(View.GONE);
        }
        v1.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: called");

        // new variables
        mMarkerPoints = new ArrayList<>();
//      HomeViewModel.mMarkerPoints = new ArrayList<>();
        getDeviceLocation();
        initSocket();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        initMap(view, savedInstanceState);

        ViewTrack();
        internetConnection();
//        observeUserDriverFull();

        return view;
    }



//    private void observeUserDriverFull(){
////        if(getActivity() != null){
////
////            mHomeViewModel.init();
////            if(HomeViewModel.userType.contains("user")){
////                mHomeViewModel.getFindUserById().observe(this, user -> HomeViewModel.userFull = user);
////            } else if(HomeViewModel.userType.contains("driver")){
////                mHomeViewModel.getFindDriverById().observe(this, driver -> HomeViewModel.driverFull = driver);
////            }
////        }
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == AppConstants.AUTOCOMPLETE_REQUEST_CODE){
            Log.d(TAG, "onActivityResult: auto comp code: " + AppConstants.AUTOCOMPLETE_REQUEST_CODE);
            if(resultCode == Activity.RESULT_OK){
                Log.d(TAG, "onActivityResult: result ok code " + Activity.RESULT_OK);
                Place place = Autocomplete.getPlaceFromIntent(data);
                chosenPlace(place);

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR){
                // TODO: Handle the error
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.d(TAG, "status " + status.getStatusMessage());
            }
            else if (resultCode == Activity.RESULT_CANCELED){
                // the user cancelled the operation
            }

        }

    }


    private void chosenPlace(Place place){
        Log.d(TAG, "chosenPlace: place " + place);
        // set variables
        HomeViewModel.searchedPlaceId = place.getId();
        HomeViewModel.searchedPlaceName = place.getName();

        Log.d(TAG, "chosenPlace: place id: " + HomeViewModel.searchedPlaceId);
        Log.d(TAG, "chosenPlace: place name: " + HomeViewModel.searchedPlaceName);
        Log.d(TAG, "chosenPlace: place lat " + place.getLatLng().latitude);
        Log.d(TAG, "chosenPlace: place lng " + place.getLatLng().longitude);


        /**
         * I was using the method below coordsCallChangeExp() to download the coordinates
         * Of a place with it's place id after selecting a a place from google place fragment
         * But I've realised you can get the coordinates straight away so I've commented it
         * and calling the methods after it which at first was called in
         * DownloadTask, ParserTask , PostExecute
         */
        //        coordsCallChangeExp();

        HomeViewModel.myDestinationLatLng = place.getLatLng();
        drawRoute(HomeViewModel.myLocationLatLng, HomeViewModel.myDestinationLatLng);
        createMarkerPoints(HomeViewModel.myLocationLatLng, HomeViewModel.myDestinationLatLng);

        setPoints(HomeViewModel.myDestinationLatLng, "exit");
        HomeViewModel.setViewTrack("USER_RIDE_OPTIONS"); // set driver view

    }


    private void initSocket(){
        try {
            // TODO GENERAL
            mSocket = IO.socket("https://chase.ewquest.com");
            mSocket
                    .on(Socket.EVENT_CONNECT, args -> {
                        Log.d(TAG, "initSocket: event connected ");
                        mSocket.emit("android", "SamSung Chase app");
                    })

                    .on(Socket.EVENT_DISCONNECT, args -> {
                        Log.d(TAG, "initSocket: socket disconnected");
                    })

                    .on("socketId", args -> {
                        String socketId = args[0].toString();
                        Log.d(TAG, "initSocket: " + HomeViewModel.userType + " socket id " + socketId);
                        HomeViewModel.socketId = socketId;
                    })

                    .on("accept", args -> {
                        String acceptRideString = args[0].toString();
                        Log.d(TAG, "initSocket, accept: " + HomeViewModel.userType + " " + acceptRideString);
                        passRequestString(acceptRideString, "accept");
                    })

                    .on("start", args -> {
                        String startRideString = args[0].toString();
                        Log.d(TAG, "initSocket, start : " + HomeViewModel.userType + startRideString);
                        passRequestString(startRideString, "start");
                    })

                    .on("finish", args -> {
                        Log.d(TAG, "initSocket, finish: " + HomeViewModel.userType + args[0]);
                        String finishRideString = args[0].toString();
                        passRequestString(finishRideString, "finish");

                    })

                    .on("cancel", args -> {
                        Log.d(TAG, "initSocket, cancel: " + HomeViewModel.userType + args[0]);
                        String cancelRideString = args[0].toString();
                        passRequestString(cancelRideString, "finish");

                    })
                    // driver method
                    .on("targetDriver", args -> {
                        String pendingRequestString = args[0].toString();
                        Log.d(TAG, "initSocket: pending request is " + pendingRequestString);
                        passRequestString(pendingRequestString, "targetDriver");
                    });
            mSocket.connect();

        } catch (URISyntaxException e){
            e.printStackTrace();
        }
    }


// union systems



    private void goOnline(){
        if(HomeViewModel.userType.contains("user")){
            mSocket.emit("userOnline", ExtraClass.userGoOnlineObj())
                    .on("userOnline", args -> {
                        Log.d(TAG, "initSocket: userOnline with " + args[0]);
                    });
        }
        if(HomeViewModel.userType.contains("driver")){
            mSocket.emit("driverOnline", ExtraClass.driverGoOnlineObj())
                    .on("driverOnline", args -> {
                        Log.d(TAG, "driverGoOnline: with " + args[0]);
                    });
        }
    }


    private void passRequestString(String requestString, String requestState){
        class PassRequestString extends AsyncTask<String, Void, String>{
            @Override
            protected String doInBackground(String... strings) {
                return strings[0];
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                HomeViewModel.requestStringDetails = s;
                HomeViewModel.mRideRequest.RideRequestReset(HomeViewModel.requestStringDetails);
                switchRequest(requestState);
            }
        }
        PassRequestString passRequestString = new PassRequestString();
        passRequestString.execute(requestString);
    }

    private void switchRequest(String requestState) {
        Log.d(TAG, "switchRequest: switchingRequest");
        if(HomeViewModel.userType.contains("user")) {
            switch (requestState) {
                case "accept":
//                    showDriverArrives();
                    HomeViewModel.setViewTrack("USER_RIDE_ARRIVE");

                    break;
                case "start":
                    HomeViewModel.setViewTrack("USER_RIDE_ARRIVE");
                    showStartRide();
                    break;
                case "finish":
                    showFinishRide();
                    break;
                case "cancel":
                default: break;
            }
        }

        // driver receiving signals
        if(HomeViewModel.userType.contains("driver")){
            Log.d(TAG, "switchRequest: container driver");
            switch (requestState) {
                case "targetDriver":
//                    targetDriver();
                    showDriverUserDetails();
                    showDriverAcceptRide();
                    break;

                case "accept":
                    showDriverStartRide();
                    break;

                case "start":
                    showDriverEnd();
                    ShowDriverExitTime();
                    break;

                case "finish":
                    break;

                case "cancel":
                default: break;
            }
        }

    }


    // TODO USER METHODS
    private void userRequestRide(){
        String requestDetails = HomeViewModel.mRideRequest.initializeRequest();
        mSocket.emit("request", requestDetails);
    }


    private void userEmittingSignals(){
//        forUserChangeRide.setOnClickListener(view -> {});
        forUserFinishRide.setOnClickListener(view -> {});

        longVehicleOption.setOnClickListener(view -> {
            HomeViewModel.rideType = "car";
            userRequestRide();
            setRideImage("longVehicle");
            HomeViewModel.setViewTrack("USER_RIDE_REQUEST");
        });
        truckOption.setOnClickListener(view -> {
            HomeViewModel.rideType = "truck";
            userRequestRide();
            setRideImage("truck");
            HomeViewModel.setViewTrack("USER_RIDE_REQUEST");
        });
        vanOption.setOnClickListener(view -> {
            HomeViewModel.rideType = "van";
            userRequestRide();
            setRideImage("van");
            HomeViewModel.setViewTrack("USER_RIDE_REQUEST");
        });
    }

    private void driverEmittingSignals() {

    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    // TODO DRIVER METHODS

    private void driverEarnings(){
        driverShowEarning.setOnClickListener(view -> {
            loadDriverEarningFragment();
        });
    }
    private void driverRatings(){
        driverShowCurrentRating.setOnClickListener(view -> {
            loadDriverRatingFragment();
        });
    }


    private void showDriverUserDetails(){
        String entry =  HomeViewModel.mRideRequest.getEntryPoint().split("&")[1];
        String exit =  HomeViewModel.mRideRequest.getExitPoint().split("&")[1];
        String firstName = HomeViewModel.mRideRequest.getUserName();

        acceptEntryLocation.setText(String.format(Locale.US, "%s", entry));
        acceptExitLocation.setText(String.format(Locale.US, "%s", exit));
        forDriverUserFirstName.setText(String.format(Locale.US, "%s", firstName));

    }

    private void ShowDriverExitTime(){
        String exit =  HomeViewModel.mRideRequest.getExitPoint().split("&")[1];
        endExitLocation.setText(String.format(Locale.US, "%s", exit));
        HomeViewModel.mTimeToExit.observe(getViewLifecycleOwner(), str -> {
            endDestinationTime.setText(str);
        });
    }


    private void driverActions(){
        driverAcceptRide.setOnClickListener(view -> {

            acceptOrDeclineRideCon.setVisibility(View.GONE);
            callOrStartRideLineCon.setVisibility(View.VISIBLE);
            HomeViewModel.setViewTrack("DRIVER_ACCEPT_RIDE");
        });

        driverStartRide.setOnClickListener(view -> {
            HomeViewModel.setViewTrack("DRIVER_START_RIDE");
        });

        driverEndRide.setOnClickListener(view -> {

            clearGoogleMap();
            HomeViewModel.setViewTrack("DRIVER_END_RIDE");
        });

    }

    private void callUser(){
        callUser.setOnClickListener(view -> {
            String phoneNumber = HomeViewModel.mRideRequest.getPhoneNumber(); // tel:+233
            Log.d(TAG, "callUser: the number " + phoneNumber);
            if(getContext() != null){
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));

                if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, AppConstants.CALL_PHONE_CODE);
                    return;
                }
                startActivity(callIntent);
            }
        });


    }

    private void cancelRideOptions(){
        cancelRideOption.setOnClickListener(view -> {
            // cancel ride
//            HomeViewModel.setViewTrack("USER_GOING_WHERE");
//            HomeViewModel.setViewTrack("USER_GOING_WHERE");
        });
    }

    private void cancelRideRequest(){
        cancelRequest.setOnClickListener(view -> {
            HomeViewModel.setViewTrack("USER_RIDE_OPTIONS");
//            HomeViewModel.setViewTrack("USER_RIDE_REQUEST");
        });
    }

    private void setHomeExtraFAB(){
        homeExtraFAB.setOnClickListener(view -> {
            homeExtraFABDialog();
        });
    }

    private void homeExtraFABDialog(){
        if(getContext() != null){
            String[] options;
            // check if user is signed in
            if(HomeViewModel.userEntity != null){
//                options = new String[]{"Sign-out", "News", "About us"};
                options = new String[]{"Sign-out", "News"};
            } else {
//                options = new String[]{"Sign-in", "News", "About us"};
                options = new String[]{"Sign-in", "News"};
            }
            final String[] finalOptions = options;
            MaterialAlertDialogBuilder mADB = new MaterialAlertDialogBuilder(getContext())
                    .setTitle("Welcome from Chase")
                    .setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mFilterDialogCallback.filterDialogCallback(finalOptions[i]);
                        }
                    });
            mADB.show();
        }
    }


    /**
     * Work on about us
     * @param filterName
     */
    @Override
    public void filterDialogCallback(String filterName) {
        switch (filterName){
            case "Sign-in":
                HomeViewModel.userState = "sign";
                loadEnterNumberFragment();
                break;
            case "Sign-out":
                clearDatabase();
                break;
            case "News":
                break;
//            case "About us":
//                setAbout();
//                break;
            default:
                //  do nothing
                break;
        }
    }

    private void driverAcceptActions(){
//      driver accepts request
        HomeViewModel.driverHasRequest = true;
        addDriverDetails();
        driverResponse("accept");
        routeDriverToUserEntry();
        Toast.makeText(getContext(), "Routing driver to location", Toast.LENGTH_SHORT).show();
    }


    private void updateDriverAccept(){
        // update driver arrival time


    }

    private void driverStartActions(){
        if(HomeViewModel.countStart == 0){
            driverResponse("start");
            routeDriverToUserExit();
            HomeViewModel.countStart = 1;
        }
    }

    private void driverFinisActions(){
        // letting it run twice in case of network error
        if(HomeViewModel.countFinish == 0){
            driverResponse("finish");
            Toast.makeText(getContext(), "Finished ride", Toast.LENGTH_SHORT).show();
            loadEndRideFragment(); /** Remove this method, only for user */
            HomeViewModel.countFinish = 1;

        }
    }

    private void addDriverDetails(){
        HomeViewModel.mRideRequest.addDriverDetailsToRequest();
    }


    private void driverResponse(String emitEvent){
        HomeViewModel.rideState = emitEvent;
        HomeViewModel.requestStringDetails = HomeViewModel.mRideRequest.returnRequestString();
        mSocket.emit(emitEvent, HomeViewModel.requestStringDetails);
    }


    // this method is called in moveCamera method, when location is changed
    private void automateDriverResponse(){

        if(HomeViewModel.rideState.contains("accept")){
            // update user with driver time
            updateDriverAccept();

        } else if(HomeViewModel.rideState.contains("start")){
            driverStartActions();
        } else if(HomeViewModel.rideState.contains("finish")){
            driverFinisActions();
        }

        // TODO FINISH RIDE
        // TODO CANCEL RIDE
        else if(HomeViewModel.rideState.contains("cancel")){
            // if driver cancels ride
        }

    }

    /**
     * This function has two methods
     * Method One; calculates distance from driver to user
     * Method two; Calculates distance from user entry to exit point
     */
    private void changeRequestState() {
        Log.d(TAG, "changeRequestState: called");
        double lat1, lat2, lng1, lng2;

        if(HomeViewModel.rideState.contains("accept")){
            Toast.makeText(getContext(), HomeViewModel.rideState, Toast.LENGTH_SHORT).show();

            lat1 = HomeViewModel.myLocationLatLng.latitude;
            lat2 = Double.parseDouble(HomeViewModel.mRideRequest.getLat());
            lng1 = HomeViewModel.myLocationLatLng.longitude;
            lng2 = Double.parseDouble(HomeViewModel.mRideRequest.getLng());

            double acceptToStart = distanceBTWN(lat1, lat2, lng1, lng2);
            Log.d(TAG, "changeRequestState: diff distance " + acceptToStart);
//            acceptDistanceToUserPickUp.setText(String.valueOf(acceptToStart));
            acceptDistanceToUserPickUp.setText(String.format(Locale.US,"%.2fkm", acceptToStart));

            if(acceptToStart < 1){
                HomeViewModel.rideState = "start";
                Toast.makeText(getContext(), "Starting ride", Toast.LENGTH_SHORT).show();
            }
        }

        else if(HomeViewModel.rideState.contains("start")){
            Toast.makeText(getContext(), HomeViewModel.rideState, Toast.LENGTH_SHORT).show();
            // driving to destination
            lat1 = HomeViewModel.myLocationLatLng.latitude;
            lat2 = Double.parseDouble(HomeViewModel.mRideRequest.getExitPoint().split("&")[2]);
            lng1 = HomeViewModel.myLocationLatLng.longitude;
            lng2 = Double.parseDouble(HomeViewModel.mRideRequest.getExitPoint().split("&")[3]);

            double startToFinish = distanceBTWN(lat1, lat2, lng1, lng2);
            if(startToFinish < 1){
                HomeViewModel.rideState = "finish";
                Toast.makeText(getContext(), "Finishing ride", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void automation(){
        if(getContext() != null){
            if(HomeViewModel.userType.contains("driver")){
                Toast.makeText(getContext(), "Automation", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "automation:  called");
                automateDriverResponse();
                changeRequestState();
            }
        }
    }

    private void routeCoords(double lat1, double lat2, double lng1, double lng2){
        LatLng origin = new LatLng(lat1, lng1);
        LatLng destination = new LatLng(lat2, lng2);
        drawRoute(origin, destination);
    }

    private void routeDriverToUserEntry(){
        routeCoords(
                Double.parseDouble(HomeViewModel.userEntryPoint.split("&")[2]),
                Double.parseDouble(HomeViewModel.mRideRequest.getLat()),
                Double.parseDouble(HomeViewModel.userEntryPoint.split("&")[3]),
                Double.parseDouble(HomeViewModel.mRideRequest.getLng())
        );
    }

    private void routeDriverToUserExit(){
        routeCoords(
                Double.parseDouble(HomeViewModel.mRideRequest.getLat()),
                Double.parseDouble(HomeViewModel.mRideRequest.getExitPoint().split("&")[2]),
                Double.parseDouble(HomeViewModel.mRideRequest.getLng()),
                Double.parseDouble(HomeViewModel.mRideRequest.getExitPoint().split("&")[3])
        );
    }


    /**
     * Apart from driver accepting or rejecting ride manually,
     * the rest should be done programmatically!
     * // if driver's location is almost at user entryPoint, start ride
     */

    private void targetDriver(){
        Log.d(TAG, "targetDriver: called");
        String msg = String.format(Locale.US, "Pick up at :  %s \nDropping at :  %s",
                HomeViewModel.mRideRequest.getEntryPoint().split("&")[1],
                HomeViewModel.mRideRequest.getExitPoint().split("&")[1]);

        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getContext());
        materialAlertDialogBuilder
                .setTitle(String.format(Locale.US, "%s requesting", HomeViewModel.mRideRequest.getUserName()))
                .setMessage(msg)
                .setPositiveButton("Accept", (dialogInterface, i) -> {

                    driverAcceptActions();
                    dialogInterface.dismiss();

                }).setNegativeButton("Reject", (dialogInterface, i) -> {
            // driver rejects request
            dialogInterface.dismiss();
        });
        materialAlertDialogBuilder.setCancelable(false);
        materialAlertDialogBuilder.create();
        materialAlertDialogBuilder.show();


    }


    private void initMap(View view, @Nullable Bundle saveInstanceState) {
        mMapView = view.findViewById(R.id.chaseMapView);
        mMapView.onCreate(saveInstanceState);
        mMapView.getMapAsync(this);

        homeExtraFAB = view.findViewById(R.id.homeExtraFAB);

        init(view);
        assert getActivity() != null;
        mHomeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
    }


    private void init(View view) {
        showCoords = view.findViewById(R.id.showCoords); /** For testing purposes */
        initInternetConnectivity(view);
        initUserWidgets(view);
        initDriverWidgets(view);
    }

    private void initUserWidgets(View view){
        initGoingWhere(view);
        initRideRequest(view);
        initRideOptions(view);
        initForUserRideArrives(view);
    }

    private void initDriverWidgets(View view){
        initDriverGoOnline(view);
        initDriverAcceptStartRide(view);
        initDriverEndRide(view);
    }


    private void initGoingWhere(View view){
        userRelativeLayout = view.findViewById(R.id.userRelativeLayout);

        mGoingWhereContainer = view.findViewById(R.id.goingWhereContainer);
        mGoingWhere = view.findViewById(R.id.goingWhere);
        mHolderTravelHistory = view.findViewById(R.id.holderTravelHistory);
    }

    private void initRideRequest(View view){
        rideRequestContainer = view.findViewById(R.id.rideRequestContainer);
        cancelRequest = view.findViewById(R.id.cancelRequest);
        rideImage = view.findViewById(R.id.rideImage);
    }

    private void initRideOptions(View view){
        rideOptionsContainer = view.findViewById(R.id.rideOptionsContainer);


        cycleTime = view.findViewById(R.id.cycleTime);
        cyclePrice = view.findViewById(R.id.cyclePrice);
        cyclePreviousPrice = view.findViewById(R.id.cyclePreviousPrice);
        cancelRideOption = view.findViewById(R.id.cancelRideOption);

        longVehicleOption = view.findViewById(R.id.longVehicleOption);
        truckOption = view.findViewById(R.id.truckOption);
        vanOption = view.findViewById(R.id.vanOption);
    }

    private void initForUserRideArrives(View view){
        driverArrivesContainer = view.findViewById(R.id.forUserDriverArrivesContainer);
//      rideDriverImage = view.findViewById(R.id.rideDriverImage);
        forUserDriverArrivesTime = view.findViewById(R.id.forUserDriverArrivesTime);
        forUserRideDescription = view.findViewById(R.id.forUserRideDescription);
        forUserRideNumber = view.findViewById(R.id.forUserRideNumber);

        forUserDriverName = view.findViewById(R.id.forUserDriverFirstName);
        forUserDriverFinishedRides = view.findViewById(R.id.forUserDriverFinishedRides);
//        forUserChangeRide = view.findViewById(R.id.forUserChangeRide);
        forUserFinishRide = view.findViewById(R.id.forUserFinishRide);
    }


    private void initInternetConnectivity(View view){
        tryAgain = view.findViewById(R.id.tryAgain);
        mEnableInternetContainer = view.findViewById(R.id.enableInternetContainer);
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(getActivity() != null){
            Log.d(TAG, "onMapReady: called");

            // need to call mapsInitializer before doing any cameraUpdateFactory calls
            try {
                MapsInitializer.initialize(this.getActivity());
            } catch (Exception e) {
                e.printStackTrace();
            }
//            mGoogleMap = googleMap;
            HomeViewModel.mGoogleMap = googleMap;
//            if (mGoogleMap != null) {
//                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
//            }
            if(HomeViewModel.mGoogleMap != null){
                HomeViewModel.mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            }

            if(HomeViewModel.mLineOptions != null){
                HomeViewModel.mPolyline = HomeViewModel.mGoogleMap.addPolyline(HomeViewModel.mLineOptions);
//                mPolyline = HomeViewModel.mGoogleMap.addPolyline(HomeViewModel.mLineOptions);
//                mPolyline = mGoogleMap.addPolyline(HomeViewModel.mLineOptions);
            }

            mapClick();
        }
    }

    private void createMarkerPoints(LatLng start, LatLng end) {
        if(mMarkerPoints.size() > 1){
            mMarkerPoints.clear();
//            mGoogleMap.clear();
            HomeViewModel.mGoogleMap.clear();
        }

//        if(HomeViewModel.mMarkerPoints.size() > 1){
//            HomeViewModel.mMarkerPoints.clear();
////            mGoogleMap.clear();
//            HomeViewModel.mGoogleMap.clear();
//        }

        // adding to markerPoint
        mMarkerPoints.add(start);
        mMarkerPoints.add(end);
//        HomeViewModel.mMarkerPoints.add(start);
//        HomeViewModel.mMarkerPoints.add(end);

        // creating markerOptions
        MarkerOptions options = new MarkerOptions().title("My location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        MarkerOptions options1 = new MarkerOptions().title(HomeViewModel.searchedPlaceName)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        // Setting the position of the marker
        options.position(start);
        options1.position(end);

        // Add new marker to the Google Map Android API v2
//        mGoogleMap.addMarker(options);
//        mGoogleMap.addMarker(options1);
        HomeViewModel.mGoogleMap.addMarker(options);
        HomeViewModel.mGoogleMap.addMarker(options1);
    }

    private void mapClick() {
        // Setting onClick event listener for the map
//        mGoogleMap.setOnMapClickListener(point -> {
        HomeViewModel.mGoogleMap.setOnMapClickListener(point -> {

            // Already two locations
            if (mMarkerPoints.size() > 1) {
                mMarkerPoints.clear();
//                mGoogleMap.clear();
//                HomeViewModel.mGoogleMap.clear();
                clearGoogleMap();
            }
//            if (HomeViewModel.mMarkerPoints.size() > 1) {
//                HomeViewModel.mMarkerPoints.clear();
////                mGoogleMap.clear();
//                HomeViewModel.mGoogleMap.clear();
//            }

            // Adding new item to the ArrayList
            mMarkerPoints.add(point);
//            HomeViewModel.mMarkerPoints.add(point);

            // creating markerOptions
            MarkerOptions options = new MarkerOptions();

            // Setting the position of the marker
            options.position(point);

            /**
             * For the start location, the color of marker is GREEN and
             * for the end location, the color of the marker is RED
             */

            if (mMarkerPoints.size() == 1) {
//            if (HomeViewModel.mMarkerPoints.size() == 1) {
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            } else if (mMarkerPoints.size() == 2) {
//            } else if (HomeViewModel.mMarkerPoints.size() == 2) {
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                    options.icon(BitmapDescriptorFactory.defaultMarker(R.drawable.ic_outline_directions_car_24));
            }

            // Add new marker to the Google Map Android API v2
//            mGoogleMap.addMarker(options);
            HomeViewModel.mGoogleMap.addMarker(options);

            // checks whether start and end locations are captured
            if (mMarkerPoints.size() >= 2) {
                LatLng origin = mMarkerPoints.get(0);
                LatLng destination = mMarkerPoints.get(1);

                drawRoute(origin, destination);
            }
        });
    }

    private void clearGoogleMap(){
        HomeViewModel.mGoogleMap.clear();
    }

    private void drawRoute(LatLng origin, LatLng destination) {
        // Getting URL to the Google Direction API
//        String url = getDirectionsUrl(mOrigin, mDestination);
//        String url = getDirectionsUrl(origin, destination);
        String url = ExtraClass.setDirectionsUrl(origin, destination);
        DownloadTask downloadTask = new DownloadTask();
        // Start downloading json data from Google Direction API
        downloadTask.execute(url);

        // drawing route outsources functions
//        String url = ExtraClass.getDirectionsUrl(mOrigin, mDestination);
//       ExtraClass.DownloadTask downloadTask1 = new ExtraClass.DownloadTask();
//       downloadTask1.execute(url);


    }




    private void getDeviceLocation() {

        if (getContext() == null) { return; }
        // getting widgets
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }

        LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, "onLocationChanged: location is changing");

                LatLng currentLocationCoords = new LatLng(location.getLatitude(), location.getLongitude());

                if(location.getLatitude() > 0){
                    HomeViewModel.setMapIsReady();

                    // set the whole map relative layout toinvisible
                    mapLoadingProgressBar.setVisibility(View.GONE);
                    mMapView.setVisibility(View.VISIBLE);
                    mGoingWhere.setVisibility(View.VISIBLE);
                    textGoingWhere.setVisibility(View.VISIBLE);
                }

                // set device location
                HomeViewModel.myLocationLatLng = currentLocationCoords;
                // moving camera
                Log.d(TAG, "onComplete: lat : " + location.getLatitude() + ", lng " + location.getLongitude());

                moveCamera(currentLocationCoords, AppConstants.DEFAULT_ZOOM, "My location");

//                Toast.makeText(getContext(), "Location has changed", Toast.LENGTH_SHORT).show();

//                if (mGoogleMap != null && getContext() !=null) {
                if(HomeViewModel.mGoogleMap != null && getContext() != null){
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        return;
                    }

                    HomeViewModel.mGoogleMap.setMyLocationEnabled(true);
                    HomeViewModel.mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
                    HomeViewModel.mGoogleMap.getUiSettings().setCompassEnabled(true);
                    HomeViewModel.mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
                }

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }


        };

        locationManager.requestLocationUpdates(provider, 5000, 3, locationListener);

    }




    private void setPoints(LatLng latLng, String userCase){
        Log.d(TAG, "setPoints: called");

            try {
                    if(getContext() != null) {
                        Log.d(TAG, "setPoints: called try");
                        Log.d(TAG, "setPoints: geoCoder present " + Geocoder.isPresent());

                        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                        if (addresses.size() > 0) {
                            Log.d(TAG, "setPoints: called address");
                            Address address = addresses.get(0);
                            String locality = address.getLocality().toLowerCase(Locale.US);
                            String subLocality =  null != address.getSubLocality() ? address.getSubLocality().toLowerCase(Locale.US) : "empty"; // gets null sometimes
                            String lat = String.valueOf(latLng.latitude);
                            String lng = String.valueOf(latLng.longitude);

                            if (userCase.contains("entry")) {
                                Log.d(TAG, "setPoints: called entry");
                                HomeViewModel.userEntryPoint = locality + "&" + subLocality + "&" + lat + "&" + lng;
                                Log.d(TAG, "thePoints: EntryPoint " + HomeViewModel.userEntryPoint);
                            } else if (userCase.contains("exit")) {
                                HomeViewModel.userExitPoint = locality + "&" + HomeViewModel.searchedPlaceName + "&" + lat + "&" + lng;
                                ;
                                Log.d(TAG, "thePoints: ExitPoint " + HomeViewModel.userExitPoint);
                            }
                            goOnline(); // user,driver goes online
                        }
                    }
            } catch (IOException e){
                e.printStackTrace();
            }

    }





    private void moveCamera(LatLng latLng, float zoom, String title){
//        if(mGoogleMap != null){
        if(HomeViewModel.mGoogleMap != null){
            Log.d(TAG, "moveCamera: my location lat :" + latLng.latitude + ", lng :" + latLng.longitude);
            showCoords.setText(String.format(Locale.US, "lat %s , lng %s", latLng.latitude, latLng.longitude));

            // driver,user goes online only when coordinates are available

            setPoints(latLng, "entry");
            automation();


            // moving camera to current location, or any other location
//            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
            HomeViewModel.mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
            drawMarker(latLng);
        }
    }

    /**
     * I wanted to use the method beneath to draw a car on the map
     * @param latLng
     */
    private void drawMarker(LatLng latLng){
//        Log.d(TAG, "drawMarker: drawn");
//        Drawable carDrawable = getResources().getDrawable(R.drawable.ic_outline_directions_car_24);
//        BitmapDescriptor markerIcon = getMarkerIconFromDrawable(carDrawable);
//         BitmapDescriptor markerIcon = ExtraClass.getMarkerIconFromDrawable(carDrawable);
//
//        mGoogleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(latLng.latitude + 00.000943, latLng.longitude + 00.000598))
//                .title("Car")
//                .icon(markerIcon)
//        );
    }



    /*** A class to download place details with place_id from Google placeDetails */

    private void downloadCoords(String placeIdString){
        Log.d(TAG, "downloadCoords: started with id " + placeIdString);
        DownloadCoords dc =  new DownloadCoords();
        dc.execute(placeIdString);
    }


    /**
     * A class to download coords with place_id from Google places details
     */

    private class DownloadCoords extends AsyncTask<String, Void, String> {
        private final String TAG = DownloadCoords.class.getCanonicalName();
        // downloading data in a non-ui thread

        @Override
        protected String doInBackground(String... strings) {
            String data = "";
            try {
                // Fetching the data from web service
//                data = downloadUrl(strings[0]);
                data = ExtraClass.downloadUrl(strings[0]);
            } catch (Exception e){
                Log.d(TAG, "doInBackground: Task failed, " + e.toString());
            }
            return data;
        }
        // Executes in ui thread
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ParseCoords parseCoords = new ParseCoords();
            parseCoords.execute(s);
        }
    }

    private class ParseCoords extends AsyncTask<String, Void, List<List<HashMap<String, String>>>>{
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {

            JSONObject jsonObject;
            List<List<HashMap<String, String>>> placeDetails = null;
            try {
                Log.d(TAG, "doInBackground: trying  " + strings[0]);
                jsonObject = new JSONObject(strings[0]);

                CoordsJsonParser parser = new CoordsJsonParser();
                placeDetails = parser.parse(jsonObject);

            } catch (Exception e){
                e.printStackTrace();
            }
//            Log.d(TAG, "place details " + placeDetails);
            return placeDetails;
        }

        // execute in ui thread after parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            super.onPostExecute(lists);

            String lat;
            String lng;

            if(lists != null && lists.size() > 0){
                List<HashMap<String, String>> path = lists.get(0);
                HashMap<String, String> theLatLng = path.get(0);
                lat = theLatLng.get("lat");
                lng = theLatLng.get("lng");

                Log.d(TAG, "the new lat : " + lat + ", lng: " + lng);

                HomeViewModel.myDestinationLatLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                drawRoute(HomeViewModel.myLocationLatLng, HomeViewModel.myDestinationLatLng);
                createMarkerPoints(HomeViewModel.myLocationLatLng, HomeViewModel.myDestinationLatLng);

                setPoints(HomeViewModel.myDestinationLatLng, "exit");
            }
        }
    }




    /**  A class to download data from Google Directions URL */
    private class DownloadTask extends AsyncTask<String, Void, String> {
        private final String TAG = DownloadTask.class.getSimpleName();
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
        private final String TAG = ParserTask.class.getSimpleName();

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
                    lineOptions.width(3);
                    lineOptions.color(Color.RED);

                    HomeViewModel.mLineOptions = lineOptions;
                }

                if(HomeViewModel.mPolyline != null){
                    HomeViewModel.mPolyline.remove();
                }
                HomeViewModel.mPolyline = HomeViewModel.mGoogleMap.addPolyline(HomeViewModel.mLineOptions);

                Log.d(TAG, "onPostExecute: Total distance is " + distance.split(" ")[0]);
                Log.d(TAG, "onPostExecute: Total duration is " + duration.split(" ")[0]);

                // distance = 0.8 km
                // distance = 4 mins

                HomeViewModel.mTimeToExit.setValue(duration.split(" ")[0]);
                HomeViewModel.mDistanceToExit.setValue(distance.split(" ")[0]);
            }
        }
    }




    private void goingWHereListener(){
        mGoingWhere.setOnClickListener(view -> {
            placeIntent();
//            HomeViewModel.setViewTrack("");
        });
    }

    private void placeIntent(){
        Log.d(TAG, "placeIntent: called");
        if(!Places.isInitialized()){
            Places.initialize(requireContext(), AppConstants.API_KEY);
        }
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

        // start the autocomplete intent
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(getContext());
        startActivityForResult(intent, AppConstants.AUTOCOMPLETE_REQUEST_CODE);
    }


    private void containerListeners(){
        /**
         * When a container is clicked the map beneath receives the click
         * To prevent this set click listener for all the containers
         */

        mGoingWhereContainer.setOnClickListener(view -> {});
        mEnableInternetContainer.setOnClickListener(view -> {});
        rideOptionsContainer.setOnClickListener(view -> {});
        rideRequestContainer.setOnClickListener(view -> {});
        driverArrivesContainer.setOnClickListener(view -> {});
//        forDriverPickingContainer.setOnClickListener(view -> {});
        driverGoOnlineContainer.setOnClickListener(view -> {});
        driverEndRidContainer.setOnClickListener(view -> {});
        driverAcceptRideContainer.setOnClickListener(view -> {});

    }

    private void internetConnection(){
        Log.d(TAG, "internetConnection: called");
        if(internetEnabled()){
            if(HomeViewModel.userType.equals("user")){
                userRelativeLayout.setVisibility(View.VISIBLE);
                driverRelativeLayout.setVisibility(View.GONE);
//                showGoingWhere();
                HomeViewModel.setViewTrack("USER_GOING_WHERE");
//                HomeViewModel.strViewTrack = "USER_GOING_WHERE";
            } else if(HomeViewModel.userType.equals("driver")){
                userRelativeLayout.setVisibility(View.GONE);
                driverRelativeLayout.setVisibility(View.VISIBLE);
//                showDriverGoOnline();
                HomeViewModel.setViewTrack("DRIVER_GO_ONLINE");
//                HomeViewModel.strViewTrack = "DRIVER_GO_ONLINE";
            }


        } else {
            Log.d(TAG, "internetConnection: internet is off baby");
//            showInternet();

            HomeViewModel.setViewTrack("CONNECT_INTERNET");
//            HomeViewModel.strViewTrack = "CONNECT_INTERNET";
        }


    }



    private void restartActivity(){
        if(getActivity() != null){
            Intent intent = getActivity().getIntent();
            getActivity().finish();
            startActivity(intent);
        }
    }

    private void setTryAgain(){
        tryAgain.setOnClickListener(view -> {
            restartActivity();
        });
    }


    private boolean internetEnabled(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }


    private void updateUser(String msg){
        forUserDriverArrivesTime.setText(msg);
    }

    private void showStartRide(){
        forUserDriverArrivesTime.setText(String.format(Locale.US, "Driving to destination"));
        forUserFinishRide.setVisibility(View.VISIBLE);
    }
    private void showFinishRide(){
        forUserDriverArrivesTime.setText(String.format(Locale.US, "Arrived at destination"));
        loadEndRideFragment();
    }

    /**
     * Work on this
     */
    private void resetDriverArrives(){
        forUserDriverArrivesTime.setText(String.format(Locale.US, "Driver arrives in %s min", HomeViewModel.mRideRequest.getdArrivalTime()));
        forUserRideDescription.setText(HomeViewModel.mRideRequest.getdRideDescription());
        forUserRideNumber.setText(HomeViewModel.mRideRequest.getdRideNumber());
        forUserDriverName.setText(String.format(Locale.US, "Your driver is %s", HomeViewModel.mRideRequest.getdName()));
        forUserDriverFinishedRides.setText(String.format(Locale.US,"%s finished rides", HomeViewModel.mRideRequest.getdFinishedRides()));

    }

    private void showDriverArrives(){
//        HomeViewModel.mRideRequest.RideRequestReset(HomeViewModel.requestStringDetails);
        resetDriverArrives();
        setUserContainerVisibility(driverArrivesContainer,
                mGoingWhereContainer,
                mEnableInternetContainer,
                rideOptionsContainer,
                rideRequestContainer);
    }

    private void showRideOptions(){
        setUserContainerVisibility(rideOptionsContainer,
                mEnableInternetContainer,
                mGoingWhereContainer,
                rideRequestContainer,
                driverArrivesContainer);
    }

    private void showInternet(){
        setUserContainerVisibility(mEnableInternetContainer,
                rideOptionsContainer,
                mGoingWhereContainer,
                rideRequestContainer,
                driverArrivesContainer);
    }

    private void showGoingWhere(){
        setUserContainerVisibility(mGoingWhereContainer,
                mEnableInternetContainer,
                rideOptionsContainer,
                rideRequestContainer,
                driverArrivesContainer);
    }

    private void showRideRequest(){
//    private void showRideRequest(String rideType){
        setUserContainerVisibility(rideRequestContainer,
                mGoingWhereContainer,
                mEnableInternetContainer,
                rideOptionsContainer,
                driverArrivesContainer);

//        setRideImage(rideType);

        // request ride view model
    }




    private void showDriverGoOnline(){
        setDriverContainerVisibility(driverGoOnlineContainer,
                                    driverAcceptRideContainer,
                                    driverEndRidContainer);
    }
    private void showDriverAcceptRide(){
        setDriverContainerVisibility(driverAcceptRideContainer,
                                    driverGoOnlineContainer,
                                    driverEndRidContainer);
    }
    private void showDriverStartRide(){
        driverAcceptRide.setVisibility(View.GONE);
        driverStartRide.setVisibility(View.VISIBLE);
    }
    private void showDriverEnd(){
        setDriverContainerVisibility(driverEndRidContainer,
                                    driverGoOnlineContainer,
                                    driverAcceptRideContainer);


    }


    private void setRideImage(String rideType){
        switch (rideType){
            case "Car":
                rideImage.setImageResource(R.drawable.ic_baseline_directions_car_24);
                break;
            case "longVehicle":
                rideImage.setImageResource(R.drawable.long_vehicle);
                break;
            case "truck":
                rideImage.setImageResource(R.drawable.truck);
                break;
            case "van":
                rideImage.setImageResource(R.drawable.van);
                break;
            default: break; // do nothing
        }
    }



    private double distanceBTWNS(double lat1, double lat2, double lng1, double lng2){

        Location startLocation = new Location("");
        startLocation.setLatitude(lat1);
        startLocation.setLongitude(lng1);

        Location endLocation = new Location("");
        endLocation.setLatitude(lat2);
        endLocation.setLongitude(lng2);

        return startLocation.distanceTo(endLocation);
    }

    private double distanceBTWN(double lat1, double lat2, double lng1, double lng2){
        // The math module contains a function named toRadians
        // which converts from degrees to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        lng1 = Math.toRadians(lng1);
        lng2 = Math.toRadians(lng2);

        // Haversine formula
        double dLat = lat2 - lat1;
        double dLng = lng2 - lng1;

        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dLng / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radians of earth in kilometers. User 3956 for miles
        double r = 6371 * 1000;  // to meters
        // calculate the result
        return (c * r);
    }



    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();

        Log.d(TAG, "mapFrag onStart: called");

        containerListeners();
        goingWHereListener();
        setTryAgain();
        userEmittingSignals();

        callUser();
        setHomeExtraFAB();
        driverActions();

        cancelRideOptions();
        cancelRideRequest();

        driverEarnings();
        driverRatings();


    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume map: called");
        mMapView.onResume();
//        HomeViewModel.mMapView.onResume();

        Log.d(TAG, "onResume map : place id: " + HomeViewModel.searchedPlaceId);
        if(!HomeViewModel.searchedPlaceId.isEmpty()){
//            Log.d(TAG, "onResume: the id: " + HomeViewModel.searchedPlaceId);
//            downloadCoords(ExtraClass.mGetCoords(HomeViewModel.searchedPlaceId));
////            showRideOptions();
////            HomeViewModel.searchedPlaceId = "";
//
//            HomeViewModel.setViewTrack("USER_RIDE_OPTIONS");
////            HomeViewModel.strViewTrack = "USER_RIDE_OPTIONS";

        }
    }

    private void coordsCallChangeExp(){
        Log.d(TAG, "onResume: the id: " + HomeViewModel.searchedPlaceId);
        downloadCoords(ExtraClass.mGetCoords(HomeViewModel.searchedPlaceId));
//            showRideOptions();
//            HomeViewModel.searchedPlaceId = "";

        HomeViewModel.setViewTrack("USER_RIDE_OPTIONS");
//            HomeViewModel.strViewTrack = "USER_RIDE_OPTIONS";
    }

    private void ViewTrack() {

        HomeViewModel.getViewTrack().observe(getViewLifecycleOwner(), s -> {
            switch (s){
                // for user
                case "USER_GOING_WHERE":
                    Log.d(TAG, "USER_GOING_WHERE: called withing");
                    showGoingWhere();
                    break;
                case "USER_RIDE_OPTIONS":
                    Log.d(TAG, "USER_RIDE_OPTIONS: called withing");
                    showRideOptions();
                    break;
                case "USER_RIDE_REQUEST":
                    showRideRequest();
                    break;
                case "USER_RIDE_ARRIVE":
                    showDriverArrives();
                    break;

                    // for driver
                case "DRIVER_GO_ONLINE":
                    showDriverGoOnline();
                    break;
                case "DRIVER_ACCEPT_RIDE":
                   setExitTimeAndDistance();
                    showDriverStartRide();
                    driverAcceptActions();
                    Log.d(TAG, "details from driver " + HomeViewModel.mRideRequest);
                    waitingForAccept.setVisibility(View.GONE);
                    break;
                /**
                 * work on this
                 * adding time to arrive to exit location
                 */
                case "DRIVER_START_RIDE":
                    HomeViewModel.mTimeToExit.observe(getViewLifecycleOwner(), str -> {
                        endDestinationTime.setText(str);
                    });

                    endExitLocation.setText(" ");

                    driverStartActions();
                    showDriverEnd();
                    break;
                case "DRIVER_END_RIDE":
                    driverFinisActions();
                    showDriverGoOnline();
                    break;

                case "CONNECT_INTERNET":
                    showInternet();
                    break;
                default:
                    // do nothing
                    break;
            }
        });

    }



    private void setExitTimeAndDistance(){
        HomeViewModel.mDriverName.setValue(HomeViewModel.mRideRequest.getdName());
        HomeViewModel.mFinishedRides.setValue(HomeViewModel.mRideRequest.getdFinishedRides());
    }


    private void clearDatabase(){
        HomeViewModel.reset();

        class ClearDatabase extends AsyncTask<Void, Void, Void>{
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient
                        .getInstance(getContext())
                        .getAppDatabase()
                        .mUserDao()
                        .deleteAll();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                loadIntroFragment();
            }
        }
        ClearDatabase clearDatabase = new ClearDatabase();
        clearDatabase.execute();
    }

    private void loadEndRideFragment(){
      if(getActivity() != null){
          EndRideFragment endRideFragment = new EndRideFragment();
          FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
          FragmentTransaction transaction = fragmentManager.beginTransaction();
          transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
          transaction.replace(R.id.conHome, endRideFragment);
          transaction.addToBackStack(null);
          transaction.commit();
      }
    }

    private void loadEnterNumberFragment(){
      if (getActivity() != null){
          EnterNumberFragment enterNumberFragment = new EnterNumberFragment();
          FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
          FragmentTransaction transaction = fragmentManager.beginTransaction();
          transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
          transaction.replace(R.id.conHome, enterNumberFragment);
          transaction.addToBackStack(null);
          transaction.commit();
      }
    }

    private void loadIntroFragment(){
       if(getActivity() != null){
           IntroFragment introFragment = new IntroFragment();
           FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
           FragmentTransaction transaction = fragmentManager.beginTransaction();
           transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
           transaction.replace(R.id.conHome, introFragment);
           transaction.addToBackStack(null);
           transaction.commit();
       }
    }

    private void loadDriverEarningFragment(){
        if(getActivity() != null){
            DriverEarningFragment driverEarningFragment = new DriverEarningFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.replace(R.id.conHome, driverEarningFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
    private void loadDriverRatingFragment(){
        if(getActivity() != null){
            DriverRatingFragment driverRatingFragment = new DriverRatingFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.replace(R.id.conHome, driverRatingFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }



    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: called");
        mMapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: called");
        mMapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "onLowMemory: called");
        mMapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called");
        mMapView.onDestroy();
    }


}

// union systems


//a smile forged in starlight
//Never get between a man and his meal


