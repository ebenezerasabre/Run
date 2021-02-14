package asabre.com.chase.view.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import asabre.com.chase.R;
import asabre.com.chase.service.model.RideRequest;
import asabre.com.chase.view.adapter.RequestAdapter;
import asabre.com.chase.view.callback.RequestCallback;
import asabre.com.chase.viewmodel.HomeViewModel;

public class RequestFragment extends Fragment implements BaseFragment, RequestCallback {
    private static final String TAG = RequestFragment.class.getSimpleName();

    private RequestAdapter mRequestAdapter;
    private HomeViewModel mHomeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        init(view);
        setViewModel();
        return view;
    }

    private void init(View view){
        mRequestAdapter = new RequestAdapter(new ArrayList<>(), this);
        RecyclerView recyclerView = view.findViewById(R.id.holderHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mRequestAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void requestCallback(RideRequest rideRequest){
        Toast.makeText(getContext(), "Details", Toast.LENGTH_SHORT).show();

        HomeViewModel.mRideRequestDetails = rideRequest;
        loadRequestDetailsFragment();
    }

    @Override
    public void onBackPressed() {

    }




    private void setViewModel(){
        if(getActivity() != null) {
            mHomeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
            mHomeViewModel.init();
            Log.d(TAG, "setViewModel: inside one");
            if(HomeViewModel.userEntity != null) {
                if(HomeViewModel.userType.contains("user")){
                    setUserRequestModel();
                } else if(HomeViewModel.userType.contains("driver")){
                    setDriverRequestModel();
                }
            }
        }
    }

    private void setUserRequestModel() {
        mHomeViewModel.userRequestHistory(HomeViewModel.userEntity.get_id()).observe(this, list -> {
            mRequestAdapter.loadNewData(list);
        });
    }

    private void setDriverRequestModel() {
        mHomeViewModel.driverRequestHistory(HomeViewModel.userEntity.get_id()).observe(this, list -> {
            mRequestAdapter.loadNewData(list);
        });
    }


    private void loadRequestDetailsFragment(){
        if(getActivity() != null){
            Log.d(TAG, "loadRequestDetailsFragment: called");
            RequestDetailsFragment requestDetailsFragment = new RequestDetailsFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.replace(R.id.containerRequests, requestDetailsFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }





}

