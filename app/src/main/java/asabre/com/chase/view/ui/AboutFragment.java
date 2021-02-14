package asabre.com.chase.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import asabre.com.chase.R;

public class AboutFragment extends Fragment implements BaseFragment {
    private static final String TAG = AboutFragment.class.getSimpleName();

    private ImageView aboutBack;
    private MaterialButton rateApp;
    private MaterialButton connectFacebook;
    private MaterialButton termsAndConditions;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        init(view);
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

    @Override
    public void onBackPressed() {

    }

    private void listeners(){
        aboutBack.setOnClickListener(view -> {

        });
        rateApp.setOnClickListener(view -> {});
        connectFacebook.setOnClickListener(view -> {});
        termsAndConditions.setOnClickListener(view -> {});
    }

    private void resetButtons(){
        aboutBack.setOnClickListener(null);
        rateApp.setOnClickListener(null);
        connectFacebook.setOnClickListener(null);
        termsAndConditions.setOnClickListener(null);
    }

    private void init(View view){
        aboutBack = view.findViewById(R.id.aboutBack);
        rateApp = view.findViewById(R.id.rateApp);
        connectFacebook = view.findViewById(R.id.connectFacebook);
        termsAndConditions = view.findViewById(R.id.termsAndConditions);
    }
}
