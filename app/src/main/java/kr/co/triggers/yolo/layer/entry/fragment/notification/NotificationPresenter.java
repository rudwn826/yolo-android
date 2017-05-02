package kr.co.triggers.yolo.layer.entry.fragment.notification;

import java.util.List;

import javax.inject.Inject;

import kr.co.triggers.yolo.component.activity.YoloFragment;
import kr.co.triggers.yolo.component.activity.YoloFragmentPresenter;
import kr.co.triggers.yolo.domain.Notification;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;
import kr.co.triggers.yolo.model.NotificationModel;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by kyungjoo on 2016. 9. 28..
 */

public class NotificationPresenter extends YoloFragmentPresenter<NotificationView> {

    @Inject
    NotificationModel notificationModel;

    public NotificationPresenter(YoloFragment fragment) {

        this.view = (NotificationView) fragment;

        ActivityComponent component = DaggerActivityComponent.builder().appComponent(view.getAppComponent()).activityModule(new ActivityModule(fragment)).build();
        component.inject(this);
    }

    public void fetch() {

        Observable<Response<List<Notification>>> observable = notificationModel.fetch();
        observable.subscribe(new Action1<Response<List<Notification>>>() {

            @Override
            public void call(Response<List<Notification>> response) {

                if (response.isSuccessful()) {

                    view.setNotification(response.body());
                }
                else {

                    // TODO : 예외 처리
                }
            }
        });
    }

    public void fetch(long before, int count) {

        Observable<Response<List<Notification>>> observable = notificationModel.fetch(before, count);
        observable.subscribe(new Action1<Response<List<Notification>>>() {

            @Override
            public void call(Response<List<Notification>> response) {

                if (response.isSuccessful()) {

                    view.attachNotification(response.body());
                }
                else {

                    // TODO : 예외 처리
                }
            }
        });
    }

    public void read(final long id) {

        Observable<Response<Void>> observable = notificationModel.read(id);
        observable.subscribe(new Action1<Response<Void>>() {

            @Override
            public void call(Response<Void> response) {

                if (response.isSuccessful()) {

                    view.readNotification(id);
                }
                else {

                    // TODO : 예외 처리
                }
            }
        });
    }
}
