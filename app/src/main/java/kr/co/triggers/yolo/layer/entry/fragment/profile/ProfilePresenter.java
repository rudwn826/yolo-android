package kr.co.triggers.yolo.layer.entry.fragment.profile;

import java.util.List;

import javax.inject.Inject;

import kr.co.triggers.yolo.component.activity.YoloFragment;
import kr.co.triggers.yolo.component.activity.YoloFragmentPresenter;
import kr.co.triggers.yolo.domain.Artist;
import kr.co.triggers.yolo.domain.Fiesta;
import kr.co.triggers.yolo.domain.Track;
import kr.co.triggers.yolo.domain.User;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;
import kr.co.triggers.yolo.model.UserModel;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Action1;

public class ProfilePresenter extends YoloFragmentPresenter<ProfileView> {

    @Inject
    UserModel userModel;

    public ProfilePresenter(YoloFragment fragment) {

        this.view = (ProfileView) fragment;

        ActivityComponent component = DaggerActivityComponent.builder().appComponent(view.getAppComponent()).activityModule(new ActivityModule(fragment)).build();
        component.inject(this);
    }

    public void me() {

        Observable<Response<User>> observable = userModel.me();
        observable.subscribe(new Action1<Response<User>>() {

            @Override
            public void call(Response<User> response) {

                if (response.isSuccessful()) {

                    view.setUser(response.body());
                }
                else {

                    // TODO : 예외 처리
                }
            }
        });
    }

    public void fetchArtists() {

        Observable<Response<List<Artist>>> observable = userModel.fetchArtists();
        observable.subscribe(new Action1<Response<List<Artist>>>() {

            @Override
            public void call(Response<List<Artist>> response) {

                if (response.isSuccessful()) {

                    view.setArtists(response.body());
                }
                else {

                    // TODO : 예외 처리
                }
            }
        });
    }

    public void fetchArtists(long before, int count) {

        Observable<Response<List<Artist>>> observable = userModel.fetchArtists(before, count);
        observable.subscribe(new Action1<Response<List<Artist>>>() {

            @Override
            public void call(Response<List<Artist>> response) {

                if (response.isSuccessful()) {

                    view.addArtists(response.body());
                }
                else {

                    // TODO : 예외 처리
                }
            }
        });
    }

    public void fetchTracks() {

        Observable<Response<List<Track>>> observable = userModel.fetchTracks();
        observable.subscribe(new Action1<Response<List<Track>>>() {

            @Override
            public void call(Response<List<Track>> response) {

                if (response.isSuccessful()) {

                    view.setTracks(response.body());
                }
                else {

                    // TODO : 예외 처리
                }
            }
        });
    }

    public void fetchTracks(long before, int count) {

        Observable<Response<List<Track>>> observable = userModel.fetchTracks(before, count);
        observable.subscribe(new Action1<Response<List<Track>>>() {

            @Override
            public void call(Response<List<Track>> response) {

                if (response.isSuccessful()) {

                    view.addTracks(response.body());
                }
                else {

                    // TODO : 예외 처리
                }
            }
        });
    }

    public void fetchFiesta() {

        Observable<Response<List<Fiesta>>> observable = userModel.fetchFiesta();
        observable.subscribe(new Action1<Response<List<Fiesta>>>() {

            @Override
            public void call(Response<List<Fiesta>> response) {

                if (response.isSuccessful()) {

                    view.setFiesta(response.body());
                }
                else {

                    // TODO : 예외 처리
                }
            }
        });
    }

    public void fetchFiesta(long before, int count) {

        Observable<Response<List<Fiesta>>> observable = userModel.fetchFiesta(before, count);
        observable.subscribe(new Action1<Response<List<Fiesta>>>() {

            @Override
            public void call(Response<List<Fiesta>> response) {

                if (response.isSuccessful()) {

                    view.addFiesta(response.body());
                }
                else {

                    // TODO : 예외 처리
                }
            }
        });
    }
}
