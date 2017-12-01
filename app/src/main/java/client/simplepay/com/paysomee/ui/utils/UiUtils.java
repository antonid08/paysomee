package client.simplepay.com.paysomee.ui.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Class of utility methods which used in UI.
 *
 * @author antonid08
 */
public class UiUtils {

    /**
     * Inflates view by ViewGroup
     *
     * @param layoutId id of layout for view
     * @param root ViewGroup к которой будет добавлен View.
     */
    public static View inflate(ViewGroup root, int layoutId) {
        return LayoutInflater.from(root.getContext()).inflate(layoutId, root, false);
    }
}
