package mum.cs401.cinemabooking.common;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public class RandomNumber {

    public static Integer generateId() {
        Long longId = System.currentTimeMillis();
        String temp = String.valueOf(longId);
        temp = temp.substring(3, 12);
        try {
            Thread.sleep(50);
        } catch (Exception e) {
        }
        return Integer.valueOf(temp);
    }

    public static String generateSecretCode() {
        Long longId = System.currentTimeMillis();
        String temp = String.valueOf(longId);
        temp = temp.substring(6, 12);
        return temp;
    }

    public static void main(String[] args) {
        System.out.println(RandomNumber.generateSecretCode());
        System.out.println(RandomNumber.generateId());
        System.out.println(Integer.MAX_VALUE);
    }
}
