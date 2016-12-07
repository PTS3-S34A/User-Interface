package nl.soccar.ui.fx.controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import nl.soccar.library.*;
import nl.soccar.library.enumeration.Duration;
import nl.soccar.ui.DisplayConstants;
import nl.soccar.ui.fx.GameCanvasFx;
import nl.soccar.ui.fx.drawable.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.shape.Rectangle;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.physics.GameEngine;
import nl.soccar.ui.rmi.ClientController;
import nl.soccar.ui.drawable.GameCanvas;

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

//        Session session = ClientController.getInstance().getCurrentPlayer().getCurrentSession(); // Will never be null.
        Session session = new Session("Test", "");
        GameCanvas gameCanvas = new GameCanvasFx(session.getGame(), canvas.getGraphicsContext2D());
        session.getGame().getGameSettings().setDuration(Duration.MINUTES_3);

        ClientController.getInstance().getCurrentPlayer().setCurrentSession(session);

        initializeMap(session, gameCanvas);
        initializeScoreboard(session, gameCanvas);
        initializeBall(session, gameCanvas);
        initializeCars(session, gameCanvas);
        initializeNotification(session, gameCanvas);

        GameEngine engine = gameCanvas.getGameEngine();
        engine.start();
    }

    private void initializeMap(Session session, GameCanvas canvas) {
        Map map = session.getGame().getMap();
        MapUiFx mapUiFx = new MapUiFx(canvas, map);

        canvas.addDrawable(mapUiFx);
        mapUiFx.addWalls();
    }

    private void initializeScoreboard(Session session, GameCanvas canvas) {
        ScoreBoardUiFx scoreBoardUiFx = new ScoreBoardUiFx(canvas, session.getGame());
        canvas.addDrawable(scoreBoardUiFx);
    }

    private void initializeBall(Session session, GameCanvas canvas) {
        BallUiFx ballUiFx = new BallUiFx(canvas, session.getGame().getMap().getBall());
        canvas.addDrawable(ballUiFx);
    }

    private void initializeCars(Session session, GameCanvas canvas) {
        Room room = session.getRoom();
        Map map = session.getGame().getMap();

        room.getTeamBlue().join(ClientController.getInstance().getCurrentPlayer());

        addCars(map, room.getTeamBlue(), canvas);
        addCars(map, room.getTeamRed(), canvas);
    }

    private void initializeBoostMeter(Car car, GameCanvas canvas) {
        BoostMeterUiFx boostMeterUiFx = new BoostMeterUiFx(canvas, car);
        canvas.addDrawable(boostMeterUiFx);
    }

    private void initializeNotification(Session session, GameCanvas canvas) {
        float x = (float) session.getGame().getMap().getSize().getWidth() / 2;
        float y = (float) session.getGame().getMap().getSize().getHeight() / 2;

        Notification notification = new Notification(x, y, 0, DisplayConstants.NOTIFICATION_DISPLAY_TIME);
        NotificationUiFx notificationUiFx = new NotificationUiFx(canvas, notification);

        session.getGame().setNotification(notification);
        canvas.addDrawable(notificationUiFx);
    }

    private void addCars(Map map, Team team, GameCanvas canvas) {
        List<Player> players = new ArrayList<>(team.getPlayers());
        Collections.shuffle(players);
        
        int teamSize = players.size();

        for (int i = 0; i < teamSize; i++) {
            Player player = players.get(i);
            addCar(canvas, map, player, team, teamSize, i);
        }
    }

    private void addCar(GameCanvas canvas, Map map, Player player, Team team, int teamSize, int number) {
        Rectangle size = map.getSize();
        float width = (float) size.getWidth();
        float height = (float) size.getHeight();
        
        Ball ball = map.getBall();
        float ballX = ball.getX();
        float ballY = ball.getY();

        TeamColour colour = team.getTeamColour();
        
        float x = colour == TeamColour.BLUE ? 30.0F : width - 30.0F;
        float y = (height / (teamSize + 1)) * (number + 1);
        float degree = getAngle(x, y, ballX, ballY) -90;

        Car car = new Car(x, y, degree, player.getCarType(), player);
        CarUiFx carUiFx = new CarUiFx(canvas, car, colour);

        canvas.addDrawable(carUiFx);
        initializeBoostMeter(car, canvas);
    }

    private static float getAngle(float sourceX, float sourceY, float targetX, float targetY) {
        float angle = (float) Math.toDegrees(Math.atan2(targetY - sourceY, targetX - sourceX));

        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }

}
