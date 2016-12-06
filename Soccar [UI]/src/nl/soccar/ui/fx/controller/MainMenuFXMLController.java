package nl.soccar.ui.fx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.soccar.library.Player;
import nl.soccar.library.SessionData;
import nl.soccar.library.Statistics;
import nl.soccar.socnet.Client;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.ui.rmi.ClientController;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.FXMLConstants;
import nl.socnet.message.JoinSessionMessage;
import nl.socnet.message.RegisterPlayerMessage;

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
    private TableView<StatisticsTableItem> tblStatisticsList;
    @FXML
    private TableColumn tbclSessionRoomName;
    @FXML
    private TableColumn tbclSessionOccupation;
    @FXML
    private TableColumn tbclSessionHost;
    @FXML
    private TableColumn tbclSessionPassword;
    @FXML
    private TableColumn tbclStatisticUsername;
    @FXML
    private TableColumn tbclStatisticGoals;
    @FXML
    private TableColumn tbclStatisticAssists;
    @FXML
    private TableColumn tbclStatisticRatio;
    @FXML
    private TableColumn tbclStatisticGamesWon;
    @FXML
    private TableColumn tbclStatisticGamesEven;
    @FXML
    private TableColumn tbclStatisticGamesLost;
    @FXML
    private Tab tabSession;
    @FXML
    private Tab tabStatistic;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Overwrite the standard placeholder text with an empty String.
        tblSessionList.setPlaceholder(new Label(""));
        tblStatisticsList.setPlaceholder(new Label(""));

        Player currentPlayer = ClientController.getInstance().getCurrentPlayer();

        lblUsername.setText(currentPlayer.getUsername());
        lblCar.setText(currentPlayer.getCarType().name());

        btnLogOut.setOnAction(e -> Main.getInstance().logOut());
        btnCreateRoom.setOnAction(e -> Main.getInstance().setScene(FXMLConstants.LOCATION_CREATE_ROOM));
        btnJoinRoom.setOnAction(e -> joinRoom(tblSessionList.getSelectionModel().getSelectedItem()));
        btnJoinRoom.setDisable(true);

        tbclSessionRoomName.setCellValueFactory(new PropertyValueFactory<>("roomName"));
        tbclSessionOccupation.setCellValueFactory(new PropertyValueFactory<>("occupancy"));
        tbclSessionHost.setCellValueFactory(new PropertyValueFactory<>("hostName"));
        tbclSessionPassword.setCellValueFactory(new PropertyValueFactory<>("passwordAvailable"));

        tbclStatisticUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        tbclStatisticGoals.setCellValueFactory(new PropertyValueFactory<>("goals"));
        tbclStatisticAssists.setCellValueFactory(new PropertyValueFactory<>("assists"));
        tbclStatisticRatio.setCellValueFactory(new PropertyValueFactory<>("ratio"));
        tbclStatisticGamesWon.setCellValueFactory(new PropertyValueFactory<>("gamesWon"));
        tbclStatisticGamesEven.setCellValueFactory(new PropertyValueFactory<>("gamesEven"));
        tbclStatisticGamesLost.setCellValueFactory(new PropertyValueFactory<>("gamesLost"));

        tbclStatisticRatio.setSortType(TableColumn.SortType.DESCENDING);

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

        tabSession.setOnSelectionChanged(e -> updateSessionTable());
        tabStatistic.setOnSelectionChanged(e -> updateStatisticTable());

        updateSessionTable();
        updateStatisticTable();
    }

    private void updateSessionTable() {
        ObservableList<SessionTableItem> sessionItems = FXCollections.observableArrayList();
        ClientController.getInstance().getAllSessions().stream().map(SessionTableItem::new).forEach(sessionItems::add);

        tblSessionList.getItems().clear();
        tblSessionList.getItems().addAll(sessionItems);
    }

    private void updateStatisticTable() {
        ObservableList<StatisticsTableItem> statisticItems = FXCollections.observableArrayList();

        List<Statistics> statistics = ClientController.getInstance().getAllStatistics();
        statistics.stream().map(StatisticsTableItem::new).forEach(statisticItems::add);

        tblStatisticsList.getItems().clear();
        tblStatisticsList.getItems().addAll(statisticItems);
        tblStatisticsList.getSortOrder().add(tbclStatisticRatio);
    }

    private void joinRoom(SessionTableItem selectedRow) {
        SessionData selectedSession = selectedRow.getSessionData();
        if (selectedSession == null) {
            // There isn't actually a selected session, we should just do nothing.
            return;
        }

        String password = NO_PASSWORD;
        if (selectedSession.hasPassword()) {
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

        ClientController controller = ClientController.getInstance();
        Client client = controller.getClient();
        try {
            client.connect(selectedSession.getAddress(), 1046);

            Connection connection;
            while ((connection = controller.getCurrentConnection()) == null) {
                try {
                    Thread.sleep(50L);
                } catch (InterruptedException e) {
                    // Ignored, I KNOW.. I know. Shh.
                }
            }

            Player currentPlayer = controller.getCurrentPlayer();
            connection.send(new RegisterPlayerMessage(currentPlayer.getUsername(), currentPlayer.getCarType()));
            LOGGER.log(Level.INFO, "Registered Player to Game Server");
            
            connection.send(new JoinSessionMessage(selectedSession.getRoomName(), password));
            LOGGER.log(Level.INFO, "Joined the chosen Session of the Game Server");   
            
        } catch (IOException ex) {
            Logger.getLogger(MainMenuFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

}
