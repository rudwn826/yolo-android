package kr.co.triggers.yolo.layer.entry.fragment.fiesta;

import java.util.List;

import javax.inject.Inject;

import kr.co.triggers.yolo.component.activity.YoloFragment;
import kr.co.triggers.yolo.component.activity.YoloFragmentPresenter;
import kr.co.triggers.yolo.domain.Fiesta;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;
import kr.co.triggers.yolo.model.FiestaModel;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Action1;

public class FiestaCollectionPresenter extends YoloFragmentPresenter<FiestaCollectionView> {

    @Inject
    FiestaModel fiestaModel;

    public FiestaCollectionPresenter(YoloFragment fragment) {

        this.view = (FiestaCollectionView) fragment;

        ActivityComponent component = DaggerActivityComponent.builder().appComponent(view.getAppComponent()).activityModule(new ActivityModule(fragment)).build();
        component.inject(this);
    }

    void fetch() {

        Observable<Response<List<Fiesta>>> observable = fiestaModel.fetch();
        observable.subscribe(new Action1<Response<List<Fiesta>>>() {

            @Override
            public void call(Response<List<Fiesta>> response) {

                if (response.isSuccessful()) {

                    view.setFiesta(response.body());
                }
                else {

                    // TODO : 예외 처리
                }
            }
        });
    }

    void fetch(long id, int count) {

        Observable<Response<List<Fiesta>>> observable = fiestaModel.fetch(id, count);
        observable.subscribe(new Action1<Response<List<Fiesta>>>() {

            @Override
            public void call(Response<List<Fiesta>> response) {

                if (response.isSuccessful()) {

                    view.addFiesta(response.body());
                }
                else {

                    // TODO : 예외 처리
                }
            }
        });
    }
}
