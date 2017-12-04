package client.simplepay.com.paysomee.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;

/**
 * Provides device id.
 * Uses {@link Settings.Secure#ANDROID_ID}, which can be changed on rooted phone!

 * @author antonid08
 */
public class DeviceIdProvider {

    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
