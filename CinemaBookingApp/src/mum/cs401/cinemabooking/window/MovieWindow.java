package mum.cs401.cinemabooking.window;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mum.cs401.cinemabooking.database.CinemaDatabaseFactory;
import mum.cs401.cinemabooking.entity.Category;
import mum.cs401.cinemabooking.entity.Movie;
import mum.cs401.cinemabooking.entity.MovieCategories;
import mum.cs401.cinemabooking.rules.InputRules;
import mum.cs401.cinemabooking.rules.RuleException;
import mum.cs401.cinemabooking.rules.RuleSetFactory;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public class MovieWindow extends Stage {

    private final static MovieWindow instance = new MovieWindow(PrimaryWindow.WINDOW_TITLE);
    private static final String CSS_PATH = "/resource/style_moviewindow.css";
    private final DatePicker tfStartDate = new DatePicker();
    private final DatePicker tfEndDate = new DatePicker();
    private final Text actionInfo = new Text();
    private final TextField tfDirector = new TextField();
    private final TextField tfName = new TextField();
    private final TextField tfDuration = new TextField();
    private final TextField tfTrailerLink = new TextField();
    private final TextField tfAdultPrice = new TextField();
    private final TextField tfChildPrice = new TextField();
    private final TextArea taActors = new TextArea();
    private ObservableList<Category> categoryList;
    private final ComboBox tfRateType = new ComboBox();
    private final ComboBox tfCategory = new ComboBox();

    public static MovieWindow getInstance() {
        return instance;
    }

    private MovieWindow(String title) {
        //
        final double gridVgap = 10;
        final double gridHgap = 10;
        final int tfColumnWidth = 40;
        // labels
        final Label lblDirector = new Label("Director:");
        final Label lblStartDate = new Label("Start date:");
        final Label lblEndDate = new Label("End date:");
        final Label lblName = new Label("Name:");
        final Label lblDuration = new Label("Duration:");
        final Label lblTrailerLink = new Label("TrailerLink:");
        final Label lblRateType = new Label("RateType:");
        final Label lblActors = new Label("Actors:");
        final Label lblChildPrice = new Label("Child price:");
        final Label lblAdultPrice = new Label("Adult price:");
        final Label lblCategory = new Label("Category:");
        // textfields        
        tfDirector.setPrefWidth(tfColumnWidth);
        tfName.setPrefWidth(tfColumnWidth);
        tfDuration.setPrefWidth(tfColumnWidth);
        tfTrailerLink.setPrefWidth(tfColumnWidth);
        //date pickers
        tfStartDate.setOnAction(new DatePickerEventHandler());
        tfEndDate.setOnAction(new DatePickerEventHandler());
        // text area        
        taActors.setPrefRowCount(3);
        taActors.setPrefColumnCount(19);
        // combobox        
        tfRateType.getItems().addAll(
                "G Rating",
                "PG Rating",
                "PG-13 Rating",
                "NC-17 Rating",
                "R rating"
        );
        tfRateType.getSelectionModel().selectFirst();
        categoryList = FXCollections.observableArrayList(
                CinemaDatabaseFactory.getInstanceDB().getCategoryList());
        tfCategory.setItems(categoryList);
        tfCategory.getSelectionModel().selectFirst();
        actionInfo.setId("actionInfo");
        actionInfo.setFill(Color.FIREBRICK);

        // buttons
        final Button btnAdd = new Button("Add");
        btnAdd.setOnAction(new BtnAddEventHandler());

        // container
        final GridPane gridFirst = new GridPane();
        gridFirst.setAlignment(Pos.CENTER);
        gridFirst.setVgap(gridVgap);
        gridFirst.setHgap(gridHgap);
        gridFirst.add(lblName, 0, 0);
        gridFirst.add(tfName, 1, 0);
        gridFirst.add(lblDirector, 0, 1);
        gridFirst.add(tfDirector, 1, 1);
        gridFirst.add(lblDuration, 0, 2);
        gridFirst.add(tfDuration, 1, 2);
        gridFirst.add(lblStartDate, 0, 3);
        gridFirst.add(tfStartDate, 1, 3);
        gridFirst.add(lblEndDate, 0, 4);
        gridFirst.add(tfEndDate, 1, 4);
        gridFirst.add(lblRateType, 0, 5);
        gridFirst.add(tfRateType, 1, 5);
        gridFirst.add(lblTrailerLink, 0, 6);
        gridFirst.add(tfTrailerLink, 1, 6);
        gridFirst.add(lblAdultPrice, 0, 7);
        gridFirst.add(tfAdultPrice, 1, 7);
        gridFirst.add(lblChildPrice, 0, 8);
        gridFirst.add(tfChildPrice, 1, 8);
        gridFirst.add(lblCategory, 0, 9);
        gridFirst.add(tfCategory, 1, 9);

        GridPane gridSecond = new GridPane();
        gridSecond.setVgap(gridVgap);
        gridSecond.setHgap(gridHgap);
        gridSecond.setAlignment(Pos.CENTER);
        gridSecond.add(lblActors, 0, 0);

        GridPane gridThird = new GridPane();
        gridThird.setVgap(gridVgap);
        gridThird.setHgap(gridHgap);
        gridThird.setAlignment(Pos.CENTER);
        gridThird.add(taActors, 0, 0);

        GridPane gridFourth = new GridPane();
        gridFourth.setVgap(gridVgap);
        gridFourth.setHgap(gridHgap);
        gridFourth.setAlignment(Pos.CENTER);
        gridFourth.add(btnAdd, 0, 0);

        GridPane gridFifth = new GridPane();
        gridFifth.setVgap(gridVgap);
        gridFifth.setHgap(gridHgap);
        gridFifth.setAlignment(Pos.CENTER);
        gridFifth.add(actionInfo, 0, 0);

        GridPane gridMain = new GridPane();
        gridMain.setVgap(gridVgap);
        gridMain.setHgap(gridHgap);
        gridMain.setAlignment(Pos.CENTER);
        gridMain.add(gridFirst, 0, 0);
        gridMain.add(gridSecond, 0, 1);
        gridMain.add(gridThird, 0, 2);
        gridMain.add(gridFourth, 0, 3);
        gridMain.add(gridFifth, 0, 4);

        //window
        Scene scene = new Scene(gridMain, 350, 550);
        scene.getStylesheets().addAll(this.getClass().getResource(CSS_PATH).toExternalForm());
        setScene(scene);
        setTitle(title);
        this.setResizable(false);
    }

    public void initiate() {
        LocalDate today = LocalDate.now();
        tfStartDate.setValue(today);
        today.plusDays(30);
        tfEndDate.setValue(today);
        tfDirector.setText("");
        actionInfo.setText("");
        tfDuration.setText("");
        tfName.setText("");
        tfRateType.getSelectionModel().selectFirst();
        tfTrailerLink.setText("");
        taActors.setText("");
        tfChildPrice.setText("");
        tfAdultPrice.setText("");
        categoryList = FXCollections.observableArrayList(
                CinemaDatabaseFactory.getInstanceDB().getCategoryList());
        tfCategory.setItems(categoryList);
        tfCategory.getSelectionModel().selectFirst();
    }

    private class BtnAddEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            if (!validateInput()) {
                return;
            }
            String ret = null;
            Date startDate = Date.from(tfStartDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(tfEndDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Movie movie = new Movie(tfDirector.getText(),
                    tfName.getText(),
                    Integer.parseInt(tfDuration.getText()),
                    tfTrailerLink.getText(),
                    String.valueOf(tfRateType.getSelectionModel().getSelectedItem()),
                    taActors.getText(), startDate,
                    endDate,
                    Double.parseDouble(tfAdultPrice.getText()),
                    Movie.AGETYPE_ADULT,
                    Double.parseDouble(tfChildPrice.getText()),
                    Movie.AGETYPE_CHILD);
            Category category = (Category) tfCategory.getSelectionModel().getSelectedItem();
            movie.getMovieCategoryList().add(new MovieCategories(movie, category));
            ret = CinemaDatabaseFactory.getInstanceDB().insertMovie(movie);
            if (ret != null && ret.equals("success")) {
                initiate();
                actionInfo.setText("Done!");
            } else {
                actionInfo.setText("Sorry, failed to add this movie!");
            }
        }
    }

    private class DatePickerEventHandler implements EventHandler {

        @Override
        public void handle(Event event) {
            if (event.getSource().equals(tfStartDate)) {
                LocalDate date = tfStartDate.getValue();
            } else if (event.getSource().equals(tfEndDate)) {
                LocalDate date = tfEndDate.getValue();
            }
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

    public String getActors() {
        return taActors.getText();
    }

    public String getChildPrice() {
        return tfChildPrice.getText();
    }

    public String getAdultPrice() {
        return tfAdultPrice.getText();
    }

    public String getTrailerLink() {
        return tfTrailerLink.getText();
    }

    public String getName() {
        return tfName.getText();
    }

    public String getDirector() {
        return tfDirector.getText();
    }

    public String getDuration() {
        return tfDuration.getText();
    }

}
