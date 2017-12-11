package com.paysomee.client.protocol.models;

import com.google.gson.annotations.SerializedName;

/**
 * Request error protocol object.
 */
public class ErrorMto {

    @SerializedName("ExceptionMessage")
    private String message;

    public String getMessage() {
        return message;
    }

}
