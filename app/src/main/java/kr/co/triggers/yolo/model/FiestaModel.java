package kr.co.triggers.yolo.model;

import java.util.List;

import javax.inject.Inject;

import kr.co.triggers.yolo.app.App;
import kr.co.triggers.yolo.domain.Fiesta;
import kr.co.triggers.yolo.domain.Perform;
import kr.co.triggers.yolo.inject.component.AppComponent;
import kr.co.triggers.yolo.network.service.FiestaService;
import kr.co.triggers.yolo.scenario.Scenario;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FiestaModel {

    @Inject
    FiestaService fiestaService;

    public FiestaModel(App app) {

        AppComponent component = app.getAppComponent();
        component.inject(this);
    }

    public Observable<Response<List<Fiesta>>> fetch() {

        Observable<Response<List<Fiesta>>> observable = Scenario.mockFiesta();
        // Observable<Response<List<Fiesta>>> observable = fiestaService.fetch();

        return observable.observeOn(AndroidSchedulers.mainThread())
                         .subscribeOn(Schedulers.newThread());
    }

    public Observable<Response<List<Fiesta>>> fetch(long id, int count) {

        Observable<Response<List<Fiesta>>> observable = Scenario.mockFiesta(id, count);
        // Observable<Response<List<Fiesta>>> observable = fiestaService.fetch(id, count);

        return observable.observeOn(AndroidSchedulers.mainThread())
                         .subscribeOn(Schedulers.newThread());
    }

    public Observable<Response<Fiesta>> findById(long id) {

        Observable<Response<Fiesta>> observable = Scenario.mockFiestaDetail();
        // Observable<Response<Fiesta>> observable = fiestaService.findById(id);

        return observable.observeOn(AndroidSchedulers.mainThread())
                         .subscribeOn(Schedulers.newThread());
    }

    public Observable<Response<String>> purchase(long id) {

        Observable<Response<String>> observable = Scenario.mockPurchaseLink();
        // Observable<Response<String>> observable = fiestaService.purchase(id);

        return observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }

    public Observable<Response<List<Perform>>> performers(long fiestaId, long before, int count) {

        Observable<Response<List<Perform>>> observable = Scenario.mockPerformers();
        // Observable<Response<List<Perform>>> observable = fiestaService.lineup(fiestaId, before, count);

        return observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }
}
