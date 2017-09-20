package mum.cs401.cinemabooking.window;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mum.cs401.cinemabooking.database.CinemaDatabaseFactory;
import mum.cs401.cinemabooking.entity.Customer;
import mum.cs401.cinemabooking.entity.Movie;
import mum.cs401.cinemabooking.entity.Order;
import mum.cs401.cinemabooking.entity.ShowTime;
import mum.cs401.cinemabooking.entity.Ticket;
import mum.cs401.cinemabooking.rules.InputRules;
import mum.cs401.cinemabooking.rules.RuleException;
import mum.cs401.cinemabooking.rules.RuleSetFactory;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public class TicketWindow extends Stage {

    private final static TicketWindow instance = new TicketWindow(PrimaryWindow.WINDOW_TITLE);
    private static final String CSS_PATH = "/resource/style_ticketwindow.css";
    private final Text actionInfo = new Text();

    private ObservableList<Movie> movieList;
    private ObservableList<String> paymentTypeList = FXCollections.observableArrayList(
            "Visa card",
            "Debit card",
            "Cash",
            "Check"
    );
    private ObservableList<ShowTime> timeList;
    private final TextField tfAdultSeat = new TextField();
    private final TextField tfChildSeat = new TextField();
    private final TextField tfFirstname = new TextField();
    private final ComboBox tfTime = new ComboBox();
    private final ComboBox tfMovie = new ComboBox();
    private final ComboBox tfPaymentType = new ComboBox(paymentTypeList);
    private Order order;

    public static TicketWindow getInstance() {
        return instance;
    }

    private TicketWindow(String title) {
        //
        final double gridVgap = 10;
        final double gridHgap = 10;
        // labels
        final Label lblMovie = new Label("Movie:");
        final Label lblTime = new Label("Time:");
        final Label lblAdultSeat = new Label("Adult seat:");
        final Label lblChildSeat = new Label("Child seat:");
        final Label lblCustomerFname = new Label("Firstname:");
        final Label lblPaymentType = new Label("Payment type:");

        // combobox                        
        movieList = FXCollections.observableArrayList(CinemaDatabaseFactory.getInstanceDB().getMovieList());
        tfMovie.setItems(movieList);
        tfMovie.getSelectionModel().selectFirst();
        tfMovie.valueProperty().addListener(new MoviesListener());
        Movie movie = (Movie) tfMovie.getSelectionModel().getSelectedItem();
        List<ShowTime> showTime = CinemaDatabaseFactory.getInstanceDB().getShowTime(movie);
        if (showTime == null) {
            timeList = FXCollections.observableArrayList();
        } else {
            timeList = FXCollections.observableArrayList(showTime);
        }
        tfTime.setItems(timeList);
        tfTime.getSelectionModel().selectFirst();
        tfPaymentType.getSelectionModel().selectFirst();

        // buttons
        final Button btnSell = new Button("Sell");
        btnSell.setOnAction(new BtnSellEventHandler());
        final Button btnCalculate = new Button("Calculate");
        btnCalculate.setOnAction(new BtnCalculateEventHandler());

        // action info
        actionInfo.setId("actionInfo");
        actionInfo.setFill(Color.FIREBRICK);

        // container
        final GridPane gridFirst = new GridPane();
        gridFirst.setAlignment(Pos.CENTER);
        gridFirst.setVgap(gridVgap);
        gridFirst.setHgap(gridHgap);
        gridFirst.add(lblMovie, 0, 0);
        gridFirst.add(tfMovie, 1, 0);
        gridFirst.add(lblTime, 0, 1);
        gridFirst.add(tfTime, 1, 1);
        gridFirst.add(lblAdultSeat, 0, 2);
        gridFirst.add(tfAdultSeat, 1, 2);
        gridFirst.add(lblChildSeat, 0, 3);
        gridFirst.add(tfChildSeat, 1, 3);
        gridFirst.add(lblCustomerFname, 0, 5);
        gridFirst.add(tfFirstname, 1, 5);
        gridFirst.add(lblPaymentType, 0, 6);
        gridFirst.add(tfPaymentType, 1, 6);

        GridPane gridSecond = new GridPane();
        gridSecond.setVgap(gridVgap);
        gridSecond.setHgap(gridHgap);
        gridSecond.setAlignment(Pos.CENTER);
        gridSecond.add(btnSell, 0, 0);
        gridSecond.add(btnCalculate, 1, 0);

        GridPane gridThird = new GridPane();
        gridThird.setVgap(gridVgap);
        gridThird.setHgap(gridHgap);
        gridThird.setAlignment(Pos.CENTER);
        gridThird.add(actionInfo, 0, 0);

        GridPane gridMain = new GridPane();
        gridMain.setVgap(gridVgap);
        gridMain.setHgap(gridHgap);
        gridMain.setAlignment(Pos.CENTER);
        gridMain.add(gridFirst, 0, 0);
        gridMain.add(gridSecond, 0, 1);
        gridMain.add(gridThird, 0, 2);

        //window
        Scene scene = new Scene(gridMain, 300, 350);
        setScene(scene);
        scene.getStylesheets().addAll(this.getClass().getResource(CSS_PATH).toExternalForm());
        setTitle(title);
        this.setResizable(false);
    }

    public void initiate() {
        actionInfo.setText("");
        movieList = FXCollections.observableArrayList(CinemaDatabaseFactory.getInstanceDB().getMovieList());
        tfMovie.setItems(movieList);
        tfMovie.getSelectionModel().selectFirst();
        Movie movie = (Movie) tfMovie.getSelectionModel().getSelectedItem();
        List<ShowTime> showTime = CinemaDatabaseFactory.getInstanceDB().getShowTime(movie);
        if (showTime == null) {
            timeList = FXCollections.observableArrayList();
        } else {
            timeList = FXCollections.observableArrayList(showTime);
        }
        tfTime.setItems(timeList);
        tfTime.getSelectionModel().selectFirst();
        tfPaymentType.getSelectionModel().selectFirst();
        tfFirstname.setText("");
        tfAdultSeat.setText("");
        tfChildSeat.setText("");
        this.order = null;
    }

    public void initOrder(Order order) {
        this.order = order;
        ShowTime showTime = CinemaDatabaseFactory.getInstanceDB().getShowTime(order);
        Movie movie = showTime.getMovie();
        Customer customer = CinemaDatabaseFactory.getInstanceDB().getCustomer(order.getId());
        tfMovie.setValue(movie);
        tfTime.setValue(showTime);
        tfFirstname.setText(customer.getFirstName());
        int adultTotal = 0;
        int childTotal = 0;
        for (Ticket ticket : order.getTicketList()) {
            if (ticket.getAmount().equals(movie.getPriceByAgeType(Movie.AGETYPE_ADULT))) {
                adultTotal++;
            } else {
                childTotal++;
            }
        }
        tfAdultSeat.setText(String.valueOf(adultTotal));
        tfChildSeat.setText(String.valueOf(childTotal));
    }

    private class BtnSellEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            if (!validateInput()) {
                return;
            }
            String ret = null;
            if (order == null) {
                ShowTime showTime = (ShowTime) tfTime.getSelectionModel().getSelectedItem();
                Movie movie = (Movie) tfMovie.getSelectionModel().getSelectedItem();
                List<Integer> seatNumberList = CinemaDatabaseFactory.getInstanceDB().getUnavialableSeatNumberList(showTime);
                int requestedNumber = Integer.parseInt(tfAdultSeat.getText()) + Integer.parseInt(tfChildSeat.getText());
                List<Integer> newNumberList = showTime.getHall().getAvailableSeat(seatNumberList, requestedNumber);
                List<Ticket> ticketList = new ArrayList<>();
                int totalAdult = Integer.parseInt(tfAdultSeat.getText());
                int cntAdult = 0;
                double totalAmount = 0;
                for (Integer seatNumber : newNumberList) {
                    if (cntAdult < totalAdult) {
                        ticketList.add(new Ticket(movie.getPriceByAgeType(Movie.AGETYPE_ADULT), seatNumber));
                        totalAmount = totalAmount + movie.getPriceByAgeType(Movie.AGETYPE_ADULT);
                        cntAdult++;
                    } else {
                        ticketList.add(new Ticket(movie.getPriceByAgeType(Movie.AGETYPE_CHILD), seatNumber));
                        totalAmount = totalAmount + movie.getPriceByAgeType(Movie.AGETYPE_CHILD);
                    }
                }
                order = new Order(null, ticketList, new Date(), (byte) 1, (String) tfPaymentType.getSelectionModel().getSelectedItem(), totalAmount);
                ret = CinemaDatabaseFactory.getInstanceDB().insertOrder(order, showTime.getId(), tfFirstname.getText());
            } else {
                order.setState((byte) 1);
                order.setStateDate(new Date());
                ret = CinemaDatabaseFactory.getInstanceDB().setOrder(order);
            }
            printReciept();
            if (ret != null && ret.equals("success")) {
                initiate();
                actionInfo.setText("Done!");
            } else {
                actionInfo.setText("Sorry, failed to sell tickets!");
            }
        }
    }

    private void printReciept() {
        StringBuilder str = new StringBuilder();
        ShowTime showTime = (ShowTime) tfTime.getSelectionModel().getSelectedItem();
        Movie movie = (Movie) tfMovie.getSelectionModel().getSelectedItem();
        Customer customer = CinemaDatabaseFactory.getInstanceDB().getCustomer(order.getId());
        str.append("=======================================\n\n");
        str.append("Customer: ");
        str.append(customer.getFirstName());
        str.append(" ");
        str.append(customer.getLastName());
        str.append("\n---------------------------------------\n");
        str.append("Movie: ");
        str.append(movie.getName());
        str.append("\n");
        str.append("Time: ");
        str.append(showTime.toString());
        str.append("\n");
        str.append("Seat numbers: ");
        boolean first = true;
        for (Ticket ticket : order.getTicketList()) {
            if (first) {
                str.append(ticket.getSeatNumber());
                first = false;
            } else {
                str.append(", ");
                str.append(ticket.getSeatNumber());
            }
        }
        str.append("\n");
        str.append("Total amount: ");
        str.append(order.getAmount());
        str.append("\n\n=======================================");
        Alert alert = new Alert(AlertType.INFORMATION, str.toString(), ButtonType.OK);
        alert.setTitle("Receipt");
        alert.setHeaderText("Receipt");
        alert.showAndWait();
        System.out.println(str.toString());
    }

    private class BtnCalculateEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            if (!validateInput()) {
                return;
            }
            if (order != null && order.getAmount() != null) {
                actionInfo.setText("Total amount: " + order.getAmount());
            } else {
                ShowTime showTime = (ShowTime) tfTime.getSelectionModel().getSelectedItem();
                Movie movie = (Movie) tfMovie.getSelectionModel().getSelectedItem();
                List<Integer> seatNumberList = CinemaDatabaseFactory.getInstanceDB().getUnavialableSeatNumberList(showTime);
                int requestedNumber = Integer.parseInt(tfAdultSeat.getText()) + Integer.parseInt(tfChildSeat.getText());
                List<Integer> newNumberList = showTime.getHall().getAvailableSeat(seatNumberList, requestedNumber);
                int totalAdult = Integer.parseInt(tfAdultSeat.getText());
                int cntAdult = 0;
                double totalAmount = 0;
                for (Integer seatNumber : newNumberList) {
                    if (cntAdult < totalAdult) {
                        totalAmount = totalAmount + movie.getPriceByAgeType(Movie.AGETYPE_ADULT);
                        cntAdult++;
                    } else {
                        totalAmount = totalAmount + movie.getPriceByAgeType(Movie.AGETYPE_CHILD);
                    }
                }
                actionInfo.setText("Total amount: " + totalAmount);
            }
        }
    }

    private class MoviesListener implements ChangeListener<Movie> {

        @Override
        public void changed(ObservableValue<? extends Movie> ov, Movie oldValue, Movie newValue) {
            List<ShowTime> showTime = CinemaDatabaseFactory.getInstanceDB().getShowTime(newValue);
            if (showTime == null) {
                timeList = FXCollections.observableArrayList();
            } else {
                timeList = FXCollections.observableArrayList(showTime);
            }
            tfTime.setItems(timeList);
            tfTime.getSelectionModel().selectFirst();
        }
    }

    private boolean validateInput() {
        boolean retValue = false;
        try {
            InputRules inputRules = RuleSetFactory.getRuleSet(this);
            inputRules.applyRules(this);
            retValue = true;
        } catch (RuleException re) {
            Alert alert = new Alert(Alert.AlertType.WARNING, re.getMessage(), ButtonType.OK);
            alert.setTitle("Warnings");
            alert.setHeaderText("Check your fields.");
            alert.showAndWait();
        }
        return retValue;
    }

    public String getFirstname() {
        return tfFirstname.getText();
    }

    public String getAdultSeat() {
        return tfAdultSeat.getText();
    }

    public String getChildSeat() {
        return tfChildSeat.getText();
    }
}
