package nl.soccar.ui.fx.drawable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;
import nl.soccar.library.Notification;
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
public class NotificationUiFx extends Drawable<Notification> {

    /**
     * Initiates a new NotificationUiFx Object using the given parameters.
     *
     * @param canvas       The canvas on which this Notification is placed.
     * @param notification The notification model to keep track of.
     */
    public NotificationUiFx(GameCanvas canvas, Notification notification) {
        super(canvas, notification);
    }

    @Override
    public void draw(GraphicsContext context) {
        Notification notification = super.getModel();

        if (!notification.isActive()) {
            return;
        }

        float x = PhysicsUtilities.toPixelX(notification.getX());
        float y = PhysicsUtilities.toPixelY(notification.getY());

        context.setLineWidth(DisplayConstants.NOTIFICATION_OUTLINE_WIDTH);
        context.setTextAlign(TextAlignment.CENTER);
        context.setFont(DisplayConstants.NOTIFICATION_FONT);
        context.setFill(DisplayConstants.NOTIFICATION_FILL);
        context.setStroke(DisplayConstants.NOTIFICATION_STROKE);
        context.fillText(notification.getContent(), x, y);
        context.strokeText(notification.getContent(), x, y);
    }

}
