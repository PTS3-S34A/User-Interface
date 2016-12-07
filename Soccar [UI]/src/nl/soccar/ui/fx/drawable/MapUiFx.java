package nl.soccar.ui.fx.drawable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import nl.soccar.library.Map;
import nl.soccar.library.MapConstants;
import nl.soccar.library.enumeration.ObstacleType;
import nl.soccar.ui.DisplayConstants;
import nl.soccar.ui.drawable.Drawable;
import nl.soccar.ui.drawable.GameCanvas;
import nl.soccar.ui.fx.drawable.ObstacleUiFx.ObstacleBuilder;
import nl.soccar.ui.util.MapUtilities;
import nl.soccar.ui.util.PhysicsUtilities;
import org.jbox2d.dynamics.World;

/**
 * A MapUiFx object represents a JavaFX Drawable of a Map. It keeps track of the
 * Map model and provides methods to draw and update the model.
 *
 * @author PTS34A
 */
public class MapUiFx extends Drawable<Map> {

    private static final Image TEXTURE_MOON;
    private static final Image TEXTURE_DESERT;
    private static final Image TEXTURE_GRASS;
    private static final float WALL_WIDTH;
    private static final float CORNER_SIZE;

    static {
        TEXTURE_MOON = new Image(DisplayConstants.LOCATION_TEXTURE_MOON);
        TEXTURE_DESERT = new Image(DisplayConstants.LOCATION_TEXTURE_DESERT);
        TEXTURE_GRASS = new Image(DisplayConstants.LOCATION_TEXTURE_GRASS);
        WALL_WIDTH = 5.0F;
        CORNER_SIZE = 15.0F;
    }

    /**
     * Initiates a new MapUiFx Object using the given parameters.
     *
     * @param canvas The canvas on which this Map is placed.
     * @param model The model to keep track of.
     */
    public MapUiFx(GameCanvas canvas, Map model) {
        super(canvas, model);
    }

    /**
     * Method that calls all methods that add map obstacles to the map.
     */
    public void addWalls() {
        Map map = super.getModel();

        Rectangle size = map.getSize();
        float mapWidth = (float) size.getWidth();
        float mapHeight = (float) size.getHeight();

        addWestWalls(mapHeight);
        addEastWalls(mapWidth, mapHeight);
        addNorthAndSouthWalls(mapWidth, mapHeight);
        addCornerWalls(mapWidth, mapHeight);
    }

    /**
     * Method that adds the obstacle drawables to the map that represent the
     * west wall.
     *
     * @param mapHeight The height of the map.
     */
    private void addWestWalls(float mapHeight) {
        Map map = super.getModel();
        GameCanvas canvas = super.getGameCanvas();
        World world = canvas.getGameEngine().getWorld();

        Rectangle leftGoal = map.getGoalBlue();
        float leftGoalY = (float) leftGoal.getY();

        ObstacleUiFx westWallUpperUi = new ObstacleBuilder(canvas, world)
                .x(WALL_WIDTH / 2).y((mapHeight + leftGoalY) / 2).degree(0)
                .width(WALL_WIDTH).height(mapHeight - leftGoalY)
                .type(ObstacleType.WALL).build();

        ObstacleUiFx westWallLowerUi = new ObstacleBuilder(canvas, world)
                .x(WALL_WIDTH / 2).y((mapHeight - leftGoalY) / 2).degree(0)
                .width(WALL_WIDTH).height(mapHeight - leftGoalY)
                .type(ObstacleType.WALL).build();

        ObstacleUiFx westWallMiddleUi = new ObstacleBuilder(canvas, world)
                .x(-WALL_WIDTH).y(mapHeight / 2).degree(0)
                .width(WALL_WIDTH).height(mapHeight)
                .type(ObstacleType.WALL).build();

        canvas.addDrawable(westWallUpperUi);
        canvas.addDrawable(westWallLowerUi);
        canvas.addDrawable(westWallMiddleUi);
    }

    /**
     * Method that adds the obstacle drawables to the map that represent the
     * easts walls.
     *
     * @param mapWidth The width of the map.
     * @param mapHeight The height of the map.
     */
    private void addEastWalls(float mapWidth, float mapHeight) {
        Map map = super.getModel();
        GameCanvas canvas = super.getGameCanvas();
        World world = canvas.getGameEngine().getWorld();

        Rectangle rightGoal = map.getGoalRed();
        float rightGoalY = (float) rightGoal.getY();

        ObstacleUiFx eastWallUpperUi = new ObstacleBuilder(canvas, world)
                .x(mapWidth - (WALL_WIDTH / 2)).y((mapHeight + rightGoalY) / 2).degree(0)
                .width(WALL_WIDTH).height(mapHeight - rightGoalY)
                .type(ObstacleType.WALL).build();

        ObstacleUiFx eastWallLowerUi = new ObstacleBuilder(canvas, world)
                .x(mapWidth - (WALL_WIDTH / 2)).y((mapHeight - rightGoalY) / 2).degree(0)
                .width(WALL_WIDTH).height(mapHeight - rightGoalY)
                .type(ObstacleType.WALL).build();

        ObstacleUiFx eastWallMiddleUi = new ObstacleBuilder(canvas, world)
                .x(mapWidth + WALL_WIDTH).y(mapHeight / 2).degree(0)
                .width(WALL_WIDTH).height(mapHeight)
                .type(ObstacleType.WALL).build();

        canvas.addDrawable(eastWallUpperUi);
        canvas.addDrawable(eastWallLowerUi);
        canvas.addDrawable(eastWallMiddleUi);
    }

    /**
     * Method that adds the obstacle drawables to the map that represent the
     * north and south walls.
     *
     * @param mapWidth The width of the map.
     * @param mapHeight The height of the map.
     */
    private void addNorthAndSouthWalls(float mapWidth, float mapHeight) {
        GameCanvas canvas = super.getGameCanvas();
        World world = canvas.getGameEngine().getWorld();

        ObstacleUiFx northWallUi = new ObstacleBuilder(canvas, world)
                .x(mapWidth / 2).y(mapHeight - (WALL_WIDTH / 2)).degree(0)
                .width(mapWidth).height(WALL_WIDTH)
                .type(ObstacleType.WALL).build();

        ObstacleUiFx southWallUi = new ObstacleBuilder(canvas, world)
                .x(mapWidth / 2).y(WALL_WIDTH / 2).degree(0)
                .width(mapWidth).height(WALL_WIDTH)
                .type(ObstacleType.WALL).build();

        canvas.addDrawable(northWallUi);
        canvas.addDrawable(southWallUi);
    }

    /**
     * Method that adds the obstacle drawables to the map that represent the
     * nort and south walls.
     *
     * @param mapWidth The width of the map.
     * @param mapHeight The height of the map.
     */
    private void addCornerWalls(float mapWidth, float mapHeight) {
        GameCanvas canvas = super.getGameCanvas();
        World world = canvas.getGameEngine().getWorld();
        
        ObstacleUiFx northWestWallUi = new ObstacleBuilder(canvas, world)
                .x(0).y(mapHeight).degree(45)
                .width(CORNER_SIZE).height(CORNER_SIZE)
                .type(ObstacleType.WALL).build();

        ObstacleUiFx northEastWallUi = new ObstacleBuilder(canvas, world)
                .x(mapWidth).y(mapHeight).degree(45)
                .width(CORNER_SIZE).height(CORNER_SIZE)
                .type(ObstacleType.WALL).build();

        ObstacleUiFx southWestWallUi = new ObstacleBuilder(canvas, world)
                .x(0).y(0).degree(45)
                .width(CORNER_SIZE).height(CORNER_SIZE)
                .type(ObstacleType.WALL).build();

        ObstacleUiFx southEastWallUi = new ObstacleBuilder(canvas, world)
                .x(mapWidth).y(0).degree(45)
                .width(CORNER_SIZE).height(CORNER_SIZE)
                .type(ObstacleType.WALL).build();

        canvas.addDrawable(northWestWallUi);
        canvas.addDrawable(northEastWallUi);
        canvas.addDrawable(southWestWallUi);
        canvas.addDrawable(southEastWallUi);
    }

    @Override
    public void draw(GraphicsContext context) {
        Map map = super.getModel();

        Rectangle size = map.getSize();
        double width = PhysicsUtilities.toPixelWidth((float) size.getWidth());
        double height = PhysicsUtilities.toPixelHeight((float) size.getHeight());

        switch (map.getMapType()) {
            case DESERT:
                context.drawImage(TEXTURE_DESERT, 0, 0, width, height);
                break;
            case MOON:
                context.drawImage(TEXTURE_MOON, 0, 0, width, height);
                break;
            case GRASSLAND:
                context.drawImage(TEXTURE_GRASS, 0, 0, width, height);
                break;
            default:
                throw new UnsupportedOperationException("This exception should never happen.");
        }

        // Draw the grid lines on the map.
        drawGridLines(context, size);

        // Draw the scoreboard
        drawScoreboard(context, size);
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
        context.setStroke(Color.WHITE);
        context.setFill(Color.WHITE);

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

    private void drawScoreboard(GraphicsContext context, Rectangle mapSize) {
        float width = PhysicsUtilities.toPixelWidth(MapConstants.SCOREBOARD_WIDTH);
        float height = PhysicsUtilities.toPixelHeight(MapConstants.SCOREBOARD_HEIGHT);

        float x = PhysicsUtilities.toPixelX(MapUtilities.getCentreX(mapSize) - (MapConstants.SCOREBOARD_WIDTH / 2));
        float y = PhysicsUtilities.toPixelY((float) mapSize.getHeight());

        context.setFill(Color.BROWN);
        context.fillRect(x, y, width, height);
    }

}
