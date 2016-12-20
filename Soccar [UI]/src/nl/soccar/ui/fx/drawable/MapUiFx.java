package nl.soccar.ui.fx.drawable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import nl.soccar.library.Map;
import nl.soccar.library.MapConstants;
import nl.soccar.ui.DisplayConstants;
import nl.soccar.ui.drawable.Drawable;
import nl.soccar.ui.drawable.GameCanvas;
import nl.soccar.ui.util.MapUtilities;
import nl.soccar.ui.util.PhysicsUtilities;

/**
 * A MapUiFx object represents a JavaFX Drawable of a Map. It keeps track of the
 * Map model and provides methods to draw and update the model.
 *
 * @author PTS34A
 */
public class MapUiFx extends Drawable<Map> {

    private static final Image TEXTURE_CHRISTMAS;
    private static final Image TEXTURE_ICE;
    private static final Image TEXTURE_GRASS;
    private static final float WALL_WIDTH;
    private static final float CORNER_SIZE;

    static {
        TEXTURE_ICE = new Image(DisplayConstants.LOCATION_TEXTURE_ICE);
        TEXTURE_CHRISTMAS = new Image(DisplayConstants.LOCATION_TEXTURE_CHRISTMAS);
        TEXTURE_GRASS = new Image(DisplayConstants.LOCATION_TEXTURE_GRASS);
        WALL_WIDTH = 5.0F;
        CORNER_SIZE = 15.0F;
    }

    private Color lineColor;

    /**
     * Initiates a new MapUiFx Object using the given parameters.
     *
     * @param canvas The canvas on which this Map is placed.
     * @param model The model to keep track of.
     * @param lineColor The color of the drawable lines.
     */
    public MapUiFx(GameCanvas canvas, Map model, Color lineColor) {
        super(canvas, model);
        this.lineColor = lineColor;
    }

    @Override
    public void draw(GraphicsContext context) {
        Map map = super.getModel();

        Rectangle size = map.getSize();
        double width = PhysicsUtilities.toPixelWidth((float) size.getWidth());
        double height = PhysicsUtilities.toPixelHeight((float) size.getHeight());

        switch (map.getMapType()) {
            case CHRISTMAS:
                context.drawImage(TEXTURE_CHRISTMAS, 0, 0, width, height);
                break;
            case ICE:
                context.drawImage(TEXTURE_ICE, 0, 0, width, height);
                break;
            case GRASSLAND:
                context.drawImage(TEXTURE_GRASS, 0, 0, width, height);
                break;
            default:
                throw new UnsupportedOperationException("This exception should never happen.");
        }

        // Draw the grid lines on the map.
        drawGridLines(context, size);
    }

    /**
     * Method that draws the grid lines on the map.
     *
     * @param context The graphics context on which the grid lines need to be
     * drawn.
     * @param mapSize The size of the map on which the grid lines need to be
     * drawn.
     */
    private void drawGridLines(GraphicsContext context, Rectangle mapSize) {
        Map map = super.getModel();

        double mapWidth = PhysicsUtilities.toPixelWidth((float) mapSize.getWidth());
        double mapHeight = PhysicsUtilities.toPixelHeight((float) mapSize.getHeight());

        double centreX = PhysicsUtilities.toPixelX(MapUtilities.getCentreX(mapSize));
        double centreY = PhysicsUtilities.toPixelY(MapUtilities.getCentreY(mapSize));

        double lineWidth = PhysicsUtilities.toPixelWidth(MapConstants.LINE_WIDTH);
        double fieldMargin = PhysicsUtilities.toPixelWidth(MapConstants.FIELD_MARGIN);

        double centreCircleSize = PhysicsUtilities.toPixelWidth(MapConstants.CENTRE_CIRCLE_SIZE);
        double centreSpotSize = PhysicsUtilities.toPixelWidth(MapConstants.CENTRE_SPOT_SIZE);

        double boxPositionY = PhysicsUtilities.toPixelY((float) (mapSize.getHeight() / 2) + (MapConstants.BOX_HEIGHT / 2));
        double boxWidth = PhysicsUtilities.toPixelWidth(MapConstants.BOX_WIDTH);
        double boxHeight = PhysicsUtilities.toPixelHeight(MapConstants.BOX_HEIGHT);

        Rectangle leftGoal = map.getGoalBlue();
        double leftGoalX = PhysicsUtilities.toPixelX((float) leftGoal.getX());
        double leftGoalY = PhysicsUtilities.toPixelY((float) leftGoal.getY());
        double leftGoalWidth = PhysicsUtilities.toPixelWidth((float) leftGoal.getWidth());
        double leftGoalHeight = PhysicsUtilities.toPixelHeight((float) leftGoal.getHeight());

        Rectangle rightGoal = map.getGoalRed();
        double rightGoalX = PhysicsUtilities.toPixelX((float) rightGoal.getX());
        double rightGoalY = PhysicsUtilities.toPixelY((float) rightGoal.getY());
        double rightGoalWidth = PhysicsUtilities.toPixelWidth((float) rightGoal.getWidth());
        double rightGoalHeight = PhysicsUtilities.toPixelHeight((float) rightGoal.getHeight());

        // Line color
        context.setStroke(lineColor);
        context.setFill(lineColor);

        // Line width
        context.setLineWidth(lineWidth);

        // Baseline
        context.strokeLine(
                centreX,
                fieldMargin,
                centreX,
                mapHeight - fieldMargin
        );

        // Centre circle
        context.strokeOval(
                centreX - (centreCircleSize / 2),
                centreY - (centreCircleSize / 2),
                centreCircleSize,
                centreCircleSize
        );

        // Centre spot
        context.fillOval(
                centreX - (centreSpotSize / 2),
                centreY - (centreSpotSize / 2),
                centreSpotSize,
                centreSpotSize
        );

        // Field
        context.strokeRect(
                fieldMargin,
                fieldMargin,
                mapWidth - (2 * fieldMargin),
                mapHeight - (2 * fieldMargin)
        );

        // Box left
        context.strokeRect(
                fieldMargin,
                boxPositionY,
                boxWidth,
                boxHeight
        );

        // Box right
        context.strokeRect(
                mapWidth - fieldMargin - boxWidth,
                boxPositionY,
                boxWidth,
                boxHeight
        );

        // Left goal
        context.strokeRect(leftGoalX, leftGoalY, leftGoalWidth, leftGoalHeight);

        // Right goal
        context.strokeRect(rightGoalX, rightGoalY, rightGoalWidth, rightGoalHeight);
    }

}
