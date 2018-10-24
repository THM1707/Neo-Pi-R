package thm.com.gr2.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import thm.com.gr2.model.LoginResponse;
import thm.com.gr2.model.QuizResponse;
import thm.com.gr2.model.RegisterResponse;
import thm.com.gr2.model.ResultResponse;

public interface MyApi {
    @POST("signup")
    Call<RegisterResponse> signup(@Query("name") String name, @Query("email") String email,
            @Query("password") String password,
            @Query("password_confirmation") String password_confirmation,
            @Query("gender") int gender);

    @POST("auth/login")
    Call<LoginResponse> login(@Query("email") String email, @Query("password") String password);

    @GET("quizzes/all")
    Call<QuizResponse> getQuizzes(@Header("Authorization") String auth);

    @GET("results/all")
    Call<ResultResponse> getResults(@Header("Authorization") String auth);
}
