package asabre.com.chase.view.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import asabre.com.chase.R;
import asabre.com.chase.viewmodel.HomeViewModel;

public class EnterEmailFragment extends Fragment {
    private static final String TAG = EnterEmailFragment.class.getSimpleName();

    private TextView enterEmailBack;
    private TextInputEditText enterEmail;
    private TextInputEditText enterPwd;
    private MaterialButton enterEmailContinue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_email, container, false);
        init(view);
        emailContinueListener();
        emailBackListener();
        return view;
    }

    private void init(View view) {
        enterEmailBack = view.findViewById(R.id.enterEmailBack);
        enterEmail = view.findViewById(R.id.enterEmail);
        enterPwd = view.findViewById(R.id.enterPwd);
        enterEmailContinue = view.findViewById(R.id.enterEmailContinue);
    }

    private boolean fieldIsNotEmpty(){
        return !enterEmail.getText().toString().isEmpty() &&
                !enterPwd.getText().toString().isEmpty();
    }

    private void emailContinueListener(){
        enterEmailContinue.setOnClickListener(view -> {
            if(fieldIsNotEmpty()){
                HomeViewModel.createObject.put("email", enterEmail.getText().toString());
                HomeViewModel.createObject.put("password", enterPwd.getText().toString());

                loadEnterNameFragment();
            } else {
                Toast.makeText(getContext(), "Field can't be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void emailBackListener(){
        enterEmailBack.setOnClickListener(view -> loadEnterNumberFragment());
    }



    private void loadEnterNumberFragment(){
        MainActivity.mRegProcess = MainActivity.RegistrationProcess.NUMBER;

        EnterNumberFragment enterNumberFragment = new EnterNumberFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.conHome, enterNumberFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadEnterNameFragment(){
        MainActivity.mRegProcess = MainActivity.RegistrationProcess.NAME;

        EnterNameFragment enterNameFragment = new EnterNameFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.conHome, enterNameFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: email called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: email called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: email called");
    }
}
