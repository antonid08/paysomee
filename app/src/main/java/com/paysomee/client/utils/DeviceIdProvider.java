package com.paysomee.client.utils;

import android.content.Context;
import android.provider.Settings;

/**
 * Provides device id.
 * Uses {@link Settings.Secure#ANDROID_ID}, which can be changed on rooted phone!
 *
 * @author antonid08
 */
public class DeviceIdProvider {

    /**
     * Returns device id.
     * FIXME IMPORTANT!! this is stub implementation: server allows only one device id!
     */
    public static String getDeviceId(Context context) {
        return "8743b52063cd84097a65d1633f5c74f5";
        // return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
