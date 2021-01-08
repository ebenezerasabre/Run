package asabre.com.chase.service.model;

// history record is created when ride is finished
// rideAccept and ride request must be delete accordingly

import com.google.gson.annotations.SerializedName;

public class History {
    @SerializedName("_id")
    private String _id;
    @SerializedName("driverId")
    private String driverId;
    @SerializedName("driverName")
    private String driverName;
    @SerializedName("userId")
    private String userId;                  // server
    @SerializedName("userName")
    private String userName;
    @SerializedName("city")
    private String city;                   // server
    @SerializedName("entryPoint")
    private String entryPoint;       // current coordinates          // server
    @SerializedName("exitPoint")
    private String exitPoint;        // destination coordinates       // server
    @SerializedName("status")
    private String status = "";      //  finished by default
    @SerializedName("fee")
    private String fee;         // money to be paid                 // server
    @SerializedName("date")
    private String date;

    public History() {
    }

    public History(String _id,
                   String driverId,
                   String driverName,
                   String userId,
                   String userName,
                   String city,
                   String entryPoint,
                   String exitPoint,
                   String status,
                   String fee,
                   String date) {
        this._id = _id;
        this.driverId = driverId;
        this.driverName = driverName;
        this.userId = userId;
        this.userName = userName;
        this.city = city;
        this.entryPoint = entryPoint;
        this.exitPoint = exitPoint;
        this.status = status;
        this.fee = fee;
        this.date = date;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "History{" +
                "_id='" + _id + '\'' +
                ", driverId='" + driverId + '\'' +
                ", driverName='" + driverName + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", city='" + city + '\'' +
                ", entryPoint='" + entryPoint + '\'' +
                ", exitPoint='" + exitPoint + '\'' +
                ", status='" + status + '\'' +
                ", fee='" + fee + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
