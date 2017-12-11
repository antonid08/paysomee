package com.paysomee.terminal.protocol.utils;


import com.paysomee.terminal.R;
import com.paysomee.terminal.protocol.models.ErrorMto;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Retrofit callback with errors processing.
 * On request failure (5xx) shows toast with error message.
 *
 * <p>
 *     Clients must implement {@link #onSuccess(Object)}.
 *     Also client can override {@link #onError(ErrorMto)}.
 * </p>
 *
 * @param <T> request result type
 * @author antonid
 */
public abstract class HandleErrorsCallback<T> implements Callback<T> {

    private Context context;

    public HandleErrorsCallback(@NonNull Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context must be not null!");
        }

        this.context = context;
    }

    @CallSuper
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        // server designed to don't use http codes :(
        // it return code 500 and error message if something goes wrong

        if (response.code() == 200) {
            onSuccess(response.body());
        } else {
            ErrorMto error = ErrorsParser.parse(response);

            if (error != null) {
                onError(error);
            } else {
                //todo log it
                onFailure(call, null);
            }
        }
    }


    @CallSuper
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Toast.makeText(context, R.string.api_error, Toast.LENGTH_SHORT).show();
    }

    /**
     * Caused if response code is 200.
     *
     * @param response response data
     */
    public abstract void onSuccess(@Nullable T response);

    /**
     * Caused if response code is not 200 and not 5xx (for 5xx {@link #onFailure(Call, Throwable)}.
     *
     * @param error error description
     */
    public void onError(ErrorMto error) {
        //nothing. client can override this.
    }


    /**
     * Returns context of operation.
     *
     * @return current context
     */
    protected Context getContext() {
        return context;
    }

}
