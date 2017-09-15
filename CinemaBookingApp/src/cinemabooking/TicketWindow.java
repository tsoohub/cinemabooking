package cinemabooking;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public class TicketWindow extends Stage {

    private final static TicketWindow instance = new TicketWindow(PrimaryWindow.WINDOW_TITLE);
    private static final String CSS_PATH = "/resource/style_ticketwindow.css";
    private final Text actionInfo = new Text();
    private ObservableList<String> movieList = FXCollections.observableArrayList(
            "BreakingBad",
            "Titanic",
            "Matrix",
            "Alien"
    );
    private ObservableList<String> paymentTypeList = FXCollections.observableArrayList(
            "Visa card",
            "Debit card",
            "Cash",
            "Check"
    );
    private ObservableList<String> timeList = FXCollections.observableArrayList(
            "00:00",
            "01:00",
            "02:00",
            "03:00",
            "04:00",
            "05:00",
            "06:00",
            "07:00",
            "08:00",
            "09:00",
            "10:00",
            "11:00",
            "12:00",
            "13:00",
            "14:00",
            "15:00",
            "16:00",
            "17:00",
            "18:00",
            "19:00",
            "20:00",
            "21:00",
            "22:00",
            "23:00"
    );
    private final TextField tfSeat = new TextField();
    private final TextField tfAmount = new TextField();
    private final TextField tfPhone = new TextField();
    private final ComboBox tfTime = new ComboBox(timeList);
    private final ComboBox tfMovie = new ComboBox(movieList);
    private final ComboBox tfPaymentType = new ComboBox(paymentTypeList);

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
        final Label lblSeat = new Label("Seat:");
        final Label lblAmount = new Label("Amount:");
        final Label lblPhone = new Label("Phone number:");
        final Label lblPaymentType = new Label("Payment type:");

        // TextField
        tfAmount.setEditable(false);

        // combobox        
        tfTime.getSelectionModel().selectFirst();
        tfMovie.getSelectionModel().selectFirst();
        tfPaymentType.getSelectionModel().selectFirst();

        // buttons
        final Button btnCheck = new Button("Check");
        btnCheck.setOnAction(new BtnSellEventHandler());

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
        gridFirst.add(lblSeat, 0, 2);
        gridFirst.add(tfSeat, 1, 2);
        gridFirst.add(lblAmount, 0, 3);
        gridFirst.add(tfAmount, 1, 3);
        gridFirst.add(lblPhone, 0, 4);
        gridFirst.add(tfPhone, 1, 4);
        gridFirst.add(lblPaymentType, 0, 5);
        gridFirst.add(tfPaymentType, 1, 5);

        GridPane gridSecond = new GridPane();
        gridSecond.setVgap(gridVgap);
        gridSecond.setHgap(gridHgap);
        gridSecond.setAlignment(Pos.CENTER);
        gridSecond.add(btnCheck, 0, 0);

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
        tfMovie.getSelectionModel().selectFirst();
        tfTime.getSelectionModel().selectFirst();
        tfPaymentType.getSelectionModel().selectFirst();
        tfAmount.setText("");
        tfPhone.setText("");
        tfSeat.setText("");
    }

    private class BtnSellEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            boolean ret = false;
            if (ret) {
                initiate();
                actionInfo.setText("Done!");
            } else {
                actionInfo.setText("Sorry, failed to sell tickets!");
            }
        }
    }
}
