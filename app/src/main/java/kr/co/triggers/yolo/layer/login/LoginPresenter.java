package kr.co.triggers.yolo.layer.login;

import android.text.TextUtils;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

import javax.inject.Inject;

import kr.co.triggers.yolo.app.App;
import kr.co.triggers.yolo.component.activity.YoloViewPresenter;
import kr.co.triggers.yolo.domain.User;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;
import kr.co.triggers.yolo.model.UserModel;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Action1;

public class LoginPresenter extends YoloViewPresenter<LoginView> implements FacebookCallback<LoginResult> {

    @Inject
    UserModel userModel;

    public LoginPresenter(LoginView view) {

        this.view = view;

        ActivityComponent component = DaggerActivityComponent.builder().appComponent(view.getAppComponent()).activityModule(new ActivityModule(view)).build();
        component.inject(this);

        LoginManager.getInstance().registerCallback(view.manager, this);
    }

    public void login() {

        AccessToken token = AccessToken.getCurrentAccessToken();

        if (token == null || token.isExpired()) {

            LoginManager manager = LoginManager.getInstance();
            manager.logInWithReadPermissions(view, Arrays.asList("email", "public_profile", "user_friends"));

            return;
        }

        requestLogin(token);
    }

    private void requestLogin(AccessToken token) {

        App app = (App) view.getApplication();

        if (!TextUtils.isEmpty(app.getSessionKey())) {

            view.moveToEntryView();
            return;
        }

        Observable<Response<User>> observable = userModel.login(token.getToken());
        observable.subscribe(new Action1<Response<User>>() {

            @Override
            public void call(Response<User> response) {

                if (response.isSuccessful()) {

                    view.moveToEntryView();
                }
                else {

                    if (response.code() == 404) {

                        view.moveToEnrollView();
                    }
                    else {

                        // TODO : 예외 처리 - 핸들링되지 않은 에러
                    }
                }
            }
        });
    }


    @Override
    public void onSuccess(LoginResult loginResult) {

        AccessToken token = loginResult.getAccessToken();
        requestLogin(token);
    }

    @Override
    public void onCancel() {

        // TODO : 예외 처리 - 로그인을 직접 취소
    }

    @Override
    public void onError(FacebookException error) {

        // TODO : 예외 처리 - 페이스북에서 에러 발생
    }
}
