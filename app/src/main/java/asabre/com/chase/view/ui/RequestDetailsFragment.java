package asabre.com.chase.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.PrimaryKey;
import asabre.com.chase.R;
import asabre.com.chase.service.model.RideRequest;
import asabre.com.chase.viewmodel.HomeViewModel;

public class RequestDetailsFragment extends Fragment implements BaseFragment {
    private static final String TAG = RequestDetailsFragment.class.getSimpleName();

    private ImageView endRideBack;
    private TextView endRideStartPoint;
    private TextView endRideEndRide;
    private TextView endRidePaymentType;
    private TextView endRideFee;
    private TextView endRideDriverName;
    private TextView endRideDate;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_details, container, false);
        init(view);
        return view;
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
            RequestFragment requestFragment = new RequestFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.replace(R.id.containerHome, requestFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        setData();
        goBack();
    }


    @Override
    public void onStop() {
        super.onStop();
        reset();
    }

    @Override
    public void onBackPressed() {

    }
}
