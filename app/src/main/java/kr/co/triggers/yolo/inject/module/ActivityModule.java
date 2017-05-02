package kr.co.triggers.yolo.inject.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;

import dagger.Module;
import dagger.Provides;
import kr.co.triggers.yolo.adapter.ArtistAdapter;
import kr.co.triggers.yolo.adapter.ArtistThumbnailAdapter;
import kr.co.triggers.yolo.adapter.NotificationAdapter;
import kr.co.triggers.yolo.adapter.PerformAdapter;
import kr.co.triggers.yolo.adapter.FeedAdapter;
import kr.co.triggers.yolo.adapter.FiestaAdapter;
import kr.co.triggers.yolo.adapter.ProfileAdapter;
import kr.co.triggers.yolo.adapter.SearchHistoryAdapter;
import kr.co.triggers.yolo.adapter.TrackAdapter;
import kr.co.triggers.yolo.component.activity.YoloFragment;
import kr.co.triggers.yolo.component.activity.YoloView;
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

@Module
public class ActivityModule {

    YoloView view;
    YoloFragment fragment;

    public ActivityModule(YoloView view) {

        this.view = view;
    }

    public ActivityModule(YoloFragment fragment) {

        this.fragment = fragment;
        this.view = (YoloView) fragment.getActivity();
    }


    @Provides
    public Activity activity() { return view; }

    @Provides
    public Fragment fragment() { return fragment; }


    @Provides
    public ArtistPresenter artistPresenter() { return new ArtistPresenter((ArtistView) view); }

    @Provides
    public EnrollPresenter enrollPresenter() { return new EnrollPresenter((EnrollView) view); }

    @Provides
    public EntryPresenter entryPresenter() { return new EntryPresenter((EntryView) view); }

    @Provides
    public ExplorePresenter explorePresenter() { return new ExplorePresenter((ExploreView) view); }

    @Provides
    public LoginPresenter loginPresenter() { return new LoginPresenter((LoginView) view); }


    @Provides
    public FiestaCollectionPresenter fiestaCollectionPresenter() { return new FiestaCollectionPresenter(fragment); }

    @Provides
    public ProfilePresenter profilePresenter() { return new ProfilePresenter(fragment); }

    @Provides
    public ArtistCollectionPresenter artistCollectionPresenter() { return new ArtistCollectionPresenter(fragment); }

    @Provides
    public ExploreDetailPresenter exploreDetailPresenter() { return new ExploreDetailPresenter(fragment); }

    @Provides
    public NotificationPresenter notificationPresenter() { return new NotificationPresenter(fragment); }


    @Provides
    FiestaCollectionView fiestaCollectionView() { return new FiestaCollectionView(); }

    @Provides
    ExploreDetailView exploreDetailView() { return new ExploreDetailView(); }

    @Provides
    ArtistCollectionView artistCollectionView() { return new ArtistCollectionView(); }

    @Provides
    NotificationView notificationView() { return new NotificationView(); }

    @Provides
    ProfileView profileView() { return new ProfileView(); }

    @Provides
    MenuView menuView() { return new MenuView(); }

    @Provides
    public ProfileAdapter profileAdapter() { return new ProfileAdapter(fragment); }

    @Provides
    public PerformAdapter performAdapter() { return new PerformAdapter(view); }

    @Provides
    public TrackAdapter trackAdapter() { return new TrackAdapter(view); }

    @Provides
    public FeedAdapter feedAdapter() { return new FeedAdapter(view); }

    @Provides
    public FiestaAdapter fiestaAdapter() { return new FiestaAdapter(view); }

    @Provides
    public ArtistAdapter artistAdapter() { return new ArtistAdapter(view); }

    @Provides
    public ArtistThumbnailAdapter artistThumbnailAdapter() { return new ArtistThumbnailAdapter(view); }

    @Provides
    public NotificationAdapter notificationAdapter() { return new NotificationAdapter(view); }


    @Provides
    public FiestaCollectionAdapter fiestaCollectionAdapter() { return new FiestaCollectionAdapter(fragment); }

    @Provides
    public SearchHistoryAdapter searchHistoryAdapter() { return new SearchHistoryAdapter(view); };

    @Provides
    public SpringSystem springSystem() { return SpringSystem.create(); }

    @Provides
    public Spring spring(SpringSystem system) { return system.createSpring(); }

}
