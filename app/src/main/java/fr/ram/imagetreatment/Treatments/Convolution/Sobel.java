package fr.ram.imagetreatment.Treatments.Convolution;

import android.graphics.Bitmap;
import android.os.Bundle;

import fr.ram.imagetreatment.Treatments.ShadesOfGrey;
import fr.ram.imagetreatment.Util.BundleArgs;
import fr.ram.imagetreatment.Util.ColorUtil;

/**
 * Created by Maxime on 10/02/2017.
 */

public class Sobel extends Convolution {
    @Override
    public Bitmap _compute(Bitmap bmp, Bundle args) {
        Bitmap returnBitmap;

        //creation of masks that will be applied to the pixel of the image
        int maskSize = 3;
        double[][] mask = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        double[][] mask2 = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
        //before applying the filter, image is transformed into a shade of gray
        ShadesOfGrey shadesOfGray = new ShadesOfGrey();
        returnBitmap = shadesOfGray._compute(bmp, null);

        args = new Bundle();
        args.putInt(BundleArgs.MASK_SIZE, 3);
        //apply a Gaussian blur in order to remove the imperfections
        GaussianBlur gaussianBlur = new GaussianBlur();
        returnBitmap = gaussianBlur._compute(returnBitmap, args);

        args = new Bundle();
        args.putInt(BundleArgs.NB_MASK, 2);
        //first mask
        args.putInt(BundleArgs.MASK_SIZE, maskSize);
        args.putSerializable(BundleArgs.MASK, mask);
        //second mask
        args.putInt(BundleArgs.MASK_2_SIZE, maskSize);
        args.putSerializable(BundleArgs.MASK_2, mask2);
        //value of all pixels with the mask is between min and max
        args.putInt(BundleArgs.MIN, -4 * ColorUtil.MAX_VALUE_COLOR_RGB);
        args.putInt(BundleArgs.MAX, 4 * ColorUtil.MAX_VALUE_COLOR_RGB);
        returnBitmap = super._compute(returnBitmap, args);

        return returnBitmap;
    }
}
