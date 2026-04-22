public class StandardParcel extends Parcel {

    private static final int RATE = 2;

    public StandardParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

    int getRate() {
        return RATE;
    }
}
