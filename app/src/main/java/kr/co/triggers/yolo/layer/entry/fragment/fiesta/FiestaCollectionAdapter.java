package kr.co.triggers.yolo.layer.entry.fragment.fiesta;

import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import kr.co.triggers.yolo.R;
import kr.co.triggers.yolo.component.activity.YoloFragment;
import kr.co.triggers.yolo.databinding.LayoutItemFiestaEntryBinding;
import kr.co.triggers.yolo.domain.Fiesta;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;
import kr.co.triggers.yolo.layer.entry.EntryView;

public class FiestaCollectionAdapter extends PagerAdapter {

    public static final int POOL_SIZE = 20;

    private List<LayoutItemFiestaEntryBinding> pool = new ArrayList<>();
    private List<Fiesta> fiesta;

    public List<Fiesta> getFiesta() { return fiesta; }
    public Fiesta getItem(int position) { return fiesta.get(position); }

    public Comparator<Fiesta> comparator = new Comparator<Fiesta>() {

        @Override
        public int compare(Fiesta f1, Fiesta f2) {

            return f1.getId() > f2.getId() ? 1 : -1;
        }
    };

    public void setFiesta(List<Fiesta> fiesta) {

        this.fiesta = fiesta;
        Collections.sort(fiesta, comparator);

        this.notifyDataSetChanged();
    }

    public void addFiesta(List<Fiesta> fiesta) {

        this.fiesta.addAll(fiesta);
        Collections.sort(fiesta, comparator);

        this.notifyDataSetChanged();
    }

    @Inject
    Picasso picasso;

    YoloFragment fragment;

    public FiestaCollectionAdapter(YoloFragment fragment) {

        EntryView view = (EntryView) fragment.getActivity();

        LayoutInflater inflater = LayoutInflater.from(view);

        for (int i = 0; i < POOL_SIZE; i++) {

            LayoutItemFiestaEntryBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_item_fiesta_entry, null, false);
            binding.setView(view);

            pool.add(binding);
        }

        ActivityComponent component = DaggerActivityComponent.builder().appComponent(fragment.getAppComponent()).activityModule(new ActivityModule(fragment)).build();
        component.inject(this);

        this.fragment = fragment;
    }

    @Override
    public int getCount() { return fiesta != null ? fiesta.size() : 0; }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutItemFiestaEntryBinding in = pool.get((position + POOL_SIZE / 2) % POOL_SIZE);

        if (in.getRoot().getParent() == null) {

            container.addView(in.getRoot());
        }

        Fiesta item = fiesta.get(position);
        in.setFiesta(item);

        int height = 1200;

        picasso.load("http://www.thatdrop.com/wp-content/uploads/2015/12/ultra-music-festival-2016-lineup-466x700.jpg").resize(768, 1280).into(in.imageViewFiestaFacade);
        // picasso.load("http://placehold.it/768x" + (height + position)).resize(768, 1280).into(in.imageViewFiestaFacade);

        // TODO : Rollback to Original link
        // picasso.load(String.format(Constraints.FACADE_THUMBNAIL_FORMAT, item.getId())).into(binding.imageViewFiestaFacade);

        return in.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public boolean isViewFromObject(View view, Object object) { return view == object; }
}
