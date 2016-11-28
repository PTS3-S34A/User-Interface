package nl.soccar.ui.fx.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import nl.soccar.library.enumeration.CarType;
import nl.soccar.ui.Main;

/**
 * FXML Controller class
 *
 * @author PTS34A
 */
public class LoginFXMLController implements Initializable {

    private static final String CSS_ERROR_BORDER = "-fx-border-color: red;";
    private static final String CSS_NORMAL_BORDER = "-fx-border-color: white;";
    
    @FXML
    private Button btnLogin;
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
        
        txtFieldName.setOnAction(e -> login());
        btnLogin.setOnAction(e -> login());
    }

    /**
     * Handler for login-button; Uses current selected car, username and
     * optional password.
     */
    private void login() {
        CarType selectedCar;
        if (btnSelectCasualCar.isSelected()) {
            selectedCar = CarType.CASUAL;
        } else if (btnSelectPickup.isSelected()) {
            selectedCar = CarType.PICKUP;
        } else if (btnSelectSportsCar.isSelected()) {
            selectedCar = CarType.SPORTSCAR;
        } else {
            btnSelectCasualCar.setStyle(CSS_ERROR_BORDER);
            btnSelectSportsCar.setStyle(CSS_ERROR_BORDER);
            btnSelectPickup.setStyle(CSS_ERROR_BORDER);
            return;
        }

        if (!txtFieldName.getText().isEmpty()) {
            Main.getInstance().login(txtFieldName.getText(), selectedCar);
        } else {
            txtFieldName.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
        }
    }
}
