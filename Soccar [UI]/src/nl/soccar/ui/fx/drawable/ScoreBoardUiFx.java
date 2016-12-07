package nl.soccar.ui.fx.drawable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;
import nl.soccar.library.Game;
import nl.soccar.library.enumeration.EventType;
import nl.soccar.ui.DisplayConstants;
import nl.soccar.ui.drawable.Drawable;
import nl.soccar.ui.drawable.GameCanvas;
import nl.soccar.ui.util.PhysicsUtilities;

/**
 * A ScoreBoardUiFx object represents a JavaFX Drawable of the Scoreboard.
 * It keeps track of the Notification and NotificationPhysics models and provides methods to draw and
 * update the models.
 *
 * @author PTS34A
 */
public class ScoreBoardUiFx extends Drawable<Game> {

    /**
     * Initiates a new NotificationUiFx Object using the given parameters.
     *
     * @param canvas The canvas on which this Notification is placed.
     */
    public ScoreBoardUiFx(GameCanvas canvas, Game game) {
        super(canvas, game);
    }

    @Override
    public void draw(GraphicsContext context) {
        float width, height, x, y;
        Game game = super.getModel();

        // Prepare text to display
        String text = String.format("%d - %s - %d", game.getEventCountByType(EventType.GOAL_BLUE), game.getTimeLeftString(), game.getEventCountByType(EventType.GOAL_RED));

        width = PhysicsUtilities.toPixelWidth(DisplayConstants.SCOREBOARD_WIDTH);
        height = PhysicsUtilities.toPixelHeight(DisplayConstants.SCOREBOARD_HEIGHT);
        x = PhysicsUtilities.toPixelX((float) (game.getMap().getSize().getWidth() / 2) - (DisplayConstants.SCOREBOARD_WIDTH / 2));//PhysicsUtilities.toPixelX(game.getX());
        y = PhysicsUtilities.toPixelY((float) game.getMap().getSize().getHeight());

        // Draw scoreboard background
        context.setLineWidth(DisplayConstants.SCOREBOARD_BG_OUTLINE_WIDTH);
        context.setFill(DisplayConstants.SCOREBOARD_BG_FILL);
        context.setStroke(DisplayConstants.SCOREBOARD_BG_STROKE);
        context.fillRect(x, y, width, height);
        context.strokeRect(x, y, width, height);

        x = PhysicsUtilities.toPixelX((float) game.getMap().getSize().getWidth() / 2);//PhysicsUtilities.toPixelX(game.getX());
        y = PhysicsUtilities.toPixelY((float) game.getMap().getSize().getHeight() - 4);//PhysicsUtilities.toPixelY(game.getY());

        // Draw scoreboard text
        context.setTextAlign(TextAlignment.CENTER);
        context.setFont(DisplayConstants.SCOREBOARD_FONT);
        context.setFill(DisplayConstants.SCOREBOARD_TEXT_FILL);
        context.setStroke(DisplayConstants.SCOREBOARD_TEXT_STROKE);
        context.setLineWidth(DisplayConstants.SCOREBOARD_TEXT_OUTLINE_WIDTH);
        context.fillText(text, x, y);
        context.strokeText(text, x, y);
    }

}
