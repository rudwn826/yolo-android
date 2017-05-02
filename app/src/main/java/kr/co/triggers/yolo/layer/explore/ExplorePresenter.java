package kr.co.triggers.yolo.layer.explore;

import javax.inject.Inject;

import kr.co.triggers.yolo.component.activity.YoloViewPresenter;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;
import kr.co.triggers.yolo.model.ExploreModel;
import kr.co.triggers.yolo.network.entity.SearchEntity;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Action1;

public class ExplorePresenter extends YoloViewPresenter<ExploreView> {

    @Inject
    ExploreModel exploreModel;

    public ExplorePresenter(ExploreView view) {

        this.view = view;

        ActivityComponent component = DaggerActivityComponent.builder().appComponent(view.getAppComponent()).activityModule(new ActivityModule(view)).build();
        component.inject(this);
    }

    public void search(String query) {

        Observable<Response<SearchEntity>> observable = exploreModel.search(query);
        observable.subscribe(new Action1<Response<SearchEntity>>() {

            @Override
            public void call(Response<SearchEntity> response) {

                if (response.isSuccessful()) {

                    view.refreshSearchResult(response.body());
                }
                else {

                    // TODO : 예외 처리
                }
            }
        });
    }
}

