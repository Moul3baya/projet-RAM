package fr.ram.imagetreatment.Treatments.Convolution;

import android.graphics.Bitmap;
import android.os.Bundle;

import fr.ram.imagetreatment.CustomViews.CustomImageView;

/**
 * Created by Maxime on 10/02/2017.
 */

public class AverageBlur extends Convolution {
    @Override
    public Bitmap _compute(CustomImageView img, Bundle args) {
        int maskSize = args.getInt("mask_size");
        double[][] mask = new double[maskSize][maskSize];
        for (int i = 0; i < maskSize; i++)
            for (int j = 0; j < maskSize; j++)
                mask[i][j] = 1.0 / (maskSize * maskSize);

        args.putInt("nbMask", 1);
        args.putSerializable("mask", mask);
        args.putInt("min",0);
        args.putInt("max",255);

        return super._compute(img, args);
    }
}
