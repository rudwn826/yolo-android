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
import kr.co.triggers.yolo.databinding.LayoutItemTrackThumbnailBinding;
import kr.co.triggers.yolo.domain.Track;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;
import kr.co.triggers.yolo.util.Constraints;
import kr.co.triggers.yolo.util.DataTag;

class TrackViewHolder extends RecyclerView.ViewHolder {

    LayoutItemTrackThumbnailBinding binding;

    public TrackViewHolder(final LayoutItemTrackThumbnailBinding binding, final OnItemClickListener<Track> listener) {

        super(binding.getRoot());

        this.binding = binding;

        binding.getRoot().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (listener != null)
                    listener.onItemClick(binding.getTrack());
            }
        });
    }

    public void bind(Track track, Picasso picasso) {

        binding.setTrack(track);

        String thumbnail = String.format(Constraints.TRACK_THUMBNAIL_FORMAT, track.getLink());
        picasso.load(thumbnail).into(binding.imageViewThumbnail);
        if(track.getGenres()!=null) {
            DataTag dataTag = new DataTag(track.getGenres());
            binding.tagViewInformation.removeAllTags();
            binding.tagViewInformation.addTags(dataTag.getTags());
        }
    }
}

public class TrackAdapter extends RecyclerView.Adapter<TrackViewHolder> implements OnItemClickListener<Track> {

    LayoutItemTrackThumbnailBinding binding;

    private OnItemClickListener<Track> listener;
    public void setOnItemClickListener(OnItemClickListener<Track> listener) { this.listener = listener; }

    private List<Track> tracks;

    public List<Track> getTracks() { return tracks; }

    public void setTracks(List<Track> tracks) {

        this.tracks = tracks;
        this.notifyDataSetChanged();
    }

    public void addAll(List<Track> tracks) {

        this.tracks.addAll(tracks);
        this.notifyDataSetChanged();
    }

    @Inject
    Picasso picasso;

    public TrackAdapter(YoloView view) {

        ActivityComponent component = DaggerActivityComponent.builder().appComponent(view.getAppComponent()).activityModule(new ActivityModule(view)).build();
        component.inject(this);
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_item_track_thumbnail, parent, false);
        return new TrackViewHolder(binding, this);
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {

        Track track = tracks.get(position);
        holder.bind(track, picasso);
    }

    @Override
    public int getItemCount() {
        return tracks != null ? tracks.size() : 0;
    }

    @Override
    public void onItemClick(Track track) {

        if (listener != null)
            listener.onItemClick(track);
    }
}
