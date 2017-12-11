package com.paysomee.client.protocol.utils;

import java.io.IOException;
import java.lang.annotation.Annotation;

import com.paysomee.client.protocol.models.ErrorMto;
import com.paysomee.client.protocol.service.ApiProvider;

import android.support.annotation.Nullable;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Parses retrofit responseBody to {@link ErrorMto}
 *
 * @author antonid08
 */
class ErrorsParser {

    /**
     * Returns parser {@link ErrorMto}, or {@code null} if can't parse.
     */
    @Nullable
    static ErrorMto parse(Response<?> response) {
        Converter<ResponseBody, ErrorMto> converter =
            ApiProvider.getRetrofit().responseBodyConverter(ErrorMto.class, new Annotation[0]);

        ErrorMto error;

        try {
            error = converter.convert(response.errorBody());
        }
        catch (IOException e) {
            return null;
        }

        return error;
    }
}
