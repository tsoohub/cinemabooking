package cinemabooking;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public class AdminWindow extends Stage {

    @SuppressWarnings("serial")
    GridPane gridMain = new GridPane();

    public AdminWindow(String title) {

        // images
        Image imageMovies = new Image(getClass().getResourceAsStream("/resource/movies.png"));
        Image imageShowTime = new Image(getClass().getResourceAsStream("/resource/showtime.png"));
        Image imageOrder = new Image(getClass().getResourceAsStream("/resource/orders.png"));
        Image imageTicket = new Image(getClass().getResourceAsStream("/resource/ticket.png"));

        // buttons
        final Button btnMovies = new Button();
        btnMovies.setOnAction(new BtnMoviesEventHandler());
        btnMovies.setGraphic(new ImageView(imageMovies));
        final Button btnShowTime = new Button();
        btnShowTime.setGraphic(new ImageView(imageShowTime));
        btnShowTime.setOnAction(new BtnShowTimeEventHandler());
        final Button btnOrder = new Button();
        btnOrder.setGraphic(new ImageView(imageOrder));
        btnOrder.setOnAction(new BtnOrderEventHandler());
        final Button btnTicket = new Button();
        btnTicket.setGraphic(new ImageView(imageTicket));
        btnTicket.setOnAction(new BtnTicketEventHandler());

        gridMain.setAlignment(Pos.CENTER);
        gridMain.add(btnMovies, 0, 0);
        gridMain.add(btnShowTime, 1, 0);
        gridMain.add(btnOrder, 0, 1);
        gridMain.add(btnTicket, 1, 1);

        //window
        Scene scene = new Scene(gridMain, 580, 580);
        setScene(scene);
        setTitle(title);
        this.setResizable(false);
    }

    private class BtnMoviesEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            // todo
        }
    }

    private class BtnShowTimeEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            // todo
        }
    }

    private class BtnOrderEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            // todo
        }
    }

    private class BtnTicketEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            // todo
        }
    }
}
