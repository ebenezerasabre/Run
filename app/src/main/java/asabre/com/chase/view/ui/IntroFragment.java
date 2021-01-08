package asabre.com.chase.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import asabre.com.chase.R;
import asabre.com.chase.viewmodel.HomeViewModel;

public class IntroFragment extends Fragment {
    private static final String TAG = IntroFragment.class.getSimpleName();

    private MaterialButton continueAsDriver;
    private MaterialButton continueAsUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro, container, false);
        init(view);
        continueAsDriverListener();
        continueAsUserListener();
        return view;
    }


    private void init(View view){
        continueAsDriver = view.findViewById(R.id.continueAsDriver);
        continueAsUser = view.findViewById(R.id.continueAsUser);
    }

    private void continueAsDriverListener(){
        continueAsDriver.setOnClickListener(view -> {
            HomeViewModel.createObject.put("userType", "driver");
            HomeViewModel.userType = "driver";
            MainActivity.mRegProcess = MainActivity.RegistrationProcess.NUMBER;
            loadEnterNumberFragment();
        });
    }

    private void continueAsUserListener(){
        continueAsUser.setOnClickListener(view -> {
            HomeViewModel.createObject.put("userType", "user");
            HomeViewModel.userType = "user";
            MainActivity.mRegProcess = MainActivity.RegistrationProcess.NUMBER;
            loadEnterNumberFragment();
        });
    }

    private void loadEnterNumberFragment(){
        EnterNumberFragment enterNumberFragment = new EnterNumberFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.containerHome, enterNumberFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onStop() {
        super.onStop();
    }


}
