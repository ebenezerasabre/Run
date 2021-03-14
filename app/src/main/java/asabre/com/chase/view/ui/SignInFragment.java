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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import asabre.com.chase.R;
import asabre.com.chase.service.model.Driver;
import asabre.com.chase.service.model.User;
import asabre.com.chase.service.model.UserEntity;
import asabre.com.chase.service.repository.DatabaseClient;
import asabre.com.chase.viewmodel.HomeViewModel;

public class SignInFragment extends Fragment {
    private static final String TAG = SignInFragment.class.getSimpleName();


    private ProgressDialog mProgressDialog;
    private HomeViewModel mHomeViewModel;
    private TextView signInBack;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        init(view);
        setViewModel();
        return  view;
    }

    private void init(View view) {
        mHomeViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(HomeViewModel.class);
        signInBack = view.findViewById(R.id.signInBack);
    }


    private void setViewModel(){
        mHomeViewModel.init();
        if(HomeViewModel.userState.contains("sign")){
            mHomeViewModel.resetRequestHistory();

            if(HomeViewModel.userType.contains("user")){
                setUserViewModel();
            }
            if(HomeViewModel.userType.contains("driver")){
                setDriverViewModel();
            }
            checkIfLoading();
        }
    }

    private void checkIfLoading() {
        HomeViewModel.getIsLoading().observe(this, aBoolean -> {
            if(aBoolean){
                Log.d(TAG, "setViewModel: something loading");
                showProgressDialog();
            }
            else {
                Log.d(TAG, "setViewModel: something not loading");
                dismissProgressDialog();
            }
        });
    }

    private void setUserViewModel() {
        mHomeViewModel.signInUser(HomeViewModel.createObject.get("phoneNumber"))
                .observe(this, user -> {
                    Log.d(TAG, "onChanged: the user is " + user);
                    HomeViewModel.userFull = new User(user);
                    saveUserData(userInfo(user));
                });
    }

    private void setDriverViewModel() {
        mHomeViewModel.signInDriver(HomeViewModel.createObject.get("phoneNumber"))
                .observe(this, driver -> {
                    Log.d(TAG, "setDriverViewModel: the driver is " + driver);
                    HomeViewModel.driverFull = driver;
                    saveUserData(driverInfo(driver));
                });
    }



    private void saveUserData(UserEntity userEntity) {
        dismissProgressDialog();

        if(!( userEntity.getFirstName().isEmpty() )){


            class SaveUserData extends AsyncTask<Void, Void, Void> {
                @Override
                protected Void doInBackground(Void... voids) {
                    Log.d(TAG, "doInBackground: called");
                    // clear database
                    DatabaseClient
                            .getInstance(getContext())
                            .getAppDatabase()
                            .mUserDao()
                            .deleteAll();

                    // save user info
                    DatabaseClient
                            .getInstance(getContext())
                            .getAppDatabase()
                            .mUserDao()
                            .insert(userEntity);

                    resetViewModelArguments(userEntity);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Log.d(TAG, "onPostExecute: called");

                    dismissProgressDialog();
                    String success = "User/driver signed in successfully";
                    resultInfo(success, "Enjoy convenient and cheaper rides on chase");
                    loadMapFragment();
                    loadAboutFragment();
                    loadProfileFragment();
                    loadRequestHistoryFragment();

                }
            }

            SaveUserData saveUserData = new SaveUserData();
            saveUserData.execute();
        } else {
            dismissProgressDialog();
            String error = "Error signing in user/driver";
            resultInfo(error, "Couldn't sign in");
        }
    }

    private void resultInfo(String title, String msg) {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getContext());
        materialAlertDialogBuilder
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", (dialogInterface, i) ->  dialogInterface.dismiss() );
        materialAlertDialogBuilder.create();
        materialAlertDialogBuilder.show();
    }

    private UserEntity userInfo(User user){
        return new UserEntity(
                user.get_id(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getUserType()
        );
    }
    private UserEntity driverInfo(Driver driver){
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
    }


    private boolean gpsIsOn(){
        LocationManager locationManager = (LocationManager) getActivity()
                .getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void loadMapFragment(){
        if(getActivity() != null){
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
    }

    private void loadAboutFragment(){
        if (getActivity() != null){
            AboutFragment aboutFragment = new AboutFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.replace(R.id.containerAbout, aboutFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private void loadRequestHistoryFragment(){
        if (getActivity() != null){
            RequestFragment requestFragment = new RequestFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.replace(R.id.containerRequests, requestFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
    private void loadProfileFragment(){
        if (getActivity() != null){
            ProfileFragment profileFragment = new ProfileFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.replace(R.id.containerProfile, profileFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private void loadOpenFragment(){
        if(getActivity() != null){
            OpenFragment openFragment = new OpenFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.replace(R.id.containerHome, openFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }



    private void showProgressDialog(){
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Signing in");
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

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }


}
