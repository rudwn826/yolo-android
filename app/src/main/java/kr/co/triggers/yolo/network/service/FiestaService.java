package kr.co.triggers.yolo.network.service;

import java.util.List;

import kr.co.triggers.yolo.domain.Fiesta;
import kr.co.triggers.yolo.domain.Perform;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface FiestaService {

    @GET("/fiesta")
    Observable<Response<List<Fiesta>>> fetch();

    @GET("/fiesta")
    Observable<Response<List<Fiesta>>> fetch(@Query("before") long before, @Query("count") int count);


    @GET("/fiesta/search")
    Observable<Response<List<Fiesta>>> search(@Query("query") String query);

    @GET("/fiesta/search")
    Observable<Response<List<Fiesta>>> search(@Query("query") String query,
                                              @Query("before") long before, @Query("count") int count);


    @GET("/fiesta/{id}")
    Observable<Response<Fiesta>> findById(@Path("id") long id);


    @GET("/fiesta/{id}/purchase")
    Observable<Response<String>> purchase(@Path("id") long id);


    @GET("/fiesta/{id}/facade")
    Observable<Response<String>> facade(@Path("id") long id);

    @GET("/fiesta/{id}/facade")
    Observable<Response<String>> facade(@Path("id") long id, @Query("redirect") boolean redirect);


    @GET("/fiesta/{id}/artists")
    Observable<Response<List<Perform>>> lineup(@Path("id") long id);

    @GET("/fiesta/{id}/artists")
    Observable<Response<List<Perform>>> lineup(@Path("id") long id,
                                               @Query("before") long before, @Query("count") int count);


    @GET("/fiesta/{id}/react")
    Observable<Response<Void>> react(@Path("id") long id, @Query("status") String status);
}
