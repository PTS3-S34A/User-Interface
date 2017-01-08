package nl.soccar.ui.fx.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import nl.soccar.library.Player;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.FXMLConstants;

/**
 * FXML Controller class
 *
 * @author PTS34A
 */
public class GameControlsFXMLController implements Initializable {

    @FXML
    private Button btnLogOut;
    @FXML
    private Button btnReturn;

    private static final Logger LOGGER = Logger.getLogger(GameControlsFXMLController.class.getSimpleName());

    private Player currentPlayer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnLogOut.setOnAction(e -> Main.getInstance().logOut());
        btnReturn.setOnAction(e -> Main.getInstance().setScene(FXMLConstants.LOCATION_MAIN_MENU));
    }

}
