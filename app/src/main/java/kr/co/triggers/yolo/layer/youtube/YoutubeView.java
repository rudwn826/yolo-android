package kr.co.triggers.yolo.layer.youtube;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import kr.co.triggers.yolo.R;
import kr.co.triggers.yolo.component.activity.YoloView;
import kr.co.triggers.yolo.databinding.LayoutViewYoutubeBinding;
import kr.co.triggers.yolo.domain.Track;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;

public class YoutubeView extends YoloView implements YouTubePlayer.OnInitializedListener {

    public static final String TAG_PLAYER = "youtube.player";
    public static final String KEY_PLAYER_POINT = "youtube.player.point";

    ActivityComponent component;
    LayoutViewYoutubeBinding binding;

    String apiKey;

    long artistId;
    String artistName;

    String trackLink;
    String trackName;

    int point = -1;

    YouTubePlayer player;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        component = DaggerActivityComponent.builder().appComponent(getAppComponent()).activityModule(new ActivityModule(this)).build();
        component.inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.layout_view_youtube);

        apiKey = getString(R.string.youtube_api_key);

        final Intent intent = getIntent();

        if (!intent.hasExtra(Track.KEY_TRACK_LINK)) {

            // TODO : 예외 처리

            finish();
            return;
        }

        this.trackLink = intent.getStringExtra(Track.KEY_TRACK_LINK);

        // TODO : 10. 6. 아티스트 이름이 전달되면 그대로 사용하고 아니면 서버에서 가져올 것 !!

        YouTubePlayerSupportFragment fragment = YouTubePlayerSupportFragment.newInstance();
        fragment.initialize(apiKey, this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.fragmentContainer, fragment, TAG_PLAYER);
        transaction.commit();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean restored) {

        this.player = player;

        if (point != -1) {

            player.seekToMillis(point);
        }

        player.loadVideo(trackLink);
        player.play();
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {

    }
}
