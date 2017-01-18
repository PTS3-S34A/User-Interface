package nl.soccar.ui.fx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import nl.soccar.library.*;
import nl.soccar.library.enumeration.MapType;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.physics.GameEngine;
import nl.soccar.physics.models.BallPhysics;
import nl.soccar.physics.models.CarPhysics;
import nl.soccar.physics.models.ObstaclePhysics;
import nl.soccar.ui.DisplayConstants;
import nl.soccar.ui.drawable.GameCanvas;
import nl.soccar.ui.fx.GameCanvasFx;
import nl.soccar.ui.fx.drawable.*;
import nl.soccar.ui.rmi.ClientController;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author PTS34A
 */
public class GameViewFXMLController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private GameCanvas gameCanvas;
    private Color textColor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        float width = (float) dimension.getWidth();
        float height = (float) dimension.getHeight();

        anchorPane.setPrefWidth(width);
        anchorPane.setPrefHeight(height);
        canvas.setWidth(width);
        canvas.setHeight(height);
        canvas.setFocusTraversable(true);

        Session session = ClientController.getInstance().getCurrentPlayer().getCurrentSession(); // Will never be null.
        initializeGameCanvas(session);
        initializeMap(session, gameCanvas);
        initializeScoreboard(session, gameCanvas);
        initializeNotification(session, gameCanvas);

        gameCanvas.start();
    }

    /**
     * Method that spawns an entity on the map.
     * 
     * @param entity The enitty that should be spawned, not null.
     */
    public void spawnEntity(Entity entity) {
        GameEngine engine = gameCanvas.getGameEngine();

        if (entity instanceof Car) {
            ClientController controller = ClientController.getInstance();
            Session session = controller.getCurrentPlayer().getCurrentSession();
            Room room = session.getRoom();

            Car car = (Car) entity;
            CarPhysics physics = new CarPhysics(car, engine.getWorld());

            Player player = car.getPlayer();
            TeamColour colour = room.getTeamBlue().getPlayers().contains(player) ? TeamColour.BLUE : TeamColour.RED;

            CarUiFx carUiFx = new CarUiFx(gameCanvas, car, physics, colour, textColor);
            gameCanvas.addDrawable(carUiFx);

            engine.getGame().getMap().addCar(car);
            engine.addCar(player, physics);

            initializeBoostMeter(car, gameCanvas);
        } else if (entity instanceof Obstacle) {
            Obstacle obstacle = (Obstacle) entity;
            ObstaclePhysics physics = new ObstaclePhysics(obstacle, engine.getWorld());

            ObstacleUiFx obstacleUiFx = new ObstacleUiFx(gameCanvas, obstacle, physics);
            gameCanvas.addDrawable(obstacleUiFx);

            engine.addWorldObject(physics);
        } else if (entity instanceof Ball) {
            Ball ball = (Ball) entity;
            BallPhysics physics = new BallPhysics(ball, engine.getWorld());

            BallUiFx ballUiFx = new BallUiFx(gameCanvas, ball, physics);
            gameCanvas.addDrawable(ballUiFx);

            engine.addWorldObject(physics);
        }
    }

    /**
     * Method that sets the gameStatus to paused/ unpaused.
     * 
     * @param paused Game is paused, not null.
     */
    public void setPaused(boolean paused) {
        gameCanvas.getGameEngine().getGame().setPaused(paused);
    }

    /**
     * Stops the underlying canvas from drawing.
     */
    public void stop() {
        gameCanvas.stop();
        gameCanvas = null;
    }

    private void initializeGameCanvas(Session session) {
        gameCanvas = new GameCanvasFx(session, canvas.getGraphicsContext2D());
        gameCanvas.initialize();

        textColor = session.getGame().getGameSettings().getMapType() == MapType.ICE ? Color.BLACK : Color.WHITE;
    }

    private void initializeMap(Session session, GameCanvas canvas) {
        Map map = session.getGame().getMap();
        MapUiFx mapUiFx = new MapUiFx(canvas, map, textColor);

        canvas.addDrawable(mapUiFx);
    }

    private void initializeScoreboard(Session session, GameCanvas canvas) {
        ScoreBoardUiFx scoreBoardUiFx = new ScoreBoardUiFx(canvas, session.getGame());
        canvas.addDrawable(scoreBoardUiFx);
    }

    private void initializeBoostMeter(Car car, GameCanvas canvas) {
        BoostMeterUiFx boostMeterUiFx = new BoostMeterUiFx(canvas, car);
        canvas.addDrawable(boostMeterUiFx);
    }

    private void initializeNotification(Session session, GameCanvas canvas) {
        float x = (float) session.getGame().getMap().getSize().getWidth() / 2;
        float y = (float) session.getGame().getMap().getSize().getHeight() / 2;

        Notification notification = new Notification(x, y, 0, DisplayConstants.NOTIFICATION_DISPLAY_TIME, textColor);
        NotificationUiFx notificationUiFx = new NotificationUiFx(canvas, notification);

        session.getGame().setNotification(notification);
        canvas.addDrawable(notificationUiFx);
    }

    /**
     * Gets the GameCanvas.
     * 
     * @return GameCanvas, the gameCanvas.
     */
    public GameCanvas getGameCanvas() {
        return gameCanvas;
    }

}
