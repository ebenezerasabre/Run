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
import androidx.lifecycle.ViewModelProviders;
import asabre.com.chase.R;
import asabre.com.chase.service.model.User;
import asabre.com.chase.service.model.UserEntity;
import asabre.com.chase.service.repository.DatabaseClient;
import asabre.com.chase.viewmodel.HomeViewModel;

public class EnterNameFragment extends Fragment implements BaseFragment{
    private static final String TAG = EnterNameFragment.class.getSimpleName();

    private TextView enterNameBack;
    private TextInputEditText enterFirstName;
    private TextInputEditText enterLastName;
    private MaterialButton enterNameDone;
    private HomeViewModel mHomeViewModel;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_name, container, false);
        init(view);
        nameContinueListener();
        nameBackListener();
        return view;
    }

    private void init(View view){
        // initialize viewModel
        mHomeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);

        enterNameBack = view.findViewById(R.id.enterNameBack);
        enterFirstName = view.findViewById(R.id.enterFirstName);
        enterLastName = view.findViewById(R.id.enterLastName);
        enterNameDone = view.findViewById(R.id.enterNameDone);
    }

    private void nameContinueListener(){
        enterNameDone.setOnClickListener(view -> {
            if(fieldsNotEmpty()){
                setValues();
                sortUserType();
            } else {
                Toast.makeText(getContext(), "Field(s) can't be empty", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void sortUserType() {
        if(HomeViewModel.userType.contains("driver")){
            loadCarDetailsFragment();
        } else if (HomeViewModel.userType.contains("user")){
            showProgressDialog();
            setViewModel();
        }
    }

    private void setValues(){
        HomeViewModel.createObject.put("firstName", enterFirstName.getText().toString());
        HomeViewModel.createObject.put("lastName", enterLastName.getText().toString());
        HomeViewModel.createObject.put("userType", HomeViewModel.userType);

        Log.d(TAG, "setValues: createObject: " + HomeViewModel.createObject);
    }

    private void setViewModel(){
        MainActivity.mRegProcess = MainActivity.RegistrationProcess.DONE;
        mHomeViewModel.init();
        // save user data in database
       mHomeViewModel.createUser(HomeViewModel.createObject).observe(this, this::saveUserData);
//               loadMapFragment();
    }


    private void saveUserData(User user){
        Log.d(TAG, "saveUserData: the user: " + user);
        dismissProgressDialog();

        if(!(user.getFirstName().isEmpty())){
            HomeViewModel.userFull = new User(user);

            class SaveUserData extends AsyncTask<Void, Void, Void> {
                @Override
                protected Void doInBackground(Void... voids) {
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
                            .insert(userInfo(user));

                    resetViewModelArguments(userInfo(user));
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);

                    dismissProgressDialog();
                    String success = "User created successfully";
                    resultInfo(success, "Enjoy convenient and cheaper rides on chase");
                    loadMapFragment();
                }
            }

            SaveUserData saveUserData = new SaveUserData();
            saveUserData.execute();
        } else {
            dismissProgressDialog();
            String error = "Error creating user";
            resultInfo(error, "Couldn't create user");
        }
    }

    private void resultInfo(String title, String msg) {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getContext());
        materialAlertDialogBuilder
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });
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

    private void resetViewModelArguments(UserEntity userEntity){
        HomeViewModel.userEntity = userEntity;
    }

    private boolean fieldsNotEmpty() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add(enterFirstName.getText().toString());
        fields.add(enterLastName.getText().toString());

        for(String str : fields){
            if(str.isEmpty()){
                return false;
            }
        }
        return true;
    }

    private void nameBackListener(){
        enterNameBack.setOnClickListener(view -> {
            loadEnterEmailFragment();
        });
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

    private void loadCarDetailsFragment(){
        MainActivity.mRegProcess = MainActivity.RegistrationProcess.CAR_DETAILS;

        EnterCarDetailsFragment carDetailsFragment = new EnterCarDetailsFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.containerHome, carDetailsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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

    private void loadEnterEmailFragment(){
        EnterEmailFragment enterEmailFragment = new EnterEmailFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.containerHome, enterEmailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showProgressDialog(){
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Creating user");
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
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getContext(), "Back from enter name", Toast.LENGTH_SHORT).show();
    }
}
