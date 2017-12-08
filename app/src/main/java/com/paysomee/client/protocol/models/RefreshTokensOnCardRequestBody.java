package com.paysomee.client.protocol.models;

import com.google.gson.annotations.SerializedName;

import com.paysomee.client.protocol.TokensApi;

/**
 * Body for request {@link TokensApi#refreshTokensOnCard(RefreshTokensOnCardRequestBody)}
 *
 * @author antonid08
 */
public class RefreshTokensOnCardRequestBody {

    @SerializedName("CardId")
    private String cardNumber;

    @SerializedName("DeviceHash")
    private String deviceId;

    public RefreshTokensOnCardRequestBody(String cardNumber, String deviceId) {
        this.cardNumber = cardNumber;
        this.deviceId = deviceId;
    }
}
