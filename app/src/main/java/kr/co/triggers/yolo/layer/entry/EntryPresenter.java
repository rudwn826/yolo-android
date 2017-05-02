package kr.co.triggers.yolo.layer.entry;

import java.util.List;

import javax.inject.Inject;

import kr.co.triggers.yolo.component.activity.YoloViewPresenter;
import kr.co.triggers.yolo.domain.Fiesta;
import kr.co.triggers.yolo.domain.Perform;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;
import kr.co.triggers.yolo.model.FiestaModel;
import kr.co.triggers.yolo.model.UserModel;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Action1;

public class EntryPresenter extends YoloViewPresenter<EntryView> {

    @Inject
    UserModel userModel;

    @Inject
    FiestaModel fiestaModel;

    public EntryPresenter(EntryView view) {

        this.view = view;

        ActivityComponent component = DaggerActivityComponent.builder().appComponent(view.getAppComponent()).activityModule(new ActivityModule(view)).build();
        component.inject(this);
    }

    void fetchById(long id) {

        Observable<Response<Fiesta>> observable = fiestaModel.findById(id);
        observable.subscribe(new Action1<Response<Fiesta>>() {

            @Override
            public void call(Response<Fiesta> response) {

                if (response.isSuccessful()) {

                    view.setFiestaDetail(response.body());
                }
                else {

                    // TODO : 예외 처리
                }
            }
        });
    }

    void requestPurchaseLink(long id) {

        Observable<Response<String>> observable = fiestaModel.purchase(id);
        observable.subscribe(new Action1<Response<String>>() {

            @Override
            public void call(Response<String> response) {

                if (response.isSuccessful()) {

                    view.launchPurchaseSite(response.body());
                }
                else {

                    // TODO : 예외 처리
                }
            }
        });
    }

    void fetchPerformers(long fiestaId, long before, int count) {

        Observable<Response<List<Perform>>> observable = fiestaModel.performers(fiestaId, before, count);
        observable.subscribe(new Action1<Response<List<Perform>>>() {

            @Override
            public void call(Response<List<Perform>> response) {

                if (response.isSuccessful()) {

                    view.attachPerformers(response.body());
                }
                else {

                    // TODO : 예외 처리
                }
            }
        });
    }
}

