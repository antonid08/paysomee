package client.simplepay.com.paysomee.protocol.models;

import com.google.gson.annotations.SerializedName;

import client.simplepay.com.paysomee.protocol.CardsApi;

/**
 * Body for request {@link CardsApi#refreshTokens(RefreshTokensRequestBody)}
 *
 * @author antonid08
 */
public class RefreshTokensRequestBody {

    @SerializedName("CardId")
    private String cardNumber;

    @SerializedName("DeviceHash")
    private String deviceId;

    public RefreshTokensRequestBody(String cardNumber, String deviceId) {
        this.cardNumber = cardNumber;
        this.deviceId = deviceId;
    }
}
