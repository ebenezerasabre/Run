package asabre.com.chase.viewmodel;

import android.icu.lang.UScript;
import android.service.autofill.UserData;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import asabre.com.chase.service.model.About;
import asabre.com.chase.service.model.Booking;
import asabre.com.chase.service.model.Driver;
import asabre.com.chase.service.model.DriverOnline;
import asabre.com.chase.service.model.Help;
import asabre.com.chase.service.model.History;
import asabre.com.chase.service.model.Review;
import asabre.com.chase.service.model.RideRequest;
import asabre.com.chase.service.model.Support;
import asabre.com.chase.service.model.User;
import asabre.com.chase.service.model.UserEntity;
import asabre.com.chase.service.model.UserOnline;
import asabre.com.chase.service.repository.HomeRepository;

public class HomeViewModel extends ViewModel {


//    private static HomeViewModel instance = null;
//    public static void resetViewModel(){ instance = null; }
//    public static HomeViewModel getInstance(){
//        if(instance == null){ instance = new HomeViewModel(); }
//        return instance;
//    }


    public static void reset(){
        searchedPlaceId = "";
        searchedPlaceName = "";

//        myLocationLatLng = null;
//        myDestinationLatLng = null;
        socketId = "";

        userEntryPoint = "";
        userExitPoint = "";

        createObject = new HashMap<>();
        userFull = new User();
        driverFull = new Driver();


        userEntity = new UserEntity();
        userType = "";
        rideType = "";
        rideState = "";
        driverHasRequest = false;

        countStart = 0;
        countFinish = 0;



        // google map
//        mGoogleMap = null;
//        mLineOptions = new PolylineOptions();
//        mPolyline = null;

        // track data loading
        isLoading = new MutableLiveData<>();
        mapIsReady = new MutableLiveData<>();
        mViewTrack = new MutableLiveData<>();

    }


    private static final String TAG = HomeViewModel.class.getSimpleName();
    private HomeRepository mHomeRepository;

    public static String msg = "Asabre here";

    public static String searchedPlaceId = "";
    public static String searchedPlaceName;

    public static LatLng myLocationLatLng;
    public static LatLng myDestinationLatLng;
//    public static String myCity;
    public static String socketId;
    // the one using the app, driver or passenger
    public static String userEntryPoint; // locality&subLocality&lat&lng
    public static String userExitPoint;


    public static HashMap<String, String> createObject = new HashMap<>();

    // when user opens app get all his data by
    // sending a get request with his id
    public static User userFull = new User();
    public static Driver driverFull = new Driver();
    private MutableLiveData<User> currentUser = new MutableLiveData<>();
    private MutableLiveData<Driver> currentDriver = new MutableLiveData<>();


    public static String userState = ""; // create, sign
    public static UserEntity userEntity = null;
    public static String userType = "";
    public static String rideType = "";
    public static String rideState = "";
    public static boolean driverHasRequest = false;

//    public static MutableLiveData<RideRequest> mmRideRequest = new MutableLiveData<>();
    public static RideRequest mRideRequest = new RideRequest();
    public static String requestStringDetails = "";
    private MutableLiveData<List<RideRequest>> mRideRequestHistory;
    private MutableLiveData<List<Booking>> mUserBookings;

    // automation of driver response
    // start, finish should be called once
    public static int countStart = 0;
    public static int countFinish = 0;

    private MutableLiveData<List<About>> about;
    private MutableLiveData<List<History>> history;
    private MutableLiveData<List<Review>> review;
    private MutableLiveData<List<Support>> support;

    // track clicked history
    public static History clickedHistory;
    public static RideRequest mRideRequestDetails = new RideRequest();



    // for google map
    public static GoogleMap mGoogleMap = null;
    public static PolylineOptions mLineOptions = new PolylineOptions();
    public static Polyline mPolyline = null;


    // track data loading
    public static MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public static MutableLiveData<Boolean> mapIsReady = new MutableLiveData<>();
    public static MutableLiveData<String>  mViewTrack = new MutableLiveData<>();
    public static String strViewTrack = "";

    public static MutableLiveData<String> mBackTrack = new MutableLiveData<>();

    public static MutableLiveData<String> mDistanceToExit = new MutableLiveData<>();
    public static MutableLiveData<String> mTimeToExit = new MutableLiveData<>();
    public static MutableLiveData<String> mDriverName = new MutableLiveData<>();
    public static MutableLiveData<String> mFinishedRides = new MutableLiveData<>();


    // view visibility
    /*
         // only for views within mapFragment
         ViewTrack {
            USER_GOING_WHERE,
            USER_RIDE_OPTIONS,
            USER_RIDE_REQUEST,
            USER_RIDE_ARRIVE,

            DRIVER_GO_ONLINE,
            DRIVER_ACCEPT_RIDE,
            DRIVER_START_RIDE,
            DRIVER_END_RIDE,
            CONNECT_INTERNET,
            SEARCH_INTENT
        };
    */


    public void init(){ mHomeRepository = HomeRepository.getInstance(); }


    // TODO USER
    public LiveData<User> createUser(HashMap<String, String> body){ return mHomeRepository.createUser(body); }
    public void setFindUserById(String userId){ currentUser = mHomeRepository.findUserById(userId); }
    public LiveData<User> getFindUserById(){ return currentUser; }

    public LiveData<User> signInUser(String phoneNumber){ return mHomeRepository.signInUser(phoneNumber); }

    public LiveData<User> updateUser(String id, HashMap<String, String> body){ return mHomeRepository.updateUser(id, body); }
//    public LiveData<String> deleteUser(String userId){ return mHomeRepository.deleteUser(userId); }
public LiveData<String> deleteUser(String userId){ return mHomeRepository.deleteObjectById(userId); }


    // TODO DRIVER
    public LiveData<Driver> createDriver(HashMap<String, String> body){ return mHomeRepository.createDriver(body); }
    public void setFindDriverById(String driverId){ currentDriver = mHomeRepository.findDriverById(driverId); }
    public LiveData<Driver> getFindDriverById(){ return currentDriver; }
    public LiveData<Driver> signInDriver(String phoneNumber){ return mHomeRepository.signInDriver(phoneNumber); }
    public LiveData<Driver> updateDriver(String driverId, HashMap<String, String> body){ return mHomeRepository.updateDriver(driverId, body); }
//    public LiveData<String> deleteDriver(String driverId){ return mHomeRepository.deleteDriver(driverId); }
public LiveData<String> deleteDriver(String driverId){ return mHomeRepository.deleteObjectById(driverId); }



    // TODO ABOUT
    public void setAbout(){ about = mHomeRepository.findAllAbouts(); }
    public LiveData<List<About>> getAbouts(){ return about; }

    // TODO HELP
    public LiveData<Help> createHelp(HashMap<String, String> body){ return mHomeRepository.createHelp(body); }

    // TODO HISTORY
    public void setUserHistory(String userId){ history = mHomeRepository.findUserHistory(userId); }
    public void setDriverHistory(String driverId){ history = mHomeRepository.findDriverHistory(driverId); }
    public LiveData<List<History>> getHistory(){ return history; }
//    public LiveData<String> deleteHistory(String id){ return mHomeRepository.deleteHistory(id);}
public LiveData<String> deleteHistory(String id){ return mHomeRepository.deleteObjectById(id);}

    // TODO REVIEW
    public LiveData<Review> createReview(HashMap<String, String> body){ return mHomeRepository.createReview(body); }
    public void setUserReviews(String userId){ review = mHomeRepository.findUserReview(userId); }
    public LiveData<List<Review>> getUserReviews(){ return review; }

    // TODO SUPPORT
    public void setSupport(){ support = mHomeRepository.findAllSupports(); }
    public LiveData<List<Support>> findAllSupports(){ return support; }


    // TODO LOADING
    public static void startLoading(){ isLoading.setValue(true); }
    public static void stopLoading(){ isLoading.setValue(false);}
    public static LiveData<Boolean> getIsLoading(){ return isLoading; }

    // TODO BOOKING
    public LiveData<Booking> createBooking(HashMap<String, String> body){ return mHomeRepository.createBooking(body);};




    // TODO MAP READY
    public static void setMapIsReady(){ mapIsReady.setValue(true);}
    public static void setmapIsNotReady(){ mapIsReady.setValue(false);}
    public static LiveData<Boolean> getMapReady(){ return mapIsReady; }

    // set viewTrack
    public static void setViewTrack(String viewTrack){ mViewTrack.setValue(viewTrack); }
    public static LiveData<String > getViewTrack(){ return mViewTrack; }

    // set backTrack
    public static void setBackTrack(String backTrack){ mBackTrack.setValue(backTrack);}
    public static LiveData<String> getBackTrack(){ return mBackTrack; }
    // eg Home, HomeDet1,
    // His, HisDet1, Hisdet2, HisDet3


    // TODO RIDE REQUEST

    public LiveData<List<RideRequest>> userRequestHistory(String id){
        if(mRideRequestHistory == null){
            mRideRequestHistory = mHomeRepository.findUserRideRequests(id);
        }
        return mRideRequestHistory;
    }

    public LiveData<List<RideRequest>> driverRequestHistory(String id){
        if(mRideRequestHistory == null){
            mRideRequestHistory = mHomeRepository.findDriverRideRequests(id);
        }
        return mRideRequestHistory;
    }

    public void resetRequestHistory(){
        mRideRequestHistory = null;
    }



    // TODO USER BOOKINGS
    public LiveData<List<Booking>> userBookings(String id){
        if(mUserBookings == null){
            mUserBookings = mHomeRepository.findUserBookings(id);
        }
        return mUserBookings;
    }




// 141
// 138

}


