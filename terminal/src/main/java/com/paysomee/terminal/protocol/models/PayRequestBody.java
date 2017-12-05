package com.paysomee.terminal.protocol.models;

import com.google.gson.annotations.SerializedName;
import com.paysomee.terminal.protocol.PaymentsApi;

/**
 * Body fof request {@link PaymentsApi#(PayRequestBody)}
 *
 * @author antonid08
 */
public class PayRequestBody {

    @SerializedName("Token")
    private String token;

    @SerializedName("Amount")
    private float amount;

    public PayRequestBody(String token, float amount) {
        this.token = token;
        this.amount = amount;
    }
}
