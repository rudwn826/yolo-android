package kr.co.triggers.yolo.layer.explore.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import kr.co.triggers.yolo.R;
import kr.co.triggers.yolo.adapter.ArtistThumbnailAdapter;
import kr.co.triggers.yolo.adapter.FiestaAdapter;
import kr.co.triggers.yolo.adapter.OnItemClickListener;
import kr.co.triggers.yolo.adapter.TrackAdapter;
import kr.co.triggers.yolo.component.activity.YoloFragment;
import kr.co.triggers.yolo.component.activity.YoloView;
import kr.co.triggers.yolo.databinding.LayoutFragmentExploreDetailBinding;
import kr.co.triggers.yolo.decoration.DividerItemDecoration;
import kr.co.triggers.yolo.domain.Artist;
import kr.co.triggers.yolo.domain.Fiesta;
import kr.co.triggers.yolo.domain.Track;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;
import kr.co.triggers.yolo.layer.artist.ArtistView;
import kr.co.triggers.yolo.layer.youtube.YoutubeView;
import kr.co.triggers.yolo.util.Constraints;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ExploreDetailView extends YoloFragment {

    class RecyclerScroller extends RecyclerView.OnScrollListener {

        private boolean loading = false;

        boolean isLoading() { return loading; }
        synchronized void setLoading(boolean loading) { this.loading = loading; }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            long id;

            if (dy <= 0) {
                return;
            }

            if (isLoading()){
                return;
            }

            LinearLayoutManager layoutManager = (LinearLayoutManager) binding.detailViewList.getLayoutManager();

            int visible = layoutManager.getChildCount();
            int total = layoutManager.getItemCount();
            int last = layoutManager.findFirstVisibleItemPosition();

            if (total <= visible + last) {
                setLoading(true);

                if(type == TRACK_DETAIL)
                    id = trackAdapter.getItemId(trackAdapter.getItemCount() - 1);
                else if(type == ARTIST_DETAIL)
                    id = thumbnailAdapter.getItemId(thumbnailAdapter.getItemCount() - 1);
                else
                    id = fiestaAdapter.getItemId(fiestaAdapter.getItemCount() - 1);

                presenter.fetch(type, id, Constraints.FETCH_COUNT);
            }
        }
    }

    public static final int TRACK_DETAIL = 1;
    public static final int ARTIST_DETAIL = 2;
    public static final int FIESTA_DETAIL = 3;

    private int type = 0;
    RecyclerScroller scroller;

    LayoutFragmentExploreDetailBinding binding;

    @Inject
    ExploreDetailPresenter presenter;

    @Inject
    TrackAdapter trackAdapter;

    @Inject
    FiestaAdapter fiestaAdapter;

    @Inject
    ArtistThumbnailAdapter thumbnailAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        YoloView view = (YoloView) getActivity();

        ActivityComponent component = DaggerActivityComponent.builder().appComponent(view.getAppComponent()).activityModule(new ActivityModule(this)).build();
        component.inject(this);

        binding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_explore_detail, container, false);
        binding.detailViewList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        scroller = new RecyclerScroller();
        binding.detailViewList.addOnScrollListener(scroller);

        view.setSupportActionBar(binding.detailToolbar);

        final ActionBar toolbar = view.getSupportActionBar();

        if (toolbar != null) {

            toolbar.setHomeButtonEnabled(true);
            toolbar.setTitle(null);
            toolbar.setDisplayHomeAsUpEnabled(true);
        }

        if (type == TRACK_DETAIL) {

            binding.detailViewList.setAdapter(trackAdapter);
            binding.detailViewList.addItemDecoration(new DividerItemDecoration(getContext(), R.id.trackItemLayout));
            trackAdapter.setOnItemClickListener(new OnItemClickListener<Track>() {

                @Override
                public void onItemClick(Track track) {

                    Intent intent = new Intent(getContext(), YoutubeView.class);
                    intent.putExtra(Track.KEY_TRACK_LINK, track.getLink());

                    startActivity(intent);
                }
            });

        }
        else if (type == ARTIST_DETAIL) {

            binding.detailViewList.setAdapter(thumbnailAdapter);
            binding.detailViewList.addItemDecoration(new DividerItemDecoration(getContext(), R.id.artistItemText));
            thumbnailAdapter.setOnItemClickListener(new OnItemClickListener<Artist>() {

                @Override
                public void onItemClick(Artist artist) {

                    Intent intent = new Intent(getApplicationContext(), ArtistView.class);
                    intent.putExtra(Artist.KEY_ARTIST_ID, artist.getId());

                    startActivity(intent);
                }
            });

        }
        else if (type == FIESTA_DETAIL) {

            binding.detailViewList.setAdapter(fiestaAdapter);
            binding.detailViewList.addItemDecoration(new DividerItemDecoration(getContext(), R.id.fiestaItemLayout));
            fiestaAdapter.setOnItemClickListener(new OnItemClickListener<Fiesta>() {

                @Override
                public void onItemClick(Fiesta fiesta) {

                    Toast.makeText(getContext(), "" + fiesta.getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        presenter.fetch(type);
        return binding.getRoot();
    }

    public void setTracks(List<Track> tracks){
        scroller.setLoading(false);
        trackAdapter.setTracks(tracks);
        binding.detailText.setText(trackAdapter.getItemCount() + " Track");
    }
    public void setArtists(List<Artist> artists) {
        scroller.setLoading(false);
        thumbnailAdapter.setArtists(artists);
        binding.detailText.setText(thumbnailAdapter.getItemCount() + " Artist");
    }
    public void setFiesta(List<Fiesta> fiesta) {
        scroller.setLoading(false);
        fiestaAdapter.setFiesta(fiesta);
        binding.detailText.setText(fiestaAdapter.getItemCount() + " Fiesta");
    }

    public void attachTracks(List<Track> tracks){
        scroller.setLoading(false);
        trackAdapter.addAll(tracks);
        binding.detailText.setText(trackAdapter.getItemCount() + " Track");
    }

    public void attachArtists(List<Artist> artists) {
        scroller.setLoading(false);
        thumbnailAdapter.addAll(artists);
        binding.detailText.setText(thumbnailAdapter.getItemCount() + " Artist");
    }

    public void attachFiesta(List<Fiesta> fiesta) {
        scroller.setLoading(false);
        fiestaAdapter.addAll(fiesta);
        binding.detailText.setText(fiestaAdapter.getItemCount() + " Fiesta");
    }

    public void setType(int type) {
        this.type = type;
    }
}
