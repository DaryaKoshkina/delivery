import java.util.ArrayList;
import java.util.List;

public class ParcelBox <T extends Parcel> {
    private final int maxWeight;
    private int currentWeight = 0;
    private final List<T> parcels = new ArrayList<>(); // Список для хранения посылок

    public ParcelBox(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void addParcel(T parcel) {
        if (currentWeight + parcel.getWeight() <= maxWeight) {
            parcels.add(parcel);
            currentWeight += parcel.getWeight();
            System.out.println("Посылка добавлена в коробку. Текущий вес всей коробки: " + currentWeight);
        } else {
            System.out.println("Ошибка: Превышен лимит веса коробки!");
        }
    }

    public void getAllParcels(){
        System.out.println("В коробке находятся посылки:");
        for (T parcel : parcels) {
            System.out.println(parcel.getDescription());
        }
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public List<T> getParcels() {
        return parcels;
    }
}
