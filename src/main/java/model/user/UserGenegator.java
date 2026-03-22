package model;

import random.Utils;

public class UserGenegator {

    public static User randomUser() {
    String name = Utils.randomString(10);
    String email = Utils.generateEmail();
    String password = Utils.generatePassword(12);
    return new User(email, password, name);
}
}
