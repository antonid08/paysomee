package com.paysomee.client.utils;

import java.util.List;

/**
 * Utility class for common methods.
 *
 * @author antonid08
 */
public class Utils {

    /**
     * Builds string from passed list using passed separator between elements.
     */
    public static String listToString(List<String> list, String separator) {
        StringBuilder builder = new StringBuilder();

        builder.append(list.get(0));
        for (String error : list.subList(1, list.size())) {
            builder.append(separator).append(error);
        }

        return builder.toString();
    }

}
