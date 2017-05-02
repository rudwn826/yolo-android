package kr.co.triggers.yolo.component.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.tsengvn.typekit.TypekitContextWrapper;

import kr.co.triggers.yolo.app.App;
import kr.co.triggers.yolo.inject.component.AppComponent;

public class YoloView extends AppCompatActivity {

    public static final String YOUTUBE_PLAYER_TAG = "player";

    public AppComponent getAppComponent() {

        App app = (App) getApplicationContext();
        return app.getAppComponent();
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
