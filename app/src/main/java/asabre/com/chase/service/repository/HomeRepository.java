package asabre.com.chase.service.repository;

import android.util.Log;

import org.json.JSONObject;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import asabre.com.chase.service.model.About;
import asabre.com.chase.service.model.Booking;
import asabre.com.chase.service.model.Driver;
import asabre.com.chase.service.model.Help;
import asabre.com.chase.service.model.History;
import asabre.com.chase.service.model.Review;
import asabre.com.chase.service.model.RideRequest;
import asabre.com.chase.service.model.Support;
import asabre.com.chase.service.model.User;
import asabre.com.chase.viewmodel.HomeViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeRepository {
    private static final String TAG = HomeRepository.class.getSimpleName();
    private static ChaseService chaseService;
    private static HomeRepository homeRepository;

    // implementing singleton
    public static HomeRepository getInstance(){
        Log.d(TAG, "getInstance: homeRepository called");
        chaseService = RetroClient.getInstance().create(ChaseService.class);
        if(homeRepository == null){
            homeRepository = new HomeRepository();
        }
        return homeRepository;
    }


    // TODO USER

    // create user
    public MutableLiveData<User> createUser(HashMap<String, String> body){
        final MutableLiveData<User> user = new MutableLiveData<>();
        Call<User> call = chaseService.createUser(body);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    user.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse: create user error " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: create user failure " + t.getMessage());
            }
        });
        return user;
    }
    public MutableLiveData<User> signInUser(String phoneNumber){
        HomeViewModel.startLoading();

        final MutableLiveData<User> user = new MutableLiveData<>();
        Call<List<User>> call = chaseService.signInUser(phoneNumber);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                HomeViewModel.stopLoading();

                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: body is " + response.body());
                    user.setValue(response.body().get(0));
                } else {
                    Log.d(TAG, "onResponse: signInUser error " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d(TAG, "onFailure: signInUser failure " + t.getMessage());
                HomeViewModel.stopLoading();
            }
        });
        return user;
    }


    public MutableLiveData<User> findUserById(String userId){
        final MutableLiveData<User> currentUser = new MutableLiveData<>();
        Call<User> call = chaseService.findUserById(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    currentUser.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse: error finding user " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: failure finding user " + t.getMessage());
            }
        });
        return currentUser;
    }

    public MutableLiveData<User> updateUser(String userId, HashMap<String, String> body){
        final MutableLiveData<User> user = new MutableLiveData<>();
        Call<User> call = chaseService.updateUser(userId, body);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    user.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse: update user error " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: updateUser failure " + t.getMessage());
            }
        });
        return user;
    }

//    public MutableLiveData<String> deleteUser(String userId){
//        final MutableLiveData<String> del = new MutableLiveData<>();
//        Call<String> call = chaseService.deleteUser(userId);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if(response.isSuccessful()){
//                    del.setValue(response.body());
//                } else {
//                    Log.d(TAG, "onResponse: deleteUser error " + response.errorBody());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Log.d(TAG, "onFailure: deleteUser failure " + t.getMessage());
//            }
//        });
//        return del;
//    }




    // TODO DRIVER

    public MutableLiveData<Driver> createDriver(HashMap<String, String> body){
        final MutableLiveData<Driver> driver = new MutableLiveData<>();
        Call<Driver> call = chaseService.createDriver(body);
        call.enqueue(new Callback<Driver>() {
            @Override
            public void onResponse(Call<Driver> call, Response<Driver> response) {
                if(response.isSuccessful()){
                    driver.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse: craete driver error " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Driver> call, Throwable t) {
                Log.d(TAG, "onFailure: create driver failure " + t.getMessage());
            }
        });
        return driver;
    }

    public MutableLiveData<Driver> signInDriver(String phoneNumber){
        HomeViewModel.startLoading();

        final MutableLiveData<Driver> driver = new MutableLiveData<>();
        Call<List<Driver>> call = chaseService.signInDriver(phoneNumber);
        call.enqueue(new Callback<List<Driver>>() {
            @Override
            public void onResponse(Call<List<Driver>> call, Response<List<Driver>> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: " + response.body().get(0));
                    driver.setValue(response.body().get(0));
                } else {
                    Log.d(TAG, "onResponse: signInDriver erro " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Driver>> call, Throwable t) {
                Log.d(TAG, "onFailure: signInDriver failuure " + t.getMessage());
                HomeViewModel.stopLoading();
            }
        });
        return driver;
    }

    public MutableLiveData<Driver> findDriverById(String driverId){
        final MutableLiveData<Driver> currentDriver = new MutableLiveData<>();
        Call<Driver> call = chaseService.findDriverById(driverId);
        call.enqueue(new Callback<Driver>() {
            @Override
            public void onResponse(Call<Driver> call, Response<Driver> response) {
                if(response.isSuccessful()){
                    currentDriver.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse: findDriverById error " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Driver> call, Throwable t) {
                Log.d(TAG, "onFailure: findDriverById failure " + t.getMessage());
            }
        });
        return currentDriver;
    }

    public MutableLiveData<Driver> updateDriver(String driverId, HashMap<String, String> body){
        final MutableLiveData<Driver> driver = new MutableLiveData<>();
        Call<Driver> call = chaseService.updateDriver(driverId, body);
        call.enqueue(new Callback<Driver>() {
            @Override
            public void onResponse(Call<Driver> call, Response<Driver> response) {
                if(response.isSuccessful()){
                    driver.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse: updateDriver error " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Driver> call, Throwable t) {
                Log.d(TAG, "onFailure: udpateDriver failure " + t.getMessage());
            }
        });
        return driver;
    }

//    public MutableLiveData<String> deleteDriver(String driverId){
//        final MutableLiveData<String> del = new MutableLiveData<>();
//        Call<String> call = chaseService.deleteDriver(driverId);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if(response.isSuccessful()){
//                    del.setValue(response.body());
//                } else {
//                    Log.d(TAG, "onResponse: delete driver error " + response.errorBody());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Log.d(TAG, "onFailure: delete driver failure " + t.getMessage());
//            }
//        });
//        return del;
//    }



    // TODO ABOUT

    public MutableLiveData<List<About>> findAllAbouts(){
        final MutableLiveData<List<About>> abouts = new MutableLiveData<>();
        Call<List<About>> call = chaseService.findAllAbouts();
        call.enqueue(new Callback<List<About>>() {
            @Override
            public void onResponse(Call<List<About>> call, Response<List<About>> response) {
                if(response.isSuccessful()){
                    abouts.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse: find abouts error " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<About>> call, Throwable t) {
                Log.d(TAG, "onFailure: find abouts failure " + t.getMessage());
            }
        });
        return abouts;
    }


    // TODO HELP
    public MutableLiveData<Help> createHelp(HashMap<String, String> body){
        final MutableLiveData<Help> help = new MutableLiveData<>();
        Call<Help> call = chaseService.createHelp(body);
        call.enqueue(new Callback<Help>() {
            @Override
            public void onResponse(Call<Help> call, Response<Help> response) {
                if(response.isSuccessful()){
                    help.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse: create help error " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Help> call, Throwable t) {
                Log.d(TAG, "onFailure: create help failure " + t.getMessage());
            }
        });
        return help;
    }


    // TODO HISTORY

    // find user's history
    public MutableLiveData<List<History>> findUserHistory(String userId){
        final MutableLiveData<List<History>> userHistory = new MutableLiveData<>();
        Call<List<History>> call = chaseService.findUserHistory(userId);
        call.enqueue(new Callback<List<History>>() {
            @Override
            public void onResponse(Call<List<History>> call, Response<List<History>> response) {
                if(response.isSuccessful()){
                    userHistory.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse: findUserHistory error " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<History>> call, Throwable t) {
                Log.d(TAG, "onFailure: findUserHistory failure " + t.getMessage());
            }
        });
        return userHistory;
    }


    public MutableLiveData<List<History>> findDriverHistory(String driverId){
        final MutableLiveData<List<History>> driverHistory = new MutableLiveData<>();
        Call<List<History>> call = chaseService.findDriverHistory(driverId);
        call.enqueue(new Callback<List<History>>() {
            @Override
            public void onResponse(Call<List<History>> call, Response<List<History>> response) {
                if(response.isSuccessful()){
                    driverHistory.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse: findDriverHistory error " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<History>> call, Throwable t) {
                Log.d(TAG, "onFailure: findDriverHistory failure " + t.getMessage());
            }
        });
        return driverHistory;
    }

    // delete history
//    public MutableLiveData<String> deleteHistory(String id){
//        final MutableLiveData<String> del = new MutableLiveData<>();
//        Call<String> call = chaseService.deleteHistory(id);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if(response.isSuccessful()){
//                    del.setValue(response.body());
//                } else {
//                    Log.d(TAG, "onResponse: deleting history error " + response.errorBody());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Log.d(TAG, "onFailure: deleting history failure " + t.getMessage());
//            }
//        });
//        return del;
//    }



    // TODO REVIEW


    public MutableLiveData<Review> createReview(HashMap<String, String> body){
        final MutableLiveData<Review> review = new MutableLiveData<>();
        Call<Review> call = chaseService.createReview(body);
        call.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if(response.isSuccessful()){
                    review.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse: create review error " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Log.d(TAG, "onFailure: create review failure " + t.getMessage());
            }
        });
        return review;
    }

    public MutableLiveData<List<Review>> findUserReview(String userId){
        final MutableLiveData<List<Review>> reviews = new MutableLiveData<>();
        Call<List<Review>> call = chaseService.findUserReviews(userId);
        call.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if (response.isSuccessful()){
                    reviews.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse: find user reviews error " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Log.d(TAG, "onFailure: find user review failure " + t.getMessage());
            }
        });
        return reviews;
    }


    // TODO SUPPORT
    public MutableLiveData<List<Support>> findAllSupports(){
        final MutableLiveData<List<Support>> supports = new MutableLiveData<>();
        Call<List<Support>> call = chaseService.findAllSupports();
        call.enqueue(new Callback<List<Support>>() {
            @Override
            public void onResponse(Call<List<Support>> call, Response<List<Support>> response) {
                if(response.isSuccessful()){
                    supports.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse: finding supports error " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Support>> call, Throwable t) {
                Log.d(TAG, "onFailure: finding supports failure " + t.getMessage());
            }
        });
        return supports;
    }


    // TODO RIDE REQUEST
    public MutableLiveData<List<RideRequest>> findUserRideRequests(String id){
        HomeViewModel.startLoading();

        final MutableLiveData<List<RideRequest>> rides = new MutableLiveData<>();
        Call<List<RideRequest>> call = chaseService.findUserRideRequests(id);
        call.enqueue(new Callback<List<RideRequest>>() {
            @Override
            public void onResponse(Call<List<RideRequest>> call, Response<List<RideRequest>> response) {
                HomeViewModel.stopLoading();
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: request body " + response.body());
                    rides.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse: findUserRideRequest error " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<RideRequest>> call, Throwable t) {
                HomeViewModel.stopLoading();
                Log.d(TAG, "onFailure: findUserRideRequest failure " + t.getMessage());
            }
        });
        return rides;
    }

    public MutableLiveData<List<RideRequest>> findDriverRideRequests(String id){
        HomeViewModel.startLoading();

        final MutableLiveData<List<RideRequest>> rides = new MutableLiveData<>();
        Call<List<RideRequest>> call = chaseService.findDriverRideRequests(id);
        call.enqueue(new Callback<List<RideRequest>>() {
            @Override
            public void onResponse(Call<List<RideRequest>> call, Response<List<RideRequest>> response) {
                if(response.isSuccessful()){
                    rides.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse: findDriverRideRequest error " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<RideRequest>> call, Throwable t) {
                Log.d(TAG, "onFailure: findDriverRideRequest failure " + t.getMessage());
            }
        });
        return rides;
    }



    // TODO BOOKING

    public MutableLiveData<List<Booking>> findUserBookings(String id){
        HomeViewModel.startLoading();

        final MutableLiveData<List<Booking>> books = new MutableLiveData<>();
        Call<List<Booking>> call = chaseService.findUserBookings(id);
        call.enqueue(new Callback<List<Booking>>() {
            @Override
            public void onResponse(Call<List<Booking>> call, Response<List<Booking>> response) {
                if(response.isSuccessful()){
                    HomeViewModel.stopLoading();
                    books.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse: findUserBookings error " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Booking>> call, Throwable t) {
                HomeViewModel.stopLoading();
                Log.d(TAG, "onFailure: findUserBooking error " + t.getMessage());
            }
        });
        return books;
    }

    public MutableLiveData<Booking> createBooking(HashMap<String, String> body){
        HomeViewModel.startLoading();

        final MutableLiveData<Booking> book = new MutableLiveData<>();
        Call<Booking> call = chaseService.createBooking(body);
        call.enqueue(new Callback<Booking>() {
            @Override
            public void onResponse(Call<Booking> call, Response<Booking> response) {
                if(response.isSuccessful()){
                    HomeViewModel.stopLoading();
                    book.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse: creating Booking error " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Booking> call, Throwable t) {
                HomeViewModel.stopLoading();
                Log.d(TAG, "onFailure: creating Booking error " + t.getMessage());
            }
        });
        return book;
    }


    // delete history
    public MutableLiveData<String> deleteObjectById(String id){
        HomeViewModel.stopLoading();

        final MutableLiveData<String> del = new MutableLiveData<>();
        Call<String> call = chaseService.deleteHistory(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    HomeViewModel.stopLoading();
                    del.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse: deleting object error " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                HomeViewModel.stopLoading();
                Log.d(TAG, "onFailure: deleting object failure " + t.getMessage());
            }
        });
        return del;
    }





}
