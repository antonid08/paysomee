package com.paysomee.client.ui.utils;

import android.content.Context;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Class of utility methods which used in UI.
 *
 * @author antonid08
 */
public class UiUtils {

    private static final long DEFAULT_VIBRATION_DURATION = 500;

    /**
     * Inflates view by ViewGroup
     *
     * @param layoutId id of layout for view
     * @param root ViewGroup к которой будет добавлен View.
     */
    public static View inflate(ViewGroup root, int layoutId) {
        return LayoutInflater.from(root.getContext()).inflate(layoutId, root, false);
    }

    /**
     * Vibration (500ms).
     * Do nothing if vibration component is absent.
     */
    public static void vibrate(Context context) {
        vibrate(context, DEFAULT_VIBRATION_DURATION);
    }

    /**
     * Vibration.
     * Do nothing if vibration component is absent.
     */
    public static void vibrate(Context context, long milliseconds) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        if (v != null) {
            v.vibrate(milliseconds);
        }
    }


}
