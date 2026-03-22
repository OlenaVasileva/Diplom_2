package random;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    private static final char[] DOMAINS = {'g', 'm', 'y', 'h'};
    private static final String[] TOP_LEVEL_DOMAINS = {"com", "ru", "org"};
    private static final String PASSWORD_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";

    public static String randomString() {
            return randomString(10);
        }

        public static String randomString(int length) {
            Random random = new Random();
            int leftLimit = 97;
            int rightLimit = 122;
            StringBuilder buffer = new StringBuilder(length);

            for(int i = 0; i < length; ++i) {
                int randomLimitedInt = leftLimit + (int)(random.nextFloat() * (float)(rightLimit - leftLimit + 1));
                buffer.append(Character.toChars(randomLimitedInt));
            }

            return buffer.toString();
        }

    public static String generateEmail() {
        Random random = new Random();
        String username = randomString(8);           // Имя пользователя (случайная строка длиной 8 символов)
        char domainLetter = DOMAINS[random.nextInt(DOMAINS.length)]; // Случайная буква доменного имени (например, gmail.com, yahoo.net)
        String topLevelDomain = TOP_LEVEL_DOMAINS[random.nextInt(TOP_LEVEL_DOMAINS.length)];

        return username + "@" + domainLetter + "mail." + topLevelDomain;
    }
    public static String generatePassword(int length) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(PASSWORD_CHARS.length());
            sb.append(PASSWORD_CHARS.charAt(index));
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        System.out.println("Случайная строка: " + randomString());
        System.out.println("Случайный email: " + generateEmail());
        System.out.println("Случайный пароль: " + generatePassword(12));
    }
}


