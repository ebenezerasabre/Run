package asabre.com.chase.service.model;

import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("_id")
    private String _id;
    @SerializedName("driverId")
    private String driverId;
    @SerializedName("userId")
    private String userId;
    @SerializedName("msg")
    private String msg = "";
    @SerializedName("stars")
    private String stars;
    @SerializedName("date")
    private String date;

    public Review() {
    }

    public Review(String _id,
                  String driverId,
                  String userId,
                  String msg,
                  String stars,
                  String date) {
        this._id = _id;
        this.driverId = driverId;
        this.userId = userId;
        this.msg = msg;
        this.stars = stars;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Review{" +
                "_id='" + _id + '\'' +
                ", driverId='" + driverId + '\'' +
                ", userId='" + userId + '\'' +
                ", msg='" + msg + '\'' +
                ", stars='" + stars + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
