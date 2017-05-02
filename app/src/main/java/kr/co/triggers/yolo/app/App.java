package kr.co.triggers.yolo.app;

import android.app.Application;
import android.content.SharedPreferences;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.tsengvn.typekit.Typekit;

import kr.co.triggers.yolo.inject.component.AppComponent;
import kr.co.triggers.yolo.inject.component.DaggerAppComponent;
import kr.co.triggers.yolo.inject.module.AppModule;

public class App extends Application {

    public static final String PREF_NAME = "CookieStore";

    private static final String SESSION_KEY_ID = "session.id";
    private static final String SESSION_KEY_TOKEN = "session.token";
    private static final String SESSION_KEY_NAME = "session.name";

    AppComponent component;

    @Override
    public void onCreate() {

        super.onCreate();

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        component = DaggerAppComponent.builder().appModule(new AppModule(this)).build();

        Typekit typekit = Typekit.getInstance();

        typekit.addNormal(Typekit.createFromAsset(this, "fonts/NotoSansCJKkr-Regular.otf"));
        typekit.addBold(Typekit.createFromAsset(this, "fonts/NotoSansCJKkr-Bold.otf"));

        component.inject(this);
    }

    public AppComponent getAppComponent() { return component; }

    public String getSessionKey() {

        return getPreferences().getString(SESSION_KEY_TOKEN, null);
    }

    public void saveSession(long id, String name, String token) {

        SharedPreferences preferences = getPreferences();
        SharedPreferences.Editor editor = preferences.edit();

        editor.putLong(SESSION_KEY_ID, id);
        editor.putString(SESSION_KEY_TOKEN, token);
        editor.putString(SESSION_KEY_NAME, name);

        editor.commit();
    }

    public void clearSession() {

        SharedPreferences preferences = getPreferences();
        SharedPreferences.Editor editor = preferences.edit();

        editor.clear();
        editor.commit();
    }

    private SharedPreferences preferences;
    private synchronized SharedPreferences getPreferences() {

        if (preferences != null)
            return preferences;

        preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        return preferences;
    }
}
