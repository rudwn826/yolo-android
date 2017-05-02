package kr.co.triggers.yolo.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import kr.co.triggers.yolo.R;
import kr.co.triggers.yolo.component.activity.YoloView;
import kr.co.triggers.yolo.databinding.LayoutItemArtistFeedBinding;
import kr.co.triggers.yolo.domain.Feed;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;

class FeedViewHolder extends RecyclerView.ViewHolder {

    LayoutItemArtistFeedBinding binding;

    public FeedViewHolder(LayoutItemArtistFeedBinding binding) {

        super(binding.getRoot());

        this.binding = binding;
    }

    public void bind(Feed feed, Picasso picasso) {

        binding.setFeed(feed);
        picasso.load(feed.getPicture()).into(binding.squareImageViewFeed);
    }
}

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {

    private List<Feed> feeds;

    @Inject
    Picasso picasso;

    public FeedAdapter(YoloView view) {

        ActivityComponent component = DaggerActivityComponent.builder().appComponent(view.getAppComponent()).activityModule(new ActivityModule(view)).build();
        component.inject(this);
    }

    @Override
    public int getItemCount() { return feeds != null ? feeds.size() : 0; }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutItemArtistFeedBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_item_artist_feed, parent, false);
        return new FeedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {

        Feed feed = feeds.get(position);
        holder.bind(feed, picasso);
    }

    public void setFeeds(List<Feed> feeds) {

        this.feeds = feeds;
        this.notifyDataSetChanged();
    }
}
