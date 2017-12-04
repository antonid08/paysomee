package client.simplepay.com.paysomee.protocol.models;

import com.google.gson.annotations.SerializedName;

import client.simplepay.com.paysomee.protocol.CardsApi;

/**
 * Body fof request {@link CardsApi#addCard(AddCardRequestBody)}
 *
 * @author antonid08
 */
public class AddCardRequestBody {

    @SerializedName("CardId")
    private String cardNumber;

    @SerializedName("CVV")
    private int cvv;

    @SerializedName("CardHolderName")
    private String cardHolderName;

    @SerializedName("DeviceHash")
    private String deviceId;

    public AddCardRequestBody(String cardNumber, int cvv, String cardHolderName, String deviceId) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.cardHolderName = cardHolderName;
        this.deviceId = deviceId;
    }
}
