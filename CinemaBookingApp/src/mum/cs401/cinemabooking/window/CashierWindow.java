package mum.cs401.cinemabooking.window;

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
public class CashierWindow extends Stage {

    public CashierWindow(String title) {

        // images
        Image imageOrder = new Image(getClass().getResourceAsStream("/resource/orders.png"));
        Image imageTicket = new Image(getClass().getResourceAsStream("/resource/ticket.png"));

        // buttons        
        final Button btnOrder = new Button();
        btnOrder.setGraphic(new ImageView(imageOrder));
        btnOrder.setOnAction(new BtnOrderEventHandler());
        final Button btnTicket = new Button();
        btnTicket.setGraphic(new ImageView(imageTicket));
        btnTicket.setOnAction(new BtnTicketEventHandler());

        final GridPane gridMain = new GridPane();
        gridMain.setAlignment(Pos.CENTER);
        gridMain.add(btnOrder, 0, 0);
        gridMain.add(btnTicket, 1, 0);

        //window
        Scene scene = new Scene(gridMain, 600, 300);
        setScene(scene);
        setTitle(title);
        this.setResizable(false);
    }

    private class BtnOrderEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            OrderWindow.getInstance().initiate();
            OrderWindow.getInstance().show();
            OrderWindow.getInstance().toFront();
        }
    }

    private class BtnTicketEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            TicketWindow.getInstance().initiate();
            TicketWindow.getInstance().show();
            TicketWindow.getInstance().toFront();
        }
    }
}
