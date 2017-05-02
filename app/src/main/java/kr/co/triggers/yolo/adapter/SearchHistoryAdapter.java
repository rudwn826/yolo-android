package kr.co.triggers.yolo.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import kr.co.triggers.yolo.R;
import kr.co.triggers.yolo.component.activity.YoloView;
import kr.co.triggers.yolo.databinding.LayoutItemSearchHistoryBinding;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by kyungjoo on 2016. 9. 19..
 */


class HistoryViewHolder extends RecyclerView.ViewHolder{
    private final OnItemClickListener<LayoutItemSearchHistoryBinding> listener;
    LayoutItemSearchHistoryBinding binding;

    public HistoryViewHolder(final LayoutItemSearchHistoryBinding binding, final OnItemClickListener<LayoutItemSearchHistoryBinding> listener) {

        super(binding.getRoot());

        this.binding = binding;
        this.listener = listener;

        binding.getRoot().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onItemClick(binding);
            }
        });

    }

    public void bind(String item) {
        binding.setHistoryItem(item);
    }
}

public class SearchHistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> implements OnItemClickListener<LayoutItemSearchHistoryBinding> {
    public static final String SEARCH_HISTORY = "RECENT_SEARCH_HISTORY";
    private static final int HISTORY_MAX_NUM = 4;
    private SharedPreferences sharedPreferences;

    @Inject
    Picasso picasso;

    private OnItemClickListener<String> listener;

    public OnItemClickListener<String> getListener() {
        return listener;
    }

    public void setOnItemClickListener(OnItemClickListener<String> listener) {
        this.listener = listener;
    }

    public SearchHistoryAdapter(YoloView view){
        ActivityComponent component = DaggerActivityComponent.builder().appComponent(view.getAppComponent()).activityModule(new ActivityModule(view)).build();
        component.inject(this);
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutItemSearchHistoryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_item_search_history, parent, false);
        return new HistoryViewHolder(binding, this);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        String item = sharedPreferences.getString("" + position, null);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return sharedPreferences != null ? sharedPreferences.getAll().size() : 0;
    }

    public void setSharedPreference(SharedPreferences sharedPreference){
        this.sharedPreferences = sharedPreference;
        this.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(LayoutItemSearchHistoryBinding binding) {
        String item = binding.getHistoryItem();

        if (listener != null)
            listener.onItemClick(item);
    }

    public void updateSearchHistory(String keyword) {
        if (!TextUtils.isEmpty(keyword)) {
            SharedPreferences preferences = getApplicationContext().getSharedPreferences(SEARCH_HISTORY, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            for (int i = Math.min(preferences.getAll().size(), HISTORY_MAX_NUM - 1); i >= 0; i--) {
                editor.putString("" + (i + 1), preferences.getString("" + i, null));
            }
            editor.putString("" + 0, keyword);
            editor.commit();

            this.setSharedPreference(preferences);
        }
    }
}