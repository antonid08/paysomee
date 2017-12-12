package com.paysomee.client.protocol.models;

import com.google.gson.annotations.SerializedName;

/**
 * Payment card protocol object
 *
 * @author antonid08
 */
public class CardMto {

    @SerializedName("CardId")
    private String number;

    @SerializedName("Value")
    private float amount;

    @SerializedName("CardHolderName")
    private String holderName;


    public String getNumber() {
        return number;
    }

    public float getAmount() {
        return amount;
    }

    public String getHolderName() {
        return holderName;
    }

}
