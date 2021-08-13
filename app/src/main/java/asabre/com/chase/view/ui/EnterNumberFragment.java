package asabre.com.chase.view.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import asabre.com.chase.R;
import asabre.com.chase.viewmodel.HomeViewModel;

public class EnterNumberFragment extends Fragment {
    private static final String TAG = EnterNumberFragment.class.getSimpleName();

    private TextView verifyNoBack;
    private TextInputEditText enterNumber;
    private MaterialButton enterNumberContinue;

    private TextView enterNumberVerificationCode;
    private ProgressDialog mProgressDialog;
//    private ProgressBar indeterminateYourNumber;
    private String mPhoneNumber = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_number, container, false);
        init(view);
        setSmsListener();
        return view;
    }

    private void init(View view){
        verifyNoBack = view.findViewById(R.id.verifyNoBack);
        enterNumber = view.findViewById(R.id.enterNumber);
        enterNumberContinue = view.findViewById(R.id.enterNumberContinue);
        enterNumberVerificationCode = view.findViewById(R.id.enterNumberVerificationCode);

    }

    private void setButton(){
        if(HomeViewModel.userState.contains("create")){
            enterNumberContinue.setText(R.string._continue);
        } else if(HomeViewModel.userState.contains("sign")){
            enterNumberContinue.setText(R.string.sign_in);
        }
    }

    private void setSmsListener(){
        enterNumberContinue.setOnClickListener(view -> sendVerificationCode());
    }

    private boolean fieldIsNotEmpty(){
       return !enterNumber.getText().toString().isEmpty();
    }

    private void sendVerificationCode() {
        if(smsGranted() && getActivity() != null){
            if(fieldIsNotEmpty()){
                showProgressDialog();
                ReadSMS readSMS = new ReadSMS();
                readSMS.execute();  // call AsyncTask
            } else {
                Toast.makeText(getContext(), "Field can't be empty", Toast.LENGTH_SHORT).show();
            }

        } else {
            dismissProgressDialog();
            grantSMSPermission();
//            indeterminateYourNumber.setVisibility(View.GONE);
        }
    }

    private String getRandom(){
        int min = 50;
        int max = 100;
        double randomDouble = Math.random() * (max - min + 1) + min;
        randomDouble  *= 25;
        int ran = (int) randomDouble;
        return String.valueOf(ran);
    }


    private String readSMS(){
        String code = "";
        if(getActivity()  != null){
            SystemClock.sleep(5000);

            Uri uri = Uri.parse("content://sms/");

            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
                int hasReadSmsPermission = getActivity().checkSelfPermission(Manifest.permission.READ_SMS);
                if(hasReadSmsPermission != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.READ_SMS}, AppConstants.READ_SMS_PERMISSION);
                    return "";
                }
            }

            int count = 0;
            ContentResolver resolver =getActivity().getContentResolver();
            Cursor cursor = resolver.query(uri, new String[]{"_id", "address", "type", "body", "date"}, null, null, null);
            List<HashMap<String, String>> smsInfos = new ArrayList<>();
            if(cursor != null && cursor.getCount() > 0){
                while (cursor.moveToNext() && count <= 2){
                    count += 1;
                    if(count == 1){
                        int _id = cursor.getInt(0);
                        String address = cursor.getString(1);
                        int type = cursor.getInt(2);
                        String body = cursor.getString(3);
                        long date = cursor.getLong(4);
                        HashMap<String, String> smsInfo = new HashMap<>();
                        smsInfo.put("_id", String.valueOf(_id));
                        smsInfo.put("address", address);
                        smsInfo.put("type", String.valueOf(type));
                        smsInfo.put("body", body);
                        smsInfo.put("date", String.valueOf(date));

                        smsInfos.add(smsInfo);
                    }
                }
                cursor.close();
            }

            String body = "";
            for(int x=0; x<smsInfos.size(); x++){
                body = smsInfos.get(x).get("body") + "\n";
                Log.d(TAG, "body is : " + body);
            }
            code = body;
        }
        return code;
    }

 private class ReadSMS extends AsyncTask<Void, Void, String>{
     @Override
     protected String doInBackground(Void... voids) {
         sendTextMessage();
         return readSMS();
     }

     @Override
     protected void onPostExecute(String s) {
         super.onPostExecute(s);
//         if(!(s.isEmpty())){
             // set phoneNumber
             HomeViewModel.createObject.put("phoneNumber", mPhoneNumber);

             enterNumberVerificationCode.setText(s);
             Log.d(TAG, "onPostExecute: the string " + s);
             dismissProgressDialog();

             loadNext();
//         }
     }
 }

 private void loadNext(){
     Log.d(TAG, "loadNext: ik");
     if(HomeViewModel.userState.contains("create")){
         Log.d(TAG, "loadNext: creating ");
         loadEnterEmailFragment();
     } else if(HomeViewModel.userState.contains("sign")){
         Log.d(TAG, "loadNext: signing buddy");
         loadSignInFragment();
     }
 }


 private void sendTextMessage(){
     mPhoneNumber = enterNumber.getText().toString();
     SmsManager smsManager = SmsManager.getDefault();
     smsManager.sendTextMessage(mPhoneNumber, null, getRandom(), null, null);
 }

    private boolean smsGranted(){
        int smsPermission = 0;
        if(getContext() != null){
             smsPermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS);
            return smsPermission == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    private void grantSMSPermission() {
        if(getActivity() != null){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, AppConstants.SEND_SMS_PERMISSION);
        }
    }



    private void showProgressDialog() {
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Verifying number");
        mProgressDialog.setMessage("Please wait.");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

    }

    private void dismissProgressDialog() {
        if(mProgressDialog != null){
            if(mProgressDialog.isShowing()){
                mProgressDialog.dismiss();
            }
        }
    }

    private void loadEnterEmailFragment() {
        MainActivity.mRegProcess = MainActivity.RegistrationProcess.EMAIL;

        EnterEmailFragment enterEmailFragment = new EnterEmailFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.conHome, enterEmailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadIntroFragment() {
        MainActivity.mRegProcess = MainActivity.RegistrationProcess.INTRO;

        IntroFragment introFragment = new IntroFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.conHome, introFragment);
        transaction.addToBackStack(null);
        transaction.commit();


    }

    private void loadSignInFragment() {
        MainActivity.mRegProcess = MainActivity.RegistrationProcess.EMAIL;

        SignInFragment signInFragment = new SignInFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.conHome, signInFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void goBack() {
        verifyNoBack.setOnClickListener(view -> {
            loadIntroFragment();
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: of enterNumber fragment called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: number called");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: number called");
        goBack();
        setButton();
    }


    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: number called");
    }


}
