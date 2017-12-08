package com.paysomee.terminal.ui.payment;

/*
 * NfcActivity.java
 * NfcLibrary project.
 *
 * Created by : Daneo van Overloop - 17/6/2014.
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 AppFoundry. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 */

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import be.appfoundry.nfclibrary.activities.NfcActivity;

/**
 * If this activity on foreground it can react on NFC tags.
 * This activity don't send TAG_DISCOVERED intent to another devices.
 *
 *
 * @author Daneo Van Overloop (NfcLibrary)
 * @author antonid08
 */
abstract public class NfcReaderActivity
    extends AppCompatActivity implements NfcAdapter.ReaderCallback {

    private static final String TAG = NfcActivity.class.getName();

    private NfcAdapter mNfcAdapter;

    private PendingIntent pendingIntent;
    private IntentFilter[] mIntentFilters;
    private String[][] mTechLists;

    /**
     * Initializes all fields and the NFC Adapter if present
     *
     * @param savedInstanceState
     *         containing state, propagated to super
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAdapter();
        initFields();
    }

    /**
     * Automatically subscribes the ForegroundDispatch
     */
    public void onResume() {
        super.onResume();
        initAdapter();

        if (getNfcAdapter() != null) {
            getNfcAdapter().enableForegroundDispatch(this, pendingIntent, mIntentFilters, mTechLists);
        }
    }

    /**
     * Disables ForegroundDispatch if NFC present
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (getNfcAdapter() != null) {
            getNfcAdapter().disableForegroundDispatch(this);
            Log.d(TAG, "FGD disabled");
        }
    }

    /**
     * Initializes which intents and NfcTechnologies to filter for
     */
    private void initFields() {
        pendingIntent = PendingIntent.getActivity(this, 0,
            new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        mIntentFilters = new IntentFilter[] {new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)};
        mTechLists = new String[][] {new String[] {Ndef.class.getName()},
            new String[] {NdefFormatable.class.getName()}};
    }

    /**
     * Initializes the NFC Adapter if not present
     */
    private void initAdapter() {
        if (getNfcAdapter() == null) {
            mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
            mNfcAdapter.enableReaderMode(this, this, NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
                null);
            Log.d(TAG, "Adapter initialized");
        }
    }

    /**
     * Retrieve the current NFC Adapter
     *
     * @return null if no adapter present, else {@link NfcAdapter}
     */
    private NfcAdapter getNfcAdapter() {
        return this.mNfcAdapter;
    }

}
