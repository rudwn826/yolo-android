package kr.co.triggers.yolo.layer.entry.fragment.fiesta;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import kr.co.triggers.yolo.R;
import kr.co.triggers.yolo.component.activity.YoloFragment;
import kr.co.triggers.yolo.databinding.LayoutFragmentFiestaCollectionBinding;
import kr.co.triggers.yolo.domain.Fiesta;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;
import kr.co.triggers.yolo.layer.entry.EntryView;
import kr.co.triggers.yolo.util.Constraints;

public class FiestaCollectionView extends YoloFragment implements ViewPager.OnPageChangeListener {

    OnFiestaChangedListener listener;
    public void setOnFiestaChangedListener(OnFiestaChangedListener listener) { this.listener = listener; }

    LayoutFragmentFiestaCollectionBinding binding;

    @Inject
    FiestaCollectionPresenter presenter;

    @Inject
    FiestaCollectionAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_fiesta_collection, container, false);

        binding.viewPagerFiesta.setOffscreenPageLimit(FiestaCollectionAdapter.POOL_SIZE / 2);
        binding.viewPagerFiesta.addOnPageChangeListener(this);

        ActivityComponent component = DaggerActivityComponent.builder().appComponent(getAppComponent()).activityModule(new ActivityModule(this)).build();
        component.inject(this);

        binding.viewPagerFiesta.setAdapter(adapter);

        presenter.fetch();

        return binding.getRoot();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if (listener != null) {

            float value = 1 - positionOffset;

            value = value <= 1 ? value : 1;
            value = value >= 0 ? value : 0;

            listener.onFiestaChange(value);
        }
    }

    @Override
    public void onPageSelected(int position) {

        if (listener != null) {

            Fiesta fiesta = adapter.getItem(position);
            listener.onFiestaChanged(fiesta);

            if (position + 1 >= adapter.getCount()) {

                presenter.fetch(fiesta.getId(), Constraints.FETCH_COUNT);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) { }

    public void setFiesta(List<Fiesta> fiesta) {

        adapter.setFiesta(fiesta);

        EntryView view = (EntryView) getActivity();

        Fiesta first = fiesta.get(0);
        view.onFiestaChanged(first);
    }

    public void addFiesta(List<Fiesta> fiesta) {

        adapter.addFiesta(fiesta);
    }
}
