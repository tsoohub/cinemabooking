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
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public class ShowTimeWindow extends Stage {

    private final static ShowTimeWindow instance = new ShowTimeWindow(PrimaryWindow.WINDOW_TITLE);
    private static final String CSS_PATH = "/resource/style_showtimewindow.css";
    private final Text actionInfo = new Text();
    private ObservableList<String> movieList = FXCollections.observableArrayList(
            "BreakingBad",
            "Titanic",
            "Matrix",
            "Alien"
    );
    private ObservableList<String> hallList = FXCollections.observableArrayList(
            "Hall 3D",
            "Hall 4D",
            "Hall IMAX"
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
    final ComboBox tfTime = new ComboBox(timeList);
    final ComboBox tfHall = new ComboBox(hallList);
    final ComboBox tfMovie = new ComboBox(movieList);

    public static ShowTimeWindow getInstance() {
        return instance;
    }

    private ShowTimeWindow(String title) {
        //
        final double gridVgap = 10;
        final double gridHgap = 10;
        // labels
        final Label lblMovie = new Label("Movie:");
        final Label lblHall = new Label("Hall:");
        final Label lblTime = new Label("Time:");

        // combobox       
        tfTime.getSelectionModel().selectFirst();
        tfHall.getSelectionModel().selectFirst();
        tfMovie.getSelectionModel().selectFirst();

        // buttons
        final Button btnSave = new Button("Save");
        btnSave.setOnAction(new BtnSaveEventHandler());

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
        gridFirst.add(lblHall, 0, 1);
        gridFirst.add(tfHall, 1, 1);
        gridFirst.add(lblTime, 0, 2);
        gridFirst.add(tfTime, 1, 2);

        GridPane gridSecond = new GridPane();
        gridSecond.setVgap(gridVgap);
        gridSecond.setHgap(gridHgap);
        gridSecond.setAlignment(Pos.CENTER);
        gridSecond.add(btnSave, 0, 0);

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
        Scene scene = new Scene(gridMain, 200, 200);
        setScene(scene);
        scene.getStylesheets().addAll(this.getClass().getResource(CSS_PATH).toExternalForm());
        setTitle(title);
        this.setResizable(false);
    }

    public void initiate() {
        actionInfo.setText("");
        tfHall.getSelectionModel().selectFirst();
        tfMovie.getSelectionModel().selectFirst();
        tfTime.getSelectionModel().selectFirst();
    }

    private class BtnSaveEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            boolean ret = false;
            // TODO
            if (ret) {
                initiate();
                actionInfo.setText("Done!");
            } else {
                actionInfo.setText("Sorry, failed to set this movie's show time!");
            }
        }
    }
}
