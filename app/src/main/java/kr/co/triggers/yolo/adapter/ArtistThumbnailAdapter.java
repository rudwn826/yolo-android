package kr.co.triggers.yolo.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import kr.co.triggers.yolo.R;
import kr.co.triggers.yolo.component.activity.YoloView;
import kr.co.triggers.yolo.databinding.LayoutItemArtistThumbnailBinding;
import kr.co.triggers.yolo.domain.Artist;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;

class ArtistThumbnailViewHolder extends RecyclerView.ViewHolder {

    LayoutItemArtistThumbnailBinding binding;

    public ArtistThumbnailViewHolder(final LayoutItemArtistThumbnailBinding binding, final OnItemClickListener<Artist> listener) {

        super(binding.getRoot());

        this.binding = binding;

        binding.getRoot().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onItemClick(binding.getArtist());
            }
        });
    }

    public void bind(Artist artist) {
        binding.setArtist(artist);
    }
}

public class ArtistThumbnailAdapter extends RecyclerView.Adapter<ArtistThumbnailViewHolder> implements OnItemClickListener<Artist> {

    private List<Artist> artists;

    public List<Artist> getArtists() { return artists; }
    public void setArtists(List<Artist> artists) {

        this.artists = artists;
        this.notifyDataSetChanged();
    }

    private OnItemClickListener<Artist> listener;
    public void setOnItemClickListener(OnItemClickListener<Artist> listener) { this.listener = listener; }

    @Inject
    Picasso picasso;

    public ArtistThumbnailAdapter(YoloView view) {

        ActivityComponent component = DaggerActivityComponent.builder().appComponent(view.getAppComponent()).activityModule(new ActivityModule(view)).build();
        component.inject(this);
    }

    @Override
    public ArtistThumbnailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutItemArtistThumbnailBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_item_artist_thumbnail, parent, false);
        return new ArtistThumbnailViewHolder(binding, this);
    }

    @Override
    public void onBindViewHolder(ArtistThumbnailViewHolder holder, int position) {

        Artist artist = artists.get(position);
        holder.bind(artist);
    }

    @Override
    public int getItemCount() {
        return artists != null ? artists.size() : 0;
    }

    @Override
    public void onItemClick(Artist artist) {

        if (listener != null)
            listener.onItemClick(artist);
    }

    public void addAll(List<Artist> item) {
        for (Artist artist : item) {
            if (!artists.contains(artist))
                artists.add(artist);
        }
        notifyDataSetChanged();
    }
}