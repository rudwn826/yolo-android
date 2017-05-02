package kr.co.triggers.yolo.layer.entry.fragment.menu;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import kr.co.triggers.yolo.R;
import kr.co.triggers.yolo.component.activity.YoloFragment;
import kr.co.triggers.yolo.databinding.LayoutFragmentMenuBinding;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;
import kr.co.triggers.yolo.layer.entry.EntryView;
import kr.co.triggers.yolo.util.Constraints;

public class MenuView extends YoloFragment {

    public static final String MODE_FIESTA = "menu.fiesta";
    public static final String MODE_ARTIST = "menu.artist";
    public static final String MODE_NOTIFICATION = "menu.notification";
    public static final String MODE_PROFILE = "menu.profile";

    LayoutFragmentMenuBinding binding;

    @Inject
    Picasso picasso;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_menu, container, false);
        binding.setView(this);

        ActivityComponent component = DaggerActivityComponent.builder().appComponent(getAppComponent()).activityModule(new ActivityModule(this)).build();
        component.inject(this);

        final View rootView = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        binding.blurView.setupWith(rootView).blurRadius(Constraints.BLUR_EFFECT_RADIUS);

        return binding.getRoot();
    }

    public void pop() {

        getFragmentManager().popBackStack();
    }

    public void show(String mode) {

        pop();
        ((EntryView) getActivity()).activate(mode);
    }
}
