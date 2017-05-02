package kr.co.triggers.yolo.layer.entry.fragment.notification;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import kr.co.triggers.yolo.R;
import kr.co.triggers.yolo.adapter.NotificationAdapter;
import kr.co.triggers.yolo.adapter.OnItemClickListener;
import kr.co.triggers.yolo.component.activity.YoloFragment;
import kr.co.triggers.yolo.component.activity.YoloView;
import kr.co.triggers.yolo.databinding.LayoutFragmentNotificationBinding;
import kr.co.triggers.yolo.decoration.DividerItemDecoration;
import kr.co.triggers.yolo.domain.Notification;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;
import kr.co.triggers.yolo.util.Constraints;

public class NotificationView extends YoloFragment implements OnItemClickListener<Notification> {

    class RecyclerScroller extends RecyclerView.OnScrollListener {

        private boolean loading = false;

        boolean isLoading() { return loading; }
        synchronized void setLoading(boolean loading) { this.loading = loading; }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (dy <= 0) {
                return;
            }

            if (isLoading()){
                return;
            }

            LinearLayoutManager layoutManager = (LinearLayoutManager) binding.listViewNotify.getLayoutManager();

            int visible = layoutManager.getChildCount();
            int total = layoutManager.getItemCount();
            int last = layoutManager.findFirstVisibleItemPosition();

            if (total <= visible + last) {
                setLoading(true);

                long id = notificationAdapter.getItemId(notificationAdapter.getItemCount() - 1);
                presenter.fetch(id, Constraints.FETCH_COUNT);
            }
        }
    }

    LayoutFragmentNotificationBinding binding;
    RecyclerScroller scroller;

    @Inject
    NotificationPresenter presenter;

    @Inject
    NotificationAdapter notificationAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        YoloView view = (YoloView) getActivity();
        ActivityComponent component = DaggerActivityComponent.builder().appComponent(view.getAppComponent()).activityModule(new ActivityModule(this)).build();
        component.inject(this);

        binding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_notification, container, false);
        scroller = new RecyclerScroller();

        view.setSupportActionBar(binding.toolbarNotify);

        final ActionBar toolbar = view.getSupportActionBar();

        if (toolbar != null) {/*
            toolbar.setHomeButtonEnabled(true);
            toolbar.setDisplayHomeAsUpEnabled(true);*/
        }

        binding.listViewNotify.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.listViewNotify.setAdapter(notificationAdapter);
        //binding.listViewNotify.addItemDecoration(new DividerItemDecoration(getContext(), R.id.notifyLayout));
        binding.listViewNotify.addItemDecoration(new DividerItemDecoration(getContext(), R.id.notifyLayout));
        binding.listViewNotify.addOnScrollListener(scroller);
        notificationAdapter.setOnItemClickListener(this);

        presenter.fetch();

        return binding.getRoot();
    }

    @Override
    public void onItemClick(Notification notification) {
        String type = notification.getType();

        presenter.read(notification.getId());

        if(type.equals(Notification.TYPE_EVENT_ARTIST_SUGGEST)){
            Toast.makeText(getContext(), "move to artist page", Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(getContext(), ArtistView.class);
            //startActivity(intent);
            return;
        }
        if(type.equals(Notification.TYPE_EVENT_FIESTA_SUGGEST) || type.equals(Notification.TYPE_EVENT_FIESTA_CHANGES)){
            Toast.makeText(getContext(), "move to fiesta page", Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(getContext(), FiestaView.class);
            //startActivity(intent);
            return;
        }
        if(type.equals(Notification.TYPE_EVENT_TRACK_SUGGEST)){
            Toast.makeText(getContext(), "move to track page", Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(getContext(), TrackView.class);
            //startActivity(intent);
            return;
        }
    }

    public void setNotification(List<Notification> notifications){
        scroller.setLoading(false);
        notificationAdapter.setNotifications(notifications);
    }

    public void attachNotification(List<Notification> notifications) {
        scroller.setLoading(false);
        notificationAdapter.addAll(notifications);
    }

    public void readNotification(long id){
        Toast.makeText(getContext(), "click notification id : " + id, Toast.LENGTH_SHORT).show();
    }
}
