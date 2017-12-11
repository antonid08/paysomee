package com.paysomee.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
        Editor editor = getSharedPreferencesEditor().putStringSet(cardNumber,
            tokens != null ? new HashSet<>(tokens) : new HashSet<String>());
        editor.commit();
    }

    @NonNull
    public List<String> loadTokens(String cardNumber) {
        return new ArrayList<>(getSharedPreferences().getStringSet(cardNumber, new HashSet<String>()));
    }

    public void removeToken(@NonNull String cardNumber, @NonNull String token) {
        List<String> tokens = loadTokens(cardNumber);
        tokens.remove(token);
        saveTokens(cardNumber, tokens);
    }

    private Editor getSharedPreferencesEditor() {
        return getSharedPreferences().edit();
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }
}
