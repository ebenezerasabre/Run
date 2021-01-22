package asabre.com.chase.view.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import asabre.com.chase.R;
import asabre.com.chase.service.model.History;
import asabre.com.chase.view.adapter.HistoryAdapter;
import asabre.com.chase.view.callback.HistoryCallback;
import asabre.com.chase.viewmodel.HomeViewModel;
import okio.HashingSink;

public class HistoryFragment extends Fragment implements BaseFragment, HistoryCallback {
    private static final String TAG = HistoryFragment.class.getSimpleName();

    private HistoryAdapter mHistoryAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        init(view);
        setViewModel();
        return view;
    }

    private void init(View view){
        mHistoryAdapter = new HistoryAdapter(new ArrayList<>(), this);
        RecyclerView mRecyclerViewHistory = view.findViewById(R.id.holderHistory);
        mRecyclerViewHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewHistory.setAdapter(mHistoryAdapter);
    }

    private void setViewModel(){
        if(getActivity() != null){
            HomeViewModel homeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
            homeViewModel.init();

            if( null != HomeViewModel.userEntity && !HomeViewModel.userEntity.get_id().isEmpty() ){
                homeViewModel.setUserHistory(HomeViewModel.userEntity.get_id());
                homeViewModel.getHistory().observe(this, list -> {
                    Log.d(TAG, "onChanged: observing history");
                    mHistoryAdapter.loadNewData(list);
                });
            }
        }
    }

    private void loadHistoryDetailsFragment(){
     if(getActivity() != null){
         HistoryDetailsFragment historyDetailsFragment = new HistoryDetailsFragment();
         FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
         FragmentTransaction transaction = fragmentManager.beginTransaction();
         transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
         transaction.replace(R.id.containerHome, historyDetailsFragment);
         transaction.addToBackStack(null);
         transaction.commit();
     }
    }

    @Override
    public void historyDetails(History history) {
        HomeViewModel.clickedHistory = history;
        loadHistoryDetailsFragment();
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
    public void onBackPressed() {

    }



}
