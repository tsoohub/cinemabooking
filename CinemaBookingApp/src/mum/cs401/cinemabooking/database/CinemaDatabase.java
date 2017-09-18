package mum.cs401.cinemabooking.database;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mum.cs401.cinemabooking.entity.Account;
import mum.cs401.cinemabooking.entity.Admin;
import mum.cs401.cinemabooking.entity.Cashier;
import mum.cs401.cinemabooking.entity.Category;
import mum.cs401.cinemabooking.entity.Customer;
import mum.cs401.cinemabooking.entity.Hall;
import mum.cs401.cinemabooking.entity.Movie;
import mum.cs401.cinemabooking.entity.MovieCategories;
import mum.cs401.cinemabooking.entity.MoviePrice;
import mum.cs401.cinemabooking.entity.Order;
import mum.cs401.cinemabooking.entity.Person;
import mum.cs401.cinemabooking.entity.Seat;
import mum.cs401.cinemabooking.entity.ShowTime;
import mum.cs401.cinemabooking.entity.Ticket;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public class CinemaDatabase extends Database {

    private final String url;
    private final String username;
    private final String password;

    CinemaDatabase(String url, String username, String password) {
        super();
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    void connect() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(url, username, password);
    }

    private void checkConn() throws Exception {
        if (conn == null || conn.isClosed()) {
            connect();
        }
    }

    public String insertMovie(Movie movie) {
        String ret = null;
        final String sql = "INSERT INTO MOVIE VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        final String sql1 = "INSERT INTO MOVIEPRICE VALUES (?, ?, ?, ?, ?, ?)";
        final String sql2 = "INSERT INTO MOVIECATEGORIES VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        try {
            checkConn();
            ps = preparedStatement(sql, movie.getId(),
                    movie.getDirector(),
                    movie.getName(),
                    movie.getDuration(),
                    movie.getTrailerLink(),
                    movie.getRateType(),
                    movie.getActors(),
                    movie.getStartDate(),
                    movie.getEndDate());
            if (ps.executeUpdate() > 0) {
                ret = "success";
            }
            if (ret.equals("success")) {
                for (MoviePrice mp : movie.getMoviePriceList()) {
                    PreparedStatement ps1 = null;
                    try {
                        ps1 = preparedStatement(sql1, mp.getId(),
                                movie.getId(),
                                mp.getPrice(),
                                mp.getAgeType(),
                                mp.getMinAge(),
                                mp.getMaxAge());
                        if (ps1.executeUpdate() == 0) {
                            ret = "Failed to insert a movie price.";
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        close(ps1);
                    }
                }
            }
            if (ret.equals("success")) {
                for (MovieCategories mc : movie.getMovieCategoryList()) {
                    PreparedStatement ps2 = null;
                    try {
                        ps2 = preparedStatement(sql2, mc.getId(),
                                mc.getMovie().getId(),
                                mc.getCategory().getId());
                        if (ps2.executeUpdate() == 0) {
                            ret = "Failed to insert a movie categories.";
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        close(ps2);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret = e.getMessage();
        } finally {
            close(ps);
        }
        return ret;
    }

    public String insertOrder(Order order, Integer showTimeId, String firstName) {
        String ret = null;
        final String sql = "INSERT INTO ORDERS VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            checkConn();
            Customer customer = getCustomer(firstName);
            Integer customerId = null;
            if (customer != null) {
                customerId = customer.getId();
            }
            ps = preparedStatement(sql, order.getId(), showTimeId, customerId,
                    order.getStateDate(), order.getState(), order.getSecretCode(),
                    order.getSecretCodeExpireDate(), order.getPaymentType(),
                    order.getAmount());
            if (ps.executeUpdate() > 0) {
                ret = "success";
            }
            if (ret.equals("success")) {
                for (Ticket ticket : order.getTicketList()) {
                    ret = insertTicket(ticket, order.getId());
                    if (!ret.equals("success")) {
                        ret = "Failed to insert order's tickets";
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret = e.getMessage();
        } finally {
            close(ps);
        }
        return ret;
    }

    public String setOrder(Order order) {
        String ret = null;
        final String sql = "UPDATE ORDERS SET STATE = ? WHERE ID = ?";
        PreparedStatement ps = null;
        try {
            checkConn();
            ps = preparedStatement(sql, order.getState(), order.getId());
            if (ps.executeUpdate() > 0) {
                ret = "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret = e.getMessage();
        } finally {
            close(ps);
        }
        return ret;
    }

    public String insertShowTime(ShowTime showTime) {
        String ret = null;
        final String sql = "INSERT INTO SHOWTIME VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            checkConn();
            ps = preparedStatement(sql, showTime.getId(),
                    showTime.getHall().getId(),
                    showTime.getMovie().getId(),
                    showTime.getMovieTime());
            if (ps.executeUpdate() > 0) {
                ret = "success";
            }
        } catch (Exception e) {
            if (e.getMessage().contains("IDX_SHOWTIMEUNIQ")) {
                ret = "unique";
            } else {
                e.printStackTrace();
                ret = e.getMessage();
            }
        } finally {
            close(ps);
        }
        return ret;
    }

    public String insertTicket(Ticket ticket, Integer orderId) {
        String ret = null;
        final String sql = "INSERT INTO TICKET VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            checkConn();
            ps = preparedStatement(sql, ticket.getId(), orderId,
                    ticket.getAmount(), ticket.getSeatNumber());
            if (ps.executeUpdate() > 0) {
                ret = "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret = e.getMessage();
        } finally {
            close(ps);
        }
        return ret;
    }

    public List<MoviePrice> getMoviePriceList(Integer id) {
        List<MoviePrice> ret = null;
        final String sql = "SELECT * FROM MOVIEPRICE WHERE MOVIE_ID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            checkConn();
            ps = preparedStatement(sql, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (ret == null) {
                    ret = new ArrayList<>();
                }
                MoviePrice mp = new MoviePrice(rs.getInt("ID"),
                        rs.getDouble("PRICE"), rs.getString("AGETYPE"),
                        rs.getByte("AGEMIN"), rs.getByte("AGEMAX"));
                ret.add(mp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    public List<Category> getCategoryList() {
        List<Category> ret = null;
        final String sql = "SELECT * FROM CATEGORY";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Object> params = new ArrayList<>();
        try {
            checkConn();
            ps = preparedStatement(sql, params.toArray());
            rs = ps.executeQuery();
            while (rs.next()) {
                if (ret == null) {
                    ret = new ArrayList<>();
                }
                Category category = new Category(rs.getInt("ID"), rs.getString("NAME"));
                ret.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    public Category getCategory(Integer id) {
        Category ret = null;
        final String sql = "SELECT * FROM CATEGORY WHERE ID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            checkConn();
            ps = preparedStatement(sql, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                ret = new Category(rs.getInt("ID"), rs.getString("NAME"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    public Account getAccount(Integer id) {
        Account ret = null;
        final String sql = "SELECT * FROM ACCOUNT WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            checkConn();
            ps = preparedStatement(sql, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                ret = new Account(rs.getString("USERNAME"),
                        rs.getString("PASSWORD"),
                        new Date(rs.getLong("CREATEDATE")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    public Person getUser(String username1, String password1) {
        Person ret = null;
        final String sql = "SELECT * FROM ACCOUNT A LEFT JOIN USER B "
                + "ON A.CUSTOMER_ID = B.ID WHERE A.USERNAME = ? AND A.PASSWORD = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            checkConn();
            ps = preparedStatement(sql, username1, password1);
            rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt("TYPE") == 1) {
                    ret = new Admin(rs.getInt("ID"),
                            rs.getDouble("SALARY"),
                            rs.getString("PHONE"),
                            rs.getString("FIRSTNAME"),
                            rs.getString("LASTNAME"),
                            rs.getString("MIDDLENAME"),
                            rs.getString("EMAIL"));
                } else if (rs.getInt("TYPE") == 2) {
                    ret = new Cashier(rs.getInt("ID"),
                            rs.getDouble("SALARY"),
                            rs.getString("PHONE"),
                            rs.getString("FIRSTNAME"),
                            rs.getString("LASTNAME"),
                            rs.getString("MIDDLENAME"),
                            rs.getString("EMAIL"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    public Customer getCustomer(Integer orderId) {
        Customer ret = null;
        final String sql = "SELECT * FROM USER A LEFT JOIN ORDERS B "
                + "ON B.CUSTOMER_ID = A.ID WHERE B.ID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            checkConn();
            ps = preparedStatement(sql, orderId);
            rs = ps.executeQuery();
            if (rs.next()) {
                ret = new Customer(rs.getDate("BIRTHDATE"),
                        null,
                        rs.getDate("CREATEDATE"),
                        rs.getInt("ID"),
                        rs.getString("FIRSTNAME"),
                        rs.getString("LASTNAME"),
                        rs.getString("MIDDLENAME"),
                        rs.getString("EMAIL"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    public Customer getCustomer(String firstName) {
        Customer ret = null;
        final String sql = "SELECT * FROM USER WHERE FIRSTNAME = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            checkConn();
            ps = preparedStatement(sql, firstName);
            rs = ps.executeQuery();
            if (rs.next()) {
                ret = new Customer(rs.getDate("BIRTHDATE"),
                        null,
                        rs.getDate("CREATEDATE"),
                        rs.getInt("ID"),
                        rs.getString("FIRSTNAME"),
                        rs.getString("LASTNAME"),
                        rs.getString("MIDDLENAME"),
                        rs.getString("EMAIL"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    public List<Integer> getUnavialableSeatNumberList(ShowTime showtime) {
        List<Integer> ret = null;
        final String sql = "SELECT SEAT_NUMBER FROM TICKET A RIGHT JOIN ORDERS B "
                + "ON A.ORDERS_ID = B.ID WHERE B.SHOWTIME_ID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            checkConn();
            ps = preparedStatement(sql, showtime.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                if (ret == null) {
                    ret = new ArrayList<>();
                }
                ret.add(rs.getInt("SEAT_NUMBER"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    public List<MovieCategories> getMovieCategoriesList(Movie movie) {
        List<MovieCategories> ret = null;
        final String sql = "SELECT * FROM MOVIECATEGORIES WHERE MOVIE_ID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            checkConn();
            ps = preparedStatement(sql, movie.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                if (ret == null) {
                    ret = new ArrayList<>();
                }
                ret.add(new MovieCategories(movie, getCategory(rs.getInt("CATEGORY_ID"))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    public List<Seat> getSeatList(Integer id) {
        List<Seat> ret = null;
        final String sql = "SELECT * FROM SEAT WHERE HALL_ID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            checkConn();
            ps = preparedStatement(sql, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (ret == null) {
                    ret = new ArrayList<>();
                }
                ret.add(new Seat(rs.getInt("ID"), rs.getInt("NUMBER")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    public List<Ticket> getTicketList(Integer id) {
        List<Ticket> ret = null;
        final String sql = "SELECT * FROM TICKET WHERE ORDERS_ID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            checkConn();
            ps = preparedStatement(sql, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (ret == null) {
                    ret = new ArrayList<>();
                }
                ret.add(new Ticket(rs.getInt("ID"), rs.getDouble("AMOUNT"),
                        rs.getInt("SEAT_NUMBER")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    public Order getOrder(String code) {
        Order ret = null;
        final String sql = "SELECT * FROM ORDERS WHERE SECRETCODE = ? "
                + "AND SECRETCODEEXPIREDATE > NOW() AND STATE = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            checkConn();
            ps = preparedStatement(sql, code, 0);
            rs = ps.executeQuery();
            if (rs.next()) {
                ret = new Order(rs.getInt("ID"),
                        rs.getDate("SECRETCODEEXPIREDATE"),
                        getTicketList(rs.getInt("ID")),
                        rs.getDate("STATEDATE"),
                        rs.getByte("STATE"),
                        rs.getString("PAYMENTTYPE"),
                        rs.getDouble("AMOUNT"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    public Hall getHall(Integer id) {
        Hall ret = null;
        final String sql = "SELECT * FROM HALL WHERE ID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            checkConn();
            ps = preparedStatement(sql, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                ret = new Hall(rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getInt("DOORNUMBER"),
                        rs.getInt("CAPACITY"),
                        getSeatList(rs.getInt("ID")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    public Movie getMovie(Integer id) {
        Movie ret = null;
        final String sql = "SELECT * FROM MOVIE WHERE ID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            checkConn();
            ps = preparedStatement(sql, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                ret = new Movie(rs.getInt("ID"),
                        rs.getString("DIRECTOR"),
                        rs.getString("NAME"),
                        rs.getInt("DURATION"),
                        rs.getString("TRAILERLINK"),
                        rs.getString("RATETYPE"),
                        rs.getString("ACTORS"),
                        new Date(rs.getLong("STARTDATE")),
                        new Date(rs.getLong("ENDDATE")));
                List<MovieCategories> movieCategories = getMovieCategoriesList(ret);
                for (MovieCategories mc : movieCategories) {
                    ret.getMovieCategoryList().add(mc);
                }
                List<MoviePrice> moviePrices = getMoviePriceList(ret.getId());
                for (MoviePrice mp : moviePrices) {
                    ret.getMoviePriceList().add(mp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    public List<ShowTime> getShowTime(Movie movie) {
        List<ShowTime> ret = null;
        if (movie == null) {
            return ret;
        }
        final String sql = "SELECT * FROM SHOWTIME A RIGHT JOIN MOVIE B "
                + "ON B.ID = A.MOVIE_ID "
                + "WHERE B.STARTDATE <= NOW() "
                + "AND B.ENDDATE > NOW()"
                + "AND B.ID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            checkConn();
            ps = preparedStatement(sql, movie.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                if (ret == null) {
                    ret = new ArrayList<>();
                }
                ShowTime showTime = new ShowTime(rs.getInt("ID"),
                        getHall(rs.getInt("HALL_ID")), movie, rs.getInt("MOVIETIME"));
                ret.add(showTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    public ShowTime getShowTime(Order order) {
        ShowTime ret = null;
        final String sql = "SELECT * FROM SHOWTIME A RIGHT JOIN ORDERS B "
                + "ON A.ID = B.SHOWTIME_ID "
                + "WHERE B.ID = ? ";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            checkConn();
            ps = preparedStatement(sql, order.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                ret = new ShowTime(rs.getInt("ID"),
                        getHall(rs.getInt("HALL_ID")),
                        getMovie(rs.getInt("MOVIE_ID")),
                        rs.getInt("MOVIETIME"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    public List<Hall> getHallList() {
        List<Hall> ret = null;
        final String sql = "SELECT * FROM HALL";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Object> params = new ArrayList<>();
        try {
            checkConn();
            ps = preparedStatement(sql, params.toArray());
            rs = ps.executeQuery();
            while (rs.next()) {
                if (ret == null) {
                    ret = new ArrayList<>();
                }
                ret.add(new Hall(rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getInt("DOORNUMBER"),
                        rs.getInt("CAPACITY"),
                        getSeatList(rs.getInt("ID"))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }

    public List<Movie> getMovieList() {
        List<Movie> ret = null;
        final String sql = "SELECT * FROM MOVIE";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Object> params = new ArrayList<>();
        try {
            checkConn();
            ps = preparedStatement(sql, params.toArray());
            rs = ps.executeQuery();
            while (rs.next()) {
                if (ret == null) {
                    ret = new ArrayList<>();
                }
                Movie mv = new Movie(rs.getInt("ID"),
                        rs.getString("DIRECTOR"),
                        rs.getString("NAME"),
                        rs.getInt("DURATION"),
                        rs.getString("TRAILERLINK"),
                        rs.getString("RATETYPE"),
                        rs.getString("ACTORS"),
                        new Date(rs.getLong("STARTDATE")),
                        new Date(rs.getLong("ENDDATE")));
                List<MovieCategories> movieCategories = getMovieCategoriesList(mv);
                for (MovieCategories mc : movieCategories) {
                    mv.getMovieCategoryList().add(mc);
                }
                List<MoviePrice> moviePrices = getMoviePriceList(mv.getId());
                for (MoviePrice mp : moviePrices) {
                    mv.getMoviePriceList().add(mp);
                }
                ret.add(mv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(rs);
        }
        return ret;
    }
}
