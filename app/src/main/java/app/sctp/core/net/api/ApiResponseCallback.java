package app.sctp.core.net.api;

import androidx.annotation.NonNull;

import java.net.HttpURLConnection;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class ApiResponseCallback<T> implements Callback<T> {

    @Override
    public final void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        if (response.isSuccessful()) {
            onCompleted();
            onSuccess(call, response);
        } else {
            onFailure(call, new HttpException(response));
        }
    }

    @Override
    public final void onFailure(@NonNull Call<T> call, @NonNull Throwable throwable) {
        onCompleted();
        onError(call, throwable);
    }

    public void onError(Call<T> call, Throwable throwable){

    }

    public void onSuccess(@NonNull Call<T> call, @NonNull Response<T> response) {

    }

    public void onCompleted(){

    }
}
