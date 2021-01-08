package asabre.com.chase.service.model;

import com.google.gson.annotations.SerializedName;

public class Help {
    @SerializedName("_id")
    private String _id;
    @SerializedName("userId")
    private String userId;
    @SerializedName("msg")
    private String msg = "";
    @SerializedName("userFirstName")
    private String userFirstName;
    @SerializedName("userPhoneNumber")
    private String userPhoneNumber;
    @SerializedName("date")
    private String date;

    public Help() {
    }

    public Help(String _id, String userId, String msg, String userFirstName, String userPhoneNumber, String date) {
        this._id = _id;
        this.userId = userId;
        this.msg = msg;
        this.userFirstName = userFirstName;
        this.userPhoneNumber = userPhoneNumber;
        this.date = date;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Help{" +
                "_id='" + _id + '\'' +
                ", userId='" + userId + '\'' +
                ", msg='" + msg + '\'' +
                ", userFirstName='" + userFirstName + '\'' +
                ", userPhoneNumber='" + userPhoneNumber + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
