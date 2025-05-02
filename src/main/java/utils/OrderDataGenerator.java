package utils;

import model.Order;
import net.datafaker.Faker;

public class OrderDataGenerator {
    private static final Faker faker = new Faker();

    public static Order generateRandomOrder(String[] colors) {
        return new Order(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.address().fullAddress(),
                faker.number().digits(2),
                faker.phoneNumber().phoneNumber(),
                faker.number().numberBetween(1, 10),
                "2024-05-01",
                faker.lorem().sentence(),
                colors
        );
    }
}