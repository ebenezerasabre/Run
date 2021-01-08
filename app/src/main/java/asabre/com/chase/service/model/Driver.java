package asabre.com.chase.service.model;

import com.google.gson.annotations.SerializedName;

public class Driver {
    @SerializedName("_id")
    private String _id;    // server
    @SerializedName("firstName")
    private String firstName = "";
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("password")
    private String password;

    @SerializedName("email")
    private String email;
    @SerializedName("userType")
    private String userType;             // driver
    @SerializedName("finishedRides")
    private String finishedRides = "";   // server
    @SerializedName("rating")
    private String rating;
    @SerializedName("isActive")
    private String isActive = "";          // 1 - 5, // server

    @SerializedName("carDescription")
    private String carDescription; // name
    @SerializedName("carNumber")
    private String carNumber;
    @SerializedName("profit")
    private String profit = "";     // server
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("updatedAt")
    private String updatedAt;

    public Driver() {
    }

    public Driver(String _id,
                  String firstName,
                  String lastName,
                  String phoneNumber,
                  String password,
                  String email,
                  String userType,
                  String finishedRides,
                  String rating,
                  String isActive,
                  String carDescription,
                  String carNumber,
                  String profit,
                  String createdAt,
                  String updatedAt) {
        this._id = _id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;

        this.userType = userType;
        this.finishedRides = finishedRides;
        this.rating = rating;
        this.isActive = isActive;
        this.carDescription = carDescription;

        this.carNumber = carNumber;
        this.profit = profit;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Driver(Driver driver){
        set_id(driver.get_id());
        setFirstName(driver.getFirstName());
        setLastName(driver.getLastName());
        setPhoneNumber(driver.getPhoneNumber());
        setEmail(driver.getEmail());

        setUserType(driver.getUserType());
        setFinishedRides(driver.getFinishedRides());
        setRating(driver.getRating());
        setIsActive(driver.getIsActive());
        setCarDescription(driver.getCarDescription());

        setCarNumber(driver.getCarNumber());
        setProfit(driver.getProfit());
        setCreatedAt(driver.getCreatedAt());
        setUpdatedAt(driver.getUpdatedAt());
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getFinishedRides() {
        return finishedRides;
    }

    public void setFinishedRides(String finishedRides) {
        this.finishedRides = finishedRides;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
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

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "_id='" + _id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", userType='" + userType + '\'' +
                ", finishedRides='" + finishedRides + '\'' +
                ", rating='" + rating + '\'' +
                ", isActive='" + isActive + '\'' +
                ", carDescription='" + carDescription + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", profit='" + profit + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
