package model;

// Represents a public class with static methods for security purposes
public class Security {

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: String is scrambled into a random integer
    public static int hashFunction(String s) {
        int hash = 6047;
        for (int i = 0; i < s.length(); i++) {
            hash = hash * 17 + s.charAt(i);
        }
        return hash;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: creates a random string of length 5
    public static String salt() {
        String alphanumeric = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            sb.append(alphanumeric.charAt((int) (alphanumeric.length() * Math.random())));
        }
        return sb.toString();
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: A countdown is initiated in seconds from given integer time. Prints each second.
    public static void countdownTimer() {
        int time = 5;
        while (time > 0) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("Exception caught");
            }
            System.out.println(time + "...");
            time--;
        }
    }

}
