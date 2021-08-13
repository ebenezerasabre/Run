package asabre.com.chase.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private TextView signInAsUser;
    private TextView signInAsDriver;


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
        signInAsUser = view.findViewById(R.id.signInAsUser);
        signInAsDriver = view.findViewById(R.id.signInAsDriver);
    }

    private void continueAsDriverListener() {
        continueAsDriver.setOnClickListener(view -> {
            HomeViewModel.createObject.put("userType", "driver");
            HomeViewModel.userType = "driver";
            HomeViewModel.userState = "create";
            MainActivity.mRegProcess = MainActivity.RegistrationProcess.NUMBER;
            HomeViewModel.setViewTrack("DRIVER_GO_ONLINE");
            loadEnterNumberFragment();
        });
    }

    private void continueAsUserListener() {
        continueAsUser.setOnClickListener(view -> {
            HomeViewModel.createObject.put("userType", "user");
            HomeViewModel.userType = "user";
            HomeViewModel.userState = "create";
            MainActivity.mRegProcess = MainActivity.RegistrationProcess.NUMBER;
            HomeViewModel.setViewTrack("USER_GOING_WHERE");
            loadEnterNumberFragment();
        });
    }

    private void loadEnterNumberFragment() {
        if(getActivity() != null){
            EnterNumberFragment enterNumberFragment = new EnterNumberFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.replace(R.id.conHome, enterNumberFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }


    private void signIn() {
        signInAsUser.setOnClickListener(view -> {
            HomeViewModel.userType = "user";
            HomeViewModel.userState = "sign";
            loadEnterNumberFragment();
        });
        signInAsDriver.setOnClickListener(view -> {
            HomeViewModel.userType = "driver";
            HomeViewModel.userState = "sign";
            loadEnterNumberFragment();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        signIn();

    }


    @Override
    public void onStop() {
        super.onStop();
    }


}
