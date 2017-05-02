package kr.co.triggers.yolo.model;

import java.util.List;

import javax.inject.Inject;

import kr.co.triggers.yolo.app.App;
import kr.co.triggers.yolo.domain.Notification;
import kr.co.triggers.yolo.inject.component.AppComponent;
import kr.co.triggers.yolo.network.service.NotifyService;
import kr.co.triggers.yolo.scenario.Scenario;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NotificationModel {

    @Inject
    NotifyService service;

    public NotificationModel(App app) {

        AppComponent component = app.getAppComponent();
        component.inject(this);
    }

    public Observable<Response<List<Notification>>> fetch() {

        Observable<Response<List<Notification>>> observable = Scenario.mockNotification();
        //Observable<Response<List<Notification>>> observable = service.fetch();

        return observable.observeOn(AndroidSchedulers.mainThread())
                         .subscribeOn(Schedulers.newThread());
    }

    public Observable<Response<List<Notification>>> fetch(long id, int count) {

        Observable<Response<List<Notification>>> observable = Scenario.mockNotification(id, count);
        // Observable<Response<List<Notification>>> observable = service.fetch(id, count);

        return observable.observeOn(AndroidSchedulers.mainThread())
                         .subscribeOn(Schedulers.newThread());
    }

    public Observable<Response<Void>> read(long id) {

        Observable<Response<Void>> observable = Scenario.mockNotificationRead(id);
        // Observable<Response<Notification>> observable = service.read(id);

        return observable.observeOn(AndroidSchedulers.mainThread())
                         .subscribeOn(Schedulers.newThread());
    }

}
