package com.paysomee.client.protocol;


import java.util.List;
import java.util.Map;

import com.paysomee.client.protocol.models.RefreshTokensOnCardRequestBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TokensApi {

    @POST("RefreshTokens")
    Call<Map<String, String>> refreshTokens(@Body String deviceId);

    @POST("RefreshTokenSet")
    Call<List<String>> refreshTokensOnCard(@Body RefreshTokensOnCardRequestBody body);

}
