package app.sctp.app;

import android.app.Application;

import retrofit2.Retrofit;

public class SctApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initComponents();
    }

    private void initComponents() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .build();
    }
}
