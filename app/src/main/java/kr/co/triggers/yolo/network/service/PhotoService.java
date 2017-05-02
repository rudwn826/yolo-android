package kr.co.triggers.yolo.network.service;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface PhotoService {

    @GET("/photos/{id}")
    Observable<Response<String>> findById(@Path("id") long id);

    @GET("/photos/{id}")
    Observable<Response<String>> findById(@Path("id") long id, @Query("redirect") boolean redirect);
}
