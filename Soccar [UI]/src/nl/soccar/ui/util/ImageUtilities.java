package nl.soccar.ui.util;

import javafx.scene.image.Image;
import nl.soccar.library.enumeration.CarType;
import nl.soccar.library.enumeration.TeamColour;

/**
 * Utility class that provides methods regarding used images.
 *
 * @author PTS34A
 */
public final class ImageUtilities {

    private ImageUtilities() {
        // Private method to prevent instantiation.
    }

    /**
     * Converts a CarType and TeamColour to an Image.
     *
     * @param carType The CarType of the image. This will be used to determine
     * what file the image should be loaded from.
     * @param teamColour The TeamColour of the image. This will be used to
     * determine what file the image should be loaded from.
     * @return An Image containing the correct imagesource.
     */
    public static Image getCarImage(CarType carType, TeamColour teamColour) {
        StringBuilder builder = new StringBuilder("resources/images/cars/");
        builder.append(carType.name().toLowerCase());
        builder.append("_");
        builder.append(teamColour.name().toLowerCase());
        builder.append("_texture.png");

        return new Image(builder.toString());
    }

}
