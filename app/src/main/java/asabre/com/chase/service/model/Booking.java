package asabre.com.chase.service.model;

import com.google.gson.annotations.SerializedName;

public class Booking {
    @SerializedName("_id")
    private String _id;
    @SerializedName("customerId")
    private String customerId;
    @SerializedName("carType")
    private String carType;
    @SerializedName("date")
    private String date;
    @SerializedName("time")
    private String time;

    @SerializedName("pickUp")
    private String pickUp;
    @SerializedName("dropOff")
    private String dropOff;
    @SerializedName("bookUpdate")
    private String bookUpdate;
    @SerializedName("driverId")
    private String driverId;
    @SerializedName("driverName")
    private String driverName;


    public Booking(String _id,
                   String customerId,
                   String carType,
                   String date,
                   String time,
                   String pickUp,
                   String dropOff,
                   String bookUpdate,
                   String driverId,
                   String driverName) {
        this._id = _id;
        this.customerId = customerId;
        this.carType = carType;
        this.date = date;
        this.time = time;
        this.pickUp = pickUp;
        this.dropOff = dropOff;
        this.bookUpdate = bookUpdate;
        this.driverId = driverId;
        this.driverName = driverName;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPickUp() {
        return pickUp;
    }

    public void setPickUp(String pickUp) {
        this.pickUp = pickUp;
    }

    public String getDropOff() {
        return dropOff;
    }

    public void setDropOff(String dropOff) {
        this.dropOff = dropOff;
    }

    public String getBookUpdate() {
        return bookUpdate;
    }

    public void setBookUpdate(String bookUpdate) {
        this.bookUpdate = bookUpdate;
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

    @Override
    public String toString() {
        return "Booking{" +
                "_id='" + _id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", carType='" + carType + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", pickUp='" + pickUp + '\'' +
                ", dropOff='" + dropOff + '\'' +
                ", bookUpdate='" + bookUpdate + '\'' +
                ", driverId='" + driverId + '\'' +
                ", driverName='" + driverName + '\'' +
                '}';
    }
}
