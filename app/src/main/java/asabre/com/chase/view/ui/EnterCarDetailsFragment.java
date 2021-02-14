package asabre.com.chase.view.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import asabre.com.chase.R;
import asabre.com.chase.service.model.Driver;
import asabre.com.chase.service.model.User;
import asabre.com.chase.service.model.UserEntity;
import asabre.com.chase.service.repository.DatabaseClient;
import asabre.com.chase.viewmodel.HomeViewModel;

public class EnterCarDetailsFragment extends Fragment {
    private static final String TAG = EnterCarDetailsFragment.class.getSimpleName();

    private TextView enterCarDetailsBack;
    private TextInputEditText enterCarDescription;
    private TextInputEditText enterCarNumber;
    private MaterialButton enterCarContinue;
    private HomeViewModel mHomeViewModel;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_details, container, false);
        init(view);
        continueListener();
        carDetailsBackListener();
        return view;
    }


    private void init(View view){
        mHomeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);

        enterCarDetailsBack = view.findViewById(R.id.enterCarDetailsBack);
        enterCarDescription = view.findViewById(R.id.enterCarDescription);
        enterCarNumber = view.findViewById(R.id.enterCarNumber);
        enterCarContinue = view.findViewById(R.id.enterCarContinue);
    }

    private void continueListener(){
        enterCarContinue.setOnClickListener(view -> {
            if(fieldsNotEmpty()){
                showProgressDialog();
                setValues();
                setViewModel();
            } else {
                Toast.makeText(getContext(), "Field(s) can't be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void carDetailsBackListener(){
        enterCarDetailsBack.setOnClickListener(view -> {
                    MainActivity.mRegProcess = MainActivity.RegistrationProcess.NAME;
                    loadEnterNameFragment();
                });
    }

    private boolean fieldsNotEmpty(){
        ArrayList<String> fields = new ArrayList<>();
        fields.add(enterCarDescription.getText().toString());
        fields.add(enterCarNumber.getText().toString());

        for(String str : fields){
            if(str.isEmpty()){
                return false;
            }
        }
        return true;
    }

    private void setValues(){
        HomeViewModel.createObject.put("carDescription", enterCarDescription.getText().toString());
        HomeViewModel.createObject.put("carNumber", enterCarNumber.getText().toString());
    }

    private void setViewModel(){
        MainActivity.mRegProcess = MainActivity.RegistrationProcess.DONE;
        mHomeViewModel.init();
        mHomeViewModel.createDriver(HomeViewModel.createObject)
                .observe(this, this::saveDriverData);
    }



    private void saveDriverData(Driver driver) {
        Log.d(TAG, "saveDriverData the driver: " + driver);
        dismissProgressDialog();

        if(!(driver.getFirstName().isEmpty())) {
            HomeViewModel.driverFull = new Driver(driver);

            class SaveDriverData extends AsyncTask<Void, Void, Void>{
                @Override
                protected Void doInBackground(Void... voids) {
                    // clear database
                    DatabaseClient.getInstance(getContext()).getAppDatabase()
                            .mUserDao()
                            .deleteAll();

                    // save driver data
                    DatabaseClient
                            .getInstance(getContext())
                            .getAppDatabase()
                            .mUserDao()
                            .insert(driverInfo(driver));

                    resetViewModelArguments(driverInfo(driver));
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    dismissProgressDialog();
                    String success = "Driver created successfully";
                    resultInfo(success, "Welcome to chase ");
                    loadMapFragment();
                }
            }
            SaveDriverData saveDriverData = new SaveDriverData();
            saveDriverData.execute();
        } else {
            dismissProgressDialog();
            String error = "Error creating driver";
            resultInfo(error, "Couldn't create driver");
        }
    }

    private void resultInfo(String title, String msg){
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getContext());
        materialAlertDialogBuilder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss());
        materialAlertDialogBuilder.create();
        materialAlertDialogBuilder.show();
    }

    private UserEntity driverInfo(Driver driver){
        Log.d(TAG, "driverInfo: id " + driver.get_id());
        Log.d(TAG, "driverInfo: " + driver.getFirstName());
        Log.d(TAG, "driverInfo: " + driver.getLastName());
        Log.d(TAG, "driverInfo: " + driver.getPhoneNumber());
        Log.d(TAG, "driverInfo: " + driver.getEmail());
        Log.d(TAG, "driverInfo: " + driver.getUserType());
        return new UserEntity(
                driver.get_id(),
                driver.getFirstName(),
                driver.getLastName(),
                driver.getPhoneNumber(),
                driver.getEmail(),
                driver.getUserType()
        );
    }

    private void resetViewModelArguments(UserEntity userEntity){
        HomeViewModel.userEntity = userEntity;

        Log.d(TAG, "id: " + HomeViewModel.userEntity.get_id());
        Log.d(TAG, "firstName : " + HomeViewModel.userEntity.getFirstName());
        Log.d(TAG, "lastName : " + HomeViewModel.userEntity.getLastName());
        Log.d(TAG, "email : " + HomeViewModel.userEntity.getEmail());
    }

    private void showProgressDialog(){
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Creating driver");
        mProgressDialog.setMessage("Please wait.");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

    }

    private void dismissProgressDialog(){
        if(mProgressDialog != null){
            if(mProgressDialog.isShowing()){
                mProgressDialog.dismiss();
            }
        }
    }

    private void loadEnterNameFragment(){
        EnterNameFragment enterNameFragment = new EnterNameFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.containerHome, enterNameFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadMapFragment(){
        if(gpsIsOn()){
            MapFragment mapFragment = new MapFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.replace(R.id.containerHome, mapFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            loadOpenFragment();
        }
    }

    private boolean gpsIsOn(){
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void loadOpenFragment(){
        OpenFragment openFragment = new OpenFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.containerHome, openFragment);
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
