package app.sctp.core.net.api.ext;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class SctpApiCallAdapterFactory extends CallAdapter.Factory {
    private final Gson gson;

    public SctpApiCallAdapterFactory(Gson gson) {
        this.gson = gson;
    }

    @Nullable
    @Override
    public CallAdapter<?, ?> get(@NonNull Type returnType, @NonNull Annotation[] annotations, @NonNull Retrofit retrofit) {
        if (getRawType(returnType) != ApiCall.class) {
            return null;
        }
        Type encapsulatedClass = getParameterUpperBound(0, (ParameterizedType) returnType);
        Class<?> rawResponseDataType = getRawType(encapsulatedClass);
        return new SctpApiCallAdapter<>(rawResponseDataType, gson);
    }
}
