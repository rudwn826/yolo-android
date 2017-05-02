package kr.co.triggers.yolo.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kr.co.triggers.yolo.R;
import kr.co.triggers.yolo.component.activity.YoloView;
import kr.co.triggers.yolo.databinding.LayoutItemFiestaThumbnailBinding;
import kr.co.triggers.yolo.domain.Fiesta;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;


class FiestaViewHolder extends RecyclerView.ViewHolder {

    LayoutItemFiestaThumbnailBinding binding;

    public FiestaViewHolder(final LayoutItemFiestaThumbnailBinding binding, final OnItemClickListener<Fiesta> listener) {

        super(binding.getRoot());

        this.binding = binding;

        binding.getRoot().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (listener != null)
                    listener.onItemClick(binding.getFiesta());
            }
        });
    }

    public void bind(Fiesta fiesta) {

        binding.setFiesta(fiesta);
    }
}

public class FiestaAdapter extends RecyclerView.Adapter<FiestaViewHolder> implements OnItemClickListener<Fiesta> {

    private OnItemClickListener<Fiesta> listener;
    public void setOnItemClickListener(OnItemClickListener<Fiesta> listener) { this.listener = listener; }

    private List<Fiesta> fiesta;
    public List<Fiesta> getFiesta() { return fiesta; }

    public void setFiesta(List<Fiesta> fiesta) {

        this.fiesta = fiesta;
        this.notifyDataSetChanged();
    }


    public void addAll(List<Fiesta> fiesta) {
        this.fiesta.addAll(fiesta);
        notifyDataSetChanged();
    }

    public FiestaAdapter(YoloView view) {

        ActivityComponent component = DaggerActivityComponent.builder().appComponent(view.getAppComponent()).activityModule(new ActivityModule(view)).build();
        component.inject(this);
    }

    @Override
    public FiestaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutItemFiestaThumbnailBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_item_fiesta_thumbnail, parent, false);
        return new FiestaViewHolder(binding, this);
    }

    @Override
    public void onBindViewHolder(FiestaViewHolder holder, int position) {

        Fiesta fiesta = this.fiesta.get(position);
        holder.bind(fiesta);
    }

    @Override
    public int getItemCount() {
        return fiesta != null ? fiesta.size() : 0;
    }

    @Override
    public void onItemClick(Fiesta fiesta) {

        if (listener != null)
            listener.onItemClick(fiesta);
    }
}
