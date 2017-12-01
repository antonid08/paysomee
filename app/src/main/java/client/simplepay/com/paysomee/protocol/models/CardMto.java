package client.simplepay.com.paysomee.protocol.models;

import com.google.gson.annotations.SerializedName;

/**
 * Payment card protocol object
 *
 * @author antonid08
 */
public class CardMto {

    @SerializedName("CardId")
    private String number;

    @SerializedName("CardholderName")
    private String holderName;

    @SerializedName("BankMane")
    private String bankName;


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
