package asabre.com.chase.view.ui;

public class AppConstants {
    private static final String TAG = AppConstants.class.getSimpleName();


    public static final int GPS_REQUEST = 100;
    public static final int AUTOCOMPLETE_REQUEST_CODE = 101;
    public static final int READ_SMS_PERMISSION = 102;
    public static final int SEND_SMS_PERMISSION = 103;
    public static final int CALL_PHONE_CODE = 104;
    public static final float DEFAULT_ZOOM = 17;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 105;

    private static AppConstants instance;

    public static String API_KEY = MainActivity.API_KEY;


    public static AppConstants getInstance(){
        if(instance == null){ // no instance available
            instance = new AppConstants();
        }
        return instance;
    }
}


