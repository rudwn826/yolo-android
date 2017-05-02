package kr.co.triggers.yolo.layer.artist;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.List;

import javax.inject.Inject;

import kr.co.triggers.yolo.R;
import kr.co.triggers.yolo.adapter.FeedAdapter;
import kr.co.triggers.yolo.adapter.OnItemClickListener;
import kr.co.triggers.yolo.adapter.TrackAdapter;
import kr.co.triggers.yolo.component.activity.YoloView;
import kr.co.triggers.yolo.databinding.LayoutViewArtistBinding;
import kr.co.triggers.yolo.decoration.SpacingItemDecoration;
import kr.co.triggers.yolo.domain.Artist;
import kr.co.triggers.yolo.domain.Track;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;
import kr.co.triggers.yolo.layer.youtube.YoutubeView;
import kr.co.triggers.yolo.util.DataTag;

public class ArtistView extends YoloView implements OnItemClickListener<Track> {

    private LayoutViewArtistBinding binding;

    @Inject
    ArtistPresenter presenter;

    @Inject
    FeedAdapter feedAdapter;

    @Inject
    TrackAdapter trackAdapter;

    @Inject
    Picasso picasso;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        if (!intent.hasExtra(Artist.KEY_ARTIST_ID)) {

            // TODO : 예외 처리 : 값이 전달되지 않음.

            finish();
            return;
        }

        long id = intent.getLongExtra(Artist.KEY_ARTIST_ID, -1);

        if (id == -1) {

            // TODO : 예외 처리 : 비정상적인 값이 전달됨.

            finish();
            return;
        }

        ActivityComponent component = DaggerActivityComponent.builder().appComponent(getAppComponent()).activityModule(new ActivityModule(this)).build();
        component.inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.layout_view_artist);
        binding.setPresenter(presenter);

        binding.recyclerViewFeeds.addItemDecoration(new SpacingItemDecoration(this));
        binding.recyclerViewFeeds.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.recyclerViewFeeds.setAdapter(feedAdapter);

        binding.recyclerViewTracks.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.recyclerViewTracks.setAdapter(trackAdapter);

        trackAdapter.setOnItemClickListener(this);

        presenter.fetchArtistProfile(id);
        presenter.fetchArtistDetail(id);
        presenter.fetchArtistTracks(id);
    }

    public void setArtistProfile(String url) {

        RequestCreator request = picasso.load(url);

        request.into(binding.imageViewArtistProfile);
        request.into(binding.imageViewBackground);
    }

    public void setArtistInformation(Artist artist) {

        binding.setArtist(artist);

        feedAdapter.setFeeds(artist.getFeeds());
        if(artist.getData()!=null) {
            DataTag dataTag = new DataTag(artist.getData());
            binding.tagViewInformation.addTags(dataTag.getTags());
        }
    }

    public void setTracks(List<Track> tracks) {

        trackAdapter.setTracks(tracks);
    }

    @Override
    public void onItemClick(Track track) {

        Artist artist = binding.getArtist();

        Intent intent = new Intent(this, YoutubeView.class);

        intent.putExtra(Artist.KEY_ARTIST_ID, artist.getId());
        intent.putExtra(Artist.KEY_ARTIST_NAME, artist.getName());

        intent.putExtra(Track.KEY_TRACK_ID, track.getId());
        intent.putExtra(Track.KEY_TRACK_NAME, track.getTitle());
        intent.putExtra(Track.KEY_TRACK_LINK, track.getLink());

        startActivity(intent);
    }
}
