package nl.soccar.ui.fx.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import nl.soccar.library.enumeration.CarType;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.FXMLConstants;
import nl.soccar.ui.util.FxUtilities;

/**
 * FXML Controller class
 *
 * @author PTS34A
 */
public class LoginFXMLController implements Initializable {

    private static final String REGEX = "^[a-zA-Z0-9]{1,16}$";

    private static final String CSS_ERROR_BORDER = "-fx-border-color: red;";
    private static final String CSS_NORMAL_BORDER = "-fx-border-color: white;";

    @FXML
    private Button btnLoginRegister;
    @FXML
    private Button btnPlayGuest;
    @FXML
    private TextField txtFieldName;
    @FXML
    private ToggleButton btnSelectCasualCar;
    @FXML
    private ToggleButton btnSelectPickup;
    @FXML
    private ToggleButton btnSelectSportsCar;

    private ToggleGroup toggleGroupCars;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        toggleGroupCars = new ToggleGroup();

        btnSelectCasualCar.setToggleGroup(toggleGroupCars);
        btnSelectCasualCar.setSelected(true);
        btnSelectSportsCar.setToggleGroup(toggleGroupCars);
        btnSelectPickup.setToggleGroup(toggleGroupCars);

        toggleGroupCars.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            btnSelectCasualCar.setStyle(CSS_NORMAL_BORDER);
            btnSelectSportsCar.setStyle(CSS_NORMAL_BORDER);
            btnSelectPickup.setStyle(CSS_NORMAL_BORDER);
        });

        txtFieldName.setOnAction(e -> playAsGuest());

        btnLoginRegister.setOnAction(e -> loginOrRegister());
        btnPlayGuest.setOnAction(e -> playAsGuest());
    }

    /**
     * Handler for loginOrRegister-button; Uses current selected car, username
     * and optional password.
     */
    private void loginOrRegister() {
        CarType car = selectedCar();
        if (car != null && checkInput()) {
            Main.getInstance().loginOrRegister(txtFieldName.getText(), car);
        }
    }

    private void playAsGuest() {
        CarType car = selectedCar();
        if (car != null && checkInput()) {
            Main.getInstance().playAsGuest(txtFieldName.getText(), car);
        }
    }

    private CarType selectedCar() {
        CarType selectedCar;
        if (btnSelectCasualCar.isSelected()) {
            selectedCar = CarType.CASUAL;
        } else if (btnSelectPickup.isSelected()) {
            selectedCar = CarType.PICKUP;
        } else if (btnSelectSportsCar.isSelected()) {
            selectedCar = CarType.SPORTSCAR;
        } else {
            FxUtilities.showInlineMessage(btnSelectPickup, FXMLConstants.MESSAGE_CAR_NOT_SELECTED);
            btnSelectCasualCar.setStyle(CSS_ERROR_BORDER);
            btnSelectSportsCar.setStyle(CSS_ERROR_BORDER);
            btnSelectPickup.setStyle(CSS_ERROR_BORDER);
            return null;
        }
        return selectedCar;
    }

    private boolean checkInput() {
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(txtFieldName.getText());

        if (m.matches()) {
            return true;
        }

        FxUtilities.showInlineMessage(txtFieldName, FXMLConstants.MESSAGE_INVALID_USERNAME);
        txtFieldName.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
        return false;
    }

}
