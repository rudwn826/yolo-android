package kr.co.triggers.yolo.layer.explore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import javax.inject.Inject;

import kr.co.triggers.yolo.R;
import kr.co.triggers.yolo.adapter.ArtistThumbnailAdapter;
import kr.co.triggers.yolo.adapter.FiestaAdapter;
import kr.co.triggers.yolo.adapter.OnItemClickListener;
import kr.co.triggers.yolo.adapter.SearchHistoryAdapter;
import kr.co.triggers.yolo.adapter.TrackAdapter;
import kr.co.triggers.yolo.component.activity.YoloView;
import kr.co.triggers.yolo.databinding.LayoutViewExploreBinding;
import kr.co.triggers.yolo.decoration.DividerItemDecoration;
import kr.co.triggers.yolo.domain.Artist;
import kr.co.triggers.yolo.domain.Fiesta;
import kr.co.triggers.yolo.domain.Track;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;
import kr.co.triggers.yolo.layer.artist.ArtistView;
import kr.co.triggers.yolo.layer.explore.fragment.ExploreDetailView;
import kr.co.triggers.yolo.layer.youtube.YoutubeView;
import kr.co.triggers.yolo.network.entity.SearchEntity;

public class ExploreView extends YoloView {

    @Inject
    ExplorePresenter presenter;

    @Inject
    SearchHistoryAdapter searchHistoryAdapter;

    @Inject
    ArtistThumbnailAdapter thumbnailAdapter;

    @Inject
    TrackAdapter trackAdapter;

    @Inject
    SearchHistoryAdapter historyAdapter;

    @Inject
    FiestaAdapter fiestaAdapter;

    @Inject
    ExploreDetailView detailView;


    private boolean isSearched = false;
    private LayoutViewExploreBinding binding;
    private InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ActivityComponent component = DaggerActivityComponent.builder().appComponent(getAppComponent()).activityModule(new ActivityModule(this)).build();
        component.inject(this);

        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        binding = DataBindingUtil.setContentView(this, R.layout.layout_view_explore);

        binding.setPresenter(presenter);

        binding.searchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean b) {

                if (view.hasFocus()) {

                    binding.searchHistoryLayout.setVisibility(View.VISIBLE);
                    binding.searchResultLayout.setVisibility(View.GONE);
                }
            }
        });

        binding.searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search(binding.searchEditText.getText().toString());
            }
        });

        binding.searchBackButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(SearchHistoryAdapter.SEARCH_HISTORY, Context.MODE_PRIVATE);

        binding.searchHistoryList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.searchHistoryList.setAdapter(searchHistoryAdapter);
        binding.searchHistoryList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), R.id.historyItemName));

        searchHistoryAdapter.setOnItemClickListener(new OnItemClickListener<String>() {

            @Override
            public void onItemClick(String query) {

                search(query);
            }
        });

        searchHistoryAdapter.setSharedPreference(preferences);

        binding.recyclerViewArtists.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.recyclerViewArtists.setAdapter(thumbnailAdapter);

        binding.recyclerViewTracks.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.recyclerViewTracks.setAdapter(trackAdapter);

        binding.buttonMoreTracks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToDetailView(ExploreDetailView.TRACK_DETAIL);
            }
        });

        binding.recyclerViewFiesta.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.recyclerViewFiesta.setAdapter(fiestaAdapter);

        thumbnailAdapter.setOnItemClickListener(new OnItemClickListener<Artist>() {

            @Override
            public void onItemClick(Artist artist) {

                Intent intent = new Intent(getApplicationContext(), ArtistView.class);
                intent.putExtra(Artist.KEY_ARTIST_ID, artist.getId());

                startActivity(intent);
            }
        });

        trackAdapter.setOnItemClickListener(new OnItemClickListener<Track>() {

            @Override
            public void onItemClick(Track track) {

                Intent intent = new Intent(getApplicationContext(), YoutubeView.class);
                intent.putExtra(Track.KEY_TRACK_LINK, track.getLink());

                startActivity(intent);
            }
        });

        fiestaAdapter.setOnItemClickListener(new OnItemClickListener<Fiesta>() {

            @Override
            public void onItemClick(Fiesta fiesta) {

                Toast.makeText(getApplicationContext(), "" + fiesta.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.searchEditText.requestFocus();
    }

    public void search(String query) {

        isSearched = true;

        binding.searchEditText.setText(query);
        binding.searchTab.requestFocus();

        hideKeyboard(binding.searchEditText);

        presenter.search(query);

        historyAdapter.updateSearchHistory(query);
    }

    public void refreshSearchResult(SearchEntity entity) {

        if (binding.searchHistoryLayout.getVisibility() == View.VISIBLE) {

            binding.searchHistoryLayout.setVisibility(View.GONE);
            binding.searchResultLayout.setVisibility(View.VISIBLE);
        }

        thumbnailAdapter.setArtists(entity.getArtists());
        trackAdapter.setTracks(entity.getTracks());
        fiestaAdapter.setFiesta(entity.getFiesta());
    }

    public void moveToDetailView(int type) {
        detailView.setType(type);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.exploreContainer, detailView, ExploreDetailView.class.getName());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void hideKeyboard(View view) {

        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onBackPressed() {

        if (binding.searchHistoryLayout.getVisibility() == View.VISIBLE) {

            if (!isSearched) {

                super.onBackPressed();
                return;
            }

            binding.searchHistoryLayout.setVisibility(View.GONE);
            binding.searchResultLayout.setVisibility(View.VISIBLE);

            binding.searchTab.requestFocus();

            return;
        }

        if (binding.searchResultLayout.getVisibility() == View.VISIBLE) {

            Fragment fragment = getSupportFragmentManager().findFragmentByTag(ExploreDetailView.class.getName());

            if (fragment != null) {

                super.onBackPressed();
                return;
            }

            isSearched = false;

            binding.searchHistoryLayout.setVisibility(View.VISIBLE);
            binding.searchResultLayout.setVisibility(View.GONE);

            return;
        }

        super.onBackPressed();
    }
}
