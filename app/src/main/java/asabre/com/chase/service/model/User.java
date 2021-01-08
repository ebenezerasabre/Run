package asabre.com.chase.service.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 110219951L;

    @SerializedName("_id")
    private String _id;
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
    private String userType;
    @SerializedName("finishedRides")
    private String finishedRides = "";   // server
    @SerializedName("rating")
    private String rating = "";          // 1 - 5, // server
    @SerializedName("isActive")
    private String isActive = "";          // on/off ie authorized  // server
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("updatedAt")
    private String updatedAt;

    public User() {
    }

    public User(String _id,
                String firstName,
                String lastName,
                String phoneNumber,
                String password,
                String email,
                String userType,
                String finishedRides,
                String rating,
                String isActive,
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
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public User(User user) {
        set_id(user.get_id());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setPhoneNumber(user.getPhoneNumber());
        setEmail(user.getEmail());

        setUserType(user.getUserType());
        setFinishedRides(user.getFinishedRides());
        setRating(user.getRating());
        setIsActive(user.getIsActive());
        setCreatedAt(user.getCreatedAt());

        setUpdatedAt(user.getUpdatedAt());
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
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
        return "User{" +
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
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
