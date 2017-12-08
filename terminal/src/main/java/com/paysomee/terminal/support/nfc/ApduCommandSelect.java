package com.paysomee.terminal.support.nfc;

/**
 * Adpu command "read record".
 *
 * @author grundid
 */
public class ApduCommandSelect extends ApduCommand {

    public ApduCommandSelect(byte[] data) {
        super(ISO_CLA, ISO_SELECT, 0x04, 0x00, data, 0x00);
    }
}

