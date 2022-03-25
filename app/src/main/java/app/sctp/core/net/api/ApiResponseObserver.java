package app.sctp.core.net.api;

import java.net.HttpURLConnection;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public class ApiResponseObserver<T> implements Observer<T> {
    private Disposable disposable;

    @Override
    public final void onSubscribe(Disposable d) {
        disposable = d;
        onStart();
    }

    @Override
    public final void onNext(T value) {
        onResponseReceived(value);
    }

    @Override
    public final void onError(Throwable e) {
        if (e instanceof HttpException) {
            onHttpError((HttpException) e);
        }
    }

    @Override
    public final void onComplete() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        onFinished();
    }

    public boolean isAuthorizationCode(HttpException httpException){
        return httpException.code() == HttpURLConnection.HTTP_UNAUTHORIZED;
    }

    public void onStart(){

    }

    public void onResponseReceived(T data){

    }

    public void onFinished(){

    }

    public void onHttpError(HttpException httpException) {

    }
}
