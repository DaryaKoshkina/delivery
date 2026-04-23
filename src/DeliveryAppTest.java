import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryAppTest {

    private final String description = "Посылка";
    private int weight = 100;
    private int sendDay = 30;
    private final String deliveryAddress = "Адрес";

    @Test
    @DisplayName("Цена доставки считается корректно для стандартной посылки")
    public void shouldBePositiveStandardParcelWeight() {
        weight = 100;
        StandardParcel standardParcel = new StandardParcel(description, weight, deliveryAddress, sendDay);
        assertEquals(standardParcel.getRate() * 100, standardParcel.calculateDeliveryCost());
    }

    @Test
    @DisplayName("Цена доставки считается некорректно для стандартной посылки")
    public void shouldBeNegativeStandardParcelWeight() {
        weight = 100;
        StandardParcel standardParcel = new StandardParcel(description, weight, deliveryAddress, sendDay);
        assertNotEquals(300, standardParcel.calculateDeliveryCost());
    }

    @Test
    @DisplayName("Цена доставки считается корректно для стандартной доставки весом 0")
    public void shouldBeNegativeStandardParcelWithZeroWeight() {
        weight = 0;
        StandardParcel standardParcel = new StandardParcel(description, weight, deliveryAddress, sendDay);
        assertEquals(standardParcel.getRate(), standardParcel.calculateDeliveryCost());
    }

    @Test
    @DisplayName("Цена доставки считается корректно для скоропортящейся посылки")
    public void shouldBePositiveFragileParcelWeight() {
        weight = 100;
        FragileParcel fragileParcel = new FragileParcel(description, weight, deliveryAddress, sendDay);
        assertEquals(fragileParcel.getRate() * 100, fragileParcel.calculateDeliveryCost());
    }

    @Test
    @DisplayName("Цена доставки считается некорректно для скоропортящейся посылки")
    public void shouldBeNegativeFragileParcelWeight() {
        weight = 100;
        FragileParcel fragileParcel = new FragileParcel(description, weight, deliveryAddress, sendDay);
        assertNotEquals(300, fragileParcel.calculateDeliveryCost());
    }

    @Test
    @DisplayName("Цена доставки считается корректно для скоропортящейся доставки весом 0")
    public void shouldBeNegativeFragileParcelWithZeroWeight() {
        weight = 0;
        FragileParcel fragileParcel = new FragileParcel(description, weight, deliveryAddress, sendDay);
        assertEquals(fragileParcel.getRate(), fragileParcel.calculateDeliveryCost());
    }

    @Test
    @DisplayName("Цена доставки считается корректно для хрупкой  посылки")
    public void shouldBePositivePerishableParcelWeight() {
        weight = 100;
        PerishableParcel perishableParcel = new PerishableParcel(description, weight, deliveryAddress, sendDay, 10);
        assertEquals(perishableParcel.getRate() * 100, perishableParcel.calculateDeliveryCost());
    }

    @Test
    @DisplayName("Цена доставки считается некорректно для хрупкой посылки")
    public void shouldBeNegativePerishableParcelWeight() {
        weight = 100;
        PerishableParcel perishableParcel = new PerishableParcel(description, weight, deliveryAddress, sendDay, 10);
        assertNotEquals(500, perishableParcel.calculateDeliveryCost());
    }

    @Test
    @DisplayName("Цена доставки считается корректно для хрупкой доставки весом 0")
    public void shouldBeNegativePerishableParcelWithZeroWeight() {
        weight = 0;
        PerishableParcel perishableParcel = new PerishableParcel(description, weight, deliveryAddress, sendDay, 10);
        assertEquals(perishableParcel.getRate(), perishableParcel.calculateDeliveryCost());
    }

    @Test
    @DisplayName("Посылка просрочена, если количество дней с момента отправки больше, чем срок хранения")
    void isExpiredShouldReturnTrueForPastDate() {
        sendDay = 10;
        PerishableParcel perishableParcel = new PerishableParcel(description, weight, deliveryAddress, sendDay, 1);
        assertTrue(perishableParcel.isExpired());
    }

    @Test
    @DisplayName("Посылка не просрочена, если количество дней с момента отправки меньше, чем срок хранения")
    void isExpiredShouldReturnFalseForFutureDate() {
        sendDay = 1;
        PerishableParcel perishableParcel = new PerishableParcel(description, weight, deliveryAddress, sendDay, 10);
        assertFalse(perishableParcel.isExpired());
    }

    @Test
    @DisplayName("Посылка не просрочена, если количество дней с момента отправки меньше и срок хранения равны")
    void isExpiredShouldReturnFalseForEqualDate() {
        sendDay = 1;
        PerishableParcel perishableParcel = new PerishableParcel(description, weight, deliveryAddress, sendDay, 1);
        assertFalse(perishableParcel.isExpired());
    }

    @Test
    @DisplayName("Посылка должна увеличивать текущий вес и добавляться в список")
    void shouldIncreaseWeightWhenParcelAdded() {
        weight = 100;
        ParcelBox<StandardParcel> standardBox = new ParcelBox<>(1000);
        StandardParcel standardParcel = new StandardParcel(description, weight, deliveryAddress, sendDay);
        standardBox.addParcel(standardParcel);
        assertEquals(100, standardBox.getCurrentWeight(), "Вес должен стать 100");
        assertEquals(1, standardBox.getParcels().size(), "В списке должна быть 1 посылка");
    }

    @Test
    @DisplayName("Посылка не должна добавляться, если лимит превышен")
    void shouldNotAddParcelIfWeightExceedsLimit() {
        weight = 1000;
        ParcelBox<StandardParcel> standardBox = new ParcelBox<>(100);
        StandardParcel standardParcel = new StandardParcel(description, weight, deliveryAddress, sendDay);
        standardBox.addParcel(standardParcel);
        assertEquals(0, standardBox.getCurrentWeight(), "Вес должен остаться 0");
        assertTrue(standardBox.getParcels().isEmpty(), "Список должен быть пустым");
    }

    @Test
    @DisplayName("Граничный случай: посылка весом ровно в лимит")
    void shouldAddParcelExactlyAtLimit() {
        weight = 100;
        ParcelBox<StandardParcel> standardBox = new ParcelBox<>(100);
        StandardParcel standardParcel = new StandardParcel(description, weight, deliveryAddress, sendDay);
        standardBox.addParcel(standardParcel);
        assertEquals(100, standardBox.getCurrentWeight(), "Граничный случай: посылка весом ровно в лимит");
        assertEquals(1, standardBox.getParcels().size(), "В списке должна быть 1 посылка");
    }

}