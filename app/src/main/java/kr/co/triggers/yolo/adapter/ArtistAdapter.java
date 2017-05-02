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
import kr.co.triggers.yolo.databinding.LayoutItemArtistSquareBinding;
import kr.co.triggers.yolo.domain.Artist;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;
import kr.co.triggers.yolo.util.DataTag;

class ArtistViewHolder extends RecyclerView.ViewHolder {

    private final LayoutItemArtistSquareBinding binding;

    ArtistViewHolder(final LayoutItemArtistSquareBinding binding, final OnItemClickListener<Artist> listener) {

        super(binding.getRoot());

        this.binding = binding;

        View view = binding.getRoot();
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (listener != null)
                    listener.onItemClick(binding.getArtist());
            }
        });
    }

    public void bind(Artist artist, Picasso picasso) {

        binding.setArtist(artist);

        if(artist.getData()!=null) {
            DataTag dataTag = new DataTag(artist.getData());
            binding.tagViewInformation.addTags(dataTag.getTags());
        }
        picasso.load("https://i1.sndcdn.com/avatars-000188440409-1jea7v-t500x500.jpg").resize(100, 100).into(binding.imageViewArtistProfile);
        // picasso.load(artist.getPicture()).into(binding.squareImageViewFeed);
    }
}

public class ArtistAdapter extends RecyclerView.Adapter<ArtistViewHolder> implements OnItemClickListener<Artist> {

    private OnItemClickListener<Artist> listener;

    public OnItemClickListener<Artist> getOnItemClickListener() { return listener; }
    public void setOnItemClickListener(OnItemClickListener<Artist> listener) { this.listener = listener; }

    private List<Artist> artists;
    private Comparator<Artist> comparator = new Comparator<Artist>() {

        @Override
        public int compare(Artist a1, Artist a2) {

            // TODO : 구현

            if (a1.getRank() == a2.getRank())
                return a1.getName().compareTo(a2.getName());

            return a1.getRank() > a2.getRank() ? 1 : -1;
        }
    };

    public void addAll(List<Artist> items) {

        for (Artist artist : items) {

            if (!artists.contains(artist))
                artists.add(artist);
        }

        Collections.sort(items, comparator);
        notifyDataSetChanged();
    }

    @Inject
    Picasso picasso;

    public void setArtists(List<Artist> artists) {

        this.artists = artists;
        this.notifyDataSetChanged();
    }

    public ArtistAdapter(YoloView view) {
        ActivityComponent component = DaggerActivityComponent.builder().appComponent(view.getAppComponent()).activityModule(new ActivityModule(view)).build();
        component.inject(this);
    }


    public int getItemCount() { return artists != null ? artists.size() : 0; }


    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutItemArtistSquareBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_item_artist_square, parent, false);
        return new ArtistViewHolder(binding, this);
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {

        Artist perform = artists.get(position);
        holder.bind(perform, picasso);
    }

    @Override
    public void onItemClick(Artist artist) {

        if (listener != null)
            listener.onItemClick(artist);
    }
}
