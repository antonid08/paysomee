package client.simplepay.com.paysomee.protocol;

import java.util.List;

import client.simplepay.com.paysomee.protocol.models.AddCardRequestBody;
import client.simplepay.com.paysomee.protocol.models.CardMto;
import client.simplepay.com.paysomee.protocol.models.ConfirmCardRequestBody;
import client.simplepay.com.paysomee.protocol.models.RefreshTokensRequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface CardsApi {

    @GET("/bank/getcards")
    Call<List<CardMto>> getCards(@Query("deviceId") String deviceId);

    @POST("/bank/addcard")
    Call<Void> addCard(@Body AddCardRequestBody body);

    @POST("/bank/ConfirmCardBy")
    Call<Void> confirmCard(@Body ConfirmCardRequestBody body);

    @POST("/bank/RefreshTokenSet")
    Call<Void> refreshTokens(@Body RefreshTokensRequestBody body)
}
