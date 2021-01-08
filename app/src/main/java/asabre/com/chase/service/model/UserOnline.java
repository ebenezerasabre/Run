package asabre.com.chase.service.model;

public class UserOnline {
    private String userId;
    private String userType;
    private String socketId;
    private String city;
    private String lat;
    private String lng;

    public UserOnline() {
    }

    public UserOnline(String userId,
                      String userType,
                      String socketId,
                      String city,
                      String lat,
                      String lng) {
        this.userId = userId;
        this.userType = userType;
        this.socketId = socketId;
        this.city = city;
        this.lat = lat;
        this.lng = lng;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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


    @Override
    public String toString() {
        return "UserOnline{" +
                "userId='" + userId + '\'' +
                ", userType='" + userType + '\'' +
                ", socketId='" + socketId + '\'' +
                ", city='" + city + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                '}';
    }
}
