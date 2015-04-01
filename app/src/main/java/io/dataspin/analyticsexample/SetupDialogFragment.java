package io.dataspin.analyticsexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import io.dataspin.analyticsSDK.DataspinManager;

/**
 * Created by rafal on 01.04.15.
 */
public class SetupDialogFragment extends DialogFragment {

    private TextView clientName;
    private TextView apiKeyField;
    private TextView versionField;

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String name, String key, String version);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View v = inflater.inflate(R.layout.setup_dialog, null);
        builder.setView(v)
                .setPositiveButton("Setup", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        clientName = (TextView) v.findViewById(R.id.clientName);
                        apiKeyField = (TextView) v.findViewById(R.id.apiKeyField);
                        versionField = (TextView) v.findViewById(R.id.versionField);

                        mListener.onDialogPositiveClick(SetupDialogFragment.this,
                                clientName.getText().toString(),
                                apiKeyField.getText().toString(),
                                versionField.getText().toString());

                        Log.i("SetupDialogFragment", "Positive Click");
                    }
                });
                /*.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });qsxw3d3ex2e3ex3exs
                */
        // Create the AlertDialog object and return it
        return builder.create();
    }
}