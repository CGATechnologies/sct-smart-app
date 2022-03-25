package app.sctp.core.net.api;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.PrintWriter;

import app.sctp.BuildConfig;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Response;

public class HttpLoggingInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        if (BuildConfig.DEBUG) {
            PrintWriter printWriter = new PrintWriter(System.err);
            printWriter.println("HttpLogger ====> ");
            printWriter.println(chain.request().method() + " " + chain.request().url());
            Headers headers = chain.request().headers();
            for (String key : headers.names()) {
                printWriter.print(key + ": ");
                for (String value : headers.values(key)) {
                    printWriter.print(value + ";");
                }
            }
            printWriter.flush();

            Response response = chain.proceed(chain.request());

            printWriter.println("<=== HttpLogger");
            printWriter.println(response.code() + " " + response.message());
            headers = response.headers();
            for (String key : headers.names()) {
                printWriter.print(key + ":");
                for (String value : headers.values(key)) {
                    printWriter.print(value + ";");
                }
            }
            printWriter.println("HttpLogger End");
            printWriter.flush();
            return response;
        }
        return chain.proceed(chain.request());
    }
}
