package asabre.com.chase.view.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

public class LocationFinder extends Service implements LocationListener {
    private static final String TAG = LocationFinder.class.getSimpleName();

    Context mContext;

    boolean mIsGPSEnabled = false;

    // flag for network status
    boolean mIsNetworkEnabled = false;

    // flag for GPS status
    boolean mCanGetLocation = false;

    Location mLocation;
    double mLongitude;
    double mLatitude;

    // the minimum distance to change updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // the minimum time between updates in milliseconds
    public static final long MIN_TIME_BW_UPDATES = 2000; // 2 seconds

    // declaring a location manager
    protected LocationManager mLocationManager;

    public LocationFinder(Context context) {
        mContext = context;
        getLocation();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

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

    public Location getLocation() {
        try {
            mLocationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            // getting GPS status
            mIsGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            mIsNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!mIsGPSEnabled && !mIsNetworkEnabled) {
                // no network provider is enabled
                Log.d(TAG, "Network-GPS Disabled");
            } else {
                mCanGetLocation = true;

                // first get location from Network provider
                if (mIsNetworkEnabled) {
                    Log.d(TAG, "mIsNetworkEnabled : true ");
//                    mLocationManager.requestLocationUpdates(
//                            LocationManager.NETWORK_PROVIDER,
//                            MIN_TIME_BW_UPDATES,
//                            MIN_DISTANCE_CHANGE_FOR_UPDATES, (Activity)
//                    );
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        Log.d(TAG, "returning null here");
                        return null;
                    }
                    Log.d(TAG, "getLocation: ok one");
                    mLocationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES,
                            this
                    );
                    Log.d(TAG, "getLocation: Network");

                    if(mLocationManager != null){
                        Log.d(TAG, "(c");
                        mLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if(mLocation != null){
                            Log.d(TAG, "(mLocation one != null : called");
                            mLongitude = mLocation.getLongitude();
                            mLatitude = mLocation.getLatitude();
                        }
                    }
                    // if GPS enabled get lat/long using GPS services
                } else if(mIsGPSEnabled){
                    Log.d(TAG, "mIsGpsEnabled : called");
                        if(mLocation != null){
                            Log.d(TAG, "(mLocation two != null : called");
                            mLocationManager.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER,
                                    MIN_TIME_BW_UPDATES,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATES,
                                    this
                            );
                            Log.d(TAG, "getLocation: GPS enabled");
                            if(mLocationManager != null){
                                mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if(mLocation != null){
                                    mLongitude = mLocation.getLongitude();
                                    mLatitude = mLocation.getLatitude();
                                }
                            }
                        }
                    }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return mLocation;
    }


    public double getLongitude(){
        if(mLocation != null){
            mLongitude = mLocation.getLongitude();
        }
        return mLongitude;
    }

    public double getLatitude(){
        if(mLocation != null){
            mLatitude = mLocation.getLatitude();
        }
        return mLatitude;
    }

    public boolean canGetLocation(){
        return mCanGetLocation;
    }


    public void showSettingsAlert(){


    }



}
