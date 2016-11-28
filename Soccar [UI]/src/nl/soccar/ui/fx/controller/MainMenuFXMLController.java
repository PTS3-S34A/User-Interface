package nl.soccar.ui.fx.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.soccar.exception.UIException;
import nl.soccar.library.Player;
import nl.soccar.library.Session;
import nl.soccar.library.SessionController;
import nl.soccar.library.Soccar;
import nl.soccar.library.enumeration.CarType;
import nl.soccar.library.enumeration.Privilege;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.FXMLConstants;

/**
 * FXML Controller class
 *
 * @author PTS34A
 */
public class MainMenuFXMLController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(MainMenuFXMLController.class.getSimpleName());
    private static final String NO_PASSWORD = "";

    @FXML
    private Button btnCreateRoom;
    @FXML
    private Button btnLogOut;
    @FXML
    private Button btnJoinRoom;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblCar;
    @FXML
    private TableView<SessionTableItem> tblSessionList;
    @FXML
    private TableColumn tbclName;
    @FXML
    private TableColumn tbclOccupation;
    @FXML
    private TableColumn tbclOwner;
    @FXML
    private TableColumn tbclPassword;

    private SessionController sessionController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sessionController = Soccar.getInstance().getSessionController();

        // Overwrite the standard placeholder text with an empty String.
        tblSessionList.setPlaceholder(new Label(""));

        lblUsername.setText(Soccar.getInstance().getCurrentPlayer().getUsername());
        lblCar.setText(Soccar.getInstance().getCurrentPlayer().getCarType().toString());

        btnLogOut.setOnAction(e -> Main.getInstance().logOut());
        btnCreateRoom.setOnAction(e -> Main.getInstance().setScene(FXMLConstants.LOCATION_CREATE_ROOM));
        btnJoinRoom.setOnAction(e -> joinRoom(tblSessionList.getSelectionModel().getSelectedItem()));
        btnJoinRoom.setDisable(true);

        tbclName.setCellValueFactory(new PropertyValueFactory<>("roomName"));
        tbclOccupation.setCellValueFactory(new PropertyValueFactory<>("occupancy"));
        tbclOwner.setCellValueFactory(new PropertyValueFactory<>("hostName"));
        tbclPassword.setCellValueFactory(new PropertyValueFactory<>("passwordAvailable"));

        tblSessionList.setRowFactory(tv -> {
            TableRow<SessionTableItem> row = new TableRow();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    joinRoom((SessionTableItem) row.getItem());
                }
            });
            return row;
        });

        TableViewSelectionModel<SessionTableItem> model = tblSessionList.getSelectionModel();
        model.setSelectionMode(SelectionMode.SINGLE);
        model.selectedItemProperty().addListener(l -> btnJoinRoom.setDisable(model.getSelectedItem() == null));

        updateTable();
    }

    private void updateTable() {
        ObservableList<SessionTableItem> sessionItems = FXCollections.observableArrayList();
        Soccar.getInstance().getSessionController().getAllSessions().stream().map(SessionTableItem::new).forEach(sessionItems::add);

        tblSessionList.getItems().clear();
        tblSessionList.getItems().addAll(sessionItems);
    }

    private void joinRoom(SessionTableItem selectedRow) {
        Session selectedSession = selectedRow.getSession();
        if (selectedSession == null) {
            // There isn't actually a selected session, we should just do nothing.
            return;
        }

        String password = NO_PASSWORD;
        if (selectedSession.getRoom().passwordAvailable()) {
            TextInputDialog dialog = new TextInputDialog("Password");
            dialog.setTitle("Room Locked");
            dialog.setHeaderText("Room Locked");
            dialog.setContentText("Enter the room password:");

            Optional<String> result = dialog.showAndWait();
            if (!result.isPresent()) {
                // There's no password set, we should just do nothing.
                return;
            }
            
            password = result.get();
        }

        try {
            sessionController.setCurrentSession(sessionController.join(selectedSession, password, Soccar.getInstance().getCurrentPlayer()));

            Main.getInstance().setScene(FXMLConstants.LOCATION_SESSION_VIEW);
        } catch (UIException e) {
            LOGGER.log(Level.WARNING, "An error occurred while joining a room.", e);

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(e.getTitle());
            alert.setHeaderText(e.getTitle());
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }
    }

}
