package kr.co.triggers.yolo.network.service;

import java.util.Date;

import kr.co.triggers.yolo.domain.User;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

public interface AuthService {

    @Multipart
    @POST("/sign/up")
    Observable<Response<User>> register(@Part("accessToken") String accessToken,
                                        @Part("nickname") String nickname,
                                        @Part("birth") Date birth,
                                        @Part("gender") String gender,
                                        @Part MultipartBody.Part profile);


    @FormUrlEncoded
    @POST("/sign/in")
    Observable<Response<User>> login(@Field("accessToken") String accessToken);


    @POST("/sign/out")
    Observable<Response<Void>> logout();
}
