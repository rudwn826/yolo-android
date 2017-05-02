package kr.co.triggers.yolo.model;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import kr.co.triggers.yolo.app.App;
import kr.co.triggers.yolo.domain.Artist;
import kr.co.triggers.yolo.domain.Fiesta;
import kr.co.triggers.yolo.domain.Track;
import kr.co.triggers.yolo.domain.User;
import kr.co.triggers.yolo.inject.component.AppComponent;
import kr.co.triggers.yolo.network.service.AuthService;
import kr.co.triggers.yolo.network.service.UserService;
import kr.co.triggers.yolo.scenario.Scenario;
import kr.co.triggers.yolo.util.Photoshop;
import okhttp3.MultipartBody;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserModel {

    @Inject
    AuthService authService;

    @Inject
    UserService userService;

    public UserModel(App app) {

        AppComponent component = app.getAppComponent();
        component.inject(this);
    }

    public Observable<Response<User>> register(String token, String name, Date birth, String gender, String url){

        MultipartBody.Part profile = Photoshop.getMultipart("profile", url);

        //Observable<Response<User>> observable = authService.register(token, name, birth, gender, profile);
        Observable<Response<User>> observable = Scenario.register(token, name, birth, gender, profile);

        return observable.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread());
    }

    public Observable<Response<User>> login(String token) {

        Observable<Response<User>> observable = authService.login(token);

        return observable.observeOn(AndroidSchedulers.mainThread())
                         .subscribeOn(Schedulers.newThread());
    }

    public Observable<Response<User>> me() {

        // Observable<Response<User>> observable = userService.me();
        Observable<Response<User>> observable = Scenario.me();

        return observable.observeOn(AndroidSchedulers.mainThread())
                         .subscribeOn(Schedulers.newThread());
    }

    public Observable<Response<List<Artist>>> fetchArtists() {

        Observable<Response<List<Artist>>> observable = Scenario.mockArtistsObservable();
        // Observable<Response<List<Artist>>> observable = userService.artists();

        return observable.observeOn(AndroidSchedulers.mainThread())
                         .subscribeOn(Schedulers.newThread());
    }

    public Observable<Response<List<Artist>>> fetchArtists(long before, int count) {

        Observable<Response<List<Artist>>> observable = Scenario.mockArtistsObservable();
        // Observable<Response<List<Artist>>> observable = userService.artists(before, count);

        return observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }


    public Observable<Response<List<Track>>> fetchTracks() {

        Observable<Response<List<Track>>> observable = Scenario.mockTracksObservable();
        // Observable<Response<List<Track>>> observable = userService.tracks();

        return observable.observeOn(AndroidSchedulers.mainThread())
                         .subscribeOn(Schedulers.newThread());
    }

    public Observable<Response<List<Track>>> fetchTracks(long before, int count) {

        Observable<Response<List<Track>>> observable = Scenario.mockTracksObservable();
        // Observable<Response<List<Track>>> observable = userService.tracks(before, count);

        return observable.observeOn(AndroidSchedulers.mainThread())
                         .subscribeOn(Schedulers.newThread());
    }

    public Observable<Response<List<Fiesta>>> fetchFiesta() {

        Observable<Response<List<Fiesta>>> observable = Scenario.mockFiesta();
        // Observable<Response<List<Fiesta>>> observable = userService.fiesta();

        return observable.observeOn(AndroidSchedulers.mainThread())
                         .subscribeOn(Schedulers.newThread());
    }

    public Observable<Response<List<Fiesta>>> fetchFiesta(long before, int count) {

        Observable<Response<List<Fiesta>>> observable = Scenario.mockFiesta();
        // Observable<Response<List<Fiesta>>> observable = userService.fiesta(before, count);

        return observable.observeOn(AndroidSchedulers.mainThread())
                         .subscribeOn(Schedulers.newThread());
    }
}

