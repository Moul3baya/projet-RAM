package fr.ram.imagetreatment.Treatments.Convolution;

import android.graphics.Bitmap;
import android.os.Bundle;

import fr.ram.imagetreatment.CustomViews.CustomImageView;
import fr.ram.imagetreatment.Treatments.ShadesOfGrey;

/**
 * Created by Maxime on 10/02/2017.
 */

public class Sobel extends Convolution {
    @Override
    public Bitmap _compute(CustomImageView img, Bundle args) {
        int maskSize = 3;
        double[][] mask = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        double[][] mask2 = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
        ShadesOfGrey shadesOfGray = new ShadesOfGrey();
        shadesOfGray._compute(img, null);
        args = new Bundle();

        args.putInt("maskSize", 3);
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur._compute(img, args);

        args = new Bundle();
        args.putInt("nbMask", 2);
        args.putInt("maskSize", maskSize);
        args.putSerializable("mask", mask);
        args.putInt("mask2Size", maskSize);
        args.putSerializable("mask2", mask2);
        args.putInt("min",-4*255);
        args.putInt("max",4*255);
        return super._compute(img, args);
    }
}
