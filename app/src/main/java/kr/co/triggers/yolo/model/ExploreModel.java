package kr.co.triggers.yolo.model;

import javax.inject.Inject;

import kr.co.triggers.yolo.app.App;
import kr.co.triggers.yolo.inject.component.AppComponent;
import kr.co.triggers.yolo.network.entity.SearchEntity;
import kr.co.triggers.yolo.network.service.ExploreService;
import kr.co.triggers.yolo.scenario.Scenario;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ExploreModel {

    @Inject
    ExploreService exploreService;

    public ExploreModel(App app) {

        AppComponent component = app.getAppComponent();
        component.inject(this);
    }

    public Observable<Response<SearchEntity>> search(String query) {

        //Observable<Response<SearchEntity>> observable = exploreService.search(query);
        Observable<Response<SearchEntity>> observable = Scenario.search(query);

        return observable.observeOn(AndroidSchedulers.mainThread())
                         .subscribeOn(Schedulers.newThread());
    }
}
