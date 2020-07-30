package com.alfred.karela.schedule;

/**
 * Created by Alfred on 06-03-2017.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogManager {

    public void showAlertDialog(Context context, String title, String message,
                                 Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);
        // Setting Dialog Message
        alertDialog.setMessage(message);
        if(status != null)
        alertDialog.setIcon((status) ? R.drawable.ic_edit : R.drawable.img_location);
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                }
            });
        // Showing Alert Message
        alertDialog.show();
        }
}
