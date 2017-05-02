package kr.co.triggers.yolo.inject.component;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;

import dagger.Component;
import kr.co.triggers.yolo.adapter.ArtistAdapter;
import kr.co.triggers.yolo.adapter.ArtistThumbnailAdapter;
import kr.co.triggers.yolo.adapter.NotificationAdapter;
import kr.co.triggers.yolo.adapter.PerformAdapter;
import kr.co.triggers.yolo.adapter.FeedAdapter;
import kr.co.triggers.yolo.adapter.FiestaAdapter;
import kr.co.triggers.yolo.adapter.ProfileAdapter;
import kr.co.triggers.yolo.adapter.SearchHistoryAdapter;
import kr.co.triggers.yolo.adapter.TrackAdapter;
import kr.co.triggers.yolo.inject.module.ActivityModule;
import kr.co.triggers.yolo.inject.scope.PerActivity;
import kr.co.triggers.yolo.layer.artist.ArtistPresenter;
import kr.co.triggers.yolo.layer.artist.ArtistView;
import kr.co.triggers.yolo.layer.enroll.EnrollPresenter;
import kr.co.triggers.yolo.layer.enroll.EnrollView;
import kr.co.triggers.yolo.layer.entry.EntryPresenter;
import kr.co.triggers.yolo.layer.entry.EntryView;
import kr.co.triggers.yolo.layer.entry.fragment.artist.ArtistCollectionPresenter;
import kr.co.triggers.yolo.layer.entry.fragment.artist.ArtistCollectionView;
import kr.co.triggers.yolo.layer.entry.fragment.fiesta.FiestaCollectionAdapter;
import kr.co.triggers.yolo.layer.entry.fragment.fiesta.FiestaCollectionPresenter;
import kr.co.triggers.yolo.layer.entry.fragment.fiesta.FiestaCollectionView;
import kr.co.triggers.yolo.layer.entry.fragment.menu.MenuView;
import kr.co.triggers.yolo.layer.entry.fragment.notification.NotificationPresenter;
import kr.co.triggers.yolo.layer.entry.fragment.notification.NotificationView;
import kr.co.triggers.yolo.layer.entry.fragment.profile.ProfilePresenter;
import kr.co.triggers.yolo.layer.entry.fragment.profile.ProfileView;
import kr.co.triggers.yolo.layer.explore.ExplorePresenter;
import kr.co.triggers.yolo.layer.explore.ExploreView;
import kr.co.triggers.yolo.layer.explore.fragment.ExploreDetailPresenter;
import kr.co.triggers.yolo.layer.explore.fragment.ExploreDetailView;
import kr.co.triggers.yolo.layer.login.LoginPresenter;
import kr.co.triggers.yolo.layer.login.LoginView;
import kr.co.triggers.yolo.layer.youtube.YoutubeView;

@PerActivity
@Component(modules = { ActivityModule.class }, dependencies = { AppComponent.class })
public interface ActivityComponent extends AppComponent {

    Activity activity();
    Fragment fragment();

    ArtistPresenter artistPresenter();
    EnrollPresenter enrollPresenter();
    EntryPresenter entryPresenter();
    ExplorePresenter explorePresenter();
    LoginPresenter loginPresenter();

    FiestaCollectionPresenter fiestaCollectionPresenter();
    ArtistCollectionPresenter artistCollectionPresenter();
    NotificationPresenter notificationPresenter();
    ProfilePresenter profilePresenter();
    ExploreDetailPresenter exploreDetailPresenter();
    ProfileAdapter profileAdapter();

    FiestaCollectionView fiestaCollectionView();
    ArtistCollectionView artistCollectionView();
    NotificationView notificationView();
    ProfileView profileView();
    MenuView menuView();

    PerformAdapter performAdapter();
    TrackAdapter trackAdapter();
    FiestaAdapter fiestaAdapter();
    FeedAdapter feedAdapter();
    SearchHistoryAdapter searchHistoryAdapter();
    ArtistAdapter artistAdapter();
    ArtistThumbnailAdapter artistThumbnailAdapter();
    NotificationAdapter notificationAdapter();

    SpringSystem springSystem();
    Spring spring();

    FiestaCollectionAdapter fiestaCollectionAdapter();

    void inject(ArtistView view);
    void inject(EnrollView view);
    void inject(EntryView view);
    void inject(ExploreView view);
    void inject(LoginView view);
    void inject(YoutubeView youtubeView);

    void inject(FiestaCollectionView fiestaCollectionView);
    void inject(ArtistCollectionView artistCollectionView);
    void inject(NotificationView notificationView);
    void inject(ProfileView profileView);

    void inject(ArtistPresenter presenter);
    void inject(EnrollPresenter presenter);
    void inject(EntryPresenter presenter);
    void inject(ExplorePresenter presenter);
    void inject(LoginPresenter presenter);

    void inject(ArtistCollectionPresenter artistCollectionPresenter);
    void inject(FiestaCollectionPresenter fiestaCollectionPresenter);
    void inject(NotificationPresenter notificationPresenter);
    void inject(ProfilePresenter profilePresenter);
    void inject(ProfileAdapter profileAdapter);

    void inject(PerformAdapter performAdapter);
    void inject(FeedAdapter feedAdapter);
    void inject(FiestaAdapter fiestaAdapter);
    void inject(TrackAdapter trackAdapter);
    void inject(SearchHistoryAdapter searchHistoryAdapter);
    void inject(ArtistThumbnailAdapter artistThumbnailAdapter);

    void inject(ExploreDetailView exploreDetailView);
    void inject(ExploreDetailPresenter exploreDetailPresenter);
    void inject(NotificationAdapter notificationAdapter);

    void inject(ArtistAdapter artistAdapter);
    void inject(FiestaCollectionAdapter fiestaCollectionAdapter);

    void inject(MenuView menuView);

}