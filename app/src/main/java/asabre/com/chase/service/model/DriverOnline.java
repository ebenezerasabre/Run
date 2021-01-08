package asabre.com.chase.service.model;

public class DriverOnline {
    private String driverId;
    private String userType;
    private String socketId;
    private String city;
    private String lat;
    private String lng;
    private String rideType;

    public DriverOnline() {
    }

    public DriverOnline(String driverId,
                        String userType,
                        String socketId,
                        String city,
                        String lat,
                        String lng,
                        String rideType) {
        this.driverId = driverId;
        this.userType = userType;
        this.socketId = socketId;
        this.city = city;
        this.lat = lat;
        this.lng = lng;
        this.rideType = rideType;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
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

    public String getRideType() {
        return rideType;
    }

    public void setRideType(String rideType) {
        this.rideType = rideType;
    }

    @Override
    public String toString() {
        return "DriverOnline{" +
                "driverId='" + driverId + '\'' +
                ", userType='" + userType + '\'' +
                ", socketId='" + socketId + '\'' +
                ", city='" + city + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", rideType='" + rideType + '\'' +
                '}';
    }
}
