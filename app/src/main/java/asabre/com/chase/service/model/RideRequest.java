package asabre.com.chase.service.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import asabre.com.chase.viewmodel.HomeViewModel;

/**
 * A user will make a request for a ride and have to wait
 * Until a driver accepts the request to pick him up
 * The request obj will be updated with the drivers details
 */
public class RideRequest {
    private static final String TAG = "RideRequest";

    private String userName = "";
    private String userId = "";
    private String userType = "";
    private String phoneNumber = "";
    private String socketId = "";
    private String entryPoint = "";

    private String exitPoint = "";
    private String lat = "";
    private String lng = "";
    private String city = "";
    private String rideType = "";

    // driver details
    private String proximityDriver = "";
    private String driverId = "";
    private String dSocketId = "";
    private String dLat = "";
    private String dLng = "";

    private String dArrivalTime = "";
    private String dName = "";
    private String dRideDescription = "";
    private String dRideNumber = "";
    private String dFinishedRides = "";

    private String driverReject = "";
    private String rideState = "pending";  // pending, picking, start, finish, cancel
    private String fee = "";

    private   JSONObject initRequest = new JSONObject();


    public RideRequest(){}


    // user,driver receiving requests
    public void RideRequestReset(String jsonString){
        Log.d(TAG, "RideRequestReset: jsonString " + jsonString);
        try {
            JSONObject request = new JSONObject(jsonString);

            setUserName(request.getString("userName"));
            setUserId(request.getString("userId"));
            setUserType(request.getString("userType"));
            setPhoneNumber(request.getString("phoneNumber"));
            setSocketId(request.getString("socketId"));
            setEntryPoint(request.getString("entryPoint"));

            setExitPoint(request.getString("exitPoint"));
            setLat(request.getString("lat"));
            setLng(request.getString("lng"));
            setCity(request.getString("city"));
            setRideType(request.getString("rideType"));

            setProximityDriver(request.getString("proximityDriver"));
            setDriverId(request.getString("driverId"));
            setdSocketId(request.getString("dSocketId"));
            setdLat(request.getString("dLat"));
            setdLng(request.getString("dLng"));

            setdArrivalTime(request.getString("dArrivalTime"));
            setdName(request.getString("dName").split(" ")[0]);
            setdRideDescription(request.getString("dRideDescription"));

            Log.d(TAG, "RideRequestReset: ride description " + request.getString("dRideDescription"));

            setdRideNumber(request.getString("dRideNumber"));
            setdFinishedRides(request.getString("dFinishedRides"));

            setDriverReject(request.getString("driverReject"));
            setRideState(request.getString("rideState"));
            setFee(request.getString("fee"));

        } catch (JSONException e){
            e.printStackTrace();
        }

    }


    // user sending request for the first time
    public String initializeRequest(){
        try {
            // entryPoint = name&lat&lng
            initRequest.put("userName", HomeViewModel.userEntity.getFirstName());
            initRequest.put("userId", HomeViewModel.userEntity.get_id());
            initRequest.put("userType", HomeViewModel.userType);
            initRequest.put("phoneNumber", HomeViewModel.userEntity.getPhoneNumber());
            initRequest.put("socketId", HomeViewModel.socketId);
            initRequest.put("entryPoint", HomeViewModel.userEntryPoint); // locality&subLocality&lat&lng
            initRequest.put("exitPoint", HomeViewModel.userExitPoint);   // locality&searchedPlace&lat&lng

            // lat and lng is part of entryPoint
            initRequest.put("lat", HomeViewModel.userEntryPoint.split("&")[2]);
            initRequest.put("lng", HomeViewModel.userEntryPoint.split("&")[3]);
            initRequest.put("city", HomeViewModel.userEntryPoint.split("&")[0].toLowerCase());
            initRequest.put("rideType", HomeViewModel.rideType);
            initRequest.put("proximityDriver", getProximityDriver());

            initRequest.put("driverId", getDriverId());
            initRequest.put("dSocketId", getdSocketId());
            initRequest.put("dLat", getdLat());
            initRequest.put("dLng", getdLng());
            initRequest.put("dArrivalTime", getdArrivalTime());

            initRequest.put("dName", getdName());
            initRequest.put("dRideDescription", getdRideDescription());
            initRequest.put("dRideNumber", getdRideNumber());
            initRequest.put("dFinishedRides", getdFinishedRides());
            initRequest.put("driverReject", getDriverReject());

            initRequest.put("fee", getFee());
            initRequest.put("rideState", getRideState());

        } catch (JSONException e){
            e.printStackTrace();
        }
        return initRequest.toString();
    }


    // call this when driver first accepts ride request
    public void addDriverDetailsToRequest() {
                // user part has already been set by user
                setDriverId(HomeViewModel.userEntity.get_id());
                setdSocketId(HomeViewModel.socketId);
                setdLat(HomeViewModel.userEntryPoint.split("&")[2]);
                setdLng(HomeViewModel.userEntryPoint.split("&")[3]);
                setdArrivalTime("3");

                setdName(HomeViewModel.userEntity.getFirstName());
                setdRideDescription(HomeViewModel.driverFull.getCarDescription());
                setdRideNumber(HomeViewModel.driverFull.getCarNumber());
                setdFinishedRides(HomeViewModel.driverFull.getFinishedRides());
                setRideState(HomeViewModel.rideState);
    }

    // driver sends this string anytime, only updating the rideStatus
    // save result in HomeViewModel.requestStringDetails and retrieve from there
    public String returnRequestString(){
        try {
            initRequest.put("userName", getUserName());
            initRequest.put("userId", getUserId());
            initRequest.put("userType", getUserType());
            initRequest.put("phoneNumber", getPhoneNumber());
            initRequest.put("socketId", getSocketId());
            initRequest.put("entryPoint", getEntryPoint()); // locality&subLocality&lat&lng
            initRequest.put("exitPoint", getExitPoint());   // locality&searchedPlace&lat&lng

            // lat and lng is part of entryPoint
            initRequest.put("lat", getLat());
            initRequest.put("lng", getLng());
            initRequest.put("city", getCity());
            initRequest.put("rideType", getRideType());
            initRequest.put("proximityDriver", getProximityDriver());

            initRequest.put("driverId", getDriverId());
            initRequest.put("dSocketId", getdSocketId());
            initRequest.put("dLat", getdLat());
            initRequest.put("dLng", getdLng());
            initRequest.put("dArrivalTime", getdArrivalTime());

            initRequest.put("dName", getdName());
            initRequest.put("dRideDescription", getdRideDescription());
            initRequest.put("dRideNumber", getdRideNumber());
            initRequest.put("dFinishedRides", getdFinishedRides());
            initRequest.put("driverReject", getDriverReject());

            initRequest.put("fee", getFee());
            initRequest.put("rideState", HomeViewModel.rideState);

        } catch (JSONException e){
            e.printStackTrace();
        }
        return initRequest.toString();
    }




    public String getUserId() {
        return userId;
    }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public void setUserId(String _id) {
        this.userId = _id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    public String getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(String entryPoint) {
        this.entryPoint = entryPoint;
    }

    public String getExitPoint() {
        return exitPoint;
    }

    public void setExitPoint(String exitPoint) {
        this.exitPoint = exitPoint;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRideType() {
        return rideType;
    }

    public void setRideType(String rideType) {
        this.rideType = rideType;
    }

    public String getProximityDriver() {
        return proximityDriver;
    }

    public void setProximityDriver(String proximityDriver) {
        this.proximityDriver = proximityDriver;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getdSocketId() {
        return dSocketId;
    }

    public void setdSocketId(String dSocketId) {
        this.dSocketId = dSocketId;
    }

    public String getdLat() {
        return dLat;
    }

    public void setdLat(String dLat) {
        this.dLat = dLat;
    }

    public String getdLng() {
        return dLng;
    }

    public void setdLng(String dLng) {
        this.dLng = dLng;
    }

    public String getdArrivalTime() {
        return dArrivalTime;
    }

    public void setdArrivalTime(String dArrivalTime) {
        this.dArrivalTime = dArrivalTime;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getdRideDescription() {
        return dRideDescription;
    }

    public void setdRideDescription(String dRideDescription) {
        this.dRideDescription = dRideDescription;
    }

    public String getdRideNumber() {
        return dRideNumber;
    }

    public void setdRideNumber(String dRideNumber) {
        this.dRideNumber = dRideNumber;
    }

    public String getdFinishedRides() {
        return dFinishedRides;
    }

    public void setdFinishedRides(String dFinishedRides) {
        this.dFinishedRides = dFinishedRides;
    }

    public String getDriverReject() {
        return driverReject;
    }

    public void setDriverReject(String driverReject) {
        this.driverReject = driverReject;
    }

    public String getRideState() {
        return rideState;
    }

    public void setRideState(String rideState) {
        this.rideState = rideState;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "RideRequest{" +
                "userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", userType='" + userType + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", socketId='" + socketId + '\'' +
                ", entryPoint='" + entryPoint + '\'' +
                ", exitPoint='" + exitPoint + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", city='" + city + '\'' +
                ", rideType='" + rideType + '\'' +
                ", proximityDriver='" + proximityDriver + '\'' +
                ", driverId='" + driverId + '\'' +
                ", dSocketId='" + dSocketId + '\'' +
                ", dLat='" + dLat + '\'' +
                ", dLng='" + dLng + '\'' +
                ", dArrivalTime='" + dArrivalTime + '\'' +
                ", dName='" + dName + '\'' +
                ", dRideDescription='" + dRideDescription + '\'' +
                ", dRideNumber='" + dRideNumber + '\'' +
                ", dFinishedRides='" + dFinishedRides + '\'' +
                ", driverReject='" + driverReject + '\'' +
                ", rideState='" + rideState + '\'' +
                ", fee='" + fee + '\'' +
                ", initRequest=" + initRequest +
                '}';
    }
}
