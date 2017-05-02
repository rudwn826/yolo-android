package kr.co.triggers.yolo.layer.artist;

import java.util.List;

import javax.inject.Inject;

import kr.co.triggers.yolo.component.activity.YoloViewPresenter;
import kr.co.triggers.yolo.domain.Artist;
import kr.co.triggers.yolo.domain.Track;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;
import kr.co.triggers.yolo.model.ArtistModel;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Action1;

public class ArtistPresenter extends YoloViewPresenter<ArtistView> {

    @Inject
    ArtistModel artistModel;

    public ArtistPresenter(ArtistView view) {

        this.view = view;

        ActivityComponent component = DaggerActivityComponent.builder().appComponent(view.getAppComponent()).activityModule(new ActivityModule(view)).build();
        component.inject(this);
    }

    public void fetchArtistDetail(long id) {

        Observable<Response<Artist>> observable = artistModel.findById(id);
        observable.subscribe(new Action1<Response<Artist>>() {

            @Override
            public void call(Response<Artist> response) {

                if (response.isSuccessful()) {

                    view.setArtistInformation(response.body());
                }
                else {

                    // TODO : 예외 처리
                }
            }
        });
    }

    public void fetchArtistTracks(long id) {

        Observable<Response<List<Track>>> observable = artistModel.tracks(id);
        observable.subscribe(new Action1<Response<List<Track>>>() {

            @Override
            public void call(Response<List<Track>> response) {

                if (response.isSuccessful()) {

                    view.setTracks(response.body());
                }
                else {

                    // TODO : 예외 처리
                }
            }
        });
    }

    public void fetchArtistProfile(long id) {

        Observable<Response<String>> observable = artistModel.profile(id);
        observable.subscribe(new Action1<Response<String>>() {

            @Override
            public void call(Response<String> response) {

                if (response.isSuccessful()) {

                    view.setArtistProfile(response.body());
                }
                else {

                    // TODO : 예외 처리
                }
            }
        });
    }
}
