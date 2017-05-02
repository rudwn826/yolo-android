package kr.co.triggers.yolo.model;

import java.util.List;

import javax.inject.Inject;

import kr.co.triggers.yolo.app.App;
import kr.co.triggers.yolo.domain.Artist;
import kr.co.triggers.yolo.domain.Track;
import kr.co.triggers.yolo.inject.component.AppComponent;
import kr.co.triggers.yolo.network.service.ArtistService;
import kr.co.triggers.yolo.scenario.Scenario;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ArtistModel {

    @Inject
    ArtistService artistService;

    public ArtistModel(App app) {

        AppComponent component = app.getAppComponent();
        component.inject(this);
    }

    public Observable<Response<Artist>> findById(long id) {

        Observable<Response<Artist>> observable = Scenario.mockArtistObservable();
        // Observable<Response<Artist>> observable = artistService.findById(id);

        return observable.observeOn(AndroidSchedulers.mainThread())
                         .subscribeOn(Schedulers.newThread());
    }

    public Observable<Response<List<Track>>> tracks(long id) {

        Observable<Response<List<Track>>> observable = Scenario.mockTracksObservable();
        // Observable<Response<Artist>> observable = artistService.tracks(id);

        return observable.observeOn(AndroidSchedulers.mainThread())
                         .subscribeOn(Schedulers.newThread());
    }

    public Observable<Response<String>> profile(long id) {

        Observable<Response<String>> observable = Scenario.mockArtistProfileObservable();
        // Observable<Response<String>> observable = artistService.profile(id);

        return observable.observeOn(AndroidSchedulers.mainThread())
                         .subscribeOn(Schedulers.newThread());
    }

    public Observable<Response<List<Artist>>> fetch() {

        Observable<Response<List<Artist>>> observable = Scenario.mockArtistsObservable();
        // Observable<Response<List<Artist>>> observable = artistService.fetch();

        return observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }

    public Observable<Response<List<Artist>>> fetch(long id, int count) {

        Observable<Response<List<Artist>>> observable = Scenario.mockArtistsObservable();
        // Observable<Response<List<Artist>>> observable = artistService.fetch(id, count);

        return observable.observeOn(AndroidSchedulers.mainThread())
                         .subscribeOn(Schedulers.newThread());
    }
}
