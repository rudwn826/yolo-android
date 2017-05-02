package kr.co.triggers.yolo.network.service;

import java.util.List;

import kr.co.triggers.yolo.domain.Track;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface TrackService {

    @GET("/tracks/search")
    Observable<Response<List<Track>>> search(@Query("query") String query);

    @GET("/tracks/search")
    Observable<Response<List<Track>>> search(@Query("query") String query,
                                             @Query("before") long before, @Query("count") int count);


    @POST("/tracks/{id}/like")
    Observable<Response<Void>> like(@Path("id") long id);

    @POST("/tracks/{id}/unlike")
    Observable<Response<Void>> unlike(@Path("id") long id);
}
