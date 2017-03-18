package fr.ram.imagetreatment.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;

import java.io.IOException;

import fr.ram.imagetreatment.Fragments.MainActivityBackButtonDialogFragment;
import fr.ram.imagetreatment.Fragments.SeekbarHueColorDialogFragment;
import fr.ram.imagetreatment.Fragments.SeekbarValueDialogFragment;
import fr.ram.imagetreatment.Fragments.SizeMaskDialogFragment;
import fr.ram.imagetreatment.R;
import fr.ram.imagetreatment.Treatments.ColorFilter;
import fr.ram.imagetreatment.Treatments.Contrast;
import fr.ram.imagetreatment.Treatments.Convolution.GaussianBlur;
import fr.ram.imagetreatment.Treatments.Convolution.Laplacian;
import fr.ram.imagetreatment.Treatments.Convolution.AverageBlur;
import fr.ram.imagetreatment.Treatments.Convolution.Sobel;
import fr.ram.imagetreatment.Treatments.FilterChoiceEnum;
import fr.ram.imagetreatment.Treatments.HistogramEqualization;
import fr.ram.imagetreatment.Treatments.HueChoice;
import fr.ram.imagetreatment.Treatments.OverExposure;
import fr.ram.imagetreatment.Treatments.Sepia;
import fr.ram.imagetreatment.Treatments.ShadesOfGray;
import fr.ram.imagetreatment.CustomViews.CustomImageView;
import fr.ram.imagetreatment.Util.ImageFile;
import fr.ram.imagetreatment.Util.PermissionUtil;

public class ImageTreatmentActivity extends AppCompatActivity {
    private Bitmap imageBitmap;
    private Uri photoUri;
    private CustomImageView imageView;
    private Toolbar toolbar;
    private HorizontalScrollView bottomBar;
    private RelativeLayout imageContainer;
    private FilterChoiceEnum option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_treatment);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageView = (CustomImageView) findViewById(R.id.imageView);
        bottomBar = (HorizontalScrollView) findViewById(R.id.bottomBar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        photoUri = (Uri) extras.getParcelable("image");

        try {
            imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
            imageBitmap = imageBitmap.copy(Bitmap.Config.ARGB_8888, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageView.setImageBitmap(imageBitmap);

        PermissionUtil.verifyStoragePermissions(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onHomePressed();
                break;
            case R.id.resetImageButton:
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
                    imageBitmap = imageBitmap.copy(Bitmap.Config.ARGB_8888, true);
                    imageView.setImageBitmap(imageBitmap);
                    imageView.setImageModified(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.saveButton:
                if (imageView.getImageModified()) {
                    ImageFile.saveImage(this, imageBitmap);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onHomePressed() {
        if (imageView.getImageModified()) {
            Bundle args = new Bundle();
            args.putParcelable("imageBitmap", imageBitmap);

            DialogFragment fragmentLeave = new MainActivityBackButtonDialogFragment();
            fragmentLeave.setArguments(args);
            fragmentLeave.show(getSupportFragmentManager(), null);
        } else {
            this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        onHomePressed();
    }

    public CustomImageView getImageView() {
        return imageView;
    }

    /**
     * OnClick event
     */
    protected void toShadesOfGray(View view) {
        ShadesOfGray shadesOfGray = new ShadesOfGray();
        shadesOfGray.compute(ImageTreatmentActivity.this, imageView, null);
    }

    protected void toSepia(View view) {
        Sepia sepia = new Sepia();
        sepia.compute(ImageTreatmentActivity.this, imageView, null);
    }

    protected void choiceHue(View view) {
        option = FilterChoiceEnum.HUE;
        SeekbarHueColorDialogFragment newFragments = new SeekbarHueColorDialogFragment();
        newFragments.show(getFragmentManager(), "choice hue");
    }

    protected void colorFilter(View view) {
        option = FilterChoiceEnum.COLOR;
        SeekbarHueColorDialogFragment newFragments = new SeekbarHueColorDialogFragment();
        newFragments.show(getFragmentManager(), "colorFilter");
    }

    protected void exposure(View view) {
        SeekbarValueDialogFragment newFragments = new SeekbarValueDialogFragment();
        newFragments.show(getFragmentManager(), "overexposure");
    }

    protected void averageBlur(View view) {
        SizeMaskDialogFragment newFragment = new SizeMaskDialogFragment();
        Bundle fragmentArgs = new Bundle();
        fragmentArgs.putString("filter", "averageBlur");
        newFragment.setArguments(fragmentArgs);
        newFragment.show(getFragmentManager(), "sizeMask");
    }

    protected void gaussianBlur(View view) {
        SizeMaskDialogFragment newFragment = new SizeMaskDialogFragment();
        Bundle fragmentArgs = new Bundle();
        fragmentArgs.putString("filter", "gaussianBlur");
        newFragment.setArguments(fragmentArgs);
        newFragment.show(getFragmentManager(), "sizeMask");
    }

    protected void laplacianFilter(View view) {
        Laplacian l = new Laplacian();
        l.compute(ImageTreatmentActivity.this, imageView, null);
    }

    protected void sobelFilter(View view) {
        Sobel s = new Sobel();
        s.compute(ImageTreatmentActivity.this, imageView, null);
    }

    protected void contrast(View view) {
        Contrast c = new Contrast();
        c.compute(ImageTreatmentActivity.this, imageView, null);
    }

    protected void histogramEqualization(View view) {
        HistogramEqualization he = new HistogramEqualization();
        he.compute(ImageTreatmentActivity.this, imageView, null);
    }

    /**
     * Functions Dialogfragment
     */
    public void hueChoice(int hue) {
        HueChoice hueChoice = new HueChoice();
        Bundle seekData = new Bundle();
        seekData.putInt("value", hue);
        hueChoice.compute(ImageTreatmentActivity.this, imageView, seekData);
    }

    public void filterColor(int color) {
        ColorFilter colorFilter = new ColorFilter();
        Bundle seekData = new Bundle();
        seekData.putInt("color", color);
        colorFilter.compute(ImageTreatmentActivity.this, imageView, seekData);
    }

    public void overExposureTreatment(int value) {
        OverExposure overExposure = new OverExposure();
        Bundle seekData = new Bundle();
        seekData.putInt("value", value);
        overExposure.compute(ImageTreatmentActivity.this, imageView, seekData);
    }

    public void averageBlurTreatment(int maskSize) {
        AverageBlur m = new AverageBlur();
        Bundle seekData = new Bundle();
        seekData.putInt("mask_size", maskSize);
        m.compute(ImageTreatmentActivity.this, imageView, seekData);
    }

    public void gaussianFilterTreatment(int maskSize) {
        GaussianBlur g = new GaussianBlur();
        Bundle seekData = new Bundle();
        seekData.putInt("mask_size", maskSize);
        g.compute(ImageTreatmentActivity.this, imageView, seekData);
    }

    public FilterChoiceEnum getOption() {
        return option;
    }

    public void processFinish(Bitmap result) {
        imageView.setImageBitmap(result);
        imageView.setImageModified(true);
    }
}
