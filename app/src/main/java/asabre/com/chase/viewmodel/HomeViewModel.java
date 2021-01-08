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
    private static final String TAG = HomeViewModel.class.getSimpleName();
    private HomeRepository mHomeRepository;

    public static String searchedPlaceId;
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

    public static UserEntity userEntity = null;
    public static String userType = "";
    public static String rideType = "";
    public static String rideState = "";
    public static boolean driverHasRequest = false;

    public static RideRequest mRideRequest = new RideRequest();
    public static String requestStringDetails = "";

    // automation of driver response
    // start, finish should be called once
    public static int countStart = 0;
    public static int countFinish = 0;

    private MutableLiveData<List<About>> about;
    private MutableLiveData<List<History>> history;
    private MutableLiveData<List<Review>> review;
    private MutableLiveData<List<Support>> support;

    // for google map
    public static GoogleMap mGoogleMap;
    public static PolylineOptions mLineOptions = new PolylineOptions();
    public static Polyline mPolyline;



    public void init(){ mHomeRepository = HomeRepository.getInstance(); }

    // TODO USER
    public LiveData<User> createUser(HashMap<String, String> body){ return mHomeRepository.createUser(body); }
    public void setFindUserById(String userId){ currentUser = mHomeRepository.findUserById(userId); }
    public LiveData<User> getFindUserById(){ return currentUser; }
    public LiveData<User> signInUser(String phoneNumber){ return mHomeRepository.signInUser(phoneNumber); }
    public LiveData<User> updateUser(String id, HashMap<String, String> body){ return mHomeRepository.updateUser(id, body); }
    public LiveData<String> deleteUser(String userId){ return mHomeRepository.deleteUser(userId); }


    // TODO DRIVER
    public LiveData<Driver> createDriver(HashMap<String, String> body){ return mHomeRepository.createDriver(body); }
    public void setFindDriverById(String driverId){ currentDriver = mHomeRepository.findDriverById(driverId); }
    public LiveData<Driver> getFindDriverById(){ return currentDriver; }
    public LiveData<Driver> signInDriver(String phoneNumber){ return mHomeRepository.signInDriver(phoneNumber); }
    public LiveData<Driver> updateDriver(String driverId, HashMap<String, String> body){ return mHomeRepository.updateDriver(driverId, body); }
    public LiveData<String> deleteDriver(String driverId){ return mHomeRepository.deleteDriver(driverId); }


    // TODO ABOUT
    public void setAbout(){about = mHomeRepository.findAllAbouts(); }
    public LiveData<List<About>> getAbouts(){ return about; }

    // TODO HELP
    public LiveData<Help> createHelp(HashMap<String, String> body){ return mHomeRepository.createHelp(body); }

    // TODO HISTORY
    public void setUserHistory(String userId){ history = mHomeRepository.findUserHistory(userId); }
    public void setDriverHistory(String driverId){ history = mHomeRepository.findDriverHistory(driverId); }
    public LiveData<List<History>> getHistory(){ return history; }
    public LiveData<String> deleteHistory(String id){ return mHomeRepository.deleteHistory(id);}

    // TODO REVIEW
    public LiveData<Review> createReview(HashMap<String, String> body){ return mHomeRepository.createReview(body); }
    public void setUserReviews(String userId){ review = mHomeRepository.findUserReview(userId); }
    public LiveData<List<Review>> getUserReviews(){ return review; }

    // TODO SUPPORT
    public void setSupport(){ support = mHomeRepository.findAllSupports(); }
    public LiveData<List<Support>> findAllSupports(){ return support; }



// 141
// 138

}


