package cinemabooking;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public class PrimaryWindow extends Application {

    private final Text actionInfo = new Text();
    private static final String WINDOW_TITLE = "Cinema booking system";
    private final TextField tfUsername = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private Stage adminWindow;
    private Stage cashierWindow;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        final Label lblPassword = new Label("Password:");
        final Label lblUserName = new Label("Username:");
        final Button btn = new Button("Sign in");
        btn.setOnAction(new BtnLoginEventHandler());
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_CENTER);
        hbBtn.getChildren().add(btn);
        final Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle.setId("welcome-text");
        actionInfo.setId("actionInfo");

        //
        tfUsername.setText("eegii");
        passwordField.setText("eegii33");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(scenetitle, 0, 0, 2, 1);
        grid.add(lblUserName, 0, 1);
        grid.add(tfUsername, 1, 1);
        grid.add(lblPassword, 0, 2);
        grid.add(passwordField, 1, 2);
        grid.add(hbBtn, 1, 4);
        grid.add(actionInfo, 1, 5);

        Scene scene = new Scene(grid, 300, 200);
        scene.getStylesheets().addAll(this.getClass().getResource("/resource/style.css").toExternalForm());
        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private class BtnLoginEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            String loggedUser = signIn();
            if (loggedUser == null) {
                actionInfo.setFill(Color.FIREBRICK);
                actionInfo.setText("Username or password is invalid!");
            } else if (loggedUser.toLowerCase().equals("admin")) {
                createAdminWindow();
            } else {
                createCashierWindow();
            }
        }
    }

    private void createAdminWindow() {
        adminWindow = new AdminWindow(WINDOW_TITLE);
        adminWindow.show();
        primaryStage.hide();
    }

    private void createCashierWindow() {
        // TODO initiate cash window
        cashierWindow = new CashierWindow(WINDOW_TITLE);
        cashierWindow.show();
        primaryStage.hide();
    }

    private String signIn() {
        String ret = null;
        if (tfUsername.getText().equals("eegii")
                && passwordField.getText().equals("eegii33")) {
            ret = "admin";
        } else if (tfUsername.getText().equals("cashier")) {
            ret = "cashier";
        }
        return ret;
    }

}
