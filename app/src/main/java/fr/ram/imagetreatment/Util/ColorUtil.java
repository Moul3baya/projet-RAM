package fr.ram.imagetreatment.Util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.util.StringBuilderPrinter;

/**
 * Created by Rémi on 28/02/2017.
 */

public class ColorUtil {
    public static final int MIN_VALUE_COLOR = 0;
    public static final int MAX_VALUE_COLOR_RGB = 255;
    public static final int MAX_VALUE_COLOR_HSV = 360;

    /***
     * Return the value of the grey pixel created with the colors passed in parameters
     * @param red The red value
     * @param green The green value
     * @param blue The blue value
     * @return The new RGB value to create grey
     */
    public static int pixelToGrey(int red, int green, int blue) {
        red = (red * 3) / 10;
        green = (green * 59) / 100;
        blue = (blue * 11) / 100;
        return red + green + blue;
    }

    /***
     * Return the values of a Sepia pixel
     * @param red The input red value
     * @param green The input green value
     * @param blue The input blue value
     * @return The array of values
     *      [O] red
     *      [1] green
     *      [2] blue
     */
    public static int[] pixelToSepia(int red, int green, int blue) {
        int newRed = (red * 393 / 1000) + (green * 769 / 1000) + (blue * 189 / 1000);
        int newGreen = (red * 349 / 1000) + (green * 686 / 1000) + (blue * 168 / 1000);
        int newBlue = (red * 272 / 1000) + (green * 534 / 1000) + (blue * 131 / 1000);
        return new int[] {newRed, newGreen, newBlue};
    }

    /***
     * If the color value if over MAX_VALUE_COLOR_RGB, set it to MAX_VALUE_COLOR_RGB
     * If it is under MIN_VALUE_COLOR, set it to MIN_VALUE_COLOR
     * @param pixel The color value
     * @return The new color value
     */
    public static int overFlowColor(int pixel) {
        if (pixel > MAX_VALUE_COLOR_RGB)
            pixel = MAX_VALUE_COLOR_RGB;
        else if (pixel < MIN_VALUE_COLOR)
            pixel = MIN_VALUE_COLOR;
        return pixel;
    }

    /***
     * Rescale the value between the RGB interval
     * @param value The pixel color value
     * @param min The former minimum value of the value interval
     * @param max The former maximum value of the value interval
     * @return The new pixel color value
     */
    public static int changeColorInterval(int value, int min, int max) {
        return ((value - min) * MAX_VALUE_COLOR_RGB) / (max - min);
    }

}
