package asabre.com.chase.view.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import asabre.com.chase.R;
import asabre.com.chase.service.model.About;
import asabre.com.chase.viewmodel.HomeViewModel;

public class ProfileFragment extends Fragment implements BaseFragment {
    private static final String TAG = ProfileFragment.class.getSimpleName();


    private HomeViewModel mViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        init(view);

        setAboutUsViewModel();

        return view;
    }


    private void init(View view){

    }

    private void setAboutUsViewModel(){
        if (getActivity() != null){
            mViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);


        }
    }


    @Override
    public void onStart() {
        Log.d(TAG, "onStart: called here");
        super.onStart();
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
