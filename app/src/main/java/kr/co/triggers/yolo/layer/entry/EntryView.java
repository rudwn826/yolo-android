package kr.co.triggers.yolo.layer.entry;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import kr.co.triggers.yolo.R;
import kr.co.triggers.yolo.adapter.OnItemClickListener;
import kr.co.triggers.yolo.adapter.PerformAdapter;
import kr.co.triggers.yolo.component.activity.YoloFragment;
import kr.co.triggers.yolo.component.activity.YoloView;
import kr.co.triggers.yolo.databinding.LayoutViewEntryBinding;
import kr.co.triggers.yolo.decoration.DividerItemDecoration;
import kr.co.triggers.yolo.domain.Artist;
import kr.co.triggers.yolo.domain.Fiesta;
import kr.co.triggers.yolo.domain.Perform;
import kr.co.triggers.yolo.domain.Photo;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;
import kr.co.triggers.yolo.layer.artist.ArtistView;
import kr.co.triggers.yolo.layer.entry.fragment.artist.ArtistCollectionView;
import kr.co.triggers.yolo.layer.entry.fragment.fiesta.FiestaCollectionView;
import kr.co.triggers.yolo.layer.entry.fragment.fiesta.OnFiestaChangedListener;
import kr.co.triggers.yolo.layer.entry.fragment.menu.MenuView;
import kr.co.triggers.yolo.layer.entry.fragment.notification.NotificationView;
import kr.co.triggers.yolo.layer.entry.fragment.profile.ProfileView;
import kr.co.triggers.yolo.layer.explore.ExploreView;
import kr.co.triggers.yolo.util.Constraints;
import kr.co.triggers.yolo.util.DataTag;

public class EntryView extends YoloView implements OnFiestaChangedListener, OnItemClickListener<Perform> {

    class RecyclerViewScroller implements NestedScrollView.OnScrollChangeListener {

        private boolean loading = false;

        boolean isLoading() { return loading; }
        synchronized void setLoading(boolean loading) { this.loading = loading; }

        @Override
        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            if (isLoading())
                return;

            if (scrollY + v.getMeasuredHeight() >= v.getChildAt(0).getMeasuredHeight()) {

                int last = performAdapter.getItemCount() - 1;

                Fiesta fiesta = binding.getFiesta();
                long id = performAdapter.getItemId(last);

                setLoading(true);

                presenter.fetchPerformers(fiesta.getId(), id, Constraints.FETCH_COUNT);
            }
        }
    }

    ActivityComponent component;
    LayoutViewEntryBinding binding;

    @Inject
    FiestaCollectionView fiestaCollectionView;

    @Inject
    ArtistCollectionView artistCollectionView;

    @Inject
    NotificationView notificationView;

    @Inject
    ProfileView profileView;

    @Inject
    MenuView menuView;

    @Inject
    PerformAdapter performAdapter;


    @Inject
    EntryPresenter presenter;

    @Inject
    Spring bottomSheetPeekAnimator;

    @Inject
    Spring bottomSheetAlphaAnimator;

    @Inject
    Picasso picasso;

    int bottomSheetHeight;
    BottomSheetBehavior behavior;

    String mode;
    RecyclerViewScroller scroller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        component = DaggerActivityComponent.builder().appComponent(getAppComponent()).activityModule(new ActivityModule(this)).build();
        component.inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.layout_view_entry);

        binding.setView(this);
        binding.setPresenter(presenter);

        scroller = new RecyclerViewScroller();

        binding.bottomSheet.setOnScrollChangeListener(scroller);

        binding.bottomSheetContent.recyclerViewLineup.setItemViewCacheSize(30);
        binding.bottomSheetContent.recyclerViewLineup.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.bottomSheetContent.recyclerViewLineup.addItemDecoration(new DividerItemDecoration(this, R.id.textViewArtistName));
        binding.bottomSheetContent.recyclerViewLineup.setAdapter(performAdapter);

        performAdapter.setOnItemClickListener(this);

        binding.bottomSheetContent.slider.setPresetTransformer(SliderLayout.Transformer.ZoomOut);
        binding.bottomSheetContent.slider.setCustomIndicator(binding.bottomSheetContent.indicator);
        binding.bottomSheetContent.slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        binding.bottomSheetContent.slider.stopAutoCycle();

        bottomSheetHeight = getResources().getDimensionPixelSize(R.dimen.bottom_sheet_peek_height_with_margin);

        behavior = BottomSheetBehavior.from(binding.bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                float alpha;

                if (newState == BottomSheetBehavior.STATE_HIDDEN || newState == BottomSheetBehavior.STATE_COLLAPSED) {

                    binding.bottomSheet.smoothScrollTo(0, 0);

                    alpha = 1.0f;
                }
                else {

                    alpha = 0.0f;
                }

                bottomSheetAlphaAnimator.setEndValue(alpha);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        bottomSheetPeekAnimator.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring spring) {

                final double value = spring.getCurrentValue();

                binding.bottomSheet.setAlpha((float) value);
                behavior.setPeekHeight((int) (spring.getCurrentValue() * bottomSheetHeight));
            }
        });

        bottomSheetAlphaAnimator.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring spring) {

                final double value = spring.getCurrentValue();
                binding.layoutCommands.setAlpha((float) value);

                if (value == 1.0f) {

                    binding.layoutCommands.setClickable(false);
                }
                else if (value == 0.0f) {

                    binding.layoutCommands.setClickable(true);
                }
            }
        });

        bottomSheetAlphaAnimator.setEndValue(1.0f);

        fiestaCollectionView.setOnFiestaChangedListener(this);

        pushFragment(profileView);
        pushFragment(notificationView);
        pushFragment(artistCollectionView);
        pushFragment(fiestaCollectionView);

        this.mode = MenuView.MODE_FIESTA;
    }

    public void pushFragment(YoloFragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.fragmentContainer, fragment);
        transaction.commitNow();
    }

    @Override
    public void onFiestaChange(float position) {

        bottomSheetPeekAnimator.setEndValue(position);
    }

    @Override
    public void onFiestaChanged(Fiesta fiesta) {

        presenter.fetchById(fiesta.getId());
    }

    @Override
    public void onItemClick(Perform perform) {

        Artist artist = perform.getArtist();

        Intent intent = new Intent(this, ArtistView.class);

        intent.putExtra(Artist.KEY_ARTIST_ID, artist.getId());
        intent.putExtra(Artist.KEY_ARTIST_NAME, artist.getName());

        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        // TODO : 모드별 분기 필요

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(MenuView.class.getName());

        if (fragment != null) {

            super.onBackPressed();
            return;
        }

        if (MenuView.MODE_FIESTA.equals(mode)) {

            if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {

                binding.bottomSheet.smoothScrollTo(0, 0);

                if (binding.bottomSheet.getScrollY() == 0) {

                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    return;
                }
            }
            else {

                super.onBackPressed();
            }
        }
        else if (MenuView.MODE_ARTIST.equals(mode)) {

            artistCollectionView.slideup();
        }
        else if (MenuView.MODE_NOTIFICATION.equals(mode)) {

        }
        else if (MenuView.MODE_PROFILE.equals(mode)) {

        }
        else {

            super.onBackPressed();
        }
    }

    public void setFiestaDetail(Fiesta fiesta) {

        binding.setFiesta(fiesta);

        scroller.setLoading(false);
        performAdapter.setPerforms(fiesta.getPerforms());

        binding.bottomSheetContent.slider.removeAllSliders();
        if(fiesta.getMeta()!=null) {
            DataTag data = new DataTag(fiesta.getMeta());
            binding.bottomSheetContent.tagViewInformation.addTags(data.getTags());
        }

        List<Photo> photos = fiesta.getPhotos();

        if (photos == null || photos.size() < 1)
            return;

        for (Photo photo : fiesta.getPhotos()) {

            DefaultSliderView view = new DefaultSliderView(this);

            view.image(photo.getLink());
            view.setPicasso(picasso);
            view.setScaleType(BaseSliderView.ScaleType.Fit);

            binding.bottomSheetContent.slider.addSlider(view);
        }
    }

    public void slideup() {

        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    public void expandMenu() {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
        transaction.replace(R.id.layoutContainer, menuView, MenuView.class.getName());
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void activate(String mode) {

        if (TextUtils.isEmpty(mode) || mode.equals(this.mode))
            return;

        this.mode = mode;

        int bottomSheetFactor = mode.equals(MenuView.MODE_FIESTA) ? 1 : 0;
        int visibility = bottomSheetFactor == 1 ? View.VISIBLE : View.GONE;

        binding.bottomSheet.setVisibility(visibility);
        bottomSheetPeekAnimator.setEndValue(bottomSheetFactor);

        Fragment fragment = null;

        if (MenuView.MODE_FIESTA.equals(mode)) fragment = fiestaCollectionView;
        if (MenuView.MODE_ARTIST.equals(mode)) fragment = artistCollectionView;
        if (MenuView.MODE_NOTIFICATION.equals(mode)) fragment = notificationView;
        if (MenuView.MODE_PROFILE.equals(mode)) fragment = profileView;

        if (fragment == null)
            throw new IllegalStateException();

        View view = fragment.getView();

        if (view != null)
            view.bringToFront();
    }

    public void purchase(Fiesta fiesta) {

        presenter.requestPurchaseLink(fiesta.getId());
    }

    public void launchPurchaseSite(String link) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(link));

        startActivity(intent);
    }

    public void attachPerformers(List<Perform> performs) {

        scroller.setLoading(false);
        performAdapter.addAll(performs);
    }

    public void moveToExplore(){
        Intent intent = new Intent(getApplicationContext(), ExploreView.class);
        startActivity(intent);
    }
}
