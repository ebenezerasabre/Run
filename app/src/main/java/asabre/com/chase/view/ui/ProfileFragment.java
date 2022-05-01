package asabre.com.chase.view.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import asabre.com.chase.R;
import asabre.com.chase.service.model.User;
import asabre.com.chase.viewmodel.HomeViewModel;

public class ProfileFragment extends Fragment implements BaseFragment {
    private static final String TAG = ProfileFragment.class.getSimpleName();

    private TextView profileNumber;
    private TextView profileFirstName;
    private TextView profileLastName;
    private TextView profileEmail;
    private LinearLayout profileEnterHomeLocation;
    private LinearLayout profileEnterWorkLocation;
    private TextView homeLocation;
    private TextView workLocation;

    private HomeViewModel mHomeViewModel;
    private TextView clickToEdit;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        init(view);
        editProfile();
        return view;
    }

    private void init(View view){

        profileNumber = view.findViewById(R.id.profileNumber);
        profileFirstName = view.findViewById(R.id.profileFirstName);
        profileLastName = view.findViewById(R.id.profileLastName);
        profileEmail = view.findViewById(R.id.profileEmail);
        profileEnterHomeLocation = view.findViewById(R.id.profileEnterHomeLocation);
        profileEnterWorkLocation = view.findViewById(R.id.profileEnterWorkLocation);
        homeLocation = view.findViewById(R.id.homeLocation);
        workLocation = view.findViewById(R.id.workLocation);
        clickToEdit = view.findViewById(R.id.clickToEdit);

        initViewModel();
    }

    private void initViewModel(){
        mHomeViewModel = ViewModelProviders.of(requireActivity()).get(HomeViewModel.class);
        mHomeViewModel.init();
    }

    private void setData(){
        profileNumber.setText(HomeViewModel.userEntity.getPhoneNumber());
        profileFirstName.setText(HomeViewModel.userEntity.getFirstName());
        profileLastName.setText(HomeViewModel.userEntity.getLastName());
        profileEmail.setText(HomeViewModel.userEntity.getEmail());

        setUser();
        setDriver();
    }

    private void editProfile(){
        clickToEdit.setOnClickListener(view ->{
            Log.d(TAG, "editProfile: clicked");
            setVisibility();
        });
    }


    private void editProfileFragment(){
        if(getActivity() != null){
            EditProfileFragment editProfileFragment = new EditProfileFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.replace(R.id.containerProfile, editProfileFragment);
            transaction.addToBackStack("editProfileFragment");
            transaction.commit();
        }
    }


    private void setVisibility(){
        if(getActivity() != null){
            Log.d(TAG, "setVisibility: ");
            MainActivity.mProfileTrack = MainActivity.ProfileTrack.PROFILE_DETAILS;
            getActivity().findViewById(R.id.containerProfile).setVisibility(View.GONE);
            getActivity().findViewById(R.id.containerProfileDetails).setVisibility(View.VISIBLE);

            editProfileFragment();
        }
    }

    private void setUser(){
//        if(HomeViewModel.userType.contains("user") && HomeViewModel.userFull.getHomeLocation() != null){
//            homeLocation.setText(HomeViewModel.userFull.getHomeLocation().split(",")[0]);
//            workLocation.setText(HomeViewModel.userFull.getWorkLocation().split(",")[0]);
//        }
    }

    private void setDriver(){
//        if(HomeViewModel.userType.contains("driver")){
//
//        }
    }


    private void placeIntent(int code){
//        Log.d(TAG, "placeIntent: profile fragment called");
//        if(Places.isInitialized()){
//            Places.initialize(getContext(), AppConstants.API_KEY);
//            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
//
//            // start the autocomplete intent
//            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(getContext());
//            startActivityForResult(intent, code);
//        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == AppConstants.AUTOCOMPLETE_REQUEST_CODE_P){
//            if(resultCode == Activity.RESULT_OK){
//                Log.d(TAG, "onActivityResult: from Profile frag ");
//                Place place = Autocomplete.getPlaceFromIntent(data);
//                Log.d(TAG, "onActivityResult: the lng " + place.getLatLng().longitude);
//                Log.d(TAG, "onActivityResult: the lat " + place.getLatLng().latitude);
//            }
//        }
//
//        if(resultCode == Activity.RESULT_OK){
//            assert data != null;
//            Place place = Autocomplete.getPlaceFromIntent(data);
//            String latLng = place.getName() + "," + Objects.requireNonNull(place.getLatLng()).latitude + "," + place.getLatLng().longitude;
//            if(requestCode == AppConstants.PROFILE_UPDATE_HOME){
//
//                updateUserHomeWork(latLng, "homeLocation");
//            } else if(requestCode == AppConstants.PROFILE_UPDATE_WORK){
//                updateUserHomeWork(latLng, "workLocation");
//            }
//        }


    }

    private void listeners(){
//        profileEnterWorkLocation.setOnClickListener(view -> {
//            placeIntent(AppConstants.PROFILE_UPDATE_WORK);
//        });
//
//        profileEnterHomeLocation.setOnClickListener(view -> {
//            placeIntent(AppConstants.PROFILE_UPDATE_HOME);
//        });
    }

    private void updateUserHomeWork(String latLng, String update){
//        HashMap<String, String> obj = new HashMap<>();
//        obj.put(update, latLng);
//
//        mHomeViewModel.updateUser(HomeViewModel.userEntity.get_id(), obj).observe(this, new Observer<User>() {
//            @Override
//            public void onChanged(User user) {
//                HomeViewModel.userFull = user;
//                Log.d(TAG, "onChanged: updated already");
//            }
//        });

    }


    @Override
    public void onStart() {
        Log.d(TAG, "onStart: called here");
        super.onStart();
        setData();
//        listeners();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: Profile back");
    }


}
