package asabre.com.chase.view.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import asabre.com.chase.R;
import asabre.com.chase.viewmodel.HomeViewModel;

public class AboutFragment extends Fragment {
    private static final String TAG = AboutFragment.class.getSimpleName();

    private ImageView aboutBack;
    private MaterialButton rateApp;
    private MaterialButton termsAndConditions;
    private MaterialButton aboutUs;
    private MaterialButton bookR;
    private HomeViewModel mHomeViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        init(view);
        initViewModel();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        listeners();
    }



    @Override
    public void onStop() {
        super.onStop();
        resetButtons();
    }



    private void listeners(){

        bookR.setOnClickListener(view -> {
            setVisibility();
            loadBookingFragment();
        });
        aboutBack.setOnClickListener(view -> {
            goBack();
        });
        aboutUs.setOnClickListener(view -> {
            setVisibility();
            loadAboutUsFragment();
        });
        rateApp.setOnClickListener(view -> {

        });

        termsAndConditions.setOnClickListener(view -> {

        });
    }

    private void resetButtons(){
        aboutBack.setOnClickListener(null);
        rateApp.setOnClickListener(null);
        termsAndConditions.setOnClickListener(null);
    }

    private void init(View view){
        aboutBack = view.findViewById(R.id.aboutBack);
        rateApp = view.findViewById(R.id.rateApp);
        termsAndConditions = view.findViewById(R.id.termsAndConditions);
        aboutUs = view.findViewById(R.id.aboutUs);
        bookR = view.findViewById(R.id.bookR);
    }

    private void initViewModel() {
        if (getActivity() != null) {
            mHomeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
            mHomeViewModel.init();
        }
    }


    private void setVisibility(){
        if(getActivity() != null){
            Log.d(TAG, "setVisibility: Going back to About");
            MainActivity.mAboutTrack = MainActivity.AboutTrack.ABOUT_DETAILS;

            getActivity().findViewById(R.id.conAbout).setVisibility(View.GONE);
            getActivity().findViewById(R.id.conAboutDetails).setVisibility(View.VISIBLE);


        }
    }


    private void loadAboutUsFragment(){
        if(getActivity() != null){
            AboutUsFragment aboutUsFragment = new AboutUsFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.replace(R.id.conAboutDetails, aboutUsFragment);
            transaction.addToBackStack("aboutUsFragment");
            transaction.commit();
        }
    }
    private void loadBookingFragment(){
        if(getActivity() != null){
            BookingFragment bookingFragment = new BookingFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.replace(R.id.conAboutDetails, bookingFragment);
            transaction.addToBackStack("bookingFragment");
            transaction.commit();
        }
    }


    private void goBack(){
        if(getActivity() != null){
            MainActivity.mTrackMain = MainActivity.TrackMain.HOME;
            getActivity().findViewById(R.id.conHome).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.conAbout).setVisibility(View.GONE);

        }
    }





}
