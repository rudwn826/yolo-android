package kr.co.triggers.yolo.network.service;

import kr.co.triggers.yolo.network.entity.TagEntity;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface TagService {

    @GET("/tags/{id}")
    Observable<Response<TagEntity>> findById(@Path("id") long id);
}
