package kr.co.triggers.yolo.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import kr.co.triggers.yolo.R;
import kr.co.triggers.yolo.component.activity.YoloView;
import kr.co.triggers.yolo.databinding.LayoutItemPerformThumbnailBinding;
import kr.co.triggers.yolo.domain.Perform;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;

class PerformViewHolder extends RecyclerView.ViewHolder {

    private final LayoutItemPerformThumbnailBinding binding;

    PerformViewHolder(final LayoutItemPerformThumbnailBinding binding, final OnItemClickListener<Perform> listener) {

        super(binding.getRoot());

        this.binding = binding;

        View view = binding.getRoot();
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (listener != null)
                    listener.onItemClick(binding.getPerform());
            }
        });
    }

    public void bind(Perform perform, Picasso picasso) {

        binding.setPerform(perform);

        picasso.load("https://i1.sndcdn.com/avatars-000188440409-1jea7v-t500x500.jpg").resize(100, 100).into(binding.imageViewArtistProfile);
        // picasso.load(perform.getPicture()).into(binding.squareImageViewFeed);
    }
}

public class PerformAdapter extends RecyclerView.Adapter<PerformViewHolder> implements OnItemClickListener<Perform> {

    private OnItemClickListener<Perform> listener;

    public OnItemClickListener<Perform> getOnItemClickListener() { return listener; }
    public void setOnItemClickListener(OnItemClickListener<Perform> listener) { this.listener = listener; }

    private List<Perform> performs;
    private Comparator<Perform> comparator = new Comparator<Perform>() {

        @Override
        public int compare(Perform p1, Perform p2) {

            if (p1.isHeadliner() != p2.isHeadliner())
                return p1.isHeadliner() ? 1 : -1;

            String name1 = p1.getArtist().getName();
            String name2 = p2.getArtist().getName();

            return name1.compareTo(name2);
        }
    };

    public void addAll(List<Perform> items) {

        for (Perform perform : items) {

            if (!performs.contains(perform))
                performs.add(perform);
        }

        Collections.sort(items, comparator);
        notifyDataSetChanged();
    }

    @Inject
    Picasso picasso;

    public void setPerforms(List<Perform> performs) {

        this.performs = performs;
        this.notifyDataSetChanged();
    }

    public PerformAdapter(YoloView view) {

        ActivityComponent component = DaggerActivityComponent.builder().appComponent(view.getAppComponent()).activityModule(new ActivityModule(view)).build();
        component.inject(this);
    }

    @Override
    public int getItemCount() { return performs != null ? performs.size() : 0; }


    @Override
    public PerformViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutItemPerformThumbnailBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_item_perform_thumbnail, parent, false);
        return new PerformViewHolder(binding, this);
    }

    @Override
    public void onBindViewHolder(PerformViewHolder holder, int position) {

        Perform perform = performs.get(position);
        holder.bind(perform, picasso);
    }

    @Override
    public void onItemClick(Perform perform) {

        if (listener != null)
            listener.onItemClick(perform);
    }
}