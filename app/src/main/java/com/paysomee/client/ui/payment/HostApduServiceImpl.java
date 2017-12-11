package com.paysomee.client.ui.payment;

import java.util.List;

import org.greenrobot.eventbus.EventBus;

import com.paysomee.client.Storage;
import com.paysomee.client.ui.utils.UiUtils;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;

public class HostApduServiceImpl extends HostApduService {

    private static final String ACTIVITY_NOT_ACTIVE_RESPONSE = "ACTIVITY_NOT_ACTIVE";
    private static final String NO_TOKENS_RESPONSE = "NO_TOKENS";

    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        if (!PaymentActivity.isActive()) {
            return ACTIVITY_NOT_ACTIVE_RESPONSE.getBytes();
        }

        String token = getToken(PaymentActivity.getCardNumber());

        if (token != null) {
            UiUtils.vibrate(this);

            new Storage(this).removeToken(PaymentActivity.getCardNumber(), token);
            EventBus.getDefault().post(new TokenRemovedEvent());

            return token.getBytes();
        } else {
            EventBus.getDefault().post(new TokensEndedEvent());
            return NO_TOKENS_RESPONSE.getBytes();
        }
    }

    /**
     * Returns token from storage or {@code null} if token doesn't exists.
     */
    private String getToken(String cardNumber) {
        List<String> tokens = new Storage(this).loadTokens(cardNumber);

        if (tokens.isEmpty()) {
            return null;
        }

        return tokens.get(tokens.size() - 1);
    }

    @Override
    public void onDeactivated(int reason) {
        //empty
    }
}
