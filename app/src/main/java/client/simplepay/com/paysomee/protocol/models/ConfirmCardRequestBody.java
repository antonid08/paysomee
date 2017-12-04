package client.simplepay.com.paysomee.protocol.models;

import com.google.gson.annotations.SerializedName;

import client.simplepay.com.paysomee.protocol.CardsApi;

/**
 * Body for request {@link CardsApi#confirmCard(ConfirmCardRequestBody)}
 *
 * @author antonid08
 */
public class ConfirmCardRequestBody {

    @SerializedName("CardId")
    private String cardNumber;

    @SerializedName("DeviceHash")
    private String deviceId;

    @SerializedName("PIN")
    private int code;

    public ConfirmCardRequestBody(String cardNumber, String deviceId, int code) {
        this.cardNumber = cardNumber;
        this.deviceId = deviceId;
        this.code = code;
    }
}
