package kr.co.triggers.yolo.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.List;

import kr.co.triggers.yolo.R;
import kr.co.triggers.yolo.component.activity.YoloView;
import kr.co.triggers.yolo.databinding.LayoutItemNotificationBinding;
import kr.co.triggers.yolo.domain.Notification;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;

/**
 * Created by kyungjoo on 2016. 9. 28..
 */

class NotifyViewHolder extends RecyclerView.ViewHolder{
    private final OnItemClickListener<LayoutItemNotificationBinding> listener;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    LayoutItemNotificationBinding binding;

    public NotifyViewHolder(final LayoutItemNotificationBinding binding, final OnItemClickListener<LayoutItemNotificationBinding> listener) {

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

    public void bind(Notification notification) {
        binding.setNotify(notification);
        binding.notifyDate.setText(sdf.format(notification.getCreatedAt()));
    }
}

public class NotificationAdapter extends RecyclerView.Adapter<NotifyViewHolder> implements OnItemClickListener<LayoutItemNotificationBinding>{
    private List<Notification> notifications;
    private OnItemClickListener<Notification> listener;
    LayoutItemNotificationBinding binding;

    public OnItemClickListener<Notification> getListener() {
        return listener;
    }

    public void setOnItemClickListener(OnItemClickListener<Notification> listener) {
        this.listener = listener;
    }

    public List<Notification> getNotifications() { return notifications; }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
        this.notifyDataSetChanged();
    }

    private YoloView view;

    public NotificationAdapter(YoloView view) {
        this.view = view;
        ActivityComponent component = DaggerActivityComponent.builder().appComponent(view.getAppComponent()).activityModule(new ActivityModule(view)).build();
        component.inject(this);
    }

    @Override
    public NotifyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_item_notification, parent, false);
        return new NotifyViewHolder(binding, this);
    }

    @Override
    public void onBindViewHolder(NotifyViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.bind(notification);
    }

    @Override
    public int getItemCount() {
        return notifications != null ? notifications.size() : 0;
    }

    @Override
    public void onItemClick(LayoutItemNotificationBinding binding) {
        if (listener != null)
            listener.onItemClick(binding.getNotify());
    }

    public void addAll(List<Notification> item) {
        for (Notification notification : item) {
            if (!notifications.contains(notification))
                notifications.add(notification);
        }
        notifyDataSetChanged();
    }
}
