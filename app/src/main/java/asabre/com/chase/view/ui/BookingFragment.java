package asabre.com.chase.view.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import asabre.com.chase.R;

public class BookingFragment extends Fragment implements BaseFragment {
    private static final String TAG = BookingFragment.class.getSimpleName();

    private TextView showDate;
    private TextView showTime;
    private TextInputEditText enterPickUp;
    private TextInputEditText enterDropOff;
//    private TextView update;
    private TextView chooseRide;
//    private MaterialButton setDate;
    private MaterialButton bookRideButton;
    private TextView bookingRideBack;
    private RecyclerView holderBookedRides;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        init(view);
        listeners();
        return view;
    }

    @Override
    public void onBackPressed() {
        setVisibility();
    }

    @Override
    public void onStart() {
        super.onStart();


    }


    private void init(View view){
        showDate = view.findViewById(R.id.showDate);
        showTime = view.findViewById(R.id.showTime);
        enterPickUp = view.findViewById(R.id.enterPickUp);
        enterDropOff = view.findViewById(R.id.enterDropOff);
//        update = view.findViewById(R.id.updateBooking);
        chooseRide = view.findViewById(R.id.chooseRide);
//        setDate = view.findViewById(R.id.setDate);
        bookRideButton = view.findViewById(R.id.bookRideButton);
        bookingRideBack = view.findViewById(R.id.bookingRideBack);
        holderBookedRides = view.findViewById(R.id.holderBookedRides);

    }


    private void listeners(){
        bookingRideBack.setOnClickListener(view -> {
            setVisibility();
        });
        datePicker();
        timePicker();
    }



    private void datePicker(){

        showDate.setOnClickListener(view -> {

            // TODO Auto-generated method stub
            //To show current date in the datepicker
            Calendar mcurrentDate = Calendar.getInstance();
            int mYear = mcurrentDate.get(Calendar.YEAR);
            int mMonth = mcurrentDate.get(Calendar.MONTH);
            int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog mDatePicker;
            mDatePicker = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                    /*      Your code   to get date and time    */
                    selectedmonth = selectedmonth + 1;
                    showDate.setText(String.format(Locale.US, "%S / %S / %S", selectedday, selectedmonth, selectedyear));
                }
            }, mYear, mMonth, mDay);
            mDatePicker.setTitle("Select Date");
            mDatePicker.show();

        });

    }


    private void timePicker(){
        showTime.setOnClickListener(view -> {

            // TODO Auto-generated method stub
            Calendar mCurrentTime = Calendar.getInstance();
            int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mCurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    showTime.setText(String.format(Locale.US, "%S:%S", selectedHour, selectedMinute));
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        });

    }


    private void setVisibility(){
        if(getActivity() != null){
            Log.d(TAG, "setVisibility: Going back to About");
            MainActivity.mAboutTrack = MainActivity.AboutTrack.ABOUT;
            getActivity().findViewById(R.id.conAbout).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.conAboutDetails).setVisibility(View.GONE);
            removeThisFragment();
        }
    }

    private void removeThisFragment(){
        if(getActivity() != null){
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.conAboutDetails);
            if(fragment != null){
                getActivity().getSupportFragmentManager().beginTransaction()
                        .remove(fragment)
                        .commit();
                Log.d(TAG, "removeThisFragment: called");
            }
        }
    }

}
