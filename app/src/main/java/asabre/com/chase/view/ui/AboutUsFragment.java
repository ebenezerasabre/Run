package asabre.com.chase.view.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import asabre.com.chase.R;
import asabre.com.chase.service.model.About;
import asabre.com.chase.view.adapter.AboutUsAdapter;
import asabre.com.chase.viewmodel.HomeViewModel;

public class AboutUsFragment  extends Fragment implements BaseFragment {
    private static final String TAG = AboutUsFragment.class.getSimpleName();

    private AboutUsAdapter mAboutUsAdapter;
    private HomeViewModel mHomeViewModel;
    private ImageView aboutUsBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        init(view);
        setViewModel();
        listeners();
        return view;
    }

    private void init(View view){
        aboutUsBack = view.findViewById(R.id.aboutUsBack);
        mAboutUsAdapter = new AboutUsAdapter(new ArrayList<>());
        RecyclerView recyclerView = view.findViewById(R.id.holder_about_us);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAboutUsAdapter);
    }

    private void setViewModel(){
        if(getActivity() != null){
            mHomeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
            mHomeViewModel.init();
            mHomeViewModel.setAbout();
            mHomeViewModel.getAbouts().observe(this, list -> mAboutUsAdapter.loadNewData(list));
        }
    }

    private void goBack(){
        if(getActivity() != null){
//            AboutFragment aboutUsFragment = new AboutFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.popBackStack("aboutUsFragment", 0);

            Log.d(TAG, "goBack: hello us");

            Toast.makeText(getContext(), "us", Toast.LENGTH_SHORT).show();


//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
//            transaction.replace(R.id.containerAbout, aboutUsFragment);
//            transaction.addToBackStack(null);
//            transaction.commit();
        }
    }

    private void listeners(){
        aboutUsBack.setOnClickListener(view -> {
            goBack();
        });
    }

    @Override
    public void onBackPressed() {
        goBack();
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
