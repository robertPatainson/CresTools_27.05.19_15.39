package com.lafayeboulhalfa.app.crestools;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by HP on 19.03.2019.
 */

public class Dialog extends AppCompatActivity{

    public Dialog(){

    }

    /**
     * Generate generic Dialog Box
     *
     * @param c The application context.
     * @param title The title of the dialog.
     * @param content The content of the dialog.
     */
    public static void getDialog(Context c, String title, String content){

        AlertDialog alertDialog = new AlertDialog.Builder(c).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(content);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }

    /**
     * Generate Dialog Box for debug
     *
     * @param c The application context.
     * @param e The exception caught.
     */

    public static void getDebug(Context c, Exception e){

        AlertDialog alertDialog = new AlertDialog.Builder(c).create();
        alertDialog.setTitle("Debug : ");
        alertDialog.setMessage(e.toString());
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }

    /**
     * Generate Dialog Box for user errors
     *
     * @param c The application context.
     * @param userError The error to adress.
     */

    public static void getUserError(Context c, String userError){

        AlertDialog alertDialog = new AlertDialog.Builder(c).create();
        alertDialog.setTitle("Error : ");
        alertDialog.setIcon(R.drawable.ic_error);
        alertDialog.setMessage(userError);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }

    public static void getUserInfo(Context c, String userInfo){

        AlertDialog alertDialog = new AlertDialog.Builder(c).create();
        alertDialog.setTitle("Note : ");
        alertDialog.setIcon(R.drawable.ic_info);
        alertDialog.setMessage(userInfo);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void applyDim(@NonNull ViewGroup parent, float dimAmount){
        Drawable dim = new ColorDrawable(Color.BLACK);
        dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
        dim.setAlpha((int) (255 * dimAmount));

        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.add(dim);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void clearDim(@NonNull ViewGroup parent) {
        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.clear();
    }
}
