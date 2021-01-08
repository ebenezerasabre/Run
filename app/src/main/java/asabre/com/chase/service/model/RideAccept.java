package asabre.com.chase.service.model;

import com.google.gson.annotations.SerializedName;

/**
 * Only drivers in users city can accept rides
 * Status can assume three forms
 *  1. When picking up user ie. picking
 *  2. When ride has started    ie. started
 *  3. When ride has finished    ie. finished
 *
 *  When user accepts ride, he will see driver details
 *  from the RideAccept object
 */
public class RideAccept {
    @SerializedName("_id")
    private String _id;
    @SerializedName("rideRequestId")
    private String rideRequestId = ""; // id of RideRequest
    @SerializedName("driverId")
    private String driverId;
    @SerializedName("driverName")
    private String driverName;
    @SerializedName("driverPoint")
    private String driverPoint;
    @SerializedName("carDescription")
    private String carDescription;
    @SerializedName("carNumber")
    private String carNumber;
    @SerializedName("finishedRides")
    private String finishedRides;           // server
    @SerializedName("driverRating")
    private String driverRating;            // server
    @SerializedName("userId")
    private String userId;                  // server
    @SerializedName("userName")
    private String userName;           // server
    @SerializedName("userPhoneNumber")
    private String userPhoneNumber;         // server
    @SerializedName("city")
    private String city;                   // server
    @SerializedName("entryPoint")
    private String entryPoint;       // current coordinates          // server
    @SerializedName("exitPoint")
    private String exitPoint;        // destination coordinates       // server
    @SerializedName("status")
    private String status;      // picking / started / finished
    @SerializedName("fee")
    private String fee;         // money to be paid                 // server


    public RideAccept() {
    }

    public RideAccept(String _id,
                      String rideRequestId,
                      String driverId,
                      String driverName,
                      String driverPoint,
                      String carDescription,
                      String carNumber,
                      String finishedRides,
                      String driverRating,
                      String userId,
                      String userName,
                      String userPhoneNumber,
                      String city,
                      String entryPoint,
                      String exitPoint,
                      String status,
                      String fee) {
        this._id = _id;
        this.rideRequestId = rideRequestId;
        this.driverId = driverId;
        this.driverName = driverName;
        this.driverPoint = driverPoint;
        this.carDescription = carDescription;
        this.carNumber = carNumber;
        this.finishedRides = finishedRides;
        this.driverRating = driverRating;
        this.userId = userId;
        this.userName = userName;
        this.userPhoneNumber = userPhoneNumber;
        this.city = city;
        this.entryPoint = entryPoint;
        this.exitPoint = exitPoint;
        this.status = status;
        this.fee = fee;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getRideRequestId() {
        return rideRequestId;
    }

    public void setRideRequestId(String rideRequestId) {
        this.rideRequestId = rideRequestId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPoint() {
        return driverPoint;
    }

    public void setDriverPoint(String driverPoint) {
        this.driverPoint = driverPoint;
    }

    public String getCarDescription() {
        return carDescription;
    }

    public void setCarDescription(String carDescription) {
        this.carDescription = carDescription;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getFinishedRides() {
        return finishedRides;
    }

    public void setFinishedRides(String finishedRides) {
        this.finishedRides = finishedRides;
    }

    public String getDriverRating() {
        return driverRating;
    }

    public void setDriverRating(String driverRating) {
        this.driverRating = driverRating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "RideAccept{" +
                "_id='" + _id + '\'' +
                ", rideRequestId='" + rideRequestId + '\'' +
                ", driverId='" + driverId + '\'' +
                ", driverName='" + driverName + '\'' +
                ", driverPoint='" + driverPoint + '\'' +
                ", carDescription='" + carDescription + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", finishedRides='" + finishedRides + '\'' +
                ", driverRating='" + driverRating + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userPhoneNumber='" + userPhoneNumber + '\'' +
                ", city='" + city + '\'' +
                ", entryPoint='" + entryPoint + '\'' +
                ", exitPoint='" + exitPoint + '\'' +
                ", status='" + status + '\'' +
                ", fee='" + fee + '\'' +
                '}';
    }
}
