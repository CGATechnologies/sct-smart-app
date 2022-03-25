package app.sctp.auth;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationService {
    @POST("/authenticate")
    Observable<AuthenticationResponse> authenticate(@Body AuthenticationRequest request);

    @POST("/authenticate")
    Call<AuthenticationResponse> auth(@Body AuthenticationRequest request);
}
