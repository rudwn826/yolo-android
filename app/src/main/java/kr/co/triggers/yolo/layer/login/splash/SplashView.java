package kr.co.triggers.yolo.layer.login.splash;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.co.triggers.yolo.R;
import kr.co.triggers.yolo.component.activity.YoloFragment;
import kr.co.triggers.yolo.databinding.LayoutFragmentSplashBinding;

public class SplashView extends YoloFragment {

    LayoutFragmentSplashBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_splash, container, false);
        return binding.getRoot();
    }
}
