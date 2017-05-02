package kr.co.triggers.yolo.network.api;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.IOException;

import kr.co.triggers.yolo.app.App;
import kr.co.triggers.yolo.domain.User;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ApiRequestInterceptor implements Interceptor {

    private static final String CHARACTER_SPACE = " ";
    private static final String CHARACTER_EMPTY = "";
    private static final String CHARACTER_EQUALS = "=";
    private static final String CHARACTER_SEMICOLON = ";";

    private static final String COOKIE = "cookie";
    private static final String SET_COOKIE = "set-cookie";
    private static final String SESSION_ID = "session_id";
    private static final String PATH_LOGIN = "/sign/in";

    private static final Gson GSON = new Gson();

    private App app;

    public ApiRequestInterceptor(App app) {

        this.app = app;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        final String session = app.getSessionKey();

        if (session != null) {

            Request.Builder builder = request.newBuilder();
            builder.addHeader(COOKIE, String.format("%s=%s;", SESSION_ID, session));

            request = builder.build();
        }

        Response response = chain.proceed(request);

        String path = request.url().encodedPath();

        if (PATH_LOGIN.equals(path) && response.isSuccessful()) {

            String cookie = response.header(SET_COOKIE);
            String body = response.body().string();

            User user = GSON.fromJson(body, User.class);

            long id = user.getId();

            String name = user.getName();
            String token = getSessionToken(cookie);

            app.saveSession(id, name, token);

            Response.Builder builder = response.newBuilder();
            builder.body(ResponseBody.create(response.body().contentType(), body));

            return builder.build();
        }

        return response;
    }

    private String getSessionToken(String cookie) {

        if (TextUtils.isEmpty(cookie))
            return null;

        String[] tokens = cookie.replace(CHARACTER_SPACE, CHARACTER_EMPTY).split(CHARACTER_SEMICOLON);

        for (String token : tokens) {

            String[] split = token.split(CHARACTER_EQUALS);

            if (split.length != 2)
                continue;

            String key = split[0];
            String value = split[1];

            if (key.equals(SESSION_ID))
                return value;
        }

        return null;
    }
}
