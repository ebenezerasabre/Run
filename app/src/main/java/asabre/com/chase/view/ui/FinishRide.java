//package asabre.com.chase.view.ui;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.RatingBar;
//import android.widget.TextView;
//
//import com.google.android.material.button.MaterialButton;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import asabre.com.chase.R;
//
//public class FinishRide extends Fragment {
//    private static final String TAG = FinishRide.class.getSimpleName();
//
//
//    private TextView finishRideBack;
//    private TextView finishRideFee;
//    private RatingBar finishRideRatingBar;
//    private TextView finishRideRatingScale;
//    private EditText finishRideFeedback;
//    private MaterialButton finishRideDone;
//
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_end_ride, container, false);
//        init(view);
//
//        setRatingBarCallback();
//        return view;
//    }
//
//    private void init(View view){
//        finishRideBack = view.findViewById(R.id.finishRideBack);
//        finishRideFee = view.findViewById(R.id.finishRideFee);
//        finishRideRatingBar = view.findViewById(R.id.finishRideRatingBar);
//        finishRideRatingScale = view.findViewById(R.id.finishRideRatingScale);
//        finishRideFeedback = view.findViewById(R.id.finishRideFeedback);
//        finishRideDone = view.findViewById(R.id.finishRideDone);
//    }
//
//    private void setRatingBarCallback(){
//        finishRideRatingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
//            finishRideRatingScale.setText(String.valueOf(rating));
//            switch ((int) ratingBar.getRating()){
//                case 1:
//                    finishRideRatingScale.setText(R.string.very_bad);
//                    break;
//                case 2:
//                    finishRideRatingScale.setText(R.string.need_some_improvement);
//                    break;
//                case 3:
//                    finishRideRatingScale.setText(R.string.good);
//                    break;
//                case 4:
//                    finishRideRatingScale.setText(R.string.great);
//                    break;
//                case 5:
//                    finishRideRatingScale.setText(R.string.awesome);
//                    break;
//                default:
//                    finishRideRatingScale.setText(R.string.rate_default);
//                    break;
//            }
//        });
//    }
//
//    private void submit(){
//
//    }
//
//    // send rating request
//
//    @Override
//    public void onStart() {
//        super.onStart();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//    }
//
//
//
//}
