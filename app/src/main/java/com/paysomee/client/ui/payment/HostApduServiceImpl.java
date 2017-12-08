package com.paysomee.client.ui.payment;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;

public class HostApduServiceImpl extends HostApduService {

    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        //return new CommandApdu();
        return "toptopkek".getBytes();
    }

    @Override
    public void onDeactivated(int reason) {
        int i = 0;
    }
}
