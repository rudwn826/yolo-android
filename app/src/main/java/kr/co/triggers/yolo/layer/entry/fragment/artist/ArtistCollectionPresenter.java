package kr.co.triggers.yolo.layer.entry.fragment.artist;

import java.util.List;

import javax.inject.Inject;

import kr.co.triggers.yolo.component.activity.YoloFragment;
import kr.co.triggers.yolo.component.activity.YoloFragmentPresenter;
import kr.co.triggers.yolo.domain.Artist;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;
import kr.co.triggers.yolo.model.ArtistModel;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Action1;

public class ArtistCollectionPresenter extends YoloFragmentPresenter<ArtistCollectionView> {

    @Inject
    ArtistModel artistModel;

    public ArtistCollectionPresenter(YoloFragment fragment) {

        this.view = (ArtistCollectionView) fragment;

        ActivityComponent component = DaggerActivityComponent.builder().appComponent(view.getAppComponent()).activityModule(new ActivityModule(fragment)).build();
        component.inject(this);
    }

    void fetch() {

        Observable<Response<List<Artist>>> observable = artistModel.fetch();
        observable.subscribe(new Action1<Response<List<Artist>>>() {

            @Override
            public void call(Response<List<Artist>> response) {

                if (response.isSuccessful()) {

                    view.setArtists(response.body());
                }
                else {

                    // TODO : 예외 처리
                }
            }
        });
    }

    void fetch(long id, int count) {

        Observable<Response<List<Artist>>> observable = artistModel.fetch(id, count);
        observable.subscribe(new Action1<Response<List<Artist>>>() {

            @Override
            public void call(Response<List<Artist>> response) {

                if (response.isSuccessful()) {

                    view.attachArtists(response.body());
                }
                else {

                    // TODO : 예외 처리
                }
            }
        });
    }
}
