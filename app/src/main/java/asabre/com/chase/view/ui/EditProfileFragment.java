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
import asabre.com.chase.view.callback.TopGoBack;
import asabre.com.chase.viewmodel.HomeViewModel;

public class EditProfileFragment extends Fragment{
    private static final String TAG = EditProfileFragment.class.getSimpleName();

    private HomeViewModel mHomeViewModel;
    private TextInputEditText editFirstName;
    private TextInputEditText editLastName;
    private TextInputEditText editPhoneNumber;
    private MaterialButton saveProfile;
    private ProgressDialog mProgressDialog;
    private TextView editProfileBack;

    String fName, lName, pNum = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        init(view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setInitials();
        saveProfileListener();
        editProfileBackListener();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    private void init(View view){
        editFirstName = view.findViewById(R.id.editFirstname);
        editLastName = view.findViewById(R.id.editLastname);
        editPhoneNumber = view.findViewById(R.id.editPhoneNumber);
        saveProfile = view.findViewById(R.id.saveProfile);
        editProfileBack = view.findViewById(R.id.editProfileBack);
        initViewModel();
    }


    private void initViewModel(){
        mHomeViewModel = ViewModelProviders.of(requireActivity()).get(HomeViewModel.class);
        mHomeViewModel.init();
    }



    private void setInitials(){
        editPhoneNumber.setText(HomeViewModel.userEntity.getPhoneNumber());
        editFirstName.setText(HomeViewModel.userEntity.getFirstName());
        editLastName.setText(HomeViewModel.userEntity.getLastName());
    }

    private void saveProfileListener(){
        saveProfile.setOnClickListener(view -> {
            if(fieldsNotEmpty()){
                editUserOrDriver();
            } else {
                Toast.makeText(getContext(), "Field(s) can't be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editUserOrDriver() {
        showProgressDialog();
        prepareJson();

        if(HomeViewModel.userType.contains("user")){
            // update user
            mHomeViewModel.updateUser(HomeViewModel.userEntity.get_id(), HomeViewModel.createObject)
                    .observe(getViewLifecycleOwner(), this::updateUserInfo);
        } else if(HomeViewModel.userType.contains("driver")){
            // update driver
            mHomeViewModel.updateDriver(HomeViewModel.userEntity.get_id(), HomeViewModel.createObject)
                    .observe(getViewLifecycleOwner(), this::updatedDriverInfo);
        }
        dismissProgressDialog();
    }


    private void prepareJson(){
        HomeViewModel.createObject.put("firstName", fName);
        HomeViewModel.createObject.put("lastName", lName);
        HomeViewModel.createObject.put("phoneNumber", pNum);
    }


    private void updateUserInfo(User user){
        HomeViewModel.userEntity = new UserEntity(
                user.get_id(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getUserType()
        );
        saveData(HomeViewModel.userEntity);
    }

    private void updatedDriverInfo(Driver driver){
        HomeViewModel.userEntity = new UserEntity(
                driver.get_id(),
                driver.getFirstName(),
                driver.getLastName(),
                driver.getPhoneNumber(),
                driver.getEmail(),
                driver.getUserType()
        );
        saveData(HomeViewModel.userEntity);
    }

    private void saveData(UserEntity userEntity){
        Log.d(TAG, "saveData: the user/driver " + userEntity.getUserType());
        dismissProgressDialog();

        if(!HomeViewModel.userEntity.get_id().isEmpty()){
            class SaveUserData extends AsyncTask<Void, Void, Void> {
                @Override
                protected Void doInBackground(Void... voids) {
                    // clear database
                    DatabaseClient
                            .getInstance(getContext())
                            .getAppDatabase()
                            .mUserDao()
                            .deleteAll();

                    // save user/driver info
                    DatabaseClient
                            .getInstance(getContext())
                            .getAppDatabase()
                            .mUserDao()
                            .insert(HomeViewModel.userEntity);

//                    resetViewModelArguments(HomeViewModel.userEntity);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);

                    dismissProgressDialog();
                    String success = "Updated successfully";
                    resultInfo(success, "Enjoy convenient and cheaper rides on chase");
//                    loadMapFragment();
                }
            }

            SaveUserData saveUserData = new SaveUserData();
            saveUserData.execute();
        } else {
            dismissProgressDialog();
            String error = "Error updating";
            resultInfo(error, "Couldn't update");
        }
    }


    private void resultInfo(String title, String msg) {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(requireContext());
        materialAlertDialogBuilder
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });
        materialAlertDialogBuilder.create();
        materialAlertDialogBuilder.show();
    }


    private void showProgressDialog(){
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Updating user");
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


    private boolean fieldsNotEmpty() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add(Objects.requireNonNull(editFirstName.getText()).toString());
        fields.add(Objects.requireNonNull(editLastName.getText()).toString());
        fields.add(Objects.requireNonNull(editPhoneNumber.getText()).toString());

        for(String str : fields){
            if(str.isEmpty()){ return false; }
        }

        fName = editFirstName.getText().toString();
        lName = editLastName.getText().toString();
        pNum = editPhoneNumber.getText().toString();
        return true;
    }


    private void loadMapFragment(){
        if(getActivity() != null){
            if(gpsIsOn()){
                MapFragment mapFragment = new MapFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                transaction.replace(R.id.conHome, mapFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            } else {
                loadOpenFragment();
            }
        }
    }


    private boolean gpsIsOn(){
        LocationManager locationManager = (LocationManager) getActivity()
                .getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void loadOpenFragment(){
        if(getActivity() != null){
            OpenFragment openFragment = new OpenFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.replace(R.id.conHome, openFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private void ProfileFragment(){
        if(getActivity() != null){
            OpenFragment openFragment = new OpenFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.replace(R.id.containerProfileDetails, openFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private void editProfileBackListener(){
        editProfileBack.setOnClickListener(view -> { ProfileFragment(); });
    }




}
