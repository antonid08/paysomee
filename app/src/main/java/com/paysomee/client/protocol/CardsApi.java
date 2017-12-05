package com.paysomee.client.protocol;

import java.util.List;

import com.paysomee.client.protocol.models.AddCardRequestBody;
import com.paysomee.client.protocol.models.CardMto;
import com.paysomee.client.protocol.models.ConfirmCardRequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CardsApi {

    @GET("getcards")
    Call<List<CardMto>> getCards(@Query("DeviceHash") String deviceId);

    @POST("addcard")
    Call<Void> addCard(@Body AddCardRequestBody body);

    @POST("ConfirmCardBy")
    Call<Void> confirmCard(@Body ConfirmCardRequestBody body);

}
