package asabre.com.chase.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import asabre.com.chase.R;

public class HistoryDetailsFragment extends Fragment implements BaseFragment {
    private static final String TAG = HistoryDetailsFragment.class.getSimpleName();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_details, container, false);
        init(view);
        return view;
    }

    private void init(View view){

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
