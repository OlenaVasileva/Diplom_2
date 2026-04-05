package model.user;


import com.github.javafaker.Faker;

public class UserGenerator {

    public static User createRandom() {
        Faker faker = new Faker();

        User user = User.builder()
                .name(faker.name().name())
                .password(faker.internet().password(6, 20))
                .email(faker.internet().safeEmailAddress())
                .build();

        System.out.println(user.getName());
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        return user;
    }
}