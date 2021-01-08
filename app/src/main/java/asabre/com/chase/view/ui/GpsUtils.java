package asabre.com.chase.view.ui;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import androidx.annotation.NonNull;

public class GpsUtils {
    private static final String TAG = GpsUtils.class.getSimpleName();


    private Context mContext;
    private SettingsClient mSettingsClient;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;

//    public static final int GPS_REQUEST = 101;


   public GpsUtils(Context context){
       mContext = context;
       mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
       mSettingsClient = LocationServices.getSettingsClient(context);
       mLocationRequest = LocationRequest.create();


       mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
       mLocationRequest.setInterval(10 * 1000);
       mLocationRequest.setFastestInterval(2 * 1000);
       LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
               .addLocationRequest(mLocationRequest);
       mLocationSettingsRequest = builder.build();

       /** This is the key ingredient */
       builder.setAlwaysShow(true); // the key ingredient

   }

   //  method to turn on GPS
    public void turnOnGps(onGpsListener onGpsListener){
       if(mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
           if(onGpsListener != null){
               onGpsListener.gpsStatus(true);
           }
       } else {
           mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                   .addOnSuccessListener((Activity) mContext, new OnSuccessListener<LocationSettingsResponse>() {
                       @Override
                       public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                           // GPS iis already enabled, callback GPS status through listener
                           if(onGpsListener != null){
                               onGpsListener.gpsStatus(true);
                           }
                       }
                   })
                   .addOnFailureListener((Activity) mContext, new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           int statusCode = ((ApiException) e).getStatusCode();
                           switch (statusCode){
                               case LocationSettingsStatusCodes
                                       .RESOLUTION_REQUIRED:
                                   try {
                                       // show the dialog by calling startResolutionForResult(),
                                       // and check the result in onActivityResult().
                                       ResolvableApiException rae = (ResolvableApiException) e;
//                                       rae.startResolutionForResult((Activity) mContext, GPS_REQUEST);
                                       rae.startResolutionForResult((Activity) mContext, AppConstants.GPS_REQUEST);
                                   } catch (IntentSender.SendIntentException sie){
                                       Log.d(TAG, "PendingIntent unable to execute request");
                                   }
                                   break;
                               case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                   String errorMessage = "Location settings are inadequate, and cannot be fixed here. Fix in settings";
                                   Log.d(TAG, errorMessage );

                                   Toast.makeText((Activity) mContext, errorMessage, Toast.LENGTH_LONG).show();

                           }
                       }
                   });
       }
    }




    public interface  onGpsListener {
       void gpsStatus(boolean isGPSEnable);
    }

}
