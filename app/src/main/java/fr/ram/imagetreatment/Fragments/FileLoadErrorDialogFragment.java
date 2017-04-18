package fr.ram.imagetreatment.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import fr.ram.imagetreatment.R;

/**
 * Created by Rémi on 05/04/2017.
 */

public class FileLoadErrorDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.imagefile_load_ioexception_title);
        builder.setMessage(R.string.imagefile_load_ioexception_content);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().finish();
            }
        });

        return builder.create();
    }
}
