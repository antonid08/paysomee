package com.paysomee.terminal.protocol;

import com.paysomee.terminal.protocol.models.PayRequestBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PaymentsApi {

    @POST("AddTransaction")
    Call<Boolean> pay(@Body PayRequestBody body);

}
