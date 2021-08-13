package asabre.com.chase.view.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import asabre.com.chase.R;
import asabre.com.chase.service.model.RideRequest;
import asabre.com.chase.viewmodel.HomeViewModel;

public class EndRideFragment extends Fragment {


    private TextView endRideBack;
    private TextView endRideFee;
    private RatingBar endRideRatingBar;
    private TextView endRideRatingScale;
    private EditText endRideFeedback;
    private MaterialButton endRideDone;

    private ProgressDialog mProgressDialog;
    private HomeViewModel mHomeViewModel;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_end_ride, container, false);
        setHomeViewModel();
        init(view);
        setRatingBarCallback();
        return view;
    }

    private void init(View view){
        endRideBack = view.findViewById(R.id.endRideBack);
        endRideFee = view.findViewById(R.id.endRideFee);
        endRideRatingBar = view.findViewById(R.id.endRideRatingBar);
        endRideRatingScale = view.findViewById(R.id.endRideRatingScale);
        endRideFeedback = view.findViewById(R.id.endRideFeedback);
        endRideDone = view.findViewById(R.id.endRideDone);

        endRideFeedback.requestFocus();
    }

    private void setRatingBarCallback(){
        endRideRatingBar.setOnRatingBarChangeListener((ratingBar, v, b) -> {
            endRideRatingScale.setText(String.valueOf(v));
            switch ((int) ratingBar.getRating()){
                case 1:
                    endRideRatingScale.setText(getString(R.string.very_bad));
                    break;
                case 2:
                    endRideRatingScale.setText(getString(R.string.need_some_improvement));
                    break;
                case 3:
                    endRideRatingScale.setText(getString(R.string.good));
                    break;
                case 4:
                    endRideRatingScale.setText(getString(R.string.great));
                    break;
                case 5:
                    endRideRatingScale.setText(getString(R.string.awesome));
                    break;
                default:
                    endRideRatingScale.setText("");
                    break;
            }
        });
    }


    private void setHomeViewModel(){
        if(getActivity() != null){
            mHomeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
            mHomeViewModel.init();
        }
    }

    private void setRatingViewModel(){
        if(!emptyField()){
            showProgressDialog();
            mHomeViewModel.createReview(reviewObj()).observe(this, review -> {
                hideVirtualKeyboard();
                dismissProgressDialog();
                setState();
                resetViewModel();
                loadMapFragment();


            });
        } else {
            Toast.makeText(getContext(), "Field can't be empty", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean emptyField(){
        return endRideFeedback.getText().toString().isEmpty();
    }

    private void submitRating(){
        endRideDone.setOnClickListener(view -> setRatingViewModel());
    }


    private HashMap<String, String> reviewObj(){
        HashMap<String, String> obj = new HashMap<>();
        obj.put("driverId", HomeViewModel.mRideRequest.getDriverId());
        obj.put("userId", HomeViewModel.mRideRequest.getUserId());
        obj.put("msg", endRideFeedback.getText().toString());
        obj.put("stars", String.valueOf(endRideRatingBar.getRating()));
        return obj;
    }

    private void showProgressDialog(){
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Sending review");
        mProgressDialog.setMessage("Please wait.");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    private void dismissProgressDialog(){
        if(mProgressDialog != null){
            if(mProgressDialog.isShowing()){
                mProgressDialog.dismiss();
            }
        }
    }

    private void hideVirtualKeyboard(){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }

    private void loadMapFragment(){
        if(getActivity() != null){
            MapFragment mapFragment = new MapFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.replace(R.id.conHome, mapFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private void resetViewModel(){
        HomeViewModel.mLineOptions = null;
        HomeViewModel.mPolyline = null;
        HomeViewModel.mRideRequest = new RideRequest();


    }

    private void setState(){
        if(HomeViewModel.userState.contains("user")){
            HomeViewModel.setViewTrack("USER_GOING_WHERE");
        } else {
            HomeViewModel.setViewTrack("DRIVER_GO_ONLINE");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        submitRating();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
