package utils;

import model.Courier;
import net.datafaker.Faker;

public class CourierDataGenerator {
    private static final Faker faker = new Faker();

    public static Courier generateRandomCourier() {
        return new Courier(
                generateRandomLogin(),
                generateRandomPassword(),
                faker.name().firstName()
        );
    }

    public static String generateRandomLogin() {
        return faker.name().username() + System.currentTimeMillis();
    }

    public static String generateRandomPassword() {
        return faker.internet().password(6, 12);
    }
}