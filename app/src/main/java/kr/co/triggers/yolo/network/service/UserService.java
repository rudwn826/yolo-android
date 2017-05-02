package kr.co.triggers.yolo.network.service;

import java.util.List;

import kr.co.triggers.yolo.domain.Artist;
import kr.co.triggers.yolo.domain.Fiesta;
import kr.co.triggers.yolo.domain.Track;
import kr.co.triggers.yolo.domain.User;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface UserService {

    @GET("/me")
    Observable<Response<User>> me();

    @GET("/users/{id}")
    Observable<Response<User>> findById(@Path("id") long id);


    @GET("/users/{id}/profile")
    Observable<Response<String>> profile(@Path("id") long id);

    @GET("/users/{id}/profile")
    Observable<Response<String>> profile(@Path("id") long id, @Query("redirect") boolean redirect);


    @GET("/me/artists")
    Observable<Response<List<Artist>>> artists();

    @GET("/me/artists")
    Observable<Response<List<Artist>>> artists(@Query("before") long before, @Query("count") int count);


    @GET("/me/tracks")
    Observable<Response<List<Track>>> tracks();

    @GET("/me/tracks")
    Observable<Response<List<Track>>> tracks(@Query("before") long before, @Query("count") int count);


    @GET("/me/fiesta")
    Observable<Response<List<Fiesta>>> fiesta();

    @GET("/me/fiesta")
    Observable<Response<List<Fiesta>>> fiesta(@Query("before") long before, @Query("count") int count);



    @DELETE("/users")
    Observable<Response<Void>> leave(@Path("id") long id);
}
