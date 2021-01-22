package asabre.com.chase.view.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import asabre.com.chase.R;

public class OpenFragment extends Fragment {
    private static final String TAG = OpenFragment.class.getSimpleName();

//    private LocationFinder mLocationFinder;

    private MaterialButton turnOnGps;

    private boolean isGps;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open, container, false);

        init(view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void init(View view){

        turnOnGps = view.findViewById(R.id.turnOnGPS);
    }

    private void locationSettings(){
        if(getContext() != null){
            // the results of tuning on gps will be shown at onActivityResult()
            new GpsUtils(getContext()).turnOnGps(new GpsUtils.onGpsListener() {
                @Override
                public void gpsStatus(boolean isGPSEnable) {
                    // callback when gps is turned on already
                    isGps = isGPSEnable;
                    Log.d(TAG, "gpsStatus:  is " + isGPSEnable);

                }
            });
        }
    }

   private void setTurnOnGps(){
        turnOnGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationSettings();
            }
        });
   }



    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ***** called");
        setTurnOnGps();
    }


    @Override
    public void onStop() {
        super.onStop();
        turnOnGps.setOnClickListener(null);
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: called");
    }


}


// what you don't know ,what you won't know, what you can't know,



