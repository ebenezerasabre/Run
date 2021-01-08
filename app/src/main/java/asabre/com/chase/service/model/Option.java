package asabre.com.chase.service.model;

import java.io.Serializable;

// this is the option to choose rideType
// eg car, cycle, bike
public class Option implements Serializable {
    private static final long serialVersionUID = 110219951L;

    private String pic;
    private String rideType;
    private String rideTime;
    private String ridePrice;
    private String ridePreviousPrice;
    private String discount;

    public Option() {
    }

    public Option(String pic,
                  String rideType,
                  String rideTime,
                  String ridePrice,
                  String ridePreviousPrice,
                  String discount) {
        this.pic = pic;
        this.rideType = rideType;
        this.rideTime = rideTime;
        this.ridePrice = ridePrice;
        this.ridePreviousPrice = ridePreviousPrice;
        this.discount = discount;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getRideType() {
        return rideType;
    }

    public void setRideType(String rideType) {
        this.rideType = rideType;
    }

    public String getRideTime() {
        return rideTime;
    }

    public void setRideTime(String rideTime) {
        this.rideTime = rideTime;
    }

    public String getRidePrice() {
        return ridePrice;
    }

    public void setRidePrice(String ridePrice) {
        this.ridePrice = ridePrice;
    }

    public String getRidePreviousPrice() {
        return ridePreviousPrice;
    }

    public void setRidePreviousPrice(String ridePreviousPrice) {
        this.ridePreviousPrice = ridePreviousPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }


    @Override
    public String toString() {
        return "Option{" +
                "pic='" + pic + '\'' +
                ", rideType='" + rideType + '\'' +
                ", rideTime='" + rideTime + '\'' +
                ", ridePrice='" + ridePrice + '\'' +
                ", ridePreviousPrice='" + ridePreviousPrice + '\'' +
                ", discount='" + discount + '\'' +
                '}';
    }
}
