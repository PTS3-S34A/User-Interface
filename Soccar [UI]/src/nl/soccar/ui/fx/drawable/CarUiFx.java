package nl.soccar.ui.fx.drawable;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import nl.soccar.library.Car;
import nl.soccar.library.enumeration.HandbrakeAction;
import nl.soccar.library.enumeration.SteerAction;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.library.enumeration.ThrottleAction;
import nl.soccar.physics.PhysicsConstants;
import nl.soccar.physics.models.CarPhysics;
import nl.soccar.physics.models.WheelPhysics;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.ui.DisplayConstants;
import nl.soccar.ui.drawable.GameCanvas;
import nl.soccar.ui.drawable.PhysicsDrawable;
import nl.soccar.ui.input.InputController;
import nl.soccar.ui.rmi.ClientController;
import nl.soccar.ui.util.ImageUtilities;
import nl.soccar.ui.util.PhysicsUtilities;
import nl.socnet.message.PlayerMovedMessage;

import java.util.List;

/**
 * A CarUiFx object represents a JavaFX Drawable of a Car. It keeps track of the
 * Car and CarPhysics models and provides methods to draw and update the models.
 *
 * @author PTS34A
 */
public class CarUiFx extends PhysicsDrawable<Car, CarPhysics> {

    private static final Font PLAYER_FONT;
    private static final Color COLOR_WHEEL;

    static {
        COLOR_WHEEL = Color.grayRgb(50);
        PLAYER_FONT = new Font("Arial", 20);
    }

    private Color textColor;
    private ThrottleAction previousThrottleAction;
    private SteerAction previousSteerAction;
    private HandbrakeAction previousHandbrakeAction;

    private Image carTexture;

    /**
     * Initiates a new CarUiFx Object using the given parameters.
     *
     * @param canvas  The canvas on which this Car is placed.
     * @param car     The model to keep track of.
     * @param physics The physics-model to keep track of.
     */
    public CarUiFx(GameCanvas canvas, Car car, CarPhysics physics, TeamColour colour, Color textColor) {
        super(canvas, car, physics);

        previousThrottleAction = ThrottleAction.IDLE;
        previousSteerAction = SteerAction.NONE;
        previousHandbrakeAction = HandbrakeAction.INACTIVE;

        carTexture = ImageUtilities.getCarImage(car.getCarType(), colour);
        this.textColor = textColor;

    }

    private void drawBody(GraphicsContext gc) {
        Car car = super.getModel();

        float x = PhysicsUtilities.toPixelX(car.getX());
        float y = PhysicsUtilities.toPixelY(car.getY());
        float width = PhysicsUtilities.toPixelWidth(car.getWidth());
        float height = PhysicsUtilities.toPixelHeight(car.getHeight());

        gc.save(); // Save the canvas so we can draw a rotated rectangle.

        gc.translate(x, y); // Set the origin point of the rotation.
        gc.rotate(-car.getDegree()); // Set the angle of the rotation.
        gc.drawImage(carTexture, -width / 2, -height / 2, width, height); // TODO: Find a solution for anti-aliasing

        gc.restore(); // Restore canvas to display a rotated image.

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(PLAYER_FONT);
        gc.setFill(textColor);
        gc.fillText(car.getPlayer().getUsername(), x, y - height / 1.5); // Draw playername.

    }

    private void drawWheel(WheelPhysics wheel, GraphicsContext gc) {
        float x = PhysicsUtilities.toPixelX(wheel.getX());
        float y = PhysicsUtilities.toPixelY(wheel.getY());
        float width = PhysicsUtilities.toPixelWidth(wheel.getWidth());
        float height = PhysicsUtilities.toPixelHeight(wheel.getHeight());

        gc.save();

        gc.translate(x, y); // Set the origin point of the rotation.
        gc.rotate(-wheel.getDegree()); // Set the angle of the rotation.
        gc.setFill(COLOR_WHEEL);
        gc.fillRect(-width / 2, -height / 2, width, height); // Draw the rectangle from the top left.

        gc.restore();
    }

    private void drawBoostTrail(List<Point2D> trail, GraphicsContext gc) {

        double prevX;
        double prevY;
        double x;
        double y;
        double o = 0.0;

        // TODO: Boost color should be the color of the team the player is in.
        int r = 30;
        int g = 30;
        int b = 230;

        // Cancel when there is no trail.
        if (trail.isEmpty()) {
            return;
        }

        // Line settings
        gc.setLineWidth(DisplayConstants.BOOST_TRAIL_WIDTH);
        gc.setLineCap(StrokeLineCap.ROUND);

        // Draw trail line
        for (int i = 1; i < trail.size(); i++) {

            gc.setStroke(Color.rgb(r, g, b, o));

            prevX = PhysicsUtilities.toPixelX((float) trail.get(i - 1).getX());
            prevY = PhysicsUtilities.toPixelY((float) trail.get(i - 1).getY());

            x = PhysicsUtilities.toPixelX((float) trail.get(i).getX());
            y = PhysicsUtilities.toPixelY((float) trail.get(i).getY());

            gc.strokeLine(prevX, prevY, x, y);
            o = Math.min(1.0F, o + (1.0F / PhysicsConstants.CAR_BOOST_TRAIL_SIZE));
        }
    }

    @Override
    public void draw(GraphicsContext context) {
        CarPhysics physics = super.getPhysicsModel();

        if (ClientController.getInstance().getCurrentPlayer().equals(super.getModel().getPlayer())) {
            InputController controller = InputController.getInstance();

            SteerAction steerAction = controller.getSteerAction();
            HandbrakeAction handbrakeAction = controller.getHandbrakeAction();
            ThrottleAction throttleAction = controller.getThrottleAction();

            super.getModel().setSteerAction(steerAction);
            super.getModel().setHandbrakeAction(handbrakeAction);
            super.getModel().setThrottleAction(throttleAction);

            // TODO : Change location of sending this message (Ugly implementation for now)
            if (previousThrottleAction != throttleAction || previousSteerAction != steerAction || previousHandbrakeAction != handbrakeAction) {
                Connection connection = ClientController.getInstance().getCurrentConnection();
                connection.send(new PlayerMovedMessage(steerAction, handbrakeAction, throttleAction));

                previousThrottleAction = throttleAction;
                previousSteerAction = steerAction;
                previousHandbrakeAction = handbrakeAction;
            }
        }

        this.drawBoostTrail(physics.getTrail(), context);
        physics.getWheels().forEach(w -> drawWheel(w, context));

        this.drawBody(context);
    }
}
