package io.dataspin.analyticsexample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import io.dataspin.analyticsSDK.DataspinManager;

/**
 * Created by rafal on 01.04.15.
 */
public class RegisterUserDialogFragment extends DialogFragment {

    private TextView registerUserName;
    private TextView registerUserSurname;
    private TextView registerUserEmail;
    private TextView registerUserGooglePlusId;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View v = inflater.inflate(R.layout.register_user_dialog, null);
        builder.setView(v)
                .setPositiveButton("Register", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        registerUserName = (TextView) v.findViewById(R.id.registerUserName);
                        registerUserSurname = (TextView) v.findViewById(R.id.registerUserSurname);
                        registerUserEmail = (TextView) v.findViewById(R.id.registerUserEmail);
                        registerUserGooglePlusId = (TextView) v.findViewById(R.id.registerUserPlusId);

                        DataspinManager.Instance().RegisterUser(registerUserName.getText().toString(),
                                registerUserSurname.getText().toString(),
                                registerUserEmail.getText().toString(),
                                registerUserGooglePlusId.getText().toString(),
                                null);
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