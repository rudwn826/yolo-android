package kr.co.triggers.yolo.network.service;

import java.util.List;

import kr.co.triggers.yolo.domain.Notification;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface NotifyService {

    @GET("/notifications")
    Observable<Response<List<Notification>>> fetch();

    @GET("/notifications")
    Observable<Response<List<Notification>>> fetch(@Query("before") long before, @Query("count") int count);

    @PUT("/notifications/{id}/read")
    Observable<Response<Void>> read(@Path("id") long id);
}
