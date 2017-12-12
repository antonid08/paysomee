package com.paysomee.client.protocol.models;

import com.google.gson.annotations.SerializedName;
import com.paysomee.client.protocol.CardsApi;

/**
 * Body fof request {@link CardsApi#deleteCard(DeleteCardRequestBody)}
 *
 * @author antonid08
 */
public class DeleteCardRequestBody {

    @SerializedName("CardId")
    private String cardNumber;

    @SerializedName("CardHolderName")
    private String cardHolderName;

    @SerializedName("DeviceHash")
    private String deviceId;

    public DeleteCardRequestBody(String cardNumber, String cardHolderName, String deviceId) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.deviceId = deviceId;
    }
}
