package kr.co.triggers.yolo.network.service;

import java.util.List;

import kr.co.triggers.yolo.domain.Artist;
import kr.co.triggers.yolo.domain.Track;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface ArtistService {

    @GET("/artists")
    Observable<Response<List<Artist>>> fetch();

    @GET("/artists")
    Observable<Response<List<Artist>>> fetch(@Query("before") long before, @Query("count") int count);


    @GET("/artists/search")
    Observable<Response<List<Artist>>> search(@Query("query") String query);

    @GET("/artists/search")
    Observable<Response<List<Artist>>> search(@Query("query") String query,
                                              @Query("before") long before, @Query("count") int count);


    @GET("/artists/{id}")
    Observable<Response<Artist>> findById(@Path("id") long id);


    @GET("/artists/{id}/profile")
    Observable<Response<String>> profile(@Path("id") long id);

    @GET("/artists/{id}/profile")
    Observable<Response<String>> profile(@Path("id") long id, @Query("redirect") boolean redirect);


    @GET("/artists/{id}/tracks")
    Observable<Response<List<Track>>> tracks(@Path("id") long id);

    @GET("/artists/{id}/tracks")
    Observable<Response<List<Track>>> tracks(@Path("id") long id,
                                             @Query("before") long before, @Query("count") int count);


    @POST("/artists/{id}/like")
    Observable<Response<Void>> like(@Path("id") long id);

    @POST("/artists/{id}/unlike")
    Observable<Response<Void>> unlike(@Path("id") long id);
}
