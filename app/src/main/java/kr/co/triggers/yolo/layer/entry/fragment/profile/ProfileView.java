package kr.co.triggers.yolo.layer.entry.fragment.profile;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import kr.co.triggers.yolo.adapter.ProfileAdapter;
import kr.co.triggers.yolo.adapter.TrackAdapter;
import kr.co.triggers.yolo.component.activity.YoloFragment;
import kr.co.triggers.yolo.databinding.LayoutFragmentProfileBinding;
import kr.co.triggers.yolo.domain.Artist;
import kr.co.triggers.yolo.domain.Fiesta;
import kr.co.triggers.yolo.domain.Track;
import kr.co.triggers.yolo.domain.User;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;
import kr.co.triggers.yolo.layer.artist.ArtistView;
import kr.co.triggers.yolo.layer.youtube.YoutubeView;
import kr.co.triggers.yolo.util.Constraints;
import kr.co.triggers.yolo.util.DataTag;


public class ProfileView extends YoloFragment {

    interface InfiniteScrollerListener {

        void onReachedToBottom();
    }

    class RecyclerViewScroller extends RecyclerView.OnScrollListener {

        private boolean loading = false;

        public boolean isLoading() { return loading; }
        public void setLoading(boolean loading) { this.loading = loading; }

        private InfiniteScrollerListener listener;
        public void setInfiniteScrollerListener(InfiniteScrollerListener listener) { this.listener = listener; }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            if (dy <= 0)
                return;

            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

            int visibleItemCount = recyclerView.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

            if (totalItemCount == firstVisibleItem + visibleItemCount) {

                loading = true;

                if (listener != null)
                    listener.onReachedToBottom();
            }
        }
    }

    private static final int PAGE_SIZE = 3;

    LayoutFragmentProfileBinding binding;

    @Inject
    ProfilePresenter presenter;

    @Inject
    ProfileAdapter profileAdapter;


    @Inject
    TrackAdapter trackAdapter;

    @Inject
    FiestaAdapter fiestaAdapter;

    @Inject
    ArtistThumbnailAdapter artistThumbnailAdapter;

    RecyclerViewScroller artistScroller = new RecyclerViewScroller();
    RecyclerViewScroller trackScroller = new RecyclerViewScroller();
    RecyclerViewScroller fiestaScroller = new RecyclerViewScroller();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ActivityComponent component = DaggerActivityComponent.builder().appComponent(getAppComponent()).activityModule(new ActivityModule(this)).build();
        component.inject(this);

        binding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_profile, container, false);
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int position = binding.tabLayout.getSelectedTabPosition();
                binding.viewPagerContents.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        binding.viewPagerContents.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                binding.tabLayout.getTabAt(position).select();
            }
        });

        if (container != null) {

            Context context = container.getContext();

            for (int i = 0; i < PAGE_SIZE; i++) {

                RecyclerView recyclerView = new RecyclerView(context);

                recyclerView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT));
                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                recyclerView.addOnScrollListener(new RecyclerViewScroller());

                profileAdapter.addRecyclerView(recyclerView);
            }

            artistThumbnailAdapter.setOnItemClickListener(new OnItemClickListener<Artist>() {

                @Override
                public void onItemClick(Artist artist) {

                    Intent intent = new Intent(getActivity(), ArtistView.class);

                    intent.putExtra(Artist.KEY_ARTIST_ID, artist.getId());
                    intent.putExtra(Artist.KEY_ARTIST_NAME, artist.getName());

                    startActivity(intent);
                }
            });

            trackAdapter.setOnItemClickListener(new OnItemClickListener<Track>() {

                @Override
                public void onItemClick(Track track) {

                    Intent intent = new Intent(getActivity(), YoutubeView.class);

                    intent.putExtra(Track.KEY_TRACK_ID, track.getId());
                    intent.putExtra(Track.KEY_TRACK_NAME, track.getTitle());
                    intent.putExtra(Track.KEY_TRACK_LINK, track.getLink());

                    startActivity(intent);
                }
            });

            fiestaAdapter.setOnItemClickListener(new OnItemClickListener<Fiesta>() {

                @Override
                public void onItemClick(Fiesta fiesta) {

                    // TODO : 상세 화면으로 이동 필요

                    Toast.makeText(getActivity(), fiesta.getName(), Toast.LENGTH_SHORT).show();
                }
            });

            artistScroller.setInfiniteScrollerListener(new InfiniteScrollerListener() {

                @Override
                public void onReachedToBottom() {

                    int last = artistThumbnailAdapter.getItemCount() - 1;
                    presenter.fetchArtists(artistThumbnailAdapter.getItemId(last), Constraints.FETCH_COUNT);
                }
            });

            trackScroller.setInfiniteScrollerListener(new InfiniteScrollerListener() {

                @Override
                public void onReachedToBottom() {

                    int last = artistThumbnailAdapter.getItemCount() - 1;
                    presenter.fetchTracks(trackAdapter.getItemId(last), Constraints.FETCH_COUNT);
                }
            });

            fiestaScroller.setInfiniteScrollerListener(new InfiniteScrollerListener() {

                @Override
                public void onReachedToBottom() {

                    int last = fiestaAdapter.getItemCount() - 1;
                    presenter.fetchFiesta(fiestaAdapter.getItemId(last), Constraints.FETCH_COUNT);
                }
            });

            profileAdapter.getRecyclerView(0).setAdapter(artistThumbnailAdapter);
            profileAdapter.getRecyclerView(0).addOnScrollListener(artistScroller);

            profileAdapter.getRecyclerView(1).setAdapter(trackAdapter);
            profileAdapter.getRecyclerView(1).addOnScrollListener(trackScroller);

            profileAdapter.getRecyclerView(2).setAdapter(fiestaAdapter);
            profileAdapter.getRecyclerView(2).addOnScrollListener(fiestaScroller);
        }

        binding.viewPagerContents.setOffscreenPageLimit(PAGE_SIZE);
        binding.viewPagerContents.setAdapter(profileAdapter);

        presenter.me();

        presenter.fetchArtists();
        presenter.fetchTracks();
        presenter.fetchFiesta();

        return binding.getRoot();
    }


    public void setUser(User user) {

        binding.setUser(user);
        if(user.getData()!=null){
            DataTag dataTag = new DataTag(user.getData());
            binding.tagViewInformation.addTags(dataTag.getTags());
        }
    }

    public void setArtists(List<Artist> artists) {

        artistScroller.setLoading(false);
        artistThumbnailAdapter.setArtists(artists);
    }

    public void addArtists(List<Artist> artists) {

        artistScroller.setLoading(false);
        artistThumbnailAdapter.addAll(artists);
    }


    public void setTracks(List<Track> tracks) {

        trackScroller.setLoading(false);
        trackAdapter.setTracks(tracks);
    }

    public void addTracks(List<Track> tracks) {

        trackScroller.setLoading(false);
        trackAdapter.addAll(tracks);
    }


    public void setFiesta(List<Fiesta> fiesta) {

        fiestaScroller.setLoading(false);
        fiestaAdapter.setFiesta(fiesta);
    }

    public void addFiesta(List<Fiesta> fiesta) {

        fiestaScroller.setLoading(false);
        fiestaAdapter.addAll(fiesta);
    }
}
