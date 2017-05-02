package kr.co.triggers.yolo.layer.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;

import javax.inject.Inject;

import kr.co.triggers.yolo.R;
import kr.co.triggers.yolo.app.App;
import kr.co.triggers.yolo.component.activity.YoloView;
import kr.co.triggers.yolo.databinding.LayoutViewLoginBinding;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;
import kr.co.triggers.yolo.layer.enroll.EnrollView;
import kr.co.triggers.yolo.layer.entry.EntryView;

public class LoginView extends YoloView {

    private static final long SPLASH_DURATION = 1000;
    ActivityComponent component;
    LayoutViewLoginBinding binding;

    CallbackManager manager;

    @Inject
    LoginPresenter presenter;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        manager = CallbackManager.Factory.create();
        handler = new Handler();

        component = DaggerActivityComponent.builder().appComponent(getAppComponent()).activityModule(new ActivityModule(this)).build();
        component.inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.layout_view_login);
        binding.setPresenter(presenter);

        App app = (App) getApplication();

        if (TextUtils.isEmpty(app.getSessionKey())) {

            final Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.splashView);

            handler.postDelayed(new Runnable() {

                @Override
                public void run() {

                    if (fragment != null) {

                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                        transaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
                        transaction.hide(fragment);
                        transaction.commit();
                    }
                }

            }, SPLASH_DURATION);

            LoginManager.getInstance().logOut();
        }
    }

    public void moveToEntryView() {

        Intent intent = new Intent(this, EntryView.class);
        startActivity(intent);

        finish();
    }

    public void moveToEnrollView() {

        Intent intent = new Intent(this, EnrollView.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        manager.onActivityResult(requestCode, resultCode, data);
    }
}
