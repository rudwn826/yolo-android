package kr.co.triggers.yolo.layer.explore.fragment;

import java.util.List;

import javax.inject.Inject;

import kr.co.triggers.yolo.component.activity.YoloFragment;
import kr.co.triggers.yolo.component.activity.YoloFragmentPresenter;
import kr.co.triggers.yolo.domain.Artist;
import kr.co.triggers.yolo.domain.Fiesta;
import kr.co.triggers.yolo.domain.Track;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;
import kr.co.triggers.yolo.model.ArtistModel;
import kr.co.triggers.yolo.model.FiestaModel;
import kr.co.triggers.yolo.model.TrackModel;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Action1;

public class ExploreDetailPresenter extends YoloFragmentPresenter<ExploreDetailView> {

    @Inject
    ArtistModel artistModel;

    @Inject
    FiestaModel fiestaModel;

    @Inject
    TrackModel trackModel;

    public ExploreDetailPresenter(YoloFragment fragment) {

        this.view = (ExploreDetailView) fragment;

        ActivityComponent component = DaggerActivityComponent.builder().appComponent(view.getAppComponent()).activityModule(new ActivityModule(fragment)).build();
        component.inject(this);
    }

    public void fetch(int type) {

        if(type == view.TRACK_DETAIL){
            Observable<Response<List<Track>>> observable = artistModel.tracks(0);
            observable.subscribe(new Action1<Response<List<Track>>>() {

                @Override
                public void call(Response<List<Track>> response) {
                    if(response.isSuccessful()){
                        view.setTracks(response.body());
                    }else{

                    }
                }
            });
            return;
        }
        if(type == view.ARTIST_DETAIL){
            Observable<Response<List<Artist>>> observable = artistModel.fetch();
            observable.subscribe(new Action1<Response<List<Artist>>>() {

                @Override
                public void call(Response<List<Artist>> response) {
                    if(response.isSuccessful()){
                        view.setArtists(response.body());
                    }else{

                    }
                }
            });
            return;
        }
        if(type == view.FIESTA_DETAIL){
            Observable<Response<List<Fiesta>>> observable = fiestaModel.fetch();
            observable.subscribe(new Action1<Response<List<Fiesta>>>() {
                @Override
                public void call(Response<List<Fiesta>> response) {
                    if(response.isSuccessful()){
                        view.setFiesta(response.body());
                    }else{

                    }
                }
            });
            return;
        }
    }

    public void fetch(int type, long before, int count){
        if(type == view.TRACK_DETAIL){
            Observable<Response<List<Track>>> observable = artistModel.tracks(before);
            observable.subscribe(new Action1<Response<List<Track>>>() {

                @Override
                public void call(Response<List<Track>> response) {
                    if(response.isSuccessful()){
                        view.attachTracks(response.body());
                    }else{

                    }
                }
            });
            return;
        }
        if(type == view.ARTIST_DETAIL){
            Observable<Response<List<Artist>>> observable = artistModel.fetch(before, count);
            observable.subscribe(new Action1<Response<List<Artist>>>() {

                @Override
                public void call(Response<List<Artist>> response) {
                    if(response.isSuccessful()){
                        view.attachArtists(response.body());
                    }else{

                    }
                }
            });
            return;
        }
        if(type == view.FIESTA_DETAIL){
            Observable<Response<List<Fiesta>>> observable = fiestaModel.fetch(before, count);
            observable.subscribe(new Action1<Response<List<Fiesta>>>() {
                @Override
                public void call(Response<List<Fiesta>> response) {
                    if(response.isSuccessful()){
                        view.attachFiesta(response.body());
                    }else{

                    }
                }
            });
            return;
        }
    }
}
