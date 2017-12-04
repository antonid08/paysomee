package client.simplepay.com.paysomee.ui.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

/**
 * Contains static helper method for displaying general purpose dialogs such as information, warning, confirmation etc.
 *
 * @author antonid08
 */
public class Dialogs {

    /**
     * Shows information dialog with Ok button.
     *
     * @param context Context to use
     * @param title CharSequence title
     * @param text CharSequence text
     */
    public static void showOkDialog(Context context, CharSequence title, CharSequence text) {
        new AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(text)
            .setPositiveButton("Ok", null)
            .show();
    }

    /**
     * Shows information dialog with Ok button.
     *
     * @param context Context to use
     * @param textId resource id to display as a message
     */
    public static void showOkDialog(Context context, @StringRes int textId) {
        new AlertDialog.Builder(context)
            .setMessage(textId)
            .setPositiveButton("Ok", null)
            .show();
    }

}
