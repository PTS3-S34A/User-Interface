package nl.soccar.ui.fx.drawable;

import javafx.scene.canvas.GraphicsContext;
import nl.soccar.library.Car;
import nl.soccar.ui.DisplayConstants;
import nl.soccar.ui.drawable.Drawable;
import nl.soccar.ui.drawable.GameCanvas;
import nl.soccar.ui.util.PhysicsUtilities;

/**
 * A NotificationUiFx object represents a JavaFX Drawable of a Notification.
 * It keeps track of the Notification and NotificationPhysics models and provides methods to draw and
 * update the models.
 *
 * @author PTS34A
 */
public class BoostMeterUiFx extends Drawable<Car> {

    /**
     * Initiates a new NotificationUiFx Object using the given parameters.
     *
     * @param canvas The canvas on which this Notification is placed.
     */
    public BoostMeterUiFx(GameCanvas canvas, Car car) {
        super(canvas, car);
    }

    @Override
    public void draw(GraphicsContext context) {
        Car car = super.getModel();

        float x = PhysicsUtilities.toPixelX(DisplayConstants.BOOST_METER_X);
        float y = PhysicsUtilities.toPixelY(DisplayConstants.BOOST_METER_Y);
        float w = PhysicsUtilities.toPixelWidth(DisplayConstants.BOOST_METER_WIDTH);
        float h = PhysicsUtilities.toPixelHeight(DisplayConstants.BOOST_METER_HEIGHT);

        context.setStroke(DisplayConstants.BOOST_METER_STROKE_COLOR);
        context.setFill(DisplayConstants.BOOST_METER_FILL_COLOR);
        context.fillRect(x, y, w / 100.0F * car.getBoostAmount(), h);
    }

}
