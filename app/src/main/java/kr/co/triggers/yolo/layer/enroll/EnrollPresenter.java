package kr.co.triggers.yolo.layer.enroll;

import java.util.Date;

import javax.inject.Inject;

import kr.co.triggers.yolo.component.activity.YoloViewPresenter;
import kr.co.triggers.yolo.domain.User;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;
import kr.co.triggers.yolo.model.UserModel;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Action1;

public class EnrollPresenter extends YoloViewPresenter<EnrollView> {

    @Inject
    UserModel model;

    public EnrollPresenter(EnrollView view) {

        this.view = view;

        ActivityComponent component = DaggerActivityComponent.builder().appComponent(view.getAppComponent()).activityModule(new ActivityModule(view)).build();
        component.inject(this);
    }

    public void enroll(String token, String name, Date birth, String gender, String url){
        Observable<Response<User>> observable = model.register(token, name, birth, gender, url);
        observable.subscribe(new Action1<Response<User>>() {
            @Override
            public void call(Response<User> response) {
                if(response.isSuccessful()){
                    view.enrollSuccess();
                }else{
                    view.enrollFail();
                }
            }
        });
    }
}