public class PerishableParcel extends Parcel {

    private static final int RATE = 3;

    private final int timeToLive;

    public PerishableParcel(String description, int weight, String deliveryAddress, int sendDay, int timeToLive) {
        super(description, weight, deliveryAddress, sendDay);
        this.timeToLive = timeToLive;
    }

    public boolean isExpired(){
        return (getSendDay() > timeToLive);
    }

    int getRate() {
        return RATE;
    }

}
