package nl.soccar.ui.fx.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import nl.soccar.ui.rmi.ClientController;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.FXMLConstants;

/**
 * FXML Controller class
 *
 * @author PTS34A
 */
public class RegisterFXMLController implements Initializable {

    @FXML
    private Button btnLoginRegister;
    @FXML
    private Button btnCancel;
    @FXML
    private PasswordField txtFieldPassword;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblUsernameDescription;

    private String username;
    private boolean userExists;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtFieldPassword.setOnAction(e -> loginOrRegister());
        btnLoginRegister.setOnAction(e -> loginOrRegister());
        btnCancel.setOnAction(e -> Main.getInstance().setScene(FXMLConstants.LOCATION_LOGIN));
        
        setState();
    }

    /**
     * Handler for loginOrRegister-button; Uses current selected car, username
     * and optional password.
     */
    private void setState() {
        ClientController controller = ClientController.getInstance();
        username = controller.getCurrentPlayer().getUsername();
        userExists = controller.checkIfExists(username);
        
        if (userExists) {
            lblUsername.setText(username);
            lblUsernameDescription.setText(FXMLConstants.WARNING_USERNAME_EXISTS);
            btnLoginRegister.setText(FXMLConstants.BUTTON_USERNAME_EXISTS);
        } else {
            lblUsername.setText(username);
            lblUsernameDescription.setText(FXMLConstants.WARNING_NEW_USERNAME);
            btnLoginRegister.setText(FXMLConstants.BUTTON_NEW_USERNAME);
        }
    }

    private void loginOrRegister() {
        String password = txtFieldPassword.getText();
        if (!checkInput(password)) {
            return;
        }
        
        ClientController controller = ClientController.getInstance();

        if (userExists && controller.checkPassword(username, password)) {
            Main.getInstance().setScene(FXMLConstants.LOCATION_MAIN_MENU);
        } else if (!userExists && controller.add(username, password)) {
            Main.getInstance().setScene(FXMLConstants.LOCATION_MAIN_MENU);
        } else {
            clearInput();
        }
    }

    private boolean checkInput(String password) {
        
        if (!(password.length() < 8)) {
            return true;
        }
        
        txtFieldPassword.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
        return false;
    }

    private void clearInput() {
        txtFieldPassword.clear();
        txtFieldPassword.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
    }

}
