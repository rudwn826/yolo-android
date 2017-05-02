package kr.co.triggers.yolo.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kr.co.triggers.yolo.component.activity.YoloFragment;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;

public class ProfileAdapter extends PagerAdapter {

    private List<RecyclerView> pages = new ArrayList<>();

    public ProfileAdapter(YoloFragment fragment) {

        ActivityComponent component = DaggerActivityComponent.builder().appComponent(fragment.getAppComponent()).activityModule(new ActivityModule(fragment)).build();
        component.inject(this);
    }

    @Override
    public int getCount() { return pages.size(); }

    @Override
    public boolean isViewFromObject(View view, Object object) { return view == object; }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        RecyclerView recyclerView = pages.get(position);

        if (recyclerView.getParent() == null) {

            container.addView(recyclerView);
        }

        return recyclerView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        RecyclerView recyclerView = pages.get(position);
        container.removeView(recyclerView);
    }

    public void addRecyclerView(RecyclerView view) {

        pages.add(view);
        notifyDataSetChanged();
    }

    public RecyclerView getRecyclerView(int position) { return pages.get(position); }
}
