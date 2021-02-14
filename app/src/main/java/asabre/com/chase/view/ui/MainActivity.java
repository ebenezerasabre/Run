package asabre.com.chase.view.ui;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import asabre.com.chase.R;
import asabre.com.chase.service.model.UserEntity;
import asabre.com.chase.service.repository.DatabaseClient;
import asabre.com.chase.viewmodel.HomeViewModel;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.libraries.places.api.Places;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();


    private static int trackPermission = 0;
    BottomNavigationView navigationView;

    // General tracking
    public enum TrackMain{HOME,HISTORY,ABOUT,PROFILE}
    public static TrackMain mTrackMain = TrackMain.HOME;

    // specific tracking
    public enum HomeTrack{HOME,HOME_DETAILS,HOME_EXTRA}
    public static HomeTrack mHomeTrack = HomeTrack.HOME;

    public enum HistoryTrack{HISTORY,HISTORY_DETAILS,HISTORY_EXTRA}
    public static HistoryTrack mHistoryTrack = HistoryTrack.HISTORY;

    public enum AboutTrack{ABOUT,ABOUT_DETAILS,ABOUT_EXTRA}
    public static AboutTrack mAboutTrack = AboutTrack.ABOUT;

    public enum ProfileTrack{PROFILE,PROFILE_DETAILS,PROFILE_EXTRA}
    public static ProfileTrack mProfileTrack = ProfileTrack.PROFILE;

    public enum  RegistrationProcess{INTRO,NUMBER,EMAIL,NAME,CAR_DETAILS,DONE}
    public static RegistrationProcess mRegProcess = RegistrationProcess.INTRO;

    public static String API_KEY;

    private HomeViewModel mHomeViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate main: called");
        setContentView(R.layout.activity_main);

        checkPermission();
        setBottomNavigation();

        init();
        Log.d(TAG, "onCreate: here " + HomeViewModel.msg);

//        readSMS();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: called");
        outState.putString("placeId", HomeViewModel.searchedPlaceId);
        Log.d(TAG, "onCreate: here " + HomeViewModel.msg);
    }



    private void init(){
        initPlaces();
        HomeViewModel.setViewTrack("USER_GOING_WHERE");
    }

    private void initPlaces(){
        API_KEY = getString(R.string.google_maps_api_key_fun);
        if(!Places.isInitialized()){
            Places.initialize(this, API_KEY);
        }
    }


    // method to retrieve user data from database when app is first launched
    // else user has to sign-in or sign-up
    private void getCustomerData(){

        // check if userEntity is null
        if(HomeViewModel.userEntity == null){
            class GetCustomerData extends AsyncTask<Void, Void, List<UserEntity>>{
                @Override
                protected List<UserEntity> doInBackground(Void... voids) {
                    return DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .mUserDao()
                            .getAll();
                }

                @Override
                protected void onPostExecute(List<UserEntity> userEntities) {
                    super.onPostExecute(userEntities);
                    if(userEntities.size() != 0){
                        HomeViewModel.userEntity = userEntities.get(0);
                        HomeViewModel.userType = HomeViewModel.userEntity.getUserType();
                        loadUserDriverFull();
                        Log.d(TAG, "firstName " + HomeViewModel.userEntity.getFirstName());
                        Log.d(TAG, "driverId " + HomeViewModel.userEntity.get_id());
                        loadMapFragment();
//                        loadRequestFragment();
                    } else {
                        // sign up user
                        startRegistering();
                    }
                }
            }
            GetCustomerData getCustomerData = new GetCustomerData();
            getCustomerData.execute();

        } else {
            Log.d(TAG, "getCustomerData: userEntity is not null");
            // user is already logged in
            loadMapFragment();
//            loadRequestFragment();
        }

    }

    private void loadUserDriverFull(){
        mHomeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mHomeViewModel.init();
        if(HomeViewModel.userType.contains("user")){
            mHomeViewModel.setFindUserById(HomeViewModel.userEntity.get_id());
        } else if(HomeViewModel.userType.contains("driver")){
            mHomeViewModel.setFindDriverById(HomeViewModel.userEntity.get_id());
        }
    }

    private void loadIntroFragment(){
        IntroFragment introFragment = new IntroFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.containerHome, introFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


    private void startRegistering(){
        switch (mRegProcess){
            case INTRO:
                loadIntroFragment();
                break;
            case NUMBER:
                loadEnterNumberFragment();
                break;
            case EMAIL:
                loadEmailFragment();
                break;
            case NAME:
                loadEnterNameFragment();
                break;
            case CAR_DETAILS:
                loadCarDetailsFragment();
                break;
            case DONE:
            default:
                loadMapFragment();
                break; // do nothing
        }
    }




    public boolean requireContext() {
        return getApplicationContext() != null;
    }




    private void loadProfileFragment(){
        ProfileFragment profileFragment = new ProfileFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerProfile, profileFragment)
                .commit();
    }


    private void setBottomNavigation(){
        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return setVisibility(item);
    }


    private boolean setVisibility(MenuItem menuItem){
        String title = menuItem.getTitle().toString();
        switch (title){
            case "Home":    setHome();      break;
            case "History": setHistory();   break;
            case "About":   setAbout();     break;
            case "Profile": setProfile();   break;
            default: break; // do nothing
        }
        return true;
    }

    private void setHome(){
        mTrackMain = TrackMain.HOME;
        switch (mHomeTrack){
            case HOME: setHomeVisible();break;
            case HOME_DETAILS: setHomeDetailsVisible();break;
            case HOME_EXTRA: setHomeExtraVisible();break;
            default: break; // do noting
        }
    }
    private void setHistory(){
        mTrackMain = TrackMain.HISTORY;
        switch (mHistoryTrack){
            case HISTORY: setHistoryVisible();break;
            case HISTORY_DETAILS: setHistoryDetailsVisible();break;
            case HISTORY_EXTRA: setHistoryExtraVisible();break;
            default: break; // do nothing
        }
    }
    private void setAbout(){
        mTrackMain = TrackMain.ABOUT;
        switch (mAboutTrack){
            case ABOUT: setAboutVisible();break;
            case ABOUT_DETAILS: setAboutDetailsVisible();break;
            case ABOUT_EXTRA: setAboutExtraVisible();break;
            default: break; // do nothing
        }
    }
    private void setProfile(){
        mTrackMain = TrackMain.PROFILE;
        switch (mProfileTrack){
            case PROFILE: setProfileVisible();break;
            case PROFILE_DETAILS: setProfileDetailsVisible();
            case PROFILE_EXTRA: setProfileExtraVisible();break;
            default: break; // do noting
        }
    }


    private void checkPermission() {
        int permissionCallPhone = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        int permissionFineLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionCoarseLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
//        int permissionReadExternalStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
//        int permissionWriteExternalStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionSendSms = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
//        int permissionReadSms = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);


        List<String> listPermissionsNeed = new ArrayList<>();

//        if(permissionCallPhone != PackageManager.PERMISSION_GRANTED){ listPermissionsNeed.add(Manifest.permission.CALL_PHONE); }
        if(permissionFineLocation != PackageManager.PERMISSION_GRANTED) { listPermissionsNeed.add(Manifest.permission.ACCESS_FINE_LOCATION); }
        if(permissionCoarseLocation != PackageManager.PERMISSION_GRANTED){ listPermissionsNeed.add(Manifest.permission.ACCESS_COARSE_LOCATION); }
//        if(permissionWriteExternalStorage != PackageManager.PERMISSION_GRANTED){ listPermissionsNeed.add(Manifest.permission.WRITE_EXTERNAL_STORAGE); }
//        if(permissionReadExternalStorage != PackageManager.PERMISSION_GRANTED){ listPermissionsNeed.add(Manifest.permission.READ_EXTERNAL_STORAGE); }
        if(permissionSendSms != PackageManager.PERMISSION_GRANTED){ listPermissionsNeed.add(Manifest.permission.SEND_SMS); }
//        if(permissionReadSms != PackageManager.PERMISSION_GRANTED){ listPermissionsNeed.add(Manifest.permission.READ_SMS); }

        if(!listPermissionsNeed.isEmpty()){
            ActivityCompat.requestPermissions(this, listPermissionsNeed.toArray(new String[listPermissionsNeed.size()]), AppConstants.REQUEST_ID_MULTIPLE_PERMISSIONS);
        }
    }


    private boolean gpsIsOn(){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: main called");
        Log.d(TAG, "main the requestCode is " + requestCode);
        switch (requestCode){
            case AppConstants.REQUEST_ID_MULTIPLE_PERMISSIONS:
                if(grantResults.length > 0){
                    for(int z=0; z<grantResults.length; z++){
                        if(grantResults[z] != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(getApplicationContext(), "Some permission denied", Toast.LENGTH_LONG).show();
                            trackPermission += 1;
                            grantPermissionsDialog();
                            return;
                        }
                    }
                    Toast.makeText(getApplicationContext(), "All permissions granted", Toast.LENGTH_LONG).show();
                    if(trackPermission >= 1){
                        restartActivity();
                    }
                }
                break;
            case AppConstants.CALL_PHONE_CODE:
                Log.d(TAG, "onRequestPermissionsResult: from mainActivity");
                callUser();
                break;

        }
    }


//    String apiKey;

    private void callUser(){
        String phoneNumber = HomeViewModel.mRideRequest.getPhoneNumber(); // tel:+233
        Log.d(TAG, "callUser: the number " + phoneNumber);
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(String.format(Locale.US, "tel:%s", phoneNumber)));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: of main called");
        Log.d(TAG, "requestCode main: " + requestCode);
        Log.d(TAG, "resultCode main: " + resultCode);

            if(requestCode == AppConstants.GPS_REQUEST){
                Log.d(TAG, "onActivityResult: gps code");
                if(resultCode == Activity.RESULT_OK){
                    Log.d(TAG, "onActivityResult: gos ok");
                        Toast.makeText(getApplicationContext(), "Gps turned on", Toast.LENGTH_LONG).show();
                        restartActivity();
                }
            }

        if(resultCode == Activity.RESULT_OK){
            Log.d(TAG, "onActivityResult: main ok is well");

            if(requestCode == AppConstants.AUTOCOMPLETE_REQUEST_CODE){
                Log.d(TAG, "onActivityResult: main all auto here");
            }
        }


    }

    private void restartActivity(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void loadMapFragment(){
        if(gpsIsOn()){
            MapFragment mapFragment = new MapFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.replace(R.id.containerHome, mapFragment);
            transaction.addToBackStack(null);
            transaction.commit();

            loadRequestFragment();
            loadAboutFragment();
            loadProfileFragment();
        } else {
            loadOpenFragment();
        }
    }

    private void loadRequestFragment(){
            RequestFragment requestFragment = new RequestFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.replace(R.id.containerRequests, requestFragment);
            transaction.addToBackStack(null);
            transaction.commit();

    }

    private void loadEnterNameFragment(){
        EnterNameFragment confirmName = new EnterNameFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.containerHome, confirmName);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadEnterNumberFragment(){
        EnterNumberFragment enterNumberFragment = new EnterNumberFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.containerHome, enterNumberFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadEmailFragment(){
        EnterEmailFragment enterEmailFragment = new EnterEmailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.containerHome, enterEmailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadOpenFragment(){
        OpenFragment openFragment = new OpenFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.containerHome, openFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadCarDetailsFragment(){
        EnterCarDetailsFragment carDetailsFragment = new EnterCarDetailsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.containerHome, carDetailsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadAboutFragment(){
            AboutFragment aboutFragment = new AboutFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.replace(R.id.containerAbout, aboutFragment);
            transaction.addToBackStack(null);
            transaction.commit();
    }





    private void grantPermissionsDialog(){
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
        materialAlertDialogBuilder.setMessage("App can't work properly if all request permissions are not granted")
                .setPositiveButton("Grant Permission", (dialogInterface, i) -> checkPermission())
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
        materialAlertDialogBuilder.create();
        materialAlertDialogBuilder.show();
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: main called");

        if(requireContext()){
            getCustomerData(); /** use this instead  */
//            loadMapFragment(); /** Calling this for testing coordinate differences */
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "act onResume: main called");
        Log.d(TAG, "onResume main : place id: " + HomeViewModel.searchedPlaceId);

        Log.d(TAG, "onResume: here " + HomeViewModel.msg);
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: main called");
        Log.d(TAG, "onStop main : place id: " + HomeViewModel.searchedPlaceId);

        Log.d(TAG, "onStop: here " + HomeViewModel.msg);
    }



    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: main called");
    }


    private void setHomeVisible(){
        setVisibility(
                HomeFrame(),HomeDetailsFrame(),HomeExtraFrame(),
                HistoryFrame(),HistoryDetailsFrame(),HistoryExtraFrame(),
                AboutFrame(),AboutDetailsFrame(),AboutExtraFrame(),
                ProfileFrame(),ProfileDetailsFrame(),ProfileExtraFrame()
        );
    }
    private void setHomeDetailsVisible(){
        setVisibility(
                HomeDetailsFrame(),HomeExtraFrame(),HomeFrame(),
                HistoryFrame(),HistoryDetailsFrame(),HistoryExtraFrame(),
                AboutFrame(),AboutDetailsFrame(),AboutExtraFrame(),
                ProfileFrame(),ProfileDetailsFrame(),ProfileExtraFrame()
        );
    }
    private void setHomeExtraVisible(){
        setVisibility(
                HomeExtraFrame(),HomeFrame(),HomeDetailsFrame(),
                HistoryFrame(),HistoryDetailsFrame(),HistoryExtraFrame(),
                AboutFrame(),AboutDetailsFrame(),AboutExtraFrame(),
                ProfileFrame(),ProfileDetailsFrame(),ProfileExtraFrame()
        );
    }

    private void setHistoryVisible(){
        setVisibility(
                HistoryFrame(),HistoryDetailsFrame(),HistoryExtraFrame(),
                HomeFrame(),HomeDetailsFrame(),HomeExtraFrame(),
                AboutFrame(),AboutDetailsFrame(),AboutExtraFrame(),
                ProfileFrame(),ProfileDetailsFrame(),ProfileExtraFrame()
        );
    }
    private void setHistoryDetailsVisible(){
        setVisibility(
                HistoryDetailsFrame(),HistoryFrame(),HistoryExtraFrame(),
                HomeDetailsFrame(),HomeExtraFrame(),HomeFrame(),
                AboutFrame(),AboutDetailsFrame(),AboutExtraFrame(),
                ProfileFrame(),ProfileDetailsFrame(),ProfileExtraFrame()
        );
    }
    private void setHistoryExtraVisible(){
        setVisibility(
                HistoryExtraFrame(),HistoryFrame(),HistoryDetailsFrame(),
                HomeExtraFrame(),HomeFrame(),HomeDetailsFrame(),
                AboutFrame(),AboutDetailsFrame(),AboutExtraFrame(),
                ProfileFrame(),ProfileDetailsFrame(),ProfileExtraFrame()
        );
    }


    private void setAboutVisible(){
        setVisibility(
                AboutFrame(),AboutDetailsFrame(),AboutExtraFrame(),
                HistoryFrame(),HistoryDetailsFrame(),HistoryExtraFrame(),
                HomeFrame(),HomeDetailsFrame(),HomeExtraFrame(),
                ProfileFrame(),ProfileDetailsFrame(),ProfileExtraFrame()
        );
    }
    private void setAboutDetailsVisible(){
        setVisibility(
                AboutDetailsFrame(),AboutExtraFrame(),AboutFrame(),
                HistoryDetailsFrame(),HistoryFrame(),HistoryExtraFrame(),
                HomeDetailsFrame(),HomeExtraFrame(),HomeFrame(),
                ProfileFrame(),ProfileDetailsFrame(),ProfileExtraFrame()
        );
    }
    private void setAboutExtraVisible(){
        setVisibility(
                AboutExtraFrame(), AboutFrame(),AboutDetailsFrame(),
                HistoryExtraFrame(),HistoryFrame(),HistoryDetailsFrame(),
                HomeExtraFrame(),HomeFrame(),HomeDetailsFrame(),
                ProfileFrame(),ProfileDetailsFrame(),ProfileExtraFrame()
        );
    }

    private void setProfileVisible(){
        setVisibility(
                ProfileFrame(),ProfileDetailsFrame(),ProfileExtraFrame(),
                AboutFrame(),AboutDetailsFrame(),AboutExtraFrame(),
                HistoryFrame(),HistoryDetailsFrame(),HistoryExtraFrame(),
                HomeFrame(),HomeDetailsFrame(),HomeExtraFrame()
        );
    }
    private void setProfileDetailsVisible(){
        setVisibility(
                ProfileDetailsFrame(),ProfileExtraFrame(),ProfileFrame(),
                AboutDetailsFrame(),AboutExtraFrame(),AboutFrame(),
                HistoryDetailsFrame(),HistoryFrame(),HistoryExtraFrame(),
                HomeDetailsFrame(),HomeExtraFrame(),HomeFrame()
        );
    }
    private void setProfileExtraVisible(){
        setVisibility(
                ProfileExtraFrame(),ProfileFrame(),ProfileDetailsFrame(),
                AboutExtraFrame(), AboutFrame(),AboutDetailsFrame(),
                HistoryExtraFrame(),HistoryFrame(),HistoryDetailsFrame(),
                HomeExtraFrame(),HomeFrame(),HomeDetailsFrame()
        );
    }



    private void setVisibility(View v1, View v2, View v3,
                               View v4, View v5, View v6,
                               View v7, View v8, View v9,
                               View v10, View v11, View v12) {
        View[] arr = {v1,v2,v3,v4,v5,v6, v7,v8,v9,v10,v11,v12};
        for(View v : arr){ v.setVisibility(View.GONE); }
        v1.setVisibility(View.VISIBLE);
    }


    private View HomeFrame(){return findViewById(R.id.containerHome);}
    private View HomeDetailsFrame(){ return findViewById(R.id.containerHomeDetails);}
    private View HomeExtraFrame(){return findViewById(R.id.containerHomeExtra);}

    private View HistoryFrame(){return findViewById(R.id.containerRequests);}
    private View HistoryDetailsFrame(){return findViewById(R.id.containerHistoryDetails);}
    private View HistoryExtraFrame(){return findViewById(R.id.containerHistoryExtra);}

    private View AboutFrame(){return findViewById(R.id.containerAbout);}
    private View AboutDetailsFrame(){return findViewById(R.id.containerAboutDetails);}
    private View AboutExtraFrame(){return findViewById(R.id.containerAboutExtra);}

    private View ProfileFrame(){return findViewById(R.id.containerProfile);}
    private View ProfileDetailsFrame(){return findViewById(R.id.containerProfileDetails);}
    private View ProfileExtraFrame(){return findViewById(R.id.containerProfileExtra);}



}

//EnterNumberFragment enterNumberFragment = new EnterNumberFragment();
//getSupportFragmentManager()
//.beginTransaction()
//        .replace(R.id.containerHome, enterNumberFragment)
//        .commit();