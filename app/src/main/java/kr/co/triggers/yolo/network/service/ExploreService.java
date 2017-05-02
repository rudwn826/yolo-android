package kr.co.triggers.yolo.network.service;

import kr.co.triggers.yolo.network.entity.SearchEntity;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ExploreService {

    @GET("/search")
    Observable<Response<SearchEntity>> search(@Query("query") String query);

    @GET("/search")
    Observable<Response<SearchEntity>> search(@Query("query") String query, @Query("type") String type);

    @GET("/search")
    Observable<Response<SearchEntity>> search(@Query("query") String query, @Query("before") long before, @Query("count") int count);

    @GET("/search")
    Observable<Response<SearchEntity>> search(@Query("query") String query, @Query("type") String type, @Query("before") long before, @Query("count") int count);
}
