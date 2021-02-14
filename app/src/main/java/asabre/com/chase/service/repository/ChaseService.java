package asabre.com.chase.service.repository;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import asabre.com.chase.service.model.About;
import asabre.com.chase.service.model.Driver;
import asabre.com.chase.service.model.Help;
import asabre.com.chase.service.model.History;
import asabre.com.chase.service.model.Review;
import asabre.com.chase.service.model.RideRequest;
import asabre.com.chase.service.model.Support;
import asabre.com.chase.service.model.User;
import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ChaseService {

    // TODO user

    @POST("user")
    Call<User> createUser(@Body HashMap<String, String> body);

    @GET("user/sign/{id}")
    Call<List<User>> signInUser(@Path("id") String id);

    @GET("user/{id}")
    Call<User> findUserById(@Path("id") String id);

    @PUT("user/{id}")
    Call<User> updateUser(@Path("id") String id, @Body HashMap<String, String> body);

    @DELETE("user/{id}")
    Call<String> deleteUser(@Path("id") String id);


    // TODO DRIVER

    @POST("driver")
    Call<Driver> createDriver(@Body HashMap<String, String> body);

    @GET("driver/sign/{id}")
    Call<List<Driver>> signInDriver(@Path("id") String id);

    @GET("driver/{id}")
    Call<Driver> findDriverById(@Path("id") String id);

    @PUT("driver/{id}")
    Call<Driver> updateDriver(@Path("id") String id, @Body HashMap<String, String> body);

    @DELETE("driver/{id}")
    Call<String> deleteDriver(@Path("id") String id);


    // TODO ABOUT
    @GET("about/")
    Call<List<About>> findAllAbouts();

    // TODO HELP
    @POST("help")
    Call<Help> createHelp(@Body HashMap<String, String> body);


    // TODO HISTORY

    @GET("history/user/{id}")
    Call<List<History>> findUserHistory(@Path("id") String id);

    @GET("history/driver/{id}")
    Call<List<History>> findDriverHistory(@Path("id") String id);

    @DELETE("history/{id}")
    Call<String> deleteHistory(@Path("id") String id);


    // TODO REVIEW

    @POST("review")
    Call<Review> createReview(@Body HashMap<String, String> body);

    @GET("review/user/{id}")
    Call<List<Review>> findUserReviews(@Path("id") String id);

    @DELETE("review/{id}")
    Call<String> deleteReview(@Path("id") String id);


    // TODO SUPPORT
    @GET("support/")
    Call<List<Support>> findAllSupports();

    // TODO RIDE REQUEST
    @GET("ride/user/{id}")
    Call<List<RideRequest>> findUserRideRequests(@Path("id") String id);

    @GET("ride/driver/{id}")
    Call<List<RideRequest>> findDriverRideRequests(@Path("id") String id);

}
