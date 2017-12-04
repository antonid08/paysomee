package com.paysomee.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * App storage. Using encrypted SharedPreferences.
 *
 * @author antonid08
 */
public class Storage {

    private static final String PREFERENCES_NAME = "storage";

    private Context context;

    public Storage(@NonNull Context context) {
        this.context = context;
    }

    public void saveTokens(String cardNumber, @Nullable List<String> tokens) {
        getSharedPreferencesEditor().putStringSet(cardNumber,
                tokens != null ? new HashSet<>(tokens) : new HashSet<String>());
    }

    public List<String> loadTokens(String cardNumber) {
        return new ArrayList<>(getSharedPreferences().getStringSet(cardNumber, new HashSet<String>()));
    }

    private Editor getSharedPreferencesEditor() {
        return getSharedPreferences().edit();
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }
}
