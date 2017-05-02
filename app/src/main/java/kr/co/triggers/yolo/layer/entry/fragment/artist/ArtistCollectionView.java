package kr.co.triggers.yolo.layer.entry.fragment.artist;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import kr.co.triggers.yolo.R;
import kr.co.triggers.yolo.adapter.ArtistAdapter;
import kr.co.triggers.yolo.adapter.OnItemClickListener;
import kr.co.triggers.yolo.component.activity.YoloFragment;
import kr.co.triggers.yolo.component.activity.YoloView;
import kr.co.triggers.yolo.databinding.LayoutFragmentArtistCollectionBinding;
import kr.co.triggers.yolo.domain.Artist;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;
import kr.co.triggers.yolo.layer.artist.ArtistView;
import kr.co.triggers.yolo.util.Constraints;

public class ArtistCollectionView extends YoloFragment implements OnItemClickListener<Artist> {


    class RecyclerScroller extends RecyclerView.OnScrollListener {

        private boolean loading = false;

        boolean isLoading() { return loading; }
        synchronized void setLoading(boolean loading) { this.loading = loading; }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            if (dy <= 0)
                return;

            if (isLoading())
                return;

            GridLayoutManager layoutManager = (GridLayoutManager) binding.recyclerViewArtists.getLayoutManager();

            int visible = layoutManager.getChildCount();
            int total = layoutManager.getItemCount();
            int last = layoutManager.findFirstVisibleItemPosition();

            if (total <= visible + last) {

                setLoading(true);

                long id = artistAdapter.getItemId(artistAdapter.getItemCount() - 1);
                presenter.fetch(id, Constraints.FETCH_COUNT);
            }
        }
    }
    LayoutFragmentArtistCollectionBinding binding;

    @Inject
    ArtistCollectionPresenter presenter;

    @Inject
    ArtistAdapter artistAdapter;


    RecyclerScroller scroller;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_artist_collection, container, false);
        binding.recyclerViewArtists.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        scroller = new RecyclerScroller();
        binding.recyclerViewArtists.addOnScrollListener(scroller);

        YoloView view = (YoloView) getActivity();

        ActivityComponent component = DaggerActivityComponent.builder().appComponent(view.getAppComponent()).activityModule(new ActivityModule(this)).build();
        component.inject(this);

        binding.recyclerViewArtists.setAdapter(artistAdapter);
        artistAdapter.setOnItemClickListener(this);

        presenter.fetch();

        return binding.getRoot();
    }

    public void setArtists(List<Artist> artists) {

        scroller.setLoading(false);
        artistAdapter.setArtists(artists);
    }

    public void attachArtists(List<Artist> artists) {

        scroller.setLoading(false);
        artistAdapter.addAll(artists);
    }

    public void slideup() {

        GridLayoutManager layoutManager = (GridLayoutManager) binding.recyclerViewArtists.getLayoutManager();

        if (layoutManager.findFirstCompletelyVisibleItemPosition() != 0) {

            layoutManager.smoothScrollToPosition(binding.recyclerViewArtists, null, 0);
        }
    }

    @Override
    public void onItemClick(Artist artist) {

        Intent intent = new Intent(getActivity(), ArtistView.class);

        intent.putExtra(Artist.KEY_ARTIST_ID, artist.getId());
        intent.putExtra(Artist.KEY_ARTIST_NAME, artist.getName());

        startActivity(intent);
    }
}
