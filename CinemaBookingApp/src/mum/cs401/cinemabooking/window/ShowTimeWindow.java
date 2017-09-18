package mum.cs401.cinemabooking.window;

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
import mum.cs401.cinemabooking.database.CinemaDatabaseFactory;
import mum.cs401.cinemabooking.entity.Hall;
import mum.cs401.cinemabooking.entity.Movie;
import mum.cs401.cinemabooking.entity.ShowTime;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public class ShowTimeWindow extends Stage {

    private final static ShowTimeWindow instance = new ShowTimeWindow(PrimaryWindow.WINDOW_TITLE);
    private static final String CSS_PATH = "/resource/style_showtimewindow.css";
    private final Text actionInfo = new Text();
    private ObservableList<Movie> movieList;
    private ObservableList<Hall> hallList;
    private ObservableList<Times> timeList = FXCollections.observableArrayList(Times.getTimeList());
    final ComboBox tfTime = new ComboBox(timeList);
    final ComboBox tfHall = new ComboBox();
    final ComboBox tfMovie = new ComboBox();

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
        hallList = FXCollections.observableArrayList(CinemaDatabaseFactory.getInstanceDB().getHallList());
        tfHall.setItems(hallList);
        tfHall.getSelectionModel().selectFirst();
        movieList = FXCollections.observableArrayList(CinemaDatabaseFactory.getInstanceDB().getMovieList());
        tfMovie.setItems(movieList);
        tfMovie.getSelectionModel().selectFirst();

        // buttons
        final Button btnSet = new Button("Set");
        btnSet.setOnAction(new BtnSetEventHandler());

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
        gridSecond.add(btnSet, 0, 0);

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
        tfTime.getSelectionModel().selectFirst();
        hallList = FXCollections.observableArrayList(CinemaDatabaseFactory.getInstanceDB().getHallList());
        tfHall.setItems(hallList);
        tfHall.getSelectionModel().selectFirst();
        movieList = FXCollections.observableArrayList(CinemaDatabaseFactory.getInstanceDB().getMovieList());
        tfMovie.setItems(movieList);
        tfMovie.getSelectionModel().selectFirst();
    }

    private class BtnSetEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            Hall hall = (Hall) tfHall.getSelectionModel().getSelectedItem();
            Movie movie = (Movie) tfMovie.getSelectionModel().getSelectedItem();
            Times movieTime = (Times) tfTime.getSelectionModel().getSelectedItem();
            ShowTime showTime = new ShowTime(hall, movie, movieTime.getIntTime());
            String ret = CinemaDatabaseFactory.getInstanceDB().insertShowTime(showTime);
            if (ret != null && ret.equals("success")) {
                initiate();
                actionInfo.setText("Done!");
            } else if (ret != null && ret.equals("unique")) {
                initiate();
                actionInfo.setText("This show time is allready set!");
            } else {
                actionInfo.setText("Sorry, failed to set this movie's show time!");
            }
        }
    }
}
