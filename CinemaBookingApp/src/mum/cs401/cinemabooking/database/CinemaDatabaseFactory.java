package mum.cs401.cinemabooking.database;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public final class CinemaDatabaseFactory {

    private static final CinemaDatabase cd = new CinemaDatabase("jdbc:mysql://localhost/cinema", "eegii", "Eegii_123");

    private CinemaDatabaseFactory() {
    }

    public static CinemaDatabase getInstanceDB() {
        return cd;
    }
}
